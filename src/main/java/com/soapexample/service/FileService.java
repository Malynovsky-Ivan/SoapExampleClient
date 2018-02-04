package com.soapexample.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;

/**
 * Provides the possibility to conversion and save selected files.
 *
 * @author Igor Faryna
 */
public interface FileService {

    /**
     * Converts data of type MultipartFile into type File and saves that file
     * in folder.
     *
     * @param multipartFile MultipartFile value representing the selected file
     * @return File which are saved
     */
    @Deprecated
    File saveFile(MultipartFile multipartFile);

    /**
     * Converts data of type MultipartFile into type File
     *
     * @param multipartFile MultipartFile value representing the selected file
     * @return File which we get after the conversion
     */
    File multipartFileToFile (MultipartFile multipartFile);
}
