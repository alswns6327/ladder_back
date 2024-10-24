package com.ladder.util;

import com.ladder.config.NasInfoProperties;
import com.ladder.vo.file.FileReadResultVo;
import com.ladder.vo.file.FileUploadResultVo;
import lombok.RequiredArgsConstructor;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Random;

@Component
@RequiredArgsConstructor
public class FileUtil {

    private final NasInfoProperties nasInfoProperties;

    public FTPClient connectFTPClient() throws IOException {
        FTPClient ftpClient = new FTPClient();
        ftpClient.connect(nasInfoProperties.getUrl(), nasInfoProperties.getPort());
        ftpClient.login(nasInfoProperties.getId(), nasInfoProperties.getPassword());
        ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
        ftpClient.setControlEncoding("UTF-8");
        ftpClient.setSoTimeout(3000);
        return ftpClient;
    }

    public FileUploadResultVo transferFile(MultipartFile file){
        FileUploadResultVo fileUploadResultVo;
        FTPClient ftpClient = new FTPClient();
        try{
            if(file.isEmpty()) return FileUploadResultVo.of(0, "파일 없음", "");
            Random random = new Random();
            String fileName = file.getOriginalFilename();
            fileName = fileName.substring(0, fileName.lastIndexOf(".")) + "_" + random.nextInt(1000) + fileName.substring(fileName.lastIndexOf("."));

            ftpClient = connectFTPClient();

            if (!ftpClient.isConnected() || !ftpClient.isAvailable()) {
                return FileUploadResultVo.of(0, "FTP 서버에 연결할 수 없습니다.", "");
            }

            String remotePath = "/HDD1/ladder/book_img/" + fileName;
            boolean done = ftpClient.storeFile(remotePath, file.getInputStream());
            if(done) fileUploadResultVo = FileUploadResultVo.of(1, "파일 전송 성공", remotePath);
            else {
                int replyCode = ftpClient.getReplyCode();
                fileUploadResultVo = FileUploadResultVo.of(-1, "파일 전송 실패, 응답코드 : " + replyCode + " 서버 메시지 : " + ftpClient.getReplyString(), "");
            }
        }catch (Exception e){
            fileUploadResultVo = FileUploadResultVo.of(-1, "Exception : " + e.getMessage(), "");
        }finally {
            try {
                if (ftpClient.isConnected()) {
                    ftpClient.logout();
                    ftpClient.disconnect();
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        return fileUploadResultVo;
    }

    public String readImgFile(FTPClient ftpClient, String imgPath){
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try{
            if(imgPath == null) return null;

            boolean success = ftpClient.retrieveFile(imgPath, outputStream);
            if(!success) return null;

            byte[] imageBytes = outputStream.toByteArray();
            return Base64.getEncoder().encodeToString(imageBytes);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public FileReadResultVo readImgFile(String imgPath){
        FileReadResultVo fileReadResultVo;
        FTPClient ftpClient = new FTPClient();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        try{
            if(imgPath == null) return FileReadResultVo.of(0, "파일이 없습니다.", null);

            ftpClient = connectFTPClient();
            if (!ftpClient.isConnected() || !ftpClient.isAvailable()) {
                return FileReadResultVo.of(0, "FTP 서버에 연결할 수 없습니다.", null);
            }


            boolean success = ftpClient.retrieveFile(imgPath, outputStream);
            if(!success) {
                fileReadResultVo = FileReadResultVo.of(-1, "파일을 읽지 못하였습니다. code : " + ftpClient.getReplyCode() + " msg : " + ftpClient.getReplyString(), null);
            }
            else{
                byte[] imageBytes = outputStream.toByteArray();
                String imageBase64 = Base64.getEncoder().encodeToString(imageBytes);
                fileReadResultVo = FileReadResultVo.of(1, "파일 읽기 성공", imageBase64);
            }
        }catch (Exception e){
            fileReadResultVo = FileReadResultVo.of(-1, "Exception : " + e.getMessage(), null);
        }finally {
            try {
                if (ftpClient.isConnected()) {
                    ftpClient.logout();
                    ftpClient.disconnect();
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        return fileReadResultVo;
    }
}
