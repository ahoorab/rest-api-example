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
import com.example.ex.model.entity.admin.CreditLine;
import com.example.ex.model.entity.admin.CreditPool;
import com.example.ex.model.entity.admin.Firm;
import com.example.ex.model.type.CreditLineType;
import com.example.ex.model.type.Status;
import com.example.ex.repository.admin.CreditLineRepository;

@RunWith(SpringRunner.class)
public class CreditLineServiceTest {

    @TestConfiguration
    static class CreditLineServiceTestContextConfiguration extends ServiceTestContextConfiguration {
        @Bean
        public CreditLineService creditLineService() {
            return new CreditLineService();
        }
    }

    @MockBean
    private CreditLineRepository creditLineRepository;
    @Autowired
    private CreditLineService creditLineService;
    @Autowired
    private FirmService firmService;
    @Autowired
    private CreditPoolService creditPoolService;
    
    protected CreditLine getTestCreditLine() {
        return new CreditLine("scot-rbc","scotia:RBC",CreditLineType.PRINCIPAL,"scotia","rbct","scot-rb",Status.ENABLED);
    }
    
    @Test(expected=EntityAlreadyExistsException.class)
    public void shouldNotSaveCreditLineWithDuplicatedHandle() {
        CreditLine existingCreditLine = getTestCreditLine();
        existingCreditLine.setId(1);
        
        Mockito.when(creditLineRepository.findByHandle(existingCreditLine.getHandle())).thenReturn(existingCreditLine);
        Mockito.when(creditLineRepository.findByGrantorFirmAndGranteeFirm(existingCreditLine.getGrantorFirm(),existingCreditLine.getGranteeFirm())).thenReturn(null);
        Mockito.when(firmService.findByHandle(existingCreditLine.getGrantorFirm())).thenReturn(new Firm());
        Mockito.when(firmService.findByHandle(existingCreditLine.getGranteeFirm())).thenReturn(new Firm());
        Mockito.when(creditPoolService.findByHandle(existingCreditLine.getCreditPool())).thenReturn(new CreditPool());
        Mockito.when(creditPoolService.findByHandleAndGrantorFirm(existingCreditLine.getCreditPool(),existingCreditLine.getGrantorFirm())).thenReturn(new CreditPool());

        CreditLine creditLine = getTestCreditLine();
        creditLineService.save(creditLine);
    }

    @Test(expected=EntityNotFoundException.class)
    public void shouldNotSaveCreditLineWithoutGrantorFirm() {
        CreditLine creditLine = getTestCreditLine();
        
        Mockito.when(creditLineRepository.findByHandle(creditLine.getHandle())).thenReturn(null);
        Mockito.when(creditLineRepository.findByGrantorFirmAndGranteeFirm(creditLine.getGrantorFirm(),creditLine.getGranteeFirm())).thenReturn(null);
        Mockito.when(firmService.findByHandle(creditLine.getGrantorFirm())).thenReturn(null);
        Mockito.when(firmService.findByHandle(creditLine.getGranteeFirm())).thenReturn(new Firm());
        Mockito.when(creditPoolService.findByHandle(creditLine.getCreditPool())).thenReturn(new CreditPool());
        Mockito.when(creditPoolService.findByHandleAndGrantorFirm(creditLine.getCreditPool(),creditLine.getGrantorFirm())).thenReturn(new CreditPool());
        
        creditLineService.save(creditLine);
    }
    
    @Test(expected=EntityNotFoundException.class)
    public void shouldNotSaveCreditLineWithoutGranteeFirm() {
        CreditLine creditLine = getTestCreditLine();
        
        Mockito.when(creditLineRepository.findByHandle(creditLine.getHandle())).thenReturn(null);
        Mockito.when(creditLineRepository.findByGrantorFirmAndGranteeFirm(creditLine.getGrantorFirm(),creditLine.getGranteeFirm())).thenReturn(null);
        Mockito.when(firmService.findByHandle(creditLine.getGrantorFirm())).thenReturn(new Firm());
        Mockito.when(firmService.findByHandle(creditLine.getGranteeFirm())).thenReturn(null);
        Mockito.when(creditPoolService.findByHandle(creditLine.getCreditPool())).thenReturn(new CreditPool());
        Mockito.when(creditPoolService.findByHandleAndGrantorFirm(creditLine.getCreditPool(),creditLine.getGrantorFirm())).thenReturn(new CreditPool());
        
        creditLineService.save(creditLine);
    }
    
    @Test(expected=EntityNotFoundException.class)
    public void shouldNotSaveCreditLineWithoutCreditPool() {
        CreditLine creditLine = getTestCreditLine();
        
        Mockito.when(creditLineRepository.findByHandle(creditLine.getHandle())).thenReturn(null);
        Mockito.when(creditLineRepository.findByGrantorFirmAndGranteeFirm(creditLine.getGrantorFirm(),creditLine.getGranteeFirm())).thenReturn(null);
        Mockito.when(firmService.findByHandle(creditLine.getGrantorFirm())).thenReturn(new Firm());
        Mockito.when(firmService.findByHandle(creditLine.getGranteeFirm())).thenReturn(new Firm());
        Mockito.when(creditPoolService.findByHandle(creditLine.getCreditPool())).thenReturn(null);
        Mockito.when(creditPoolService.findByHandleAndGrantorFirm(creditLine.getCreditPool(),creditLine.getGrantorFirm())).thenReturn(new CreditPool());
        
        creditLineService.save(creditLine);
    }
    
    @Test(expected=EntityNotFoundException.class)
    public void shouldNotSaveCreditLineWithoutHandleAndGrantor() {
        CreditLine creditLine = getTestCreditLine();
        
        Mockito.when(creditLineRepository.findByHandle(creditLine.getHandle())).thenReturn(null);
        Mockito.when(creditLineRepository.findByGrantorFirmAndGranteeFirm(creditLine.getGrantorFirm(),creditLine.getGranteeFirm())).thenReturn(null);
        Mockito.when(firmService.findByHandle(creditLine.getGrantorFirm())).thenReturn(new Firm());
        Mockito.when(firmService.findByHandle(creditLine.getGranteeFirm())).thenReturn(new Firm());
        Mockito.when(creditPoolService.findByHandle(creditLine.getCreditPool())).thenReturn(new CreditPool());
        Mockito.when(creditPoolService.findByHandleAndGrantorFirm(creditLine.getCreditPool(),creditLine.getGrantorFirm())).thenReturn(null);
        
        creditLineService.save(creditLine);
    }
    
    @Test(expected=EntityAlreadyExistsException.class)
    public void shouldNotSaveCreditLineWithDuplicatedGrantorGrantee() {
        CreditLine existingCreditLine = getTestCreditLine();
        existingCreditLine.setId(1);
        
        Mockito.when(creditLineRepository.findByHandle(existingCreditLine.getHandle())).thenReturn(null);
        Mockito.when(creditLineRepository.findByGrantorFirmAndGranteeFirm(existingCreditLine.getGrantorFirm(),existingCreditLine.getGranteeFirm())).thenReturn(existingCreditLine);
        Mockito.when(firmService.findByHandle(existingCreditLine.getGrantorFirm())).thenReturn(new Firm());
        Mockito.when(firmService.findByHandle(existingCreditLine.getGranteeFirm())).thenReturn(new Firm());
        Mockito.when(creditPoolService.findByHandle(existingCreditLine.getCreditPool())).thenReturn(new CreditPool());
        Mockito.when(creditPoolService.findByHandleAndGrantorFirm(existingCreditLine.getCreditPool(),existingCreditLine.getGrantorFirm())).thenReturn(new CreditPool());
        
        CreditLine creditLine = getTestCreditLine();
        creditLineService.save(creditLine);
    }
    
    @Test
    public void shouldSaveCreditLine() {
        CreditLine creditLine = getTestCreditLine();

        Mockito.when(creditLineRepository.findByHandle(creditLine.getHandle())).thenReturn(null);
        Mockito.when(creditLineRepository.findByGrantorFirmAndGranteeFirm(creditLine.getGrantorFirm(),creditLine.getGranteeFirm())).thenReturn(null);
        Mockito.when(firmService.findByHandle(creditLine.getGrantorFirm())).thenReturn(new Firm());
        Mockito.when(firmService.findByHandle(creditLine.getGranteeFirm())).thenReturn(new Firm());
        Mockito.when(creditPoolService.findByHandle(creditLine.getCreditPool())).thenReturn(new CreditPool());
        Mockito.when(creditPoolService.findByHandleAndGrantorFirm(creditLine.getCreditPool(),creditLine.getGrantorFirm())).thenReturn(new CreditPool());
        Mockito.when(creditLineService.save(creditLine)).thenReturn(creditLine);

        CreditLine savedCreditLine = creditLineService.save(creditLine);
        
        assertThat(savedCreditLine).isEqualTo(creditLine);
    }
    
    @Test
    public void shouldDeleteCreditLine() {
        CreditLine creditLine = getTestCreditLine();
        creditLine.setId(1);

        Mockito.when(creditLineRepository.findById(creditLine.getId())).thenReturn(Optional.of(creditLine));

        creditLineService.deleteById(creditLine.getId());
        
        Mockito.verify(creditLineRepository, Mockito.times(1)).deleteById(creditLine.getId());
    }
    
    @Test(expected=EntityNotFoundException.class)
    public void shouldNotDeleteANonExistingCreditLine() {
        Mockito.when(creditLineRepository.findById(1)).thenReturn(Optional.empty());
        creditLineService.deleteById(1);
    }
    
}