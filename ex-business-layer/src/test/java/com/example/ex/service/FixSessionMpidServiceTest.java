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
import com.example.ex.model.entity.admin.FixSession;
import com.example.ex.model.entity.admin.FixSessionMpid;
import com.example.ex.model.entity.admin.Mpid;
import com.example.ex.model.type.InstructionType;
import com.example.ex.model.type.Status;
import com.example.ex.repository.admin.FixSessionMpidRepository;

@RunWith(SpringRunner.class)
public class FixSessionMpidServiceTest {

    @TestConfiguration
    static class FixSessionMpidServiceTestContextConfiguration extends ServiceTestContextConfiguration {
        @Bean
        public FixSessionMpidService fixSessionMpidService() {
            return new FixSessionMpidService();
        }
    }

    @MockBean
    private FixSessionMpidRepository fixSessionMpidRepository;
    @Autowired
    private FixSessionMpidService fixSessionMpidService;
    @Autowired
    private MpidService mpidService;
    @Autowired
    private FixSessionService fixSessionService;
    
    private FixSessionMpid getTestFixSessionMpid() {
        return new FixSessionMpid("exxx-cg","exxx-cg","exx","--","OO",InstructionType.COMPOSITE,Status.ENABLED);
    }
    
    @Test(expected=EntityAlreadyExistsException.class)
    public void shouldNotSaveFixSessionMpidWithDuplicatedHandle() {
        FixSessionMpid existingFixSessionMpid = getTestFixSessionMpid();
        existingFixSessionMpid.setId(1);
        
        Mockito.when(fixSessionMpidRepository.findByHandle(existingFixSessionMpid.getHandle())).thenReturn(existingFixSessionMpid);

        FixSessionMpid fixSessionMpid = getTestFixSessionMpid();
        fixSessionMpidService.save(fixSessionMpid);
    }

    @Test(expected=EntityNotFoundException.class)
    public void shouldNotSaveFixSessionMpidWithNullBrokerSession() {
        FixSessionMpid fixSessionMpid = getTestFixSessionMpid();
        fixSessionMpid.setBrokerSession(null);

        fixSessionMpidService.save(fixSessionMpid);
    }

    @Test(expected=EntityNotFoundException.class)
    public void shouldNotSaveFixSessionMpidWithInvalidBrokerSession() {
        FixSessionMpid fixSessionMpid = getTestFixSessionMpid();
        fixSessionMpid.setBrokerSession("xxx");

        fixSessionMpidService.save(fixSessionMpid);
    }

    @Test(expected=EntityNotFoundException.class)
    public void shouldNotSaveFixSessionMpidWithNullMpid() {
        FixSessionMpid fixSessionMpid = getTestFixSessionMpid();
        fixSessionMpid.setMpid(null);

        Mockito.when(fixSessionService.findByHandle(fixSessionMpid.getBrokerSession())).thenReturn(new FixSession());

        fixSessionMpidService.save(fixSessionMpid);
    }

    @Test(expected=EntityNotFoundException.class)
    public void shouldNotSaveFixSessionMpidWithInvalidMpid() {
        FixSessionMpid fixSessionMpid = getTestFixSessionMpid();
        fixSessionMpid.setMpid("xxx");

        Mockito.when(fixSessionService.findByHandle(fixSessionMpid.getBrokerSession())).thenReturn(new FixSession());

        fixSessionMpidService.save(fixSessionMpid);
    }

    @Test
    public void shouldSaveFixSessionMpid() {
        FixSessionMpid fixSessionMpid = getTestFixSessionMpid();

        Mockito.when(fixSessionMpidRepository.save(fixSessionMpid)).thenReturn(fixSessionMpid);
        Mockito.when(fixSessionService.findByHandle(fixSessionMpid.getBrokerSession())).thenReturn(new FixSession());
        Mockito.when(mpidService.findByHandle(fixSessionMpid.getMpid())).thenReturn(new Mpid());

        FixSessionMpid savedFixSessionMpid = fixSessionMpidService.save(fixSessionMpid);
        
        assertThat(savedFixSessionMpid).isEqualTo(fixSessionMpid);
        Mockito.verify(fixSessionMpidRepository, Mockito.times(1)).save(fixSessionMpid);
        Mockito.verify(fixSessionService, Mockito.times(1)).findByHandle(fixSessionMpid.getBrokerSession());
        Mockito.verify(mpidService, Mockito.times(1)).findByHandle(fixSessionMpid.getMpid());
    }
    
    @Test
    public void shouldDeleteFixSessionMpid() {
        FixSessionMpid fixSessionMpid = getTestFixSessionMpid();
        fixSessionMpid.setId(1);

        Mockito.when(fixSessionMpidRepository.findById(fixSessionMpid.getId())).thenReturn(Optional.of(fixSessionMpid));
        List<FixSessionMpid> list = new ArrayList<>(); 
        list.add(fixSessionMpid);
        Mockito.when(fixSessionMpidRepository.findAllById(Arrays.asList(fixSessionMpid.getId()))).thenReturn(list);

        fixSessionMpidService.deleteById(fixSessionMpid.getId());
        
        Mockito.verify(fixSessionMpidRepository, Mockito.times(1)).deleteById(fixSessionMpid.getId());
   }
    
    @Test(expected=EntityNotFoundException.class)
    public void shouldNotDeleteANonExistingFixSessionMpid() {
        Mockito.when(fixSessionMpidRepository.findById(1)).thenReturn(Optional.empty());
        fixSessionMpidService.deleteById(1);
    }
    
    @Test
    public void shouldGetFixSessionMpidsByFixSession() {
        FixSessionMpid fixSessionMpid = getTestFixSessionMpid();
        fixSessionMpid.setId(1);

        Set<FixSessionMpid> fixSessionMpids = new HashSet<>();
        fixSessionMpids.add(fixSessionMpid);

        Mockito.when(fixSessionMpidRepository.findByBrokerSession(fixSessionMpid.getBrokerSession())).thenReturn(fixSessionMpids);

        Set<FixSessionMpid> foundFixSessionMpids = fixSessionMpidService.findByBrokerSession(fixSessionMpid.getBrokerSession());
        
        assertThat(foundFixSessionMpids).isEqualTo(fixSessionMpids);
        Mockito.verify(fixSessionMpidRepository, Mockito.times(1)).findByBrokerSession(fixSessionMpid.getBrokerSession());
    }

    @Test
    public void shouldGetFixSessionMpidsByMpid() {
        FixSessionMpid fixSessionMpid = getTestFixSessionMpid();
        fixSessionMpid.setId(1);

        Set<FixSessionMpid> fixSessionMpids = new HashSet<>();
        fixSessionMpids.add(fixSessionMpid);

        Mockito.when(fixSessionMpidRepository.findByMpid(fixSessionMpid.getMpid())).thenReturn(fixSessionMpids);

        Set<FixSessionMpid> foundFixSessionMpids = fixSessionMpidService.findByMpid(fixSessionMpid.getMpid());
        
        assertThat(foundFixSessionMpids).isEqualTo(fixSessionMpids);
        Mockito.verify(fixSessionMpidRepository, Mockito.times(1)).findByMpid(fixSessionMpid.getMpid());
    }

}