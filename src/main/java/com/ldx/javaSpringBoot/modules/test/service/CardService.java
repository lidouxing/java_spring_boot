package com.ldx.javaSpringBoot.modules.test.service;

import com.ldx.javaSpringBoot.modules.common.vo.Result;
import com.ldx.javaSpringBoot.modules.test.entity.Card;

public interface CardService {
    Result<Card> addCard(Card card);
}
