package com.soapexample.controller;

import com.soapexample.consumer.DocumentsClient;
import com.soapexample.consumer.VideoFileClient;
import com.soapexample.generated.GetFileRequest;
import com.soapexample.service.FileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
public class HomeController {

    private final Logger LGR = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    VideoFileClient videoFileClient;
    @Autowired
    DocumentsClient documentsClient;
    @Autowired
    FileService fileService;

    @RequestMapping(value = {"/", "/home"})
    public String home(Model model) {
        model.addAttribute("files", videoFileClient.getExistFilesNames());
        return "home";
    }

    @RequestMapping(value = "/getFile", method = RequestMethod.POST)
    public String getFile(@RequestParam String fileName) throws IOException {
        LGR.info("Request to get file: {}", fileName);
        System.out.println(fileName);
        videoFileClient.getVideoFile(fileName);
        return "redirect:home";
    }

    @RequestMapping(value = "/searchWord", method = RequestMethod.POST)
    public String searchWord(@RequestParam("file") MultipartFile file, @RequestParam String searchWord) {
        String filePath = fileService.saveFile(file);
        documentsClient.storeDocument(searchWord, filePath);
        return "redirect:home";
    }
}
