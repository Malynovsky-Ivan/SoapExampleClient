package com.soapexample.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomeController {

    @RequestMapping(value = { "/", "/home" })
    public String home(){
        return "home";
    }

    @RequestMapping(value = "/getFile", method = RequestMethod.POST)
    public String getFile(){
        return "home";
    }

    @RequestMapping(value = "/searchWord", method = RequestMethod.POST)
    public String searchWord(){
        return "home";
    }
}
