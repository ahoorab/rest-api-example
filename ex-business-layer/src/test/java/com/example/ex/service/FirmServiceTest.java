package com.example.ex.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
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
import com.example.ex.model.entity.admin.BlockedCounterParty;
import com.example.ex.model.entity.admin.DealCode;
import com.example.ex.model.entity.admin.Firm;
import com.example.ex.model.type.Status;
import com.example.ex.repository.admin.FirmRepository;

@RunWith(SpringRunner.class)
public class FirmServiceTest {

    @TestConfiguration
    static class FirmServiceTestContextConfiguration extends ServiceTestContextConfiguration {
        @Bean
        public FirmService firmService() {
            return new FirmService();
        }
    }

    @MockBean
    private FirmRepository firmRepository;
    @Autowired
    private FirmService firmService;
    @Autowired
    private DealCodeService dealCodeService;
    @Autowired
    private BlockedCounterPartyService blockedCounterPartyService;
    
    private Firm getTestFirm() {
        return new Firm("exx", "EXX", "Example Markets", 0, 0, 0, 0, 0, 0, 0, Status.ENABLED);
    }
    
    @Test(expected=EntityAlreadyExistsException.class)
    public void shouldNotSaveFirmWithDuplicatedHandle() {
        Firm existingFirm = getTestFirm();
        existingFirm.setId(1);
        
        Mockito.when(firmRepository.findByHandle(existingFirm.getHandle())).thenReturn(existingFirm);

        Firm firm = getTestFirm();
        firmService.save(firm);
    }

    @Test(expected=EntityAlreadyExistsException.class)
    public void shouldNotSaveFirmWithDuplicatedMnemonic() {
        Firm existingFirm = getTestFirm();
        existingFirm.setId(1);
        
        Mockito.when(firmRepository.findByMnemonic(existingFirm.getMnemonic())).thenReturn(existingFirm);

        Firm firm = getTestFirm();
        firmService.save(firm);
    }

    
    @Test
    public void shouldSaveFirm() {
        Firm firm = getTestFirm();

        Mockito.when(firmRepository.save(firm)).thenReturn(firm);

        Firm savedFirm = firmService.save(firm);
        
        assertThat(savedFirm).isEqualTo(firm);
    }
    
    @Test
    public void shouldDeleteFirm() {
        Firm firm = getTestFirm();
        firm.setId(1);

        Mockito.when(firmRepository.findById(firm.getId())).thenReturn(Optional.of(firm));
        Mockito.when(dealCodeService.findByFirm(firm.getHandle())).thenReturn(new HashSet<>());
        Mockito.when(blockedCounterPartyService.findByFirm(firm.getHandle())).thenReturn(new ArrayList<>());

        firmService.deleteById(firm.getId());
        
        Mockito.verify(firmRepository, Mockito.times(1)).deleteById(firm.getId());
    }
    
    @Test(expected=EntityReferenceException.class)
    public void shouldNotDeleteAFirmReferencedByDealCodes() {
        Set<DealCode> dealCodes = new HashSet<>();
        dealCodes.add(new DealCode());

        Firm firm = getTestFirm();
        firm.setId(1);

        Mockito.when(firmRepository.findById(firm.getId())).thenReturn(Optional.of(firm));
        Mockito.when(dealCodeService.findByFirm(firm.getHandle())).thenReturn(dealCodes);
        Mockito.when(blockedCounterPartyService.findByFirm(firm.getHandle())).thenReturn(new ArrayList<>());

        firmService.deleteById(firm.getId());
    }
    
    @Test(expected=EntityReferenceException.class)
    public void shouldNotDeleteAFirmReferencedByBlockedCounterParties() {
        List<BlockedCounterParty> bcps = new ArrayList<>();
        bcps.add(new BlockedCounterParty());

        Firm firm = getTestFirm();
        firm.setId(1);

        Mockito.when(firmRepository.findById(firm.getId())).thenReturn(Optional.of(firm));
        Mockito.when(dealCodeService.findByFirm(firm.getHandle())).thenReturn(new HashSet<>());
        Mockito.when(blockedCounterPartyService.findByFirm(firm.getHandle())).thenReturn(bcps);

        firmService.deleteById(firm.getId());
    }
    
    @Test(expected=EntityNotFoundException.class)
    public void shouldNotDeleteANonExistingFirm() {
        Mockito.when(firmRepository.findById(1)).thenReturn(Optional.empty());
        firmService.deleteById(1);
    }
    
}