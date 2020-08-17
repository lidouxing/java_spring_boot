package com.ldx.javaSpringBoot.modules.test.service;

import com.ldx.javaSpringBoot.modules.test.entity.Country;

public interface CountryService {

    Country getCountryByCountryId(int countryId);

    Country getCountryByCountryName(String countryName);

    Country mograteCountryByRedis(int countryId);
}
