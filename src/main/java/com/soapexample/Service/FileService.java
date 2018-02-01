package com.soapexample.Service;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {

    void saveFile(MultipartFile multipartFile);
}
