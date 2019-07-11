package com.example.ex.controller;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ex.model.entity.admin.AppServer;
import com.example.ex.service.AppServerService;;

@RestController
@RequestMapping(AppServerController.BASE_URI)
public class AppServerController extends AdminController<AppServer>{

    public static final String BASE_URI = "/appservers";

    @Autowired
    private AppServerService appServerService;
        
    @PostConstruct
    public void init() {
        super.baseUri = AppServerController.BASE_URI;
        super.service = this.appServerService;
    }
}
