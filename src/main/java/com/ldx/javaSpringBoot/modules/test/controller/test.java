package com.ldx.javaSpringBoot.modules.test.controller;


import com.ldx.javaSpringBoot.modules.test.entity.City;
import com.ldx.javaSpringBoot.modules.test.entity.Country;
import com.ldx.javaSpringBoot.modules.test.service.CityService;
import com.ldx.javaSpringBoot.modules.test.service.CountryService;
import com.ldx.javaSpringBoot.modules.test.vo.ApplicatonTest;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.MalformedURLException;
import java.nio.file.Paths;
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
//    @GetMapping("/index")
//    public String index(){
//        return "index";
//    }

    //返回到碎片化主页面
//    @GetMapping("/index2")
//    public String index2(ModelMap modelMap){
//        modelMap.addAttribute("template", "test/index2");
//        return "index2";
//    }
    
    @GetMapping("/testindex1")
    public String testindex1(ModelMap modelMap){
        modelMap.addAttribute("thymeleafTitle","ThymeleafText");
        modelMap.addAttribute("template", "test/index");
        return "indexSimple";
    }
    //127.0.0.1/test/index2  /test/index2请求路径和文件访问路径/test/index2.html一样
    @GetMapping("/index")
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
//        modelMap.addAttribute("template", "test/index");
        return "index";
    }

    @GetMapping("/indexSimple")
    public String indexSimple(){
        return "indexSimple";
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
     * 127.0.0.1/test/testDesc?paramKey=fuck ---- get
     */
    @GetMapping("/testDesc")
    @ResponseBody// @RequestParam(value = "paramKey")这个是因为后面的.getParameter("paramKey");无法取到String paramValue的值
    public String testDesc(HttpServletRequest request, @RequestParam(value = "paramKey")  String paramValue) {
        String paramValue2=request.getParameter("paramKey");
        return "This is test module desc."+paramValue+"=="+paramValue2;
    }

    /**
     * 127.0.0.1/test/index2-> /test/file  ---post
     */
    //文件单个上传
    //前端以json的有app、json，如果是form表单就是app/www，而这里由于是文件上传所以要一直
    @PostMapping(value = "/file",consumes = "multipart/form-data")//和前端一样，一般来说不要以动词来命名
   //from表单提交要使用@ModelAttribute（键值对）,查询的话使用@RequestParam，@PathVariable,而文件上传
    //RedirectAttributes进行参数传递
    /**
     * 步骤实现
     * 1 先得到文件名和路径，然后拷贝，抛出异常。对操作成功或失败进行不一样的指示。
     * 2 指定展示成功需要页面的一个重定向。
     * 3 文件拷贝会出现多种情况，成功，失败，以及文件不存在，可以先对文件是否存在进行一个验证
     */
    public String uploadFile(@RequestParam MultipartFile file, RedirectAttributes redirectAttributes){
      //判断文件是否存在
        if (file.isEmpty()){
          redirectAttributes.addFlashAttribute("message","please select file");//传到前端的message出现的字段
          return "redirect:/test/index";
      }
        //当文件上传出现问题跑出异常
       try {
           String filePath="F:\\File\\"+file.getOriginalFilename();//写文件路径以及文件名
           File file1=new File(filePath);//需要路径,用file对象
           file.transferTo(file1);//拷贝
       }catch (IOException e){
           e.printStackTrace();
           redirectAttributes.addFlashAttribute("message","upload failed");
       }
        redirectAttributes.addFlashAttribute("message","upload success");
       //重定向
        return "redirect:/test/index";
    }

    //多个文件上传
    /**
     * 步骤
     * 1 多个文件上传需要数组，页面可以上传一个以及上传多个，以此来做判断
     * 2 先对数组进行判断，如果文件是空的就跳出循环，并且不执行文件拷贝（这样emtry = true），进行下一个循环，如果文件都是空的，那么
     * Boolean判断就会指示选择文件（即没有文件上传的），如果哪怕只有一个数组中存在，就会执行到
     * 拷贝命令行，这时的emtry就成为false，这样就提示上传成功
     * */
    @PostMapping(value = "/files",consumes = "multipart/form-data")
    public String uploadFiles(@RequestParam MultipartFile[] files, RedirectAttributes redirectAttributes) {
        Boolean emtry = true;
        try {
            for (MultipartFile file : files) {
                if (file.isEmpty()) {
                    continue;//如果不存在就跳出for循环，后面的拷贝文件就不执行，直接进行下一次循环
                }
                //得到路径和文件名
                String filesPath = "F:\\File\\" + file.getOriginalFilename();
                //建一个file我、对象装入
                File file1 = new File(filesPath);
                //拷贝
                file.transferTo(file1);
                emtry = false;
            }
            if (emtry) {
                redirectAttributes.addFlashAttribute("message", "please select file");
            } else {
                redirectAttributes.addFlashAttribute("message", "upload success");
            }
        } catch (IOException e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("message", "upload failed");
        }
        return "redirect:/test/index";
    }

    //文件下载
    @GetMapping("/file")
    public ResponseEntity<Resource> downloadFile(@RequestParam String fileName){
        //Resource 一个资源接口，就是装入我们需要的信息，比如URI，File
        //将本地路径变成一个url,Paths.get()
        Resource resource= null;
        try {
            //UrlResource装的是url，所以需要将路径toUrl
            //get是Paths方法
            resource = new UrlResource(Paths.get("F:\\File\\"+fileName).toUri());//路径+文件名=url
            return ResponseEntity
                    .ok()
                    //HttpHeaders.CONTENT_TYPE已经封装了content-type
                    .header(HttpHeaders.CONTENT_TYPE, "application/octet-stream")//application/octet-stream请求头的类型
                    //CONTENT_DISPOSITION下载文件描述
                    .header(HttpHeaders.CONTENT_DISPOSITION,
                            //attachment附录存在，filename=\"%s\""下载文件的名称，resource.getFilename()把名称装在%s中
                            String.format("attachment; filename=\"%s\"", resource.getFilename()))
                    .body(resource);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将文件以BufferedInputStream的方式读取到byte[]里面，然后用OutputStream.write输出文件
     */
    @RequestMapping("/download1")
    public void downloadFile1(HttpServletRequest request,
                              HttpServletResponse response, @RequestParam String fileName) {
        //获得路径名和文件名
        String filePath = "F:\\File\\" + File.separator + fileName;
        File downloadFile = new File(filePath);
//判断是否存在
        if (downloadFile.exists()) {
            //设置下载的一个类型
            response.setContentType("application/octet-stream");
            response.setContentLength((int)downloadFile.length());
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
                    String.format("attachment; filename=\"%s\"", fileName));

            byte[] buffer = new byte[1024];
            FileInputStream fis = null;
            BufferedInputStream bis = null;
            try {
                fis = new FileInputStream(downloadFile);
                bis = new BufferedInputStream(fis);
                OutputStream os = response.getOutputStream();
                int i = bis.read(buffer);
                while (i != -1) {
                    os.write(buffer, 0, i);
                    i = bis.read(buffer);
                }
            } catch (Exception e) {
                LOGGER.debug(e.getMessage());
                e.printStackTrace();
            } finally {
                try {
                    if (fis != null) {
                        fis.close();
                    }
                    if (bis != null) {
                        bis.close();
                    }
                } catch (Exception e2) {
                    LOGGER.debug(e2.getMessage());
                    e2.printStackTrace();
                }
            }
        }
    }

    /**
     * 包装类 IOUtils 输出文件
     */
    @RequestMapping("/download2")
    public void downloadFile2(HttpServletRequest request,
                              HttpServletResponse response, @RequestParam String fileName) {
        String filePath ="F:\\File\\" + File.separator + fileName;
        File downloadFile = new File(filePath);
        try {
            if (downloadFile.exists()) {
                response.setContentType("application/octet-stream");
                response.setContentLength((int)downloadFile.length());
                response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
                        String.format("attachment; filename=\"%s\"", fileName));
                InputStream is = new FileInputStream(downloadFile);
                IOUtils.copy(is, response.getOutputStream());
                response.flushBuffer();
            }
        } catch (Exception e) {
            LOGGER.debug(e.getMessage());
            e.printStackTrace();
        }
    }
}
