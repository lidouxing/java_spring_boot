package com.ldx.javaSpringBoot.modules.mine.controller;

import com.ldx.javaSpringBoot.modules.mine.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/mine")
public class LoginController {

    @Autowired
    private LoginService loginService;
    @RequestMapping("")
    public String loginPerson(){

        return null;
    }
}
