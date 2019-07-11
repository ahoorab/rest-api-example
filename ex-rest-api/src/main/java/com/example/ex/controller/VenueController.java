package com.example.ex.controller;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ex.model.entity.admin.Venue;
import com.example.ex.service.VenueService;

@RestController
@RequestMapping(VenueController.BASE_URI)
public class VenueController extends AdminController<Venue> {

    public static final String BASE_URI = "/venues";

    @Autowired
    private VenueService venueService;
    
    @PostConstruct
    public void init() {
        super.baseUri = VenueController.BASE_URI;
        super.service = this.venueService;
    }

}