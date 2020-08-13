package com.ldx.javaSpringBoot.modules.test.controller;

import com.ldx.javaSpringBoot.modules.common.vo.Result;
import com.ldx.javaSpringBoot.modules.test.entity.Card;
import com.ldx.javaSpringBoot.modules.test.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("api")
public class CardController {
    @Autowired
    private  CardService cardService;
    @PostMapping(value = "/card",consumes = "application/json")
    public Result<Card> addCard(@RequestBody Card card){
        return cardService.addCard(card);
    }

    @GetMapping(value = "/cards/{cardId}")
    public Card selectCard(@PathVariable int cardId){
        return cardService.selectCard(cardId);
    }
}
