package com.example.ex.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.example.ex.exception.EntityNotFoundException;
import com.example.ex.exception.EntityReferenceException;
import com.example.ex.model.entity.admin.AppInstance;
import com.example.ex.model.entity.admin.AppJvm;
import com.example.ex.model.entity.admin.AppServer;
import com.example.ex.repository.admin.AppJvmRepository;

/**
 * Business layer object for the AppJvm entity
 * 
 * It covers basic operations inherited from JpaRepository and applies business
 * validations
 * 
 * @author Sergio Pintos <spintos@gmail.com>
 */

@Service
public class AppJvmService extends BaseService<AppJvm> {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(AppJvmService.class);

    @Autowired
    private AppJvmRepository appJvmRepository;
    @Autowired
    private AppServerService appServerService;
    @Autowired
    private AppInstanceService appInstanceService;

    @Override
    public AppJvm save(AppJvm appJvm) {
        super.validateUnique(appJvmRepository::findByHandle, appJvm, appJvm.getHandle());
        super.validateDependency(appServerService::findByHandle,AppServer.class,appJvm.getServer());
        return appJvmRepository.save(appJvm);
    }

    @Override
    public void deleteById(Integer id) {
        Optional<AppJvm> appJvm = appJvmRepository.findById(id);
        if (!appJvm.isPresent()) {
            throw new EntityNotFoundException("App jvm with id <" + id + "> was not found");
        }
        
        Set<AppInstance> appInstances = appInstanceService.findByAppJvm(appJvm.get().getHandle());
        if (!appInstances.isEmpty()) {
            throw new EntityReferenceException("App jvm with id <" + id + "> is being referenced by app instances");

        }

        appJvmRepository.deleteById(id);
    }

    public AppJvm findByHandle(String jvm) {
        return appJvmRepository.findByHandle(jvm);
    }

    public List<AppJvm> findByServer(String handle) {
        return appJvmRepository.findByServer(handle);
    }

    @Override
    protected JpaRepository<AppJvm, Integer> getRepository() {
        return appJvmRepository;
    }

}