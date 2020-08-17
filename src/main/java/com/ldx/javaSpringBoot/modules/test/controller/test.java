package com.ldx.javaSpringBoot.modules.test.controller;


import com.ldx.javaSpringBoot.modules.test.entity.City;
import com.ldx.javaSpringBoot.modules.test.entity.Country;
import com.ldx.javaSpringBoot.modules.test.service.CityService;
import com.ldx.javaSpringBoot.modules.test.service.CountryService;
import com.ldx.javaSpringBoot.modules.test.vo.ApplicatonTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/test")
public class test {

    private final static Logger LOGGER = LoggerFactory.getLogger(test.class);
    @Value("${com.name}")
    private String name;
    @Value("${com.age}")
    private int age;
    @Value("${com.desc}")
    private String desc;
    @Value("${com.random}")
    private String random;

    @Autowired
    private ApplicatonTest applicatonTest;
    @Autowired
    private CityService cityService;
    @Autowired
    private CountryService countryService;

    //返回到主页面
    @GetMapping("/index")
    public String index(){
        return "index";
    }

    //返回到碎片化主页面
    @GetMapping("/index2")
    public String index2(ModelMap modelMap){
        modelMap.addAttribute("template", "test/index2");
        return "index2";
    }
    
    @GetMapping("/testindex1")
    public String testindex(ModelMap modelMap){
        modelMap.addAttribute("thymeleafTitle","ThymeleafText");
        modelMap.addAttribute("template", "test/index");
        return "index2";
    }

    @GetMapping("/testindex2")
    public String testindex2(ModelMap modelMap) {
        int countryId = 522;
        List<City> cities = cityService.getCitiesByCountryId(countryId);
        cities = cities.stream().limit(10).collect(Collectors.toList());
        Country country = countryService.getCountryByCountryId(countryId);
        modelMap.addAttribute("thymeleafTitle", "666666");
        modelMap.addAttribute("checked", true);
        modelMap.addAttribute("currentNumber", 99);
        modelMap.addAttribute("changeType", "checkbox");
        modelMap.addAttribute("baiduUrl", "/test/log");
        modelMap.addAttribute("city", cities.get(0));
        modelMap.addAttribute("country", country);
        modelMap.addAttribute("cities", cities);
        modelMap.addAttribute("updateCityUri", "/api/city");
        modelMap.addAttribute("template", "test/index");
        return "index";
    }
    /**
     * 127.0.0.1:8085/test/logTest ---- get
     */
    @GetMapping("/logTest")
    @ResponseBody
    public String logTest() {
        LOGGER.trace("This is trace log");
        LOGGER.debug("This is debug log");
        LOGGER.info("This is info log");
        LOGGER.warn("This is warn log");
        LOGGER.error("This is error log");
        return "This is log test";
    }

    /**
     * 127.0.0.1:8085/test/config ---- get
     */
    @GetMapping("/config")
    @ResponseBody
    public String configTest() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(name).append("----")
                .append(age).append("----")
                .append(desc).append("----")
                .append(random).append("----").append("<br>");
        stringBuffer.append(applicatonTest.getPort()).append("----")
                .append(applicatonTest.getName()).append("----")
                .append(applicatonTest.getAge()).append("----")
                .append(applicatonTest.getDesc()).append("----")
                .append(applicatonTest.getRandom()).append("----").append("<br>");

        return stringBuffer.toString();

    }

    /**
     * 127.0.0.1:8080/test/testDesc ---- get
     */
    @GetMapping("/testDesc")
    @ResponseBody
    public String testDesc() {
        return "This is test module desc.";
    }
}
