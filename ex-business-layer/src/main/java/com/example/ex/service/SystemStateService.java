package com.example.ex.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.example.ex.exception.EntityNotFoundException;
import com.example.ex.model.entity.admin.Currency;
import com.example.ex.model.entity.admin.SystemState;
import com.example.ex.repository.admin.SystemStateRepository;

/**
 * Business layer object for the System State entity
 * 
 * It covers basic operations inherited from JpaRepository and applies business
 * validations
 * 
 * @author Sergio Pintos <spintos@gmail.com>
 */

@Service
public class SystemStateService extends BaseService<SystemState> {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(SystemStateService.class);

    @Autowired
    private SystemStateRepository systemStateRepository;
    @Autowired
    private CurrencyService currencyService;

    @Override
    public SystemState save(SystemState systemState) {
        super.validateDependency(currencyService::findByName,Currency.class,systemState.getBaseCcy());
        
        LOGGER.debug("Saving System State <{}>",systemState);
        return systemStateRepository.save(systemState);
    }
    
    @Override
    public void deleteById(Integer id) {
        Optional<SystemState> systemState = this.findById(id);
        if (!systemState.isPresent()) {
            throw new EntityNotFoundException("System State with id <" + id + "> was not found");
        }
        systemStateRepository.deleteById(id);
    }

    public List<SystemState> findByCurrencyName(String name) {
        return systemStateRepository.findByBaseCcy(name);
    }

    @Override
    protected JpaRepository<SystemState, Integer> getRepository() {
        return systemStateRepository;
    }

}