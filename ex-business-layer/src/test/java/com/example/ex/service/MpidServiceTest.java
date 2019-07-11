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
import com.example.ex.model.entity.admin.DealCode;
import com.example.ex.model.entity.admin.Mpid;
import com.example.ex.model.type.BrokerType;
import com.example.ex.model.type.Status;
import com.example.ex.repository.admin.MpidRepository;

@RunWith(SpringRunner.class)
public class MpidServiceTest {

    @TestConfiguration
    static class MpidServiceTestContextConfiguration extends ServiceTestContextConfiguration {
        @Bean
        public MpidService mpidService() {
            return new MpidService();
        }
    }

    @MockBean
    private MpidRepository mpidRepository;
    @Autowired
    private MpidService mpidService;
    @Autowired
    private DealCodeService dealCodeService;
    
    private Mpid getTestMpid() {
        return new Mpid("first","First eagle","FRST","frst","scotia",BrokerType.NONE,BrokerType.NONE,1,0L,"US",Status.ENABLED);
    }
    
    @Test(expected=EntityAlreadyExistsException.class)
    public void shouldNotSaveMpidWithDuplicatedHandle() {
        Mpid existingMpid = getTestMpid();
        existingMpid.setId(1);
        
        Mockito.when(mpidRepository.findByHandle(existingMpid.getHandle())).thenReturn(existingMpid);

        Mpid mpid = getTestMpid();
        mpidService.save(mpid);
    }

    @Test
    public void shouldSaveMpidWithNullParent() {
        Mpid mpid = getTestMpid();
        mpid.setParentMpid(null);

        Mockito.when(mpidRepository.save(mpid)).thenReturn(mpid);
        Mockito.when(dealCodeService.findByHandle(mpid.getDealCode())).thenReturn(new DealCode());
        Mpid savedDc = mpidService.save(mpid);
        
        assertThat(savedDc).isEqualTo(mpid);
    }

    @Test(expected=EntityNotFoundException.class)
    public void shouldNotSaveMpidWithInvalidParent() {
        Mpid mpid = getTestMpid();
        mpid.setParentMpid("xxx");

        mpidService.save(mpid);
    }

    @Test(expected=EntityNotFoundException.class)
    public void shouldNotSaveMpidWithNullDealCode() {
        Mpid mpid = getTestMpid();
        mpid.setDealCode(null);

        Mockito.when(mpidRepository.findByHandle(mpid.getParentMpid())).thenReturn(mpid);

        mpidService.save(mpid);
    }
    
    @Test
    public void shouldSaveMpid() {
        Mpid mpid = getTestMpid();

        Mockito.when(mpidRepository.save(mpid)).thenReturn(mpid);
        Mockito.when(mpidRepository.findByHandle(mpid.getParentMpid())).thenReturn(mpid);
        Mockito.when(dealCodeService.findByHandle(mpid.getDealCode())).thenReturn(new DealCode());
        Mpid savedMpid = mpidService.save(mpid);
        
        assertThat(savedMpid).isEqualTo(mpid);
    }
    
    @Test
    public void shouldDeleteMpid() {
        Mpid mpid = getTestMpid();
        mpid.setId(1);

        Mockito.when(mpidRepository.findById(mpid.getId())).thenReturn(Optional.of(mpid));
        List<Mpid> list = new ArrayList<>(); 
        list.add(mpid);
        Mockito.when(mpidRepository.findAllById(Arrays.asList(mpid.getId()))).thenReturn(list);

        
        mpidService.deleteById(mpid.getId());
        
        Mockito.verify(mpidRepository, Mockito.times(1)).deleteById(mpid.getId());

    }
    
    
}