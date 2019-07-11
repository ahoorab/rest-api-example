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
import com.example.ex.model.entity.admin.AppJvm;
import com.example.ex.model.entity.admin.AppServer;
import com.example.ex.repository.admin.AppServerRepository;

@RunWith(SpringRunner.class)
public class AppServerServiceTest {

    @TestConfiguration
    static class AppServerServiceTestContextConfiguration extends ServiceTestContextConfiguration {
        @Bean
        public AppServerService appServerService() {
            return new AppServerService();
        }
    }

    @MockBean
    private AppServerRepository appServerRepository;
    @Autowired
    private AppServerService appServerService;
    @Autowired
    private AppJvmService appJvmService;
    
    private AppServer getTestAppServer() {
        return new AppServer("fo", "172.31.45.24", "34.199.149.130", "fo.dev", "dev-ex-fo.exx.nyc", "dev-ex-cg.exx.nyc", 1, 1, 1, "empy note", 1);
    }
    
    @Test(expected=EntityAlreadyExistsException.class)
    public void shouldNotSaveAppServerWithDuplicatedHandle() {
        AppServer existingAppServer = getTestAppServer();
        existingAppServer.setId(1);
        
        Mockito.when(appServerRepository.findByHandle(existingAppServer.getHandle())).thenReturn(existingAppServer);

        AppServer appServer = getTestAppServer();
        appServerService.save(appServer);
    }

    @Test
    public void shouldSaveAppServer() {
        AppServer appServer = getTestAppServer();

        Mockito.when(appServerRepository.save(appServer)).thenReturn(appServer);

        AppServer savedAppServer = appServerService.save(appServer);
        
        assertThat(savedAppServer).isEqualTo(appServer);
    }
    
    @Test(expected=EntityReferenceException.class)
    public void shouldNotDeleteAnAppServerReferencedByAppJvms() {
        AppServer appServer = getTestAppServer();
        appServer.setId(1);
        
        List<AppJvm> appJvms = new ArrayList<>();
        appJvms.add(new AppJvm());

        Mockito.when(appServerRepository.findById(appServer.getId())).thenReturn(Optional.of(appServer));
        Mockito.when(appJvmService.findByServer(appServer.getHandle())).thenReturn(appJvms);

        appServerService.deleteById(appServer.getId());
    }
    
    @Test
    public void shouldDeleteAppServer() {
        AppServer appServer = getTestAppServer();
        appServer.setId(1);

        Mockito.when(appServerRepository.findById(appServer.getId())).thenReturn(Optional.of(appServer));
        Mockito.when(appJvmService.findByServer(appServer.getHandle())).thenReturn(new ArrayList<>());

        appServerService.deleteById(appServer.getId());
        
        Mockito.verify(appServerRepository, Mockito.times(1)).deleteById(appServer.getId());
    }
    
    @Test(expected=EntityNotFoundException.class)
    public void shouldNotDeleteANonExistingAppServer() {
        Mockito.when(appServerRepository.findById(1)).thenReturn(Optional.empty());
        appServerService.deleteById(1);
    }
    
}