package com.example.ex.controller;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ex.model.entity.admin.AppInstance;
import com.example.ex.service.AppInstanceService;;

@RestController
@RequestMapping(AppInstanceController.BASE_URI)
public class AppInstanceController extends AdminController<AppInstance> {

    public static final String BASE_URI = "/appinstances";

    @Autowired
    private AppInstanceService appInstanceService;
        
    @PostConstruct
    public void init() {
        super.baseUri = AppInstanceController.BASE_URI;
        super.service = this.appInstanceService;
    }
}
