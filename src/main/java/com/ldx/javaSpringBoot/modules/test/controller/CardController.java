package com.ldx.javaSpringBoot.modules.test.controller;

import com.ldx.javaSpringBoot.modules.common.vo.Result;
import com.ldx.javaSpringBoot.modules.test.entity.Card;
import com.ldx.javaSpringBoot.modules.test.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
@RequestMapping("api")
public class CardController {
    @Autowired
    private  CardService cardService;
    public Result<Card> addCard(Card card){
        return cardService.addCard(card);
    }
}
