package com.bester.platform.platformchain.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liuwen
 */
@RestController
public class IndexController {

    @GetMapping("/index")
    public String test(){
        return "hello world";
    }
}
