package com.example.ex.controller;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ex.model.entity.admin.DealCode;
import com.example.ex.service.DealCodeService;;

@RestController
@RequestMapping(DealCodeController.BASE_URI)
public class DealCodeController extends AdminController<DealCode> {

    public static final String BASE_URI = "/dealcodes";

    @Autowired
    private DealCodeService dealCodeService;
        
    @PostConstruct
    public void init() {
        super.baseUri = DealCodeController.BASE_URI;
        super.service = this.dealCodeService;
    }
}
