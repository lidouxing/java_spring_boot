package com.ldx.javaSpringBoot.modules.test.service;

import com.github.pagehelper.PageInfo;
import com.ldx.javaSpringBoot.modules.common.vo.SearchVo;
import com.ldx.javaSpringBoot.modules.test.entity.City;

import java.util.List;

public interface CityService {
    List<City> getCitiesByCountryId(int countryId);

    PageInfo<City> getCitiesBySearchVo(int countryId, SearchVo searchVo);
}
