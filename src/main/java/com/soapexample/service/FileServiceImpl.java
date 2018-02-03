package com.soapexample.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class FileServiceImpl implements FileService {
    private final Logger LOGGER = LoggerFactory.getLogger(FileServiceImpl.class);

    @Override
    public File saveFile(MultipartFile multipartFile) {
        String path = System.getProperty("catalina.home") + File.separator + multipartFile.getOriginalFilename();
        File file = new File(path);

        try {
            // Can't uncomment it jet. Will get a FileNotFoudException
            //FileUtils.cleanDirectory(new File(System.getProperty("catalina.home") + "/"));
            multipartFile.transferTo(file);
            LOGGER.info("File saved in: {}", path);
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }

        return file;
    }
}
