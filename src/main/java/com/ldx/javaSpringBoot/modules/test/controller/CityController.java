package com.ldx.javaSpringBoot.modules.test.controller;

import com.github.pagehelper.PageInfo;
import com.ldx.javaSpringBoot.modules.common.vo.Result;
import com.ldx.javaSpringBoot.modules.common.vo.SearchVo;
import com.ldx.javaSpringBoot.modules.test.entity.City;
import com.ldx.javaSpringBoot.modules.test.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
/*
* 127.0.0.1/api/cities
* {"currentPage":"1","pageSize":"5","keyWord":"sha","orderBy":"city_name","sort":"desc"}
* 通过分页里面额参数进行查询
* */
    @PostMapping(value = "/cities",consumes = "application/json")
    public PageInfo<City> getCitiesBySearchVo(@RequestBody SearchVo searchVo){
        return cityService.getCitiesBySearchVo(searchVo);

    }
    /*
     * 127.0.0.1/api/city
     *
     * 增加
     * */
    @PostMapping(value = "/city",consumes = "application/json")
    public Result<City> addCity(@RequestBody City city){
        return cityService.addCity(city);
    }
    /*
     * 127.0.0.1/api/city
     *
     * 增加
     * */
    @PutMapping(value = "/city",consumes = "application/x-www-form-urlencoded")
    public Result<City> updateCity(@RequestBody City city){
        return  cityService.updateCity(city);
    }
    /*
     * 127.0.0.1/api/city
     *
     * 删除
     * */
    @DeleteMapping (value = "city",consumes = "application/json")
    public  Result<City> deleteCity(@RequestBody City city){
        return cityService.deleteCity(city);

    }
}
