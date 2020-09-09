package com.ldx.javaSpringBoot.modules.test.controller;

import com.ldx.javaSpringBoot.modules.common.vo.Result;
import com.ldx.javaSpringBoot.modules.common.vo.SearchVo;
import com.ldx.javaSpringBoot.modules.test.entity.Student;
import com.ldx.javaSpringBoot.modules.test.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api")
public class StudentController {

    @Autowired
    private StudentService studentService;
  //127.0.0.1/api/student ---- post
//{"studentName":"ldx","studentCard":{"cardId":"1"}}
    @PostMapping(value = "student",consumes = "application/json")
    public Result<Student> addStudent(@RequestBody Student student){
        return studentService.addStudent(student);
    }

    //分页查询
    //127.0.0.1/api/student ---- post
    //{"studentName":"ldx","studentCard":{"cardId":"1"}}
    @PostMapping(value = "students",consumes = "application/json")
    public Page<Student> getStudentBySearchVo(@RequestBody  SearchVo searchVo) {
        return studentService.getStudentBySearchVo(searchVo);
    }

    //父类属性查询
    //127.0.0.1/api/students1/?studentName=ldx --  get  查询参数
    @GetMapping("/students1")
    public List<Student> selectByStudentName(@RequestBody String studentName){
        return studentService.selectByStudentName(studentName);
    }

    //模糊查询
    @GetMapping("/students2")
    public List<Student> selectByStudentNameLike(@RequestBody String studentName){
        return studentService.selectByStudentNameLike(studentName);
    }
//    //自定义查询
//    @GetMapping("/students3")//(required = false cardId并不是必须  defaultValue = "1") 默认为一
//    public List<Student> getStudentsByParams(@RequestParam String studentName,
//                                             @RequestParam(required = false,defaultValue = "1") Integer cardId){
//        return studentService.selectByStudentNameLike(studentName,cardId);
//    }
//    @RequestMapping("/login")
//    public String loginStudent(){
//     String login=   studentService.loginStudent();
//     return "mine/login/login";
//    }
}
