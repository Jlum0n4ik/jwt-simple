package com.example.jwtrepeat2.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {
    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public String demo() {
        return "Public source";
    }
    @GetMapping("/admin")
    @ResponseStatus(HttpStatus.OK)
    public String admin() {
        return "Admin endPoint";
    }
}
