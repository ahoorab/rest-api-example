package com.example.ex.controller;

import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.ex.model.entity.admin.CreditLine;
import com.example.ex.service.CreditLineService;

@RestController
@RequestMapping(CreditLineController.BASE_URI)
public class CreditLineController extends AdminController<CreditLine> {

    public static final String BASE_URI = "/creditlines";

    @Autowired
    private CreditLineService creditLineService;
    
    @PostConstruct
    public void init() {
        super.baseUri = CreditLineController.BASE_URI;
        super.service = this.creditLineService;
    }
    
    @GetMapping("/grantorfirms")
    @ResponseBody
    public Set<String> getGrantorFirms() {
        return creditLineService.getGrantorFirms();
    }

    @GetMapping("/granteefirms")
    @ResponseBody
    public Set<String> getGranteeFirms() {
        return creditLineService.getGranteeFirms();
    }
    
}