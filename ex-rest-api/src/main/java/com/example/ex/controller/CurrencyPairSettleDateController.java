package com.example.ex.controller;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ex.model.entity.admin.CurrencyPairSettleDate;
import com.example.ex.service.CurrencyPairSettleDateService;;

@RestController
@RequestMapping(CurrencyPairSettleDateController.BASE_URI)
public class CurrencyPairSettleDateController extends AdminController<CurrencyPairSettleDate> {

    public static final String BASE_URI = "/ccpairsettledates";

    @Autowired
    private CurrencyPairSettleDateService currencyPairSettleDateService;
        
    @PostConstruct
    public void init() {
        super.baseUri = CurrencyPairSettleDateController.BASE_URI;
        super.service = this.currencyPairSettleDateService;
    }
}
