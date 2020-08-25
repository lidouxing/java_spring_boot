package com.ldx.javaSpringBoot.modules.account.controller;

import com.ldx.javaSpringBoot.modules.account.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private UserService userService;

    /**
     * 127.0.0.1/account/login ---- get
     */
    @GetMapping("/login")
    public String loginPage() {
        return "indexSimple";
    }

    /**
     * 127.0.0.1/account/register ---- get
     */
    @GetMapping("/register")
    public String registerPage() {
        return "indexSimple";
    }

    /**
     * 127.0.0.1/account/users ---- get
     */
    @GetMapping("/users")
    public String usersPage() {
        return "index";
    }

    /**
     * 127.0.0.1/account/roles ---- get
     */
    @GetMapping("/roles")
    public String rolesPage() {
        return "index";
    }

    /**
     * 127.0.0.1/account/resources ---- get
     */
    @GetMapping("/resources")
    public String resourcesPage() {
        return "index";
    }

    /**
     * 127.0.0.1/account/profile ---- get
     */
    @GetMapping("/profile")
    public String profilePage() {
        return "index";
    }

    /**
     * 127.0.0.1/account/registerVue ---- get
     */
    @GetMapping("/registerVue")
    public String registerVuePage() {
        return "indexSimple";
    }

    /**
     * 127.0.0.1/account/logout ---- get
     */

    @GetMapping("/logout")
    public String logout(ModelMap modelMap) {
        userService.logout();
        modelMap.addAttribute("template", "account/login");
        return "indexSimple";
    }
}
