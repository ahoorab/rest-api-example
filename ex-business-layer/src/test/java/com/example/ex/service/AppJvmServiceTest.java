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
import com.example.ex.model.entity.admin.AppServer;
import com.example.ex.model.type.Status;
import com.example.ex.repository.admin.AppJvmRepository;

@RunWith(SpringRunner.class)
public class AppJvmServiceTest {

    @TestConfiguration
    static class AppJmvServiceTestContextConfiguration extends ServiceTestContextConfiguration {
        @Bean
        public AppJvmService appJvmService() {
            return new AppJvmService();
        }
    }

    @MockBean
    private AppJvmRepository appJvmRepository;
    @Autowired
    private AppJvmService appJvmService;
    @Autowired
    private AppServerService appServerService;
    @Autowired
    private AppInstanceService appInstanceService;
    
    private AppJvm getTestAppJvm() {
        return new AppJvm("seqr3","fo","seqr2",3,"16G","a","b","INFO",Status.ENABLED);
    }
    
    @Test(expected=EntityAlreadyExistsException.class)
    public void shouldNotSaveAppJvmWithDuplicatedHandle() {
        AppJvm existingAppJvm = getTestAppJvm();
        existingAppJvm.setId(1);
        
        Mockito.when(appJvmRepository.findByHandle(existingAppJvm.getHandle())).thenReturn(existingAppJvm);

        AppJvm appJvm = getTestAppJvm();
        appJvmService.save(appJvm);
    }

    @Test
    public void shouldSaveAppJvm() {
        AppJvm appJvm = getTestAppJvm();

        Mockito.when(appJvmRepository.save(appJvm)).thenReturn(appJvm);
        Mockito.when(appServerService.findByHandle(appJvm.getServer())).thenReturn(new AppServer());

        AppJvm savedAppJvm = appJvmService.save(appJvm);
        
        assertThat(savedAppJvm).isEqualTo(appJvm);
    }

    @Test(expected=EntityNotFoundException.class)
    public void shouldNotSaveJvmWithInvalidServerHandle() {
        AppJvm appJvm = getTestAppJvm();
        appJvm.setServer(null);
        appJvmService.save(appJvm);
    }
    
    @Test(expected=EntityReferenceException.class)
    public void shouldNotDeleteAnAppJvmReferencedByAppInstances() {
        AppJvm appJvm = getTestAppJvm();
        appJvm.setId(1);
        
        Set<AppInstance> appInstances = new HashSet<>();
        appInstances.add(new AppInstance());

        Mockito.when(appJvmRepository.findById(appJvm.getId())).thenReturn(Optional.of(appJvm));
        Mockito.when(appInstanceService.findByAppJvm(appJvm.getHandle())).thenReturn(appInstances);

        appJvmService.deleteById(appJvm.getId());
    }
    
    @Test
    public void shouldDeleteAppJvm() {
        AppJvm appJvm = getTestAppJvm();
        appJvm.setId(1);

        Mockito.when(appJvmRepository.findById(appJvm.getId())).thenReturn(Optional.of(appJvm));
        Mockito.when(appInstanceService.findByAppJvm(appJvm.getHandle())).thenReturn(new HashSet<>());

        appJvmService.deleteById(appJvm.getId());
        
        Mockito.verify(appJvmRepository, Mockito.times(1)).deleteById(appJvm.getId());
    }
    
    @Test(expected=EntityNotFoundException.class)
    public void shouldNotDeleteANonExistingAppJvm() {
        Mockito.when(appJvmRepository.findById(1)).thenReturn(Optional.empty());
        appJvmService.deleteById(1);
    }

}