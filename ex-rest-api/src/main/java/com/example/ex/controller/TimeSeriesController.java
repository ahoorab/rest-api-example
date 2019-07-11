package com.example.ex.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.ex.service.TimeSeriesService;

public abstract class TimeSeriesController<T> {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(TimeSeriesController.class);
    
    protected String baseUri;

    @Autowired
    protected TimeSeriesService timeSeriesService;
    
    public abstract List<T> findAll();

    public abstract Optional<T> findById(Long id);
    
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public List<T> getAll() {
        LOGGER.debug("get all entities {}",baseUri);
        return findAll();
    }

    @GetMapping("/{sequence}")
    @ResponseBody
    public ResponseEntity<T> read(@PathVariable("sequence") Long sequence) {
        LOGGER.debug("get {} by sequence <{}>",baseUri,sequence);
        return findById(sequence).map(entity -> new ResponseEntity<>(entity, HttpStatus.OK))
            .orElse(ResponseEntity.notFound().build());
    }
    
}