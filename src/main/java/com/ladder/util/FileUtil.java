package com.ladder.util;

import com.ladder.config.NasInfoProperties;
import com.ladder.vo.file.FileResultVo;
import lombok.RequiredArgsConstructor;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Random;

@Component
@RequiredArgsConstructor
public class FileUtil {

    private final NasInfoProperties nasInfoProperties;

    public FileResultVo transferFile(MultipartFile file){
        FileResultVo fileResultVo;
        FTPClient ftpClient = new FTPClient();
        try{
            if(file.isEmpty()) fileResultVo = FileResultVo.of(0, "파일 없음", "");
            Random random = new Random();
            String fileName = file.getOriginalFilename();
            fileName = fileName.substring(0, fileName.lastIndexOf(".")) + "_" + random.nextInt(1000) + fileName.substring(fileName.lastIndexOf("."));

            ftpClient.connect(nasInfoProperties.getUrl(), nasInfoProperties.getPort());
            ftpClient.login(nasInfoProperties.getId(), nasInfoProperties.getPassword());
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

            if (!ftpClient.isConnected() || !ftpClient.isAvailable()) {
                fileResultVo = FileResultVo.of(0, "FTP 서버에 연결할 수 없습니다.", "");
            }


            String remotePath = "/HDD1/ladder/book_img/" + fileName;
            boolean done = ftpClient.storeFile(remotePath, file.getInputStream());
            if(done) fileResultVo = FileResultVo.of(1, "파일 전송 성공", remotePath);
            else {
                int replyCode = ftpClient.getReplyCode();
                fileResultVo = FileResultVo.of(-1, "파일 전송 실패, 응답코드 : " + replyCode + " 서버 메시지 : " + ftpClient.getReplyString(), "");
            }
        }catch (Exception e){
            fileResultVo = FileResultVo.of(-1, "Exception : " + e.getMessage(), "");
        }finally {
            try {
                if (ftpClient.isConnected()) {
                    ftpClient.disconnect();
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        return fileResultVo;
    }

}
