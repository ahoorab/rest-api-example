package com.example.ex.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
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
import com.example.ex.exception.EntityReferenceException;
import com.example.ex.model.entity.admin.AppInstance;
import com.example.ex.model.entity.admin.AppType;
import com.example.ex.model.type.Status;
import com.example.ex.repository.admin.AppTypeRepository;

@RunWith(SpringRunner.class)
public class AppTypeServiceTest {

    @TestConfiguration
    static class AppTypeServiceTestContextConfiguration extends ServiceTestContextConfiguration {
        @Bean
        public AppTypeService appTypeService() {
            return new AppTypeService();
        }
    }

    @MockBean
    private AppTypeRepository appTypeRepository;
    @Autowired
    private AppTypeService appTypeService;
    @Autowired
    private AppInstanceService appInstanceService;
    
    private AppType getTestAppType() {
        return new AppType("CLGW",0,1,0,Status.ENABLED);
    }
    
    @Test(expected=EntityAlreadyExistsException.class)
    public void shouldNotSaveAppTypeWithDuplicatedName() {
        AppType existingAppType = getTestAppType();
        existingAppType.setId(1);
        
        Mockito.when(appTypeRepository.findByName(existingAppType.getName())).thenReturn(existingAppType);

        AppType appType = getTestAppType();
        appTypeService.save(appType);
    }

    @Test
    public void shouldSaveAppType() {
        AppType appType = getTestAppType();

        Mockito.when(appTypeRepository.save(appType)).thenReturn(appType);

        AppType savedAppType = appTypeService.save(appType);
        
        assertThat(savedAppType).isEqualTo(appType);
    }
    
    @Test(expected=EntityReferenceException.class)
    public void shouldNotDeleteAnAppTypeReferencedByAppInstances() {
        AppType appType = getTestAppType();
        appType.setId(1);
        
        List<AppInstance> appInstances = new ArrayList<>();
        appInstances.add(new AppInstance());

        Mockito.when(appTypeRepository.findById(appType.getId())).thenReturn(Optional.of(appType));
        Mockito.when(appInstanceService.findByAppType(appType.getName())).thenReturn(appInstances);

        appTypeService.deleteById(appType.getId());
    }
    
    @Test
    public void shouldDeleteAppType() {
        AppType appType = getTestAppType();
        appType.setId(1);

        Mockito.when(appTypeRepository.findById(appType.getId())).thenReturn(Optional.of(appType));
        Mockito.when(appInstanceService.findByAppType(appType.getName())).thenReturn(new ArrayList<>());

        appTypeService.deleteById(appType.getId());
        
        Mockito.verify(appTypeRepository, Mockito.times(1)).deleteById(appType.getId());
    }
    
    @Test(expected=EntityNotFoundException.class)
    public void shouldNotDeleteANonExistingAppType() {
        Mockito.when(appTypeRepository.findById(1)).thenReturn(Optional.empty());
        appTypeService.deleteById(1);
    }
    
}