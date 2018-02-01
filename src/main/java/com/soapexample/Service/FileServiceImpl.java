package com.soapexample.Service;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class FileServiceImpl implements FileService{

    public void saveFile(MultipartFile multipartFile) {
        String path = System.getProperty("catalina.home") + "/resources/"
                + multipartFile.getOriginalFilename();

        File file = new File(path);
        try {
            file.mkdirs();
            try {
                FileUtils.cleanDirectory(new File(System
                        .getProperty("catalina.home")
                        + "/resources/"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            multipartFile.transferTo(file);
        } catch (IOException e) {
            System.out.println("error with file");
        }
    }
}
