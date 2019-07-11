package com.example.ex.controller;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ex.model.entity.admin.CurrencyPair;
import com.example.ex.service.CurrencyPairService;;

@RestController
@RequestMapping(CurrencyPairController.BASE_URI)
public class CurrencyPairController extends AdminController<CurrencyPair> {

    public static final String BASE_URI = "/currencypairs";

    @Autowired
    private CurrencyPairService currencyPairService;
        
    @PostConstruct
    public void init() {
        super.baseUri = CurrencyPairController.BASE_URI;
        super.service = this.currencyPairService;
    }
}
