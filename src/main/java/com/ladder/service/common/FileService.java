package com.ladder.service.common;

import com.ladder.config.NasInfoProperties;
import lombok.RequiredArgsConstructor;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.Random;

@Component
@RequiredArgsConstructor
public class FileService {

    private final NasInfoProperties nasInfoProperties;

    public int transferFile(MultipartFile file){
        try{
            if(file.isEmpty()) return 0; // 비어있을 경우
            Random random = new Random();
            String fileName = file.getOriginalFilename();
            fileName = fileName.substring(0, fileName.lastIndexOf(".")) + "_" + random.nextInt(1000) + fileName.substring(fileName.lastIndexOf("."));

            FTPClient ftpClient = new FTPClient();
            ftpClient.connect(nasInfoProperties.getUrl(), nasInfoProperties.getPort());
            ftpClient.login(nasInfoProperties.getId(), nasInfoProperties.getPassword());
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

            if (!ftpClient.isConnected() || !ftpClient.isAvailable()) {
                System.out.println("FTP 서버에 연결할 수 없습니다.");
                return 0;
            }

            try {
                boolean done = ftpClient.storeFile("/HDD1/ladder/book_img/" + fileName, file.getInputStream());
                if(done) System.out.println("success");
                else {
                    int replyCode = ftpClient.getReplyCode();
                    System.out.println("파일 전송 실패, 응답 코드: " + replyCode);
                    System.out.println("서버 메시지: " + ftpClient.getReplyString());
                }
            }catch (IOException e){
                e.printStackTrace();
            }finally {
                try {
                    if (ftpClient.isConnected()) {
                        ftpClient.disconnect();
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }




            return 1;
        }catch (Exception e){
            e.printStackTrace();
            return -1;
        }
    }

}
