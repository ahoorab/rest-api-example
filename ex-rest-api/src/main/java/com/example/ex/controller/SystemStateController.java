package com.example.ex.controller;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ex.model.entity.admin.SystemState;
import com.example.ex.service.SystemStateService;;

@RestController
@RequestMapping(SystemStateController.BASE_URI)
public class SystemStateController extends AdminController<SystemState> {

    public static final String BASE_URI = "/systemstates";

    @Autowired
    private SystemStateService systemStateService;
        
    @PostConstruct
    public void init() {
        super.baseUri = SystemStateController.BASE_URI;
        super.service = this.systemStateService;
    }
}
