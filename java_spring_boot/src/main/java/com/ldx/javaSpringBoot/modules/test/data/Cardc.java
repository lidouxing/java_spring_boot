package com.ldx.javaSpringBoot.modules.test.data;

import com.ldx.javaSpringBoot.modules.test.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Cardc extends JpaRepository<Card,Integer> {
}
