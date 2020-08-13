package com.ldx.javaSpringBoot.modules.test.service.impl;

import com.ldx.javaSpringBoot.modules.common.vo.Result;
import com.ldx.javaSpringBoot.modules.common.vo.SearchVo;
import com.ldx.javaSpringBoot.modules.test.data.Students;
import com.ldx.javaSpringBoot.modules.test.entity.Student;
import com.ldx.javaSpringBoot.modules.test.service.StudentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private Students students;
    @Override
    public Result<Student> addStudent(Student student) {
        student.setCreateDate(LocalDateTime.now());
        students.saveAndFlush(student);
        return new Result<Student>(Result.ResultStatus.SUCCESS.status,"add success",student);
    }

    //使用jpa封装的的分页方法
    @Override
    public Page<Student> getStudentBySearchVo(SearchVo searchVo) {
        Sort.Direction direction="desc".equalsIgnoreCase(searchVo.getSort())//字符串在前面如果报空就不会报异常
                ? Sort.Direction.DESC:Sort.Direction.ASC
                ;//确定一个排序方向
        Sort sort=new Sort(direction,StringUtils.isBlank(searchVo.getOrderBy())?
                "studentId":searchVo.getOrderBy());//将排序的方式给sort，且使用studentId进行排序
        //由于我们的起始页需要从零开始，这里的getCurrentPage是1
        Pageable pageable= PageRequest.of(searchVo.getCurrentPage()-1,searchVo.getPageSize(),sort);
        //前面是进行分页
        //条件查询
        Student student=new Student();
        student.setStudentName(searchVo.getKeyWord());
        ExampleMatcher matcher=ExampleMatcher.matching().
                withMatcher("studentName",match -> match.contains())
                .withIgnorePaths("studentId");//忽略
        Example<Student> example=Example.of(student,matcher);
        //findall(sort)进行排序查询,没有参数就是查询所有
        //findall(example,pageable)，example主要进行的是一个判断数据库为空的null等情况，而pageable就是分页信息
        return  students.findAll(example,pageable);
    }

    //属性查询
    @Override
    public List<Student> selectByStudentName(String studentName) {
        return Optional.ofNullable(students.findByStudentName(studentName)).orElse(Collections.emptyList());
    }

    //属性模糊查询
    @Override
    public List<Student> selectByStudentNameLike(String studentName) {
        return Optional.ofNullable(students.findByStudentNameLike
                (String.format("%s%S%s","%",studentName,"%")))//模糊查询的一种方式s是%，S是studentName
                .orElse(Collections.emptyList());
    }

    //自定义查询

//    @Override
//    public List<Student> getStudentsByParams(String studentName, Integer cardId) {
//        return Optional.ofNullable(students.findByStudentNameLike (String.format("%s%S%s","%",studentName,"%")))//模糊查询的一种方式s是%，S是studentName
//                .orElse(Collections.emptyList());
//    }
}

