package com.example.ex.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

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
import com.example.ex.model.entity.admin.AppJvm;
import com.example.ex.model.entity.admin.AppType;
import com.example.ex.model.entity.admin.FixSession;
import com.example.ex.model.type.Status;
import com.example.ex.repository.admin.AppInstanceRepository;

@RunWith(SpringRunner.class)
public class AppInstanceServiceTest {

    @TestConfiguration
    static class AppInstanceServiceTestContextConfiguration extends ServiceTestContextConfiguration {
        @Bean
        public AppInstanceService appInstanceService() {
            return new AppInstanceService();
        }
    }

    @MockBean
    private AppInstanceRepository appInstanceRepository;
    @Autowired
    private AppInstanceService appInstanceService;
    @Autowired
    private AppTypeService appTypeService;
    @Autowired
    private AppJvmService appJvmService;
    @Autowired
    private FixSessionService fixSessionService;
    
    private AppInstance getTestAppInstance() {
        return new AppInstance("CLGW01","ADM",-1,"SEQR","SEQR2",100,105,Status.ENABLED);
    }
    
    @Test(expected=EntityAlreadyExistsException.class)
    public void shouldNotSaveAppInsntaceWithDuplicatedAppId() {
        AppInstance existingAppInstance = getTestAppInstance();
        existingAppInstance.setId(1);
        
        Mockito.when(appInstanceRepository.findByAppId(existingAppInstance.getAppId())).thenReturn(existingAppInstance);

        AppInstance appInstance = getTestAppInstance();
        appInstanceService.save(appInstance);
    }

    @Test(expected=EntityNotFoundException.class)
    public void shouldNotSaveInstanceWithInvalidFirstJvmName() {
        AppInstance appInstance = getTestAppInstance();

        Mockito.when(appTypeService.findByName(appInstance.getAppType())).thenReturn(new AppType());
        Mockito.when(appJvmService.findByHandle(appInstance.getFirstJvm())).thenReturn(null);
        Mockito.when(appJvmService.findByHandle(appInstance.getSecondJvm())).thenReturn(new AppJvm());

        appInstanceService.save(appInstance);
    }
    
    @Test(expected=EntityNotFoundException.class)
    public void shouldNotSaveInstanceWithInvalidSecondJvmName() {
        AppInstance appInstance = getTestAppInstance();

        Mockito.when(appTypeService.findByName(appInstance.getAppType())).thenReturn(new AppType());
        Mockito.when(appJvmService.findByHandle(appInstance.getFirstJvm())).thenReturn(new AppJvm());
        Mockito.when(appJvmService.findByHandle(appInstance.getSecondJvm())).thenReturn(null);

        appInstanceService.save(appInstance);
    }
    
    @Test(expected=EntityNotFoundException.class)
    public void shouldNotSaveInstanceWithInvalidAppType() {
        AppInstance appInstance = getTestAppInstance();

        Mockito.when(appTypeService.findByName(appInstance.getAppType())).thenReturn(null);
        Mockito.when(appJvmService.findByHandle(appInstance.getFirstJvm())).thenReturn(new AppJvm());
        Mockito.when(appJvmService.findByHandle(appInstance.getSecondJvm())).thenReturn(new AppJvm());

        appInstanceService.save(appInstance);
    }
    
    @Test
    public void shouldSaveAppInstance() {
        AppInstance appInstance = getTestAppInstance();

        Mockito.when(appTypeService.findByName(appInstance.getAppType())).thenReturn(new AppType());
        Mockito.when(appJvmService.findByHandle(appInstance.getFirstJvm())).thenReturn(new AppJvm());
        Mockito.when(appJvmService.findByHandle(appInstance.getSecondJvm())).thenReturn(new AppJvm());
        Mockito.when(appInstanceRepository.save(appInstance)).thenReturn(appInstance);

        AppInstance savedAppInstance = appInstanceService.save(appInstance);
        
        assertThat(savedAppInstance).isEqualTo(appInstance);
    }
    
    @Test
    public void shouldSaveAppInstanceWithoutAppJvm1() {
        AppInstance appInstance = getTestAppInstance();
        appInstance.setFirstJvm(null);

        Mockito.when(appTypeService.findByName(appInstance.getAppType())).thenReturn(new AppType());
        Mockito.when(appJvmService.findByHandle(appInstance.getSecondJvm())).thenReturn(new AppJvm());
        Mockito.when(appInstanceRepository.save(appInstance)).thenReturn(appInstance);

        AppInstance savedAppInstance = appInstanceService.save(appInstance);
        
        assertThat(savedAppInstance).isEqualTo(appInstance);
    }
    
    @Test
    public void shouldSaveAppInstanceWithoutAppJvm2() {
        AppInstance appInstance = getTestAppInstance();
        appInstance.setSecondJvm(null);

        Mockito.when(appTypeService.findByName(appInstance.getAppType())).thenReturn(new AppType());
        Mockito.when(appJvmService.findByHandle(appInstance.getFirstJvm())).thenReturn(new AppJvm());
        Mockito.when(appInstanceRepository.save(appInstance)).thenReturn(appInstance);

        AppInstance savedAppInstance = appInstanceService.save(appInstance);
        
        assertThat(savedAppInstance).isEqualTo(appInstance);
    }
    
    @Test(expected=EntityReferenceException.class)
    public void shouldNotDeleteAnAppInstanceReferencedByFixSessions() {
        AppInstance appInstance = getTestAppInstance();
        appInstance.setId(1);
        
        Set<FixSession> fixSessions = new HashSet<>();
        fixSessions.add(new FixSession());

        Mockito.when(appInstanceRepository.findById(appInstance.getId())).thenReturn(Optional.of(appInstance));
        Mockito.when(fixSessionService.findByAppInstance(appInstance.getAppId())).thenReturn(fixSessions);

        appInstanceService.deleteById(appInstance.getId());
    }
    
    @Test
    public void shouldDeleteAppInstance() {
        AppInstance appInstance = getTestAppInstance();
        appInstance.setId(1);

        Mockito.when(appInstanceRepository.findById(appInstance.getId())).thenReturn(Optional.of(appInstance));
        Mockito.when(fixSessionService.findByAppInstance(appInstance.getAppId())).thenReturn(new HashSet<>());

        appInstanceService.deleteById(appInstance.getId());
        
        Mockito.verify(appInstanceRepository, Mockito.times(1)).deleteById(appInstance.getId());
    }
    
    @Test(expected=EntityNotFoundException.class)
    public void shouldNotDeleteANonExistingAppInstance() {
        Mockito.when(appInstanceRepository.findById(1)).thenReturn(Optional.empty());
        appInstanceService.deleteById(1);
    }
    
    
}