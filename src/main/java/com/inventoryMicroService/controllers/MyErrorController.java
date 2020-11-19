package com.inventoryMicroService.controllers;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MyErrorController implements ErrorController {
    @RequestMapping("/error")
    public String errorHandle() {
        return "Something went wrong.";
    }

    @Override
    public String getErrorPath() {
        return null;
    }
}
