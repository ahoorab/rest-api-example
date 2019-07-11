package com.example.ex.controller;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ex.model.entity.admin.Currency;
import com.example.ex.service.CurrencyService;

@RestController
@RequestMapping(CurrencyController.BASE_URI)
public class CurrencyController extends AdminController<Currency>{

    public static final String BASE_URI = "/currencies";

    @Autowired
    private CurrencyService currencyService;
        
    @PostConstruct
    public void init() {
        super.baseUri = CurrencyController.BASE_URI;
        super.service = this.currencyService;
    }
}
