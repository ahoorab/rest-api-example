package com.example.ex.controller;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ex.model.entity.admin.Holiday;
import com.example.ex.service.HolidayService;;

@RestController
@RequestMapping(HolidayController.BASE_URI)
public class HolidayController extends AdminController<Holiday>{

    public static final String BASE_URI = "/holidays";

    @Autowired
    private HolidayService holidayService;
        
    @PostConstruct
    public void init() {
        super.baseUri = HolidayController.BASE_URI;
        super.service = this.holidayService;
    }
}
