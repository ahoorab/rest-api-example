package com.example.ex.controller;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ex.model.entity.admin.FixSessionMpid;
import com.example.ex.service.FixSessionMpidService;;

@RestController
@RequestMapping(FixSessionMpidController.BASE_URI)
public class FixSessionMpidController extends AdminController<FixSessionMpid> {

    public static final String BASE_URI = "/fixsessionmpids";

    @Autowired
    private FixSessionMpidService fixSessionMpidService;
        
    @PostConstruct
    public void init() {
        super.baseUri = FixSessionMpidController.BASE_URI;
        super.service = this.fixSessionMpidService;
    }
}
