package com.ldx.javaSpringBoot.modules.test.service.impl;

import com.github.pagehelper.PageInfo;
import com.ldx.javaSpringBoot.modules.common.vo.SearchVo;
import com.ldx.javaSpringBoot.modules.test.dao.CityDao;
import com.ldx.javaSpringBoot.modules.test.entity.City;
import com.ldx.javaSpringBoot.modules.test.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class CityServiceImpl implements CityService {

    @Autowired
    private CityDao cityDao;

    @Override
    public List<City> getCitiesByCountryId(int countryId) {
        return cityDao.getCitiesByCountryId(countryId);
    }
    @Override
    public PageInfo<City> getCitiesBySearchVo(int countryId, SearchVo searchVo) {
        return new PageInfo<City>(Optional.ofNullable(cityDao.getCitiesByCountryId(countryId)).orElse(Collections.emptyList()));
    }
}
