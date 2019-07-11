package com.example.ex.service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.example.ex.exception.EntityNotFoundException;
import com.example.ex.exception.EntityReferenceException;
import com.example.ex.model.entity.admin.AppInstance;
import com.example.ex.model.entity.admin.AppJvm;
import com.example.ex.model.entity.admin.AppType;
import com.example.ex.model.entity.admin.FixSession;
import com.example.ex.repository.admin.AppInstanceRepository;

/**
 * Business layer object for the AppInstance entity
 * 
 * It covers basic operations inherited from JpaRepository and applies business
 * validations
 * 
 * @author Sergio Pintos <spintos@gmail.com>
 */

@Service
public class AppInstanceService extends BaseService<AppInstance> {

    @Autowired
    private AppInstanceRepository appInstanceRepository;
    @Autowired
    private AppTypeService appTypeService;
    @Autowired
    private AppJvmService appJvmService;
    @Autowired
    private FixSessionService fixSessionService;

    @Override
    public AppInstance save(AppInstance appInstance) {
        super.validateUnique(appInstanceRepository::findByAppId,appInstance,appInstance.getAppId());
        super.validateDependency(appTypeService::findByName,AppType.class,appInstance.getAppType());
        super.validateDependency(appJvmService::findByHandle,AppJvm.class,appInstance.getFirstJvm(),true);
        super.validateDependency(appJvmService::findByHandle,AppJvm.class,appInstance.getSecondJvm(),true);
        return appInstanceRepository.save(appInstance);
    }
    
    @Override
    public void deleteById(Integer id) {
        Optional<AppInstance> appInstance = appInstanceRepository.findById(id);
        if (!appInstance.isPresent()) {
            throw new EntityNotFoundException("App instance with id <" + id + "> was not found");
        }
        
        Set<FixSession> fixSessions = fixSessionService.findByAppInstance(appInstance.get().getAppId());
        if (!fixSessions.isEmpty()) {
            throw new EntityReferenceException("App Instance with id <" + id + "> is being referenced by fix sessions");

        }
        appInstanceRepository.deleteById(id);
    }

    public AppInstance findByAppId(String appId) {
        return appInstanceRepository.findByAppId(appId);
    }

    public List<AppInstance> findByAppType(String name) {
        return appInstanceRepository.findByAppType(name);
    }

    public Set<AppInstance> findByAppJvm(String handle) {
        Set<AppInstance> instances = new HashSet<>();
        instances.addAll(appInstanceRepository.findByFirstJvm(handle));
        instances.addAll(appInstanceRepository.findBySecondJvm(handle));
        return instances;
    }

    @Override
    protected JpaRepository<AppInstance, Integer> getRepository() {
        return appInstanceRepository;
    }

}