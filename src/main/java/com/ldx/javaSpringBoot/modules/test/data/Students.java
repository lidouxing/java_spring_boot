package com.ldx.javaSpringBoot.modules.test.data;


import com.ldx.javaSpringBoot.modules.test.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Students extends JpaRepository<Student,Integer> {
    //属性查询方式,通过学生名字查询
    List<Student> findByStudentName(String studentName);

    //属性通过模糊查询
    List<Student> findByStudentNameLike(String studentName);

    //自定义查询的查询全部使用@Query
    //参数传递方式有两种一种是下标,？+数字，从1开始。
    @Query(value = "select s from Student as s where s.studentName=?1 and  s.studentCard.cardId=?2")
    List<Student> getStudentsByParams(String studentName,int cardId);
}
