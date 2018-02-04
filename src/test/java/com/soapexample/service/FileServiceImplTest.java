package com.soapexample.service;

import junitx.framework.FileAssert;
import org.junit.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileServiceImplTest {

    FileService fileService = new FileServiceImpl();


    @Test
    public void multipartFileToFileTest() {
        File file = fileService.multipartFileToFile(createMultipartFile());
        File testFile = new File("src/test/resources/file.txt");
        FileAssert.assertEquals(file, testFile);
    }

    public MultipartFile createMultipartFile() {
        Path path = Paths.get("src/test/resources/file.txt");
        String name = "file.txt";
        String originalFileName = "file.txt";
        String contentType = "text/plain";
        byte[] content = null;
        try {
            content = Files.readAllBytes(path);
        } catch (final IOException e) {
        }
        MultipartFile multipartFile = new MockMultipartFile(name,
                originalFileName, contentType, content);
        return multipartFile;
    }
}
