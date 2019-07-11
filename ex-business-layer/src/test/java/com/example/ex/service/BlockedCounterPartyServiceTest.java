package com.example.ex.service;

import static org.assertj.core.api.Assertions.assertThat;

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
import com.example.ex.model.entity.admin.BlockedCounterParty;
import com.example.ex.model.entity.admin.DealCode;
import com.example.ex.model.entity.admin.Firm;
import com.example.ex.model.type.Status;
import com.example.ex.repository.admin.BlockedCounterPartyRepository;

@RunWith(SpringRunner.class)
public class BlockedCounterPartyServiceTest {

    @TestConfiguration
    static class BlockedCounterPartyServiceTestContextConfiguration extends ServiceTestContextConfiguration {
        @Bean
        public BlockedCounterPartyService blockedCounterPartyService() {
            return new BlockedCounterPartyService();
        }
    }

    @MockBean
    private BlockedCounterPartyRepository blockedCounterPartyRepository;
    @Autowired
    private BlockedCounterPartyService blockedCounterPartyService;
    @Autowired
    private FirmService firmService;
    @Autowired
    private DealCodeService dealCodeService;
    
    private BlockedCounterParty getTestBlockedCounterParty() {
        return new BlockedCounterParty("hera-scnd","hera-scnd","hera","pbfirm1","tradingFirm1","scnd","pbFirm2","tradingFirm2","note",Status.ENABLED);
    }
    
    @Test(expected=EntityAlreadyExistsException.class)
    public void shouldNotSaveBlockedCounterPartyWithDuplicatedHandle() {
        BlockedCounterParty existingBlockedCounterParty = getTestBlockedCounterParty();
        existingBlockedCounterParty.setId(1);
        
        Mockito.when(blockedCounterPartyRepository.findByHandle(existingBlockedCounterParty.getHandle())).thenReturn(existingBlockedCounterParty);

        BlockedCounterParty blockedCounterParty = getTestBlockedCounterParty();
        blockedCounterPartyService.save(blockedCounterParty);
    }

    @Test(expected=EntityAlreadyExistsException.class)
    public void shouldNotSaveBlockedCounterPartyWithDuplicatedMnemonic() {
        BlockedCounterParty existingBlockedCounterParty = getTestBlockedCounterParty();
        existingBlockedCounterParty.setId(1);
        
        Mockito.when(blockedCounterPartyRepository.findByMnemonic(existingBlockedCounterParty.getMnemonic())).thenReturn(existingBlockedCounterParty);

        BlockedCounterParty blockedCounterParty = getTestBlockedCounterParty();
        blockedCounterPartyService.save(blockedCounterParty);
    }
    
    @Test(expected=EntityNotFoundException.class)
    public void shouldNotSaveBlockedCounterPartyWithPbFirm1NotFound() {
        BlockedCounterParty blockedCounterParty = getTestBlockedCounterParty();
        blockedCounterParty.setId(1);
        
        Mockito.when(blockedCounterPartyRepository.findByHandle(blockedCounterParty.getHandle())).thenReturn(blockedCounterParty);
        Mockito.when(blockedCounterPartyRepository.findByMnemonic(blockedCounterParty.getMnemonic())).thenReturn(blockedCounterParty);
        Mockito.when(firmService.findByHandle(blockedCounterParty.getPbFirm1())).thenReturn(null);
        Mockito.when(firmService.findByHandle(blockedCounterParty.getPbFirm2())).thenReturn(new Firm());
        Mockito.when(firmService.findByHandle(blockedCounterParty.getTradingFirm1())).thenReturn(new Firm());
        Mockito.when(firmService.findByHandle(blockedCounterParty.getTradingFirm2())).thenReturn(new Firm());
        Mockito.when(dealCodeService.findByHandle(blockedCounterParty.getDealCode1())).thenReturn(new DealCode());
        Mockito.when(dealCodeService.findByHandle(blockedCounterParty.getDealCode2())).thenReturn(new DealCode());

        blockedCounterPartyService.save(blockedCounterParty);
    }
    
    @Test(expected=EntityNotFoundException.class)
    public void shouldNotSaveBlockedCounterPartyWithPbFirm2NotFound() {
        BlockedCounterParty blockedCounterParty = getTestBlockedCounterParty();
        blockedCounterParty.setId(1);
        
        Mockito.when(blockedCounterPartyRepository.findByHandle(blockedCounterParty.getHandle())).thenReturn(blockedCounterParty);
        Mockito.when(blockedCounterPartyRepository.findByMnemonic(blockedCounterParty.getMnemonic())).thenReturn(blockedCounterParty);
        Mockito.when(firmService.findByHandle(blockedCounterParty.getPbFirm1())).thenReturn(new Firm());
        Mockito.when(firmService.findByHandle(blockedCounterParty.getPbFirm2())).thenReturn(null);
        Mockito.when(firmService.findByHandle(blockedCounterParty.getTradingFirm1())).thenReturn(new Firm());
        Mockito.when(firmService.findByHandle(blockedCounterParty.getTradingFirm2())).thenReturn(new Firm());
        Mockito.when(dealCodeService.findByHandle(blockedCounterParty.getDealCode1())).thenReturn(new DealCode());
        Mockito.when(dealCodeService.findByHandle(blockedCounterParty.getDealCode2())).thenReturn(new DealCode());

        blockedCounterPartyService.save(blockedCounterParty);
    }

    @Test(expected=EntityNotFoundException.class)
    public void shouldNotSaveBlockedCounterPartyWithTradingFirm1NotFound() {
        BlockedCounterParty blockedCounterParty = getTestBlockedCounterParty();
        blockedCounterParty.setId(1);
        
        Mockito.when(blockedCounterPartyRepository.findByHandle(blockedCounterParty.getHandle())).thenReturn(blockedCounterParty);
        Mockito.when(blockedCounterPartyRepository.findByMnemonic(blockedCounterParty.getMnemonic())).thenReturn(blockedCounterParty);
        Mockito.when(firmService.findByHandle(blockedCounterParty.getPbFirm1())).thenReturn(new Firm());
        Mockito.when(firmService.findByHandle(blockedCounterParty.getPbFirm2())).thenReturn(new Firm());
        Mockito.when(firmService.findByHandle(blockedCounterParty.getTradingFirm1())).thenReturn(null);
        Mockito.when(firmService.findByHandle(blockedCounterParty.getTradingFirm2())).thenReturn(new Firm());
        Mockito.when(dealCodeService.findByHandle(blockedCounterParty.getDealCode1())).thenReturn(new DealCode());
        Mockito.when(dealCodeService.findByHandle(blockedCounterParty.getDealCode2())).thenReturn(new DealCode());

        blockedCounterPartyService.save(blockedCounterParty);
    }
    
    @Test(expected=EntityNotFoundException.class)
    public void shouldNotSaveBlockedCounterPartyWithTradingFirm2NotFound() {
        BlockedCounterParty blockedCounterParty = getTestBlockedCounterParty();
        blockedCounterParty.setId(1);
        
        Mockito.when(blockedCounterPartyRepository.findByHandle(blockedCounterParty.getHandle())).thenReturn(blockedCounterParty);
        Mockito.when(blockedCounterPartyRepository.findByMnemonic(blockedCounterParty.getMnemonic())).thenReturn(blockedCounterParty);
        Mockito.when(firmService.findByHandle(blockedCounterParty.getPbFirm1())).thenReturn(new Firm());
        Mockito.when(firmService.findByHandle(blockedCounterParty.getPbFirm2())).thenReturn(new Firm());
        Mockito.when(firmService.findByHandle(blockedCounterParty.getTradingFirm1())).thenReturn(new Firm());
        Mockito.when(firmService.findByHandle(blockedCounterParty.getTradingFirm2())).thenReturn(null);
        Mockito.when(dealCodeService.findByHandle(blockedCounterParty.getDealCode1())).thenReturn(new DealCode());
        Mockito.when(dealCodeService.findByHandle(blockedCounterParty.getDealCode2())).thenReturn(new DealCode());

        blockedCounterPartyService.save(blockedCounterParty);
    }
    
    @Test(expected=EntityNotFoundException.class)
    public void shouldNotSaveBlockedCounterPartyWithDealCode1NotFound() {
        BlockedCounterParty blockedCounterParty = getTestBlockedCounterParty();
        blockedCounterParty.setId(1);
        
        Mockito.when(blockedCounterPartyRepository.findByHandle(blockedCounterParty.getHandle())).thenReturn(blockedCounterParty);
        Mockito.when(blockedCounterPartyRepository.findByMnemonic(blockedCounterParty.getMnemonic())).thenReturn(blockedCounterParty);
        Mockito.when(firmService.findByHandle(blockedCounterParty.getPbFirm1())).thenReturn(new Firm());
        Mockito.when(firmService.findByHandle(blockedCounterParty.getPbFirm2())).thenReturn(new Firm());
        Mockito.when(firmService.findByHandle(blockedCounterParty.getTradingFirm1())).thenReturn(new Firm());
        Mockito.when(firmService.findByHandle(blockedCounterParty.getTradingFirm2())).thenReturn(new Firm());
        Mockito.when(dealCodeService.findByHandle(blockedCounterParty.getDealCode1())).thenReturn(null);
        Mockito.when(dealCodeService.findByHandle(blockedCounterParty.getDealCode2())).thenReturn(new DealCode());

        blockedCounterPartyService.save(blockedCounterParty);
    }
    
    @Test(expected=EntityNotFoundException.class)
    public void shouldNotSaveBlockedCounterPartyWithDealCode2NotFound() {
        BlockedCounterParty blockedCounterParty = getTestBlockedCounterParty();
        blockedCounterParty.setId(1);
        
        Mockito.when(blockedCounterPartyRepository.findByHandle(blockedCounterParty.getHandle())).thenReturn(blockedCounterParty);
        Mockito.when(blockedCounterPartyRepository.findByMnemonic(blockedCounterParty.getMnemonic())).thenReturn(blockedCounterParty);
        Mockito.when(firmService.findByHandle(blockedCounterParty.getPbFirm1())).thenReturn(new Firm());
        Mockito.when(firmService.findByHandle(blockedCounterParty.getPbFirm2())).thenReturn(new Firm());
        Mockito.when(firmService.findByHandle(blockedCounterParty.getTradingFirm1())).thenReturn(new Firm());
        Mockito.when(firmService.findByHandle(blockedCounterParty.getTradingFirm2())).thenReturn(new Firm());
        Mockito.when(dealCodeService.findByHandle(blockedCounterParty.getDealCode1())).thenReturn(new DealCode());
        Mockito.when(dealCodeService.findByHandle(blockedCounterParty.getDealCode2())).thenReturn(null);

        blockedCounterPartyService.save(blockedCounterParty);
    }
    
    @Test
    public void shouldSaveBlockedCounterParty() {
        BlockedCounterParty blockedCounterParty = getTestBlockedCounterParty();

        Mockito.when(blockedCounterPartyRepository.save(blockedCounterParty)).thenReturn(blockedCounterParty);
        Mockito.when(blockedCounterPartyRepository.findByMnemonic(blockedCounterParty.getMnemonic())).thenReturn(blockedCounterParty);
        Mockito.when(firmService.findByHandle(blockedCounterParty.getPbFirm1())).thenReturn(new Firm());
        Mockito.when(firmService.findByHandle(blockedCounterParty.getPbFirm2())).thenReturn(new Firm());
        Mockito.when(firmService.findByHandle(blockedCounterParty.getTradingFirm1())).thenReturn(new Firm());
        Mockito.when(firmService.findByHandle(blockedCounterParty.getTradingFirm2())).thenReturn(new Firm());
        Mockito.when(dealCodeService.findByHandle(blockedCounterParty.getDealCode1())).thenReturn(new DealCode());
        Mockito.when(dealCodeService.findByHandle(blockedCounterParty.getDealCode2())).thenReturn(new DealCode());

        BlockedCounterParty savedBlockedCounterParty = blockedCounterPartyService.save(blockedCounterParty);
        
        assertThat(savedBlockedCounterParty).isEqualTo(blockedCounterParty);
    }
    
    @Test
    public void shouldDeleteBlockedCounterParty() {
        BlockedCounterParty blockedCounterParty = getTestBlockedCounterParty();
        blockedCounterParty.setId(1);

        Mockito.when(blockedCounterPartyRepository.findById(blockedCounterParty.getId())).thenReturn(Optional.of(blockedCounterParty));

        blockedCounterPartyService.deleteById(blockedCounterParty.getId());
        
        Mockito.verify(blockedCounterPartyRepository, Mockito.times(1)).deleteById(blockedCounterParty.getId());
    }
    
    @Test(expected=EntityNotFoundException.class)
    public void shouldNotDeleteANonExistingBlockedCounterParty() {
        Mockito.when(blockedCounterPartyRepository.findById(1)).thenReturn(Optional.empty());
        blockedCounterPartyService.deleteById(1);
    }
    
}