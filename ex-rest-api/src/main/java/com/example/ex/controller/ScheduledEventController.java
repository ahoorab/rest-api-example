package com.example.ex.controller;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ex.model.entity.admin.ScheduledEvent;
import com.example.ex.service.ScheduledEventService;;

@RestController
@RequestMapping(ScheduledEventController.BASE_URI)
public class ScheduledEventController extends AdminController<ScheduledEvent> {

    public static final String BASE_URI = "/scheduledevents";

    @Autowired
    private ScheduledEventService scheduledEventMpidService;
        
    @PostConstruct
    public void init() {
        super.baseUri = ScheduledEventController.BASE_URI;
        super.service = this.scheduledEventMpidService;
    }
}
