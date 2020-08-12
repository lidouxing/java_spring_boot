package com.ldx.javaSpringBoot.modules.test.service.impl;

import com.ldx.javaSpringBoot.modules.common.vo.Result;
import com.ldx.javaSpringBoot.modules.test.data.Cardc;
import com.ldx.javaSpringBoot.modules.test.entity.Card;
import com.ldx.javaSpringBoot.modules.test.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CardServiceImpl implements CardService {
    @Autowired
    private Cardc cardc;
    @Override
    public Result<Card> addCard(Card card) {
        cardc.addCard(card);
        return new Result<Card>(Result.ResultStatus.SUCCESS.status,"success",card);
    }
}
