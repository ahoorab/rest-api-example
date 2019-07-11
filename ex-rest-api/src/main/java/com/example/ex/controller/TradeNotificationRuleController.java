package com.example.ex.controller;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ex.model.entity.admin.TradeNotificationRule;
import com.example.ex.service.TradeNotificationRuleService;

@RestController
@RequestMapping(TradeNotificationRuleController.BASE_URI)
public class TradeNotificationRuleController extends AdminController<TradeNotificationRule> {

    public static final String BASE_URI = "/tradenotificationrules";

    @Autowired
    private TradeNotificationRuleService tradeNotificationRuleService;
    
    @PostConstruct
    public void init() {
        super.baseUri = TradeNotificationRuleController.BASE_URI;
        super.service = this.tradeNotificationRuleService;
    }

}