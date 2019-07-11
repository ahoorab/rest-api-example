package com.example.ex.controller;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ex.model.entity.admin.BlockedCounterParty;
import com.example.ex.service.BlockedCounterPartyService;

@RestController
@RequestMapping(BlockedCounterPartyController.BASE_URI)
public class BlockedCounterPartyController extends AdminController<BlockedCounterParty> {

    public static final String BASE_URI = "/blockedcounterparties";

    @Autowired
    private BlockedCounterPartyService blockedCounterPartyService;
    
    @PostConstruct
    public void init() {
        super.baseUri = BlockedCounterPartyController.BASE_URI;
        super.service = this.blockedCounterPartyService;
    }

}