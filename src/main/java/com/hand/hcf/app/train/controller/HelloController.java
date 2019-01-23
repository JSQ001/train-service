package com.hand.hcf.app.train.controller;


/*
* created by jsq 2019/01/23
* */

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public class HelloController {
    @GetMapping
    String hello(){
        return "hello world!";
    }
}
