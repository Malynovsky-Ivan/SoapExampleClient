package com.soapexample.controller;

import com.soapexample.client.DocumentsClient;
import com.soapexample.client.VideoFileClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import static com.soapexample.ProjectContants.DEFAULT_COUNT_RESULT;

/**
 * Main controller. Processes all requests.  Each method returns home.jsp view.
 *
 * @author Igor Faryna
 */
@Controller
public class HomeController {

    private final Logger LOGGER = LoggerFactory.getLogger(HomeController.class);
    private String fileName = "";
    private int countMatches = DEFAULT_COUNT_RESULT;

    @Autowired
    private VideoFileClient videoFileClient;

    @Autowired
    private DocumentsClient documentsClient;

    @RequestMapping(value = {"/", "/home"})
    public String home(Model model) {
        model.addAttribute("files", videoFileClient.getExistFilesNames(false));
        model.addAttribute("fileName", fileName);
        model.addAttribute("countMatches", countMatches);
        return "home";
    }

    @RequestMapping(value = "/getFile", method = RequestMethod.POST)
    public String getFile(@RequestParam String fileName) {
        LOGGER.debug("Request to get file: {}", fileName);
        videoFileClient.getVideoFile(fileName);
        this.fileName = fileName;
        return "redirect:home";
    }

    @RequestMapping(value = "/searchWord", method = RequestMethod.POST)
    public String searchWord(@RequestParam("file") MultipartFile file, @RequestParam String searchWord) {
        this.countMatches = documentsClient.storeDocument(searchWord, file);
        return "redirect:home";
    }
}
