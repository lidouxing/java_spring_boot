package com.ldx.javaSpringBoot.modules.test.controller;

import com.ldx.javaSpringBoot.modules.test.entity.Country;
import com.ldx.javaSpringBoot.modules.test.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;

@RestController
@RequestMapping("/api")
public class CountryController {

    @Autowired
    private CountryService countryService;
    //@PathVariable使用方式
    @RequestMapping("/country/{countryId}")
    public Country getCountryByCountryId(@PathVariable int countryId){
        return countryService.getCountryByCountryId(countryId);
    }
    
    //@RequestParam()使用方式
    @RequestMapping("/country")
    public Country getCountryByCountryName(@RequestParam String countryName){
        return countryService.getCountryByCountryName(countryName);
    }

    //redis 127.0.0.1/redis/country/522
    @GetMapping("/redis/country/{countryId}")
    public Country mograteCountryByRedis(@PathVariable int countryId){
        return countryService.mograteCountryByRedis(countryId);
    }

}
