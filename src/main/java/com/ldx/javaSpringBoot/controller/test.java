package com.ldx.javaSpringBoot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/test")
public class test {

    @RequestMapping("controller")
    @ResponseBody
    public String test(){
        return "this is first boot";
    }
}
