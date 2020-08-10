package com.ldx.javaSpringBoot.controller;


import com.ldx.javaSpringBoot.vo.ApplicatonTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Description TestController
 * @Author HymanHu
 * @Date 2020/8/10 10:39
 */
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
