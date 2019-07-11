package com.example.ex.controller;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ex.model.entity.admin.Firm;
import com.example.ex.service.FirmService;

@RestController
@RequestMapping(FirmController.BASE_URI)
public class FirmController extends AdminController<Firm> {

    public static final String BASE_URI = "/firms";

    @Autowired
    private FirmService firmService;
    
    @PostConstruct
    public void init() {
        super.baseUri = FirmController.BASE_URI;
        super.service = this.firmService;
    }

}