package com.example.ex.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.example.ex.exception.EntityNotFoundException;
import com.example.ex.exception.EntityReferenceException;
import com.example.ex.model.entity.admin.AppJvm;
import com.example.ex.model.entity.admin.AppServer;
import com.example.ex.repository.admin.AppServerRepository;

/**
 * Data access object for the AppServer entity
 * 
 * It covers basic operations inherited from JpaRepository
 * and applies business validations
 * @author Sergio Pintos <spintos@gmail.com>
 */
@Service
public class AppServerService extends BaseService<AppServer> {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(AppServerService.class);
    
    @Autowired
    private AppServerRepository appServerRepository;
    @Autowired
    private AppJvmService appJvmService;

    @Override
    public AppServer save(AppServer appServer) {
        super.validateUnique(appServerRepository::findByHandle, appServer, appServer.getHandle());
        return appServerRepository.save(appServer);
    }

    @Override
    public void deleteById(Integer id) {
        Optional<AppServer> appServer = appServerRepository.findById(id);
        if (!appServer.isPresent()) {
            throw new EntityNotFoundException("App Server with id <" + id + "> was not found");
        }
        
        List<AppJvm> appServers = appJvmService.findByServer(appServer.get().getHandle());
        if (!appServers.isEmpty()) {
            throw new EntityReferenceException("App Server with id <" + id + "> is being referenced by app jvms");

        }
        appServerRepository.deleteById(id);
    }

    public AppServer findByHandle(String handle) {
        return appServerRepository.findByHandle(handle);
    }

    @Override
    protected JpaRepository<AppServer, Integer> getRepository() {
        return appServerRepository;
    }

}