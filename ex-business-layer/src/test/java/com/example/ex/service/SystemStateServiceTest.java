package com.example.ex.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Date;
import java.sql.Time;
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

import com.example.ex.exception.EntityNotFoundException;
import com.example.ex.model.entity.admin.Currency;
import com.example.ex.model.entity.admin.SystemState;
import com.example.ex.model.type.SystemStatus;
import com.example.ex.repository.admin.SystemStateRepository;

@RunWith(SpringRunner.class)
public class SystemStateServiceTest {

    @TestConfiguration
    static class SystemStateServiceTestContextConfiguration extends ServiceTestContextConfiguration {
        @Bean
        public SystemStateService systemStateService() {
            return new SystemStateService();
        }
    }

    @MockBean
    private SystemStateRepository systemStateRepository;
    @Autowired
    private SystemStateService systemStateService;
    @Autowired
    private CurrencyService currencyService;
    
    private SystemState getNewTestEntity() {
        Date date = new Date(System.currentTimeMillis());
        Time time = new Time(System.currentTimeMillis());
        return new SystemState("USD",date,date,SystemStatus.NONE,time,time);
    }
    
    @Test(expected=EntityNotFoundException.class)
    public void shouldNotSaveStateWithoutCurrency() {
        SystemState systemState = getNewTestEntity();
        systemState.setBaseCcy(null);
        systemStateService.save(systemState);
    }

    @Test
    public void shouldSaveValidNewState() {
        SystemState systemState = getNewTestEntity();
        
        Mockito.when(currencyService.findByName(systemState.getBaseCcy())).thenReturn(new Currency());
        Mockito.when(systemStateRepository.save(systemState)).thenReturn(systemState);
        
        SystemState savedState = systemStateService.save(systemState);
 
        assertThat(savedState).isEqualTo(systemState);
    }
    
    @Test
    public void shouldUpdateState() {
        SystemState existingState = getNewTestEntity();
        existingState.setId(1);
        
        Mockito.when(currencyService.findByName(existingState.getBaseCcy())).thenReturn(new Currency());
        Mockito.when(systemStateRepository.save(existingState)).thenReturn(existingState);
        
        SystemState savedState = systemStateService.save(existingState);
 
        assertThat(savedState).isEqualTo(existingState);
    }

    @Test
    public void shouldReturnStatesByCurrency() {
        List<SystemState> expectedStates = new ArrayList<>();
        expectedStates.add(getNewTestEntity());
        expectedStates.add(getNewTestEntity());

        Currency currency = new Currency();
        currency.setName("USD");
        Mockito.when(systemStateRepository.findByBaseCcy(currency.getName())).thenReturn(expectedStates);
        
        List<SystemState> states = systemStateService.findByCurrencyName(currency.getName());
 
        assertThat(states).containsExactlyInAnyOrder(expectedStates.toArray(new SystemState[2]));
    }
    
    @Test(expected=EntityNotFoundException.class)
    public void shouldNotDeleteANonExistingSystemState() {
        Mockito.when(systemStateRepository.findById(1)).thenReturn(Optional.empty());
        systemStateService.deleteById(1);
    }
    
    @Test
    public void shouldDeleteSystemState() {
        SystemState systemState = getNewTestEntity();
        systemState.setId(1);

        Mockito.when(systemStateRepository.findById(systemState.getId())).thenReturn(Optional.of(systemState));
        List<SystemState> list = new ArrayList<>(); 
        list.add(systemState);
        Mockito.when(systemStateRepository.findAllById(Arrays.asList(systemState.getId()))).thenReturn(list);

        systemStateService.deleteById(systemState.getId());

        Mockito.verify(systemStateRepository, Mockito.times(1)).deleteById(systemState.getId());
    }

}