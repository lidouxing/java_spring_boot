package com.ldx.javaSpringBoot.modules.test.service.impl;

import com.github.pagehelper.PageInfo;
import com.ldx.javaSpringBoot.aspect.ServiceAnnotation;
import com.ldx.javaSpringBoot.modules.common.vo.Result;
import com.ldx.javaSpringBoot.modules.common.vo.SearchVo;
import com.ldx.javaSpringBoot.modules.test.dao.CityDao;
import com.ldx.javaSpringBoot.modules.test.entity.City;
import com.ldx.javaSpringBoot.modules.test.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CityServiceImpl implements CityService {

    @Autowired
    private CityDao cityDao;

    @Override
    @ServiceAnnotation(value="bbb")
    public List<City> getCitiesByCountryId(int countryId) {
        return cityDao.getCitiesByCountryId(countryId);
    }
    @Override
    public PageInfo<City> getCitiesBySearchVo(int countryId, SearchVo searchVo) {
        return new PageInfo<City>(Optional.ofNullable(cityDao.getCitiesByCountryId(countryId)).orElse(Collections.emptyList()));
    }
    @Override
    public PageInfo<City> getCitiesBySearchVo(SearchVo searchVo) {
        return new PageInfo<City>(Optional.ofNullable(cityDao.getCitiesBySearchVo(searchVo)).orElse(Collections.emptyList()));
    }

    @Override
    public Result<City> addCity(City city) {
        city.setDateCreated(new Date());
        cityDao.addCity(city);
        return new Result<City>(Result.ResultStatus.SUCCESS.status,"add success",city);
    }

    @Override
    public Result<City> updateCity(City city) {
        cityDao.updateCity(city);
        return new Result<City>(Result.ResultStatus.SUCCESS.status,"update success",city);
    }

    @Override
    public Result<City> deleteCity(City city) {
        cityDao.deleteCity(city);
        return new Result<City>(Result.ResultStatus.SUCCESS.status,"delete success",city);
    }
}
