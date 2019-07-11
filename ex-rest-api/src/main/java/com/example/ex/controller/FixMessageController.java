package com.example.ex.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.ex.model.entity.ts.FixMessageOrderCxlRejectToBroker;
import com.example.ex.model.entity.ts.FixMessageStatusToBroker;

@RestController
@RequestMapping(FixMessageController.BASE_URI)
public class FixMessageController {

    public static final String BASE_URI = "/fixmessages";
    
    @Autowired
    private FixMessageOrderCxlRejectToBrokerController orderCxlRejectToBroker;
    @Autowired
    private FixMessageStatusToBrokerController statusToBroker;
    
    @GetMapping(FixMessageOrderCxlRejectToBrokerController.BASE_URI)
    @ResponseBody
    public List<FixMessageOrderCxlRejectToBroker> getOrderCxlRejectToBrokers() {
        return orderCxlRejectToBroker.getAll();
    }

    @GetMapping(FixMessageOrderCxlRejectToBrokerController.BASE_URI + "/{sequence}")
    @ResponseBody
    public ResponseEntity<FixMessageOrderCxlRejectToBroker> readOrderCxlRejectToBroker(@PathVariable("sequence") Long sequence) {
        return orderCxlRejectToBroker.read(sequence);
    }
    
    @GetMapping(FixMessageStatusToBrokerController.BASE_URI)
    @ResponseBody
    public List<FixMessageStatusToBroker> getStatusToBrokers() {
        return statusToBroker.getAll();
    }

    @GetMapping(FixMessageStatusToBrokerController.BASE_URI + "/{sequence}")
    @ResponseBody
    public ResponseEntity<FixMessageStatusToBroker> readStatusToBroker(@PathVariable("sequence") Long sequence) {
        return statusToBroker.read(sequence);
    }

}
