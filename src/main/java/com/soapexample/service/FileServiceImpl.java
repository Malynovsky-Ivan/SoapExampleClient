package com.soapexample.service;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class FileServiceImpl implements FileService {
    private final Logger LGR = LoggerFactory.getLogger(FileServiceImpl.class);

    @Override
    public File saveFile(MultipartFile multipartFile) {
        String path = System.getProperty("catalina.home") + File.separator + multipartFile.getOriginalFilename();
        File file = new File(path);

        try {
            // Can't uncomment it jet. Will get a FileNotFoudException
            //FileUtils.cleanDirectory(new File(System.getProperty("catalina.home") + "/"));
            multipartFile.transferTo(file);
            LGR.info("File saved in: {}", path);
        } catch (IOException e) {
            LGR.error(e.getMessage());
        }

        return file;
    }
}
