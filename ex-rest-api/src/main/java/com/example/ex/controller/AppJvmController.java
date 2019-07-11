package com.example.ex.controller;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ex.model.entity.admin.AppJvm;
import com.example.ex.service.AppJvmService;;

@RestController
@RequestMapping(AppJvmController.BASE_URI)
public class AppJvmController extends AdminController<AppJvm>{

    public static final String BASE_URI = "/appjvms";

    @Autowired
    private AppJvmService appJvmService;
        
    @PostConstruct
    public void init() {
        super.baseUri = AppJvmController.BASE_URI;
        super.service = this.appJvmService;
    }
}
