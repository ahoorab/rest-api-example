package com.example.ex.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
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
import com.example.ex.model.entity.admin.FixSession;
import com.example.ex.model.entity.admin.FixSessionMpid;
import com.example.ex.model.entity.admin.Mpid;
import com.example.ex.model.type.ConnectionType;
import com.example.ex.model.type.OrderCapacity;
import com.example.ex.model.type.Status;
import com.example.ex.model.type.Symbology;
import com.example.ex.repository.admin.FixSessionRepository;

@RunWith(SpringRunner.class)
public class FixSessionServiceTest {

    @TestConfiguration
    static class FixSessionServiceTestContextConfiguration extends ServiceTestContextConfiguration {
        @Bean
        public FixSessionService fixSessionService() {
            return new FixSessionService();
        }
    }

    @MockBean
    private FixSessionRepository fixSessionRepository;
    @Autowired
    private FixSessionService fixSessionService;
    @Autowired
    private MpidService mpidService;
    @Autowired
    private AppInstanceService appInstanceService;
    @Autowired
    private FixSessionMpidService fixSessionMpidService;
    
    private FixSession getTestFixSession() {
        return new FixSession("exxx-cg","exxx-cg","CLGW01","exx","EXXX","EXXG","127.0.0.1",".*",Integer.MAX_VALUE,ConnectionType.ACCEPTOR,"A",Status.ENABLED,1,1,1,Integer.MAX_VALUE,Long.MAX_VALUE,(int)Short.MAX_VALUE,"FIX.4.4",OrderCapacity.PRINCIPAL,0,Symbology.INET,"");
    }
    
    @Test(expected=EntityAlreadyExistsException.class)
    public void shouldNotSaveFixSessionWithDuplicatedHandle() {
        FixSession existingFixSession = getTestFixSession();
        existingFixSession.setId(1);
        
        Mockito.when(fixSessionRepository.findByHandle(existingFixSession.getHandle())).thenReturn(existingFixSession);

        FixSession fixSession = getTestFixSession();
        fixSessionService.save(fixSession);
    }

    @Test(expected=EntityNotFoundException.class)
    public void shouldNotSaveFixSessionWithNullAppInstance() {
        FixSession fixSession = getTestFixSession();
        fixSession.setAppInstance(null);

        fixSessionService.save(fixSession);
    }

    @Test(expected=EntityNotFoundException.class)
    public void shouldNotSaveFixSessionWithInvalidAppInstance() {
        FixSession fixSession = getTestFixSession();
        fixSession.setAppInstance("xxx");

        fixSessionService.save(fixSession);
    }

    @Test(expected=EntityNotFoundException.class)
    public void shouldNotSaveFixSessionWithNullMpid() {
        FixSession fixSession = getTestFixSession();
        fixSession.setMpid(null);

        Mockito.when(appInstanceService.findByAppId(fixSession.getAppInstance())).thenReturn(new AppInstance());

        fixSessionService.save(fixSession);
    }

    @Test(expected=EntityNotFoundException.class)
    public void shouldNotSaveFixSessionWithInvalidMpid() {
        FixSession fixSession = getTestFixSession();
        fixSession.setMpid("xxx");

        Mockito.when(appInstanceService.findByAppId(fixSession.getAppInstance())).thenReturn(new AppInstance());

        fixSessionService.save(fixSession);
    }

    @Test
    public void shouldSaveFixSession() {
        FixSession fixSession = getTestFixSession();

        Mockito.when(fixSessionRepository.save(fixSession)).thenReturn(fixSession);
        Mockito.when(appInstanceService.findByAppId(fixSession.getAppInstance())).thenReturn(new AppInstance());
        Mockito.when(mpidService.findByHandle(fixSession.getMpid())).thenReturn(new Mpid());

        FixSession savedFixSession = fixSessionService.save(fixSession);
        
        assertThat(savedFixSession).isEqualTo(fixSession);
    }
    
    @Test
    public void shouldDeleteFixSession() {
        FixSession fixSession = getTestFixSession();
        fixSession.setId(1);

        Mockito.when(fixSessionRepository.findById(fixSession.getId())).thenReturn(Optional.of(fixSession));
        Mockito.when(fixSessionMpidService.findByBrokerSession(fixSession.getHandle())).thenReturn(new HashSet<>());
        List<FixSession> list = new ArrayList<>(); 
        list.add(fixSession);
        Mockito.when(fixSessionRepository.findAllById(Arrays.asList(fixSession.getId()))).thenReturn(list);


        fixSessionService.deleteById(fixSession.getId());
        
        Mockito.verify(fixSessionRepository, Mockito.times(1)).deleteById(fixSession.getId());
   }
    
    @Test(expected=EntityNotFoundException.class)
    public void shouldNotDeleteANonExistingFixSession() {
        Mockito.when(fixSessionRepository.findById(1)).thenReturn(Optional.empty());
        fixSessionService.deleteById(1);
    }
    
    @Test(expected=EntityReferenceException.class)
    public void shouldNotDeleteAFixSessionReferencedByFixSessionMpids() {
        FixSession fixSession = getTestFixSession();
        fixSession.setId(1);

        Set<FixSessionMpid> fixSessionMpids = new HashSet<>();
        fixSessionMpids.add(new FixSessionMpid());

        Mockito.when(fixSessionRepository.findById(fixSession.getId())).thenReturn(Optional.of(fixSession));
        Mockito.when(fixSessionMpidService.findByBrokerSession(fixSession.getHandle())).thenReturn(fixSessionMpids);
        List<FixSession> list = new ArrayList<>(); 
        list.add(fixSession);
        Mockito.when(fixSessionRepository.findAllById(Arrays.asList(fixSession.getId()))).thenReturn(list);

        fixSessionService.deleteById(fixSession.getId());
    }
    
    @Test
    public void shouldGetFixSessionsByAppInstance() {
        FixSession fixSession = getTestFixSession();
        fixSession.setId(1);

        Set<FixSession> fixSessions = new HashSet<>();
        fixSessions.add(fixSession);

        Mockito.when(fixSessionRepository.findByAppInstance(fixSession.getAppInstance())).thenReturn(fixSessions);

        Set<FixSession> foundFixSessions = fixSessionService.findByAppInstance(fixSession.getAppInstance());
        
        assertThat(foundFixSessions).isEqualTo(fixSessions);
    }

    @Test
    public void shouldGetFixSessionsByAppMpid() {
        FixSession fixSession = getTestFixSession();
        fixSession.setId(1);

        Set<FixSession> fixSessions = new HashSet<>();
        fixSessions.add(fixSession);

        Mockito.when(fixSessionRepository.findByMpid(fixSession.getMpid())).thenReturn(fixSessions);

        Set<FixSession> foundFixSessions = fixSessionService.findByMpid(fixSession.getMpid());
        
        assertThat(foundFixSessions).isEqualTo(fixSessions);
    }

    @Test
    public void shouldGetFixSessionsByHandle() {
        FixSession fixSession = getTestFixSession();
        fixSession.setId(1);

        Mockito.when(fixSessionRepository.findByHandle(fixSession.getHandle())).thenReturn(fixSession);

        FixSession foundFixSession = fixSessionService.findByHandle(fixSession.getHandle());
        
        assertThat(foundFixSession).isEqualTo(fixSession);
    }

}