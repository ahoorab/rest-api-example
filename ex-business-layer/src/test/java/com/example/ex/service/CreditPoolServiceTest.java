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
import com.example.ex.model.entity.admin.CreditLine;
import com.example.ex.model.entity.admin.CreditPool;
import com.example.ex.model.entity.admin.Firm;
import com.example.ex.model.type.CreditMethod;
import com.example.ex.model.type.Status;
import com.example.ex.repository.admin.CreditPoolRepository;

@RunWith(SpringRunner.class)
public class CreditPoolServiceTest {

    @TestConfiguration
    static class CreditPoolServiceTestContextConfiguration extends ServiceTestContextConfiguration {
        @Bean
        public CreditPoolService creditPoolService() {
            return new CreditPoolService();
        }
    }

    @MockBean
    private CreditPoolRepository creditPoolRepository;
    @Autowired
    private CreditPoolService creditPoolService;
    @Autowired
    private CreditLineService creditLineService;
    @Autowired
    private FirmService firmService;
    
    protected CreditPool getTestCreditPool() {
        return new CreditPool("scot-tdbk","scot-tdb","scotia","scotia td bank",CreditMethod.NET_SHORTS,0,Integer.MAX_VALUE,75,Status.ENABLED);
    }
    
    @Test(expected=EntityAlreadyExistsException.class)
    public void shouldNotSaveCreditPoolWithDuplicatedHandle() {
        CreditPool existingCreditPool = getTestCreditPool();
        existingCreditPool.setId(1);
        
        Mockito.when(creditPoolRepository.findByHandle(existingCreditPool.getHandle())).thenReturn(existingCreditPool);
        Mockito.when(creditPoolRepository.findByMnemonic(existingCreditPool.getMnemonic())).thenReturn(null);
        Mockito.when(firmService.findByHandle(existingCreditPool.getGrantorFirm())).thenReturn(new Firm());

        CreditPool creditPool = getTestCreditPool();
        creditPoolService.save(creditPool);
    }

    @Test(expected=EntityAlreadyExistsException.class)
    public void shouldNotSaveCreditPoolWithDuplicatedMnemonic() {
        CreditPool existingCreditPool = getTestCreditPool();
        existingCreditPool.setId(1);
        
        Mockito.when(creditPoolRepository.findByMnemonic(existingCreditPool.getMnemonic())).thenReturn(existingCreditPool);
        Mockito.when(creditPoolRepository.findByHandle(existingCreditPool.getHandle())).thenReturn(null);
        Mockito.when(firmService.findByHandle(existingCreditPool.getGrantorFirm())).thenReturn(new Firm());

        CreditPool creditPool = getTestCreditPool();
        creditPoolService.save(creditPool);
    }

    @Test(expected=EntityNotFoundException.class)
    public void shouldNotSaveCreditPoolWithoutGrantorFirm() {
        CreditPool existingCreditPool = getTestCreditPool();
        
        Mockito.when(creditPoolRepository.findByHandle(existingCreditPool.getHandle())).thenReturn(null);
        Mockito.when(creditPoolRepository.findByMnemonic(existingCreditPool.getMnemonic())).thenReturn(null);
        Mockito.when(firmService.findByHandle(existingCreditPool.getGrantorFirm())).thenReturn(null);

        CreditPool creditPool = getTestCreditPool();
        creditPoolService.save(creditPool);
    }
    
    @Test
    public void shouldSaveCreditPool() {
        CreditPool creditPool = getTestCreditPool();

        Mockito.when(creditPoolRepository.save(creditPool)).thenReturn(creditPool);
        Mockito.when(creditPoolRepository.findByHandle(creditPool.getHandle())).thenReturn(null);
        Mockito.when(creditPoolRepository.findByMnemonic(creditPool.getMnemonic())).thenReturn(null);
        Mockito.when(firmService.findByHandle(creditPool.getGrantorFirm())).thenReturn(new Firm());

        CreditPool savedCreditPool = creditPoolService.save(creditPool);
        
        assertThat(savedCreditPool).isEqualTo(creditPool);
    }
    
    @Test
    public void shouldDeleteCreditPool() {
        CreditPool creditPool = getTestCreditPool();
        creditPool.setId(1);

        Mockito.when(creditPoolRepository.findById(creditPool.getId())).thenReturn(Optional.of(creditPool));
        Mockito.when(creditLineService.findByCreditPool(creditPool.getHandle())).thenReturn(new ArrayList<>());

        creditPoolService.deleteById(creditPool.getId());
        
        Mockito.verify(creditPoolRepository, Mockito.times(1)).deleteById(creditPool.getId());
    }
    
    @Test(expected=EntityReferenceException.class)
    public void shouldNotDeleteACreditPoolReferencedByCreditLines() {
        List<CreditLine> creditLines = new ArrayList<>();
        creditLines.add(new CreditLine());

        CreditPool creditPool = getTestCreditPool();
        creditPool.setId(1);

        Mockito.when(creditPoolRepository.findById(creditPool.getId())).thenReturn(Optional.of(creditPool));
        Mockito.when(creditLineService.findByCreditPool(creditPool.getHandle())).thenReturn(creditLines);

        creditPoolService.deleteById(creditPool.getId());
    }
    
    @Test(expected=EntityNotFoundException.class)
    public void shouldNotDeleteANonExistingCreditPool() {
        Mockito.when(creditPoolRepository.findById(1)).thenReturn(Optional.empty());
        creditPoolService.deleteById(1);
    }
    
}