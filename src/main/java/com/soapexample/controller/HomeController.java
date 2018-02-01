package com.soapexample.controller;

import com.soapexample.Service.FileService;
import com.soapexample.consumer.VideoFileClient;
import com.soapexample.generated.GetFileRequest;
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
import java.security.Principal;

@Controller
public class HomeController {

    private final Logger LGR = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    VideoFileClient videoFileClient;

    @Autowired
    FileService fileService;

    @RequestMapping(value = { "/", "/home" })
    public String home(Model model){
        model.addAttribute( "files", videoFileClient.getExistFilesNames());
        model.addAttribute("fileName", new GetFileRequest());
        return "home";
    }

    @RequestMapping(value = "/getFile", method = RequestMethod.POST)
    public String getFile(@ModelAttribute("fileName") GetFileRequest fileRequest) throws IOException{
        LGR.info("Request to get file: {}", fileRequest.getFileName());
        videoFileClient.getVideoFile(fileRequest);
        return "redirect:home";
    }
    @RequestMapping(value = "/searchWord", method = RequestMethod.POST)
    public String searchWord(@RequestParam MultipartFile file){
        fileService.saveFile(file);
        return "home";
    }
}
