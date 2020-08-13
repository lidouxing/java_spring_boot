package com.ldx.javaSpringBoot.modules.test.service;

import com.ldx.javaSpringBoot.modules.common.vo.Result;
import com.ldx.javaSpringBoot.modules.common.vo.SearchVo;
import com.ldx.javaSpringBoot.modules.test.entity.Student;
import org.springframework.data.domain.Page;

import java.util.List;

public interface StudentService {
    Result<Student> addStudent(Student student);

    //分页
    Page<Student> getStudentBySearchVo(SearchVo searchVo);

    List<Student> selectByStudentName(String studentName);

    List<Student> selectByStudentNameLike(String studentName);

}
