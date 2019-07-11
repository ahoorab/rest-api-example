package com.example.ex.controller;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ex.model.entity.admin.CreditPool;
import com.example.ex.service.CreditPoolService;

@RestController
@RequestMapping(CreditPoolController.BASE_URI)
public class CreditPoolController extends AdminController<CreditPool> {

    public static final String BASE_URI = "/creditpools";

    @Autowired
    private CreditPoolService creditPoolService;
    
    @PostConstruct
    public void init() {
        super.baseUri = CreditPoolController.BASE_URI;
        super.service = this.creditPoolService;
    }
    
}