package com.ldx.javaSpringBoot.modules.test.controller;

import com.github.pagehelper.PageInfo;
import com.ldx.javaSpringBoot.modules.common.vo.SearchVo;
import com.ldx.javaSpringBoot.modules.test.entity.City;
import com.ldx.javaSpringBoot.modules.test.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@RequestMapping("/api")
public class CityController {

    @Autowired
    private CityService cityService;
    //实现查询不分页
    @GetMapping("/cities/{countryId}")
    public List<City> getCitiesByCountryId(@PathVariable int countryId){
        return cityService.getCitiesByCountryId(countryId);
    }

    //分页查询
    @PostMapping(value = "/cities/{countryId}", consumes = "application/json")
    public PageInfo<City> getCitiesBySearchVo(@PathVariable int countryId, @RequestBody SearchVo searchVo){
        return cityService.getCitiesBySearchVo(countryId,searchVo);
    }

}
