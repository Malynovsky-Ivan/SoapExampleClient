package com.soapexample.service;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * The implementation of {@link FileService}
 *
 * @author Igor Faryna
 */
@Service
public class FileServiceImpl implements FileService {
    private final Logger LOGGER = LoggerFactory.getLogger(FileServiceImpl.class);

    /**
     * @see FileService#saveFile(MultipartFile)
     */
    @Override
    public File saveFile(MultipartFile multipartFile) {
        String path = System.getProperty("catalina.home") + File.separator + multipartFile.getOriginalFilename();
        File file = new File(path);

        try {
            file.mkdirs();
            FileUtils.cleanDirectory(new File(System.getProperty("catalina.home") + "/"));
            multipartFile.transferTo(file);
            LOGGER.info("File saved in: {}", path);
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }

        return file;
    }

    /**
     * Converts selected file of type MultipartFile into type File
     *
     * @see FileService#multipartFileToFile(MultipartFile)
     *
     * @param multipartFile MultipartFile value representing the selected file
     * @return File which we get after the conversion
     */
    @Override
    public File multipartFileToFile(MultipartFile multipartFile) {
        File file = new File(multipartFile.getOriginalFilename());
        try (FileOutputStream fos = new FileOutputStream(file)){
            file.createNewFile();
            fos.write(multipartFile.getBytes());
        }catch(IOException e) {
            LOGGER.error(e.getMessage());
        }
        return file;
    }
}
