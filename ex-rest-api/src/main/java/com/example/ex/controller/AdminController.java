package com.example.ex.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.ex.model.entity.admin.BaseEntity;
import com.example.ex.service.BaseService;

public abstract class AdminController<T extends BaseEntity> {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(AdminController.class);
    
    protected String baseUri;

    protected BaseService<T> service;
    
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public List<T> findAll() {
        LOGGER.debug("get all entities {}",baseUri);
        return service.findAll();
    }

    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<T> read(@PathVariable("id") Integer id) {
        LOGGER.debug("get {} by id <{}>",baseUri,id);
        return service.findById(id).map(entity -> new ResponseEntity<>(entity, HttpStatus.OK))
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping(consumes="application/json")
    public ResponseEntity<Object> create(@Valid @RequestBody T entity, UriComponentsBuilder ucBuilder) {
        LOGGER.debug("create {}, payload: {}",baseUri,entity);
        if (entity.getId() != null) {
            LOGGER.debug("create {}, id was specified, which is wrong, returning bad request",baseUri);
            return ResponseEntity.badRequest().body("Id can not be specified");
        }
        LOGGER.debug("create {}, about to save entity {}",baseUri,entity);
        entity = service.save(entity);
        LOGGER.debug("create {}, entity {} was succesfully saved",baseUri,entity);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path(baseUri.concat("/{id}")).buildAndExpand(entity.getId()).toUri());
        LOGGER.debug("returning status created {}, response entity {} and headers {}",baseUri,entity,headers);
        return new ResponseEntity<>(entity,headers,HttpStatus.CREATED);
    }
    
    @PutMapping(value="/{id}",consumes="application/json")
    public ResponseEntity<String> update(@Valid @RequestBody T entity, @PathVariable("id") Integer id) {
        LOGGER.debug("update {}, payload: {} with id {}",baseUri,entity, id);
        Optional<T> found = service.findById(id);
        if (!found.isPresent()) {
            LOGGER.debug("update {}, id {} was not found",baseUri,id);
            return ResponseEntity.notFound().build();
        }
        LOGGER.debug("update {}, about to save entity {}",baseUri,entity);
        entity.setId(found.get().getId());
        service.save(entity);
        LOGGER.debug("update {}, entity {} was succesfully saved",baseUri,entity);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Integer id) {
        LOGGER.debug("delete {} with id {}",baseUri,id);
        Optional<T> found = service.findById(id);
        if (!found.isPresent()) {
            LOGGER.debug("delete {}, id {} was not found",baseUri,id);
            return ResponseEntity.notFound().build();
        }
        LOGGER.debug("delete {}, about to delete id {}",baseUri,id);
        service.deleteById(id);
        LOGGER.debug("delete {}, id {} was successfull deleted",id,baseUri);
        return ResponseEntity.noContent().build();
    }

}