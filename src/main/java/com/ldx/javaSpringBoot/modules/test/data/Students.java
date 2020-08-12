package com.ldx.javaSpringBoot.modules.test.data;


import com.ldx.javaSpringBoot.modules.test.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Students extends JpaRepository<Student,Integer> {
}
