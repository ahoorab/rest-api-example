package com.example.ex.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.example.ex.exception.EntityNotFoundException;
import com.example.ex.model.entity.admin.ScheduledEvent;
import com.example.ex.repository.admin.ScheduledEventRepository;

/**
 * Business layer object for the Scheduled Event entity
 * 
 * It covers basic operations inherited from JpaRepository and applies business
 * validations
 * 
 * @author Sergio Pintos <spintos@gmail.com>
 */

@Service
public class ScheduledEventService extends BaseService<ScheduledEvent> {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(ScheduledEventService.class);

    @Autowired
    private ScheduledEventRepository scheduledEventRepository;

    @Override
    public ScheduledEvent save(ScheduledEvent scheduledEvent) {
        super.validateUnique(scheduledEventRepository::findByHandle,scheduledEvent,scheduledEvent.getHandle());
        
        return scheduledEventRepository.save(scheduledEvent);
    }
    
    @Override
    public void deleteById(Integer id) {
        LOGGER.debug("checking if scheduled event id <{}> exists",id);
        Optional<ScheduledEvent> scheduledEvent = this.findById(id);
        if (!scheduledEvent.isPresent()) {
            LOGGER.debug("scheduled event id <{}> does not exists, cannot delete",id);
            throw new EntityNotFoundException("ScheduledEvent with id <" + id + "> was not found");
        }
        
        scheduledEventRepository.deleteById(id);
    }

    @Override
    protected JpaRepository<ScheduledEvent, Integer> getRepository() {
        return scheduledEventRepository;
    }
    
}