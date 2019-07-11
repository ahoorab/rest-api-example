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
import com.example.ex.model.entity.admin.AppInstance;
import com.example.ex.model.entity.admin.AppType;
import com.example.ex.repository.admin.AppTypeRepository;

/**
 * Business validation service object for the AppType entity
 * 
 * It covers basic operations inherited from JpaRepository
 * and applies business validations
 * @author Sergio Pintos <spintos@gmail.com>
 */
@Service
public class AppTypeService extends BaseService<AppType> {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(AppTypeService.class);
    
    @Autowired
    private AppTypeRepository appTypeRepository;
    @Autowired
    private AppInstanceService appInstanceService;

    @Override
    public AppType save(AppType appType) {
        super.validateUnique(appTypeRepository::findByName,appType,appType.getName());
        return appTypeRepository.save(appType);
    }

    @Override
    public void deleteById(Integer id) {
        Optional<AppType> appType = appTypeRepository.findById(id);
        if (!appType.isPresent()) {
            throw new EntityNotFoundException("App type with id <" + id + "> was not found");
        }
        
        List<AppInstance> appInstances = appInstanceService.findByAppType(appType.get().getName());
        if (!appInstances.isEmpty()) {
            throw new EntityReferenceException("App Type with id <" + id + "> is being referenced by app instances");

        }

        appTypeRepository.deleteById(id);
    }

    public AppType findByName(String name) {
        return appTypeRepository.findByName(name);
    }

    @Override
    protected JpaRepository<AppType, Integer> getRepository() {
        return appTypeRepository;
    }

}