package com.example.ex.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Date;
import java.sql.Timestamp;
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
import com.example.ex.model.entity.admin.CurrencyPair;
import com.example.ex.model.entity.admin.CurrencyPairSettleDate;
import com.example.ex.model.type.Status;
import com.example.ex.repository.admin.CurrencyPairSettleDateRepository;

@RunWith(SpringRunner.class)
public class CurrencyPairSettleDateServiceTest {

    @TestConfiguration
    static class CurrencyPairSettleDateServiceTestContextConfiguration extends ServiceTestContextConfiguration {
        @Bean
        public CurrencyPairSettleDateService currencyPairSettleDateService() {
            return new CurrencyPairSettleDateService();
        }
    }

    @MockBean
    private CurrencyPairSettleDateRepository currencyPairSettleDateRepository;
    @Autowired
    private CurrencyPairSettleDateService currencyPairSettleDateService;
    @Autowired
    private CurrencyPairService currencyPairService;
    
    private CurrencyPairSettleDate getNewTestEntity() {
        return new CurrencyPairSettleDate("USDCAD", new Date(System.currentTimeMillis()),
                new Date(System.currentTimeMillis()), 2, "notes", Status.ENABLED,
                new Timestamp(System.currentTimeMillis()));
    }
    
    @Test(expected=EntityNotFoundException.class)
    public void shouldNotSavePairSettleDateWithoutPair() {
        CurrencyPairSettleDate settleDate = getNewTestEntity();
        settleDate.setCurrencyPair(null);

        currencyPairSettleDateService.save(settleDate);
    }
    
    @Test(expected=EntityNotFoundException.class)
    public void shouldNotSavePairSettleDateWithoutInvalidPair() {
        CurrencyPairSettleDate settleDate = getNewTestEntity();
        settleDate.setCurrencyPair("xxx");

        currencyPairSettleDateService.save(settleDate);
    }

    @Test(expected=EntityAlreadyExistsException.class)
    public void shouldNotSaveSettleDateWithDuplicatedCurrencyPairAndTradeDate() {
        Date tradeDate = new Date(System.currentTimeMillis());
        CurrencyPairSettleDate existingSettleDate = getNewTestEntity();
        existingSettleDate.setCurrencyPair("USDUSD");
        existingSettleDate.setTradeDate(tradeDate);
        existingSettleDate.setId(1);
        
        Mockito.when(currencyPairSettleDateRepository.findByCurrencyPairAndTradeDate(existingSettleDate.getCurrencyPair(), existingSettleDate.getTradeDate())).thenReturn(existingSettleDate);
        
        CurrencyPairSettleDate settleDate = getNewTestEntity();
        settleDate.setCurrencyPair("USDUSD");
        settleDate.setTradeDate(tradeDate);
        currencyPairSettleDateService.save(settleDate);
    }

    @Test
    public void shouldSaveValidNewSettleDate() {
        CurrencyPairSettleDate settleDate = getNewTestEntity();
        
        Mockito.when(currencyPairService.findByName(settleDate.getCurrencyPair())).thenReturn(new CurrencyPair());
        Mockito.when(currencyPairSettleDateRepository.save(settleDate)).thenReturn(settleDate);
        
        CurrencyPairSettleDate savedSettleDate = currencyPairSettleDateService.save(settleDate);
 
        assertThat(savedSettleDate).isEqualTo(settleDate);
    }
    
    @Test
    public void shouldUpdatePair() {
        CurrencyPairSettleDate existingSettleDate = getNewTestEntity();
        existingSettleDate.setId(1);
        
        Mockito.when(currencyPairSettleDateRepository.save(existingSettleDate)).thenReturn(existingSettleDate);
        Mockito.when(currencyPairSettleDateRepository.findByCurrencyPairAndTradeDate(existingSettleDate.getCurrencyPair(), existingSettleDate.getTradeDate())).thenReturn(existingSettleDate);
        
        CurrencyPairSettleDate savedSettleDate = currencyPairSettleDateService.save(existingSettleDate);
 
        assertThat(savedSettleDate).isEqualTo(existingSettleDate);
    }

    @Test
    public void shouldReturnSettleDatesByCurrencyPairName() {
        Set<CurrencyPairSettleDate> settleDates = new HashSet<>();
        settleDates.add(new CurrencyPairSettleDate("USDCAD", new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()), 1, "notes", Status.ENABLED, new Timestamp(System.currentTimeMillis())));
        settleDates.add(new CurrencyPairSettleDate("USDCAD", new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()), 3, null, Status.ENABLED, new Timestamp(System.currentTimeMillis())));
        settleDates.add(new CurrencyPairSettleDate("USDCAD", new Date(System.currentTimeMillis()), null, 2, "", Status.DISABLED, new Timestamp(System.currentTimeMillis())));

        CurrencyPair currencyPair = new CurrencyPair();
        currencyPair.setName("USDCAD");

        Mockito.when(currencyPairSettleDateRepository.findByCurrencyPair(currencyPair.getName())).thenReturn(settleDates);
        
        Set<CurrencyPairSettleDate> foundSettleDates = currencyPairSettleDateService.findByCurrencyPairName(currencyPair.getName());
 
        assertThat(foundSettleDates).containsExactlyInAnyOrder(settleDates.toArray(new CurrencyPairSettleDate[3]));
    }

    @Test(expected=EntityNotFoundException.class)
    public void shouldNotDeleteANonExistingCurrencyPairSettleDate() {
        Mockito.when(currencyPairSettleDateRepository.findById(1)).thenReturn(Optional.empty());

        currencyPairSettleDateService.deleteById(1);
    }
    
    @Test
    public void shouldDeleteCurrencyPairSettleDate() {
        CurrencyPairSettleDate settleDate = getNewTestEntity();
        settleDate.setId(1);

        Mockito.when(currencyPairSettleDateRepository.findById(settleDate.getId())).thenReturn(Optional.of(settleDate));
        List<CurrencyPairSettleDate> list = new ArrayList<>(); 
        list.add(settleDate);
        Mockito.when(currencyPairSettleDateRepository.findAllById(Arrays.asList(settleDate.getId()))).thenReturn(list);

        currencyPairSettleDateService.deleteById(settleDate.getId());
        
        Mockito.verify(currencyPairSettleDateRepository, Mockito.times(1)).deleteById(settleDate.getId());

    }
}