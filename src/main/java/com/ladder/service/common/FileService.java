package com.ladder.service.common;

import com.ladder.config.NasInfoProperties;
import lombok.RequiredArgsConstructor;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPSClient;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

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
            fileName = fileName.substring(0, fileName.lastIndexOf(".")) + "_" + random.nextInt(1000) + fileName.substring(fileName.lastIndexOf(".") + 1);
            InputStream fileContent = file.getInputStream();

            // FTP로 NAS 전송
            FTPClient ftpClient = new FTPSClient();
            System.out.println(nasInfoProperties.getUrl());
            ftpClient.connect(nasInfoProperties.getUrl(), nasInfoProperties.getPort());
            ftpClient.login(nasInfoProperties.getId(), nasInfoProperties.getPassword());
            if(true)return 1;
            if(true)return 1;
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

            String remoteFilePath = Paths.get("HDD1", "ladder", "book_img", fileName).toString();
            boolean done = ftpClient.storeFile(remoteFilePath, fileContent);

            if(done) System.out.println("success");
            else System.out.println("fail");

            return 1;
        }catch (Exception e){
            e.printStackTrace();
            return -1;
        }
    }

}
