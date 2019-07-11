package com.example.ex.controller;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ex.model.entity.admin.FixSession;
import com.example.ex.service.FixSessionService;;

@RestController
@RequestMapping(FixSessionController.BASE_URI)
public class FixSessionController extends AdminController<FixSession> {

    public static final String BASE_URI = "/fixsessions";

    @Autowired
    private FixSessionService fixSessionService;
        
    @PostConstruct
    public void init() {
        super.baseUri = FixSessionController.BASE_URI;
        super.service = this.fixSessionService;
    }
}
