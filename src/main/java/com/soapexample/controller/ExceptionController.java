package com.soapexample.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.xml.soap.SOAPException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.ConnectException;

/**
 * Created by Ivan on 04.02.2018.
 *
 * Controller which handle thrown exception which were not caught.
 *
 * @version 1.1.3
 */
@ControllerAdvice(basePackages = "com.soapexample")
public class ExceptionController {
    private final Logger LOGGER = LoggerFactory.getLogger(ExceptionController.class);
    private static final String ERROR_VIEW_NAME = "/error";

    @ExceptionHandler(FileNotFoundException.class)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ModelAndView handleFileNotFound(final Exception exception) {
        LOGGER.error(exception.getMessage());

        return getModelAndView(exception.getMessage());
    }

    @ExceptionHandler(IOException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ModelAndView handleFileTooBig(final Exception exception) {
        LOGGER.error(exception.getMessage());

        return getModelAndView("Requested file is too big or can't be read.");
    }

    @ExceptionHandler({ConnectException.class, SOAPException.class})
    @ResponseStatus(HttpStatus.BAD_GATEWAY)
    public ModelAndView handleConnectionError(final Exception exception) {
        LOGGER.error("Unable to connect ro server.");

        return getModelAndView("Can't establish a connection to server.\n Probably server is down now.");
    }

    private ModelAndView getModelAndView(String message) {
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName("/error");
        modelAndView.addObject("exceptionMessage", message);

        return modelAndView;
    }
}
