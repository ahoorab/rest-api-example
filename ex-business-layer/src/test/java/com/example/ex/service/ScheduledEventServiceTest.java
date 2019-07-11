package com.example.ex.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.ex.exception.EntityAlreadyExistsException;
import com.example.ex.exception.EntityNotFoundException;
import com.example.ex.model.entity.admin.ScheduledEvent;
import com.example.ex.model.type.DataMasterType;
import com.example.ex.model.type.FieldNameType;
import com.example.ex.model.type.FieldValueType;
import com.example.ex.model.type.Status;
import com.example.ex.repository.admin.ScheduledEventRepository;

@RunWith(SpringRunner.class)
public class ScheduledEventServiceTest {

    @TestConfiguration
    static class ScheduledEventServiceTestContextConfiguration extends ServiceTestContextConfiguration {
        @Bean
        public ScheduledEventService scheduledEventService() {
            return new ScheduledEventService();
        }
    }

    @MockBean
    private ScheduledEventRepository scheduledEventRepository;
    @Autowired
    private ScheduledEventService scheduledEventService;
    
    private ScheduledEvent getTestScheduledEvent() {
        return new ScheduledEvent("sysopen-2","System Open","00:03:00",DataMasterType.VENUE_REF_DATA,2,FieldNameType.MARKET_SESSION,FieldValueType.MARKET_OPEN,"EXXG",Status.ENABLED);
    }
    
    @Test(expected=EntityAlreadyExistsException.class)
    public void shouldNotSaveScheduledEventWithDuplicatedHandle() {
        ScheduledEvent existingScheduledEvent = getTestScheduledEvent();
        existingScheduledEvent.setId(1);
        
        Mockito.when(scheduledEventRepository.findByHandle(existingScheduledEvent.getHandle())).thenReturn(existingScheduledEvent);

        ScheduledEvent scheduledEvent = getTestScheduledEvent();
        scheduledEventService.save(scheduledEvent);
    }

    @Test
    public void shouldSaveScheduledEvent() {
        ScheduledEvent scheduledEvent = getTestScheduledEvent();

        Mockito.when(scheduledEventRepository.save(scheduledEvent)).thenReturn(scheduledEvent);

        ScheduledEvent savedScheduledEvent = scheduledEventService.save(scheduledEvent);
        
        assertThat(savedScheduledEvent).isEqualTo(scheduledEvent);
    }
    
    @Test
    public void shouldDeleteScheduledEvent() {
        ScheduledEvent scheduledEvent = getTestScheduledEvent();
        scheduledEvent.setId(1);

        Mockito.when(scheduledEventRepository.findById(scheduledEvent.getId())).thenReturn(Optional.of(scheduledEvent));
        List<ScheduledEvent> list = new ArrayList<>(); 
        list.add(scheduledEvent);
        Mockito.when(scheduledEventRepository.findAllById(Arrays.asList(scheduledEvent.getId()))).thenReturn(list);

        scheduledEventService.deleteById(scheduledEvent.getId());
        
        Mockito.verify(scheduledEventRepository, Mockito.times(1)).deleteById(scheduledEvent.getId());
   }
    
    @Test(expected=EntityNotFoundException.class)
    public void shouldNotDeleteANonExistingScheduledEvent() {
        Mockito.when(scheduledEventRepository.findById(1)).thenReturn(Optional.empty());
        scheduledEventService.deleteById(1);
    }
    
}