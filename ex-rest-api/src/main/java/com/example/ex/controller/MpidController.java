package com.example.ex.controller;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ex.model.entity.admin.Mpid;
import com.example.ex.service.MpidService;;

@RestController
@RequestMapping(MpidController.BASE_URI)
public class MpidController extends AdminController<Mpid> {

    public static final String BASE_URI = "/mpids";

    @Autowired
    private MpidService mpidService;
        
    @PostConstruct
    public void init() {
        super.baseUri = MpidController.BASE_URI;
        super.service = this.mpidService;
    }
}
