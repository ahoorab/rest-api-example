package com.example.ex.controller;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ex.model.entity.admin.AppType;
import com.example.ex.service.AppTypeService;

@RestController
@RequestMapping(AppTypeController.BASE_URI)
public class AppTypeController extends AdminController<AppType> {

    public static final String BASE_URI = "/apptypes";

    @Autowired
    private AppTypeService appTypeService;
    
    @PostConstruct
    public void init() {
        super.baseUri = AppTypeController.BASE_URI;
        super.service = this.appTypeService;
    }

}