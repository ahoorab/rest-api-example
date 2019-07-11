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
import com.example.ex.model.entity.admin.Currency;
import com.example.ex.model.entity.admin.CurrencyPair;
import com.example.ex.model.entity.admin.CurrencyPairSettleDate;
import com.example.ex.model.type.Status;
import com.example.ex.repository.admin.CurrencyPairRepository;

@RunWith(SpringRunner.class)
public class CurrencyPairServiceTest {

    @TestConfiguration
    static class CurrencyPairServiceTestContextConfiguration extends ServiceTestContextConfiguration {
        @Bean
        public CurrencyPairService currencyPairService() {
            return new CurrencyPairService();
        }
    }

    @MockBean
    private CurrencyPairRepository currencyPairRepository;
    @Autowired
    private CurrencyPairService currencyPairService;
    @Autowired
    private CurrencyService currencyService;
    @Autowired
    private CurrencyPairSettleDateService currencyPairSettleDateService;
    
    private CurrencyPair getNewTestEntity() {
        return new CurrencyPair("AURUSD","AUR","USD",true,2,2,true,true,Status.ENABLED,true,0D,0D,0,0);
    }
    
    @Test(expected=EntityNotFoundException.class)
    public void shouldNotSavePairWithoutCcy1() {
        CurrencyPair pair = getNewTestEntity();
        pair.setCcy1(null);

        Mockito.when(currencyService.findByName(pair.getCcy2())).thenReturn(new Currency());

        currencyPairService.save(pair);
    }

    @Test(expected=EntityNotFoundException.class)
    public void shouldNotSavePairWithInvalidCcy1() {
        CurrencyPair pair = getNewTestEntity();
        pair.setCcy1("xxx");

        Mockito.when(currencyService.findByName(pair.getCcy2())).thenReturn(new Currency());

        currencyPairService.save(pair);
    }

    @Test(expected=EntityNotFoundException.class)
    public void shouldNotSavePairWithoutCcy2() {
        CurrencyPair pair = getNewTestEntity();
        pair.setCcy2(null);

        Mockito.when(currencyService.findByName(pair.getCcy1())).thenReturn(new Currency());

        currencyPairService.save(pair);
    }
    
    @Test(expected=EntityNotFoundException.class)
    public void shouldNotSavePairWithInvalidCcy2() {
        CurrencyPair pair = getNewTestEntity();
        pair.setCcy2("xxx");

        Mockito.when(currencyService.findByName(pair.getCcy1())).thenReturn(new Currency());

        currencyPairService.save(pair);
    }

    @Test(expected=EntityAlreadyExistsException.class)
    public void shouldNotSavePairWithDuplicatedName() {
        CurrencyPair existingPair = getNewTestEntity();
        existingPair.setId(1);
        
        Mockito.when(currencyPairRepository.findByName(existingPair.getName())).thenReturn(existingPair);
        
        CurrencyPair pair = getNewTestEntity();
        currencyPairService.save(pair);
    }

    @Test
    public void shouldSaveValidNewPair() {
        CurrencyPair pair = getNewTestEntity();
        
        Mockito.when(currencyService.findByName(pair.getCcy1())).thenReturn(new Currency());
        Mockito.when(currencyService.findByName(pair.getCcy2())).thenReturn(new Currency());
        Mockito.when(currencyPairRepository.save(pair)).thenReturn(pair);
        
        CurrencyPair savedPair = currencyPairService.save(pair);
 
        assertThat(savedPair).isEqualTo(pair);
    }
    
    @Test
    public void shouldUpdatePair() {
        CurrencyPair existingPair = getNewTestEntity();
        existingPair.setId(1);
        
        Mockito.when(currencyService.findByName(existingPair.getCcy1())).thenReturn(new Currency());
        Mockito.when(currencyService.findByName(existingPair.getCcy2())).thenReturn(new Currency());
        Mockito.when(currencyPairRepository.save(existingPair)).thenReturn(existingPair);
        Mockito.when(currencyPairRepository.findByName(existingPair.getName())).thenReturn(existingPair);
        
        CurrencyPair savedPair = currencyPairService.save(existingPair);
 
        assertThat(savedPair).isEqualTo(existingPair);
    }

    @Test
    public void shouldReturnCurrencyPairsByCurrency() {
        List<CurrencyPair> ccy1s = new ArrayList<>();
        ccy1s.add(new CurrencyPair("USDARS","USD","ARS",true,2,2,true,true,Status.ENABLED,true,0D,0D,0,0));
        ccy1s.add(new CurrencyPair("USDAUR","USD","AUR",true,2,2,true,true,Status.ENABLED,true,0D,0D,0,0));

        List<CurrencyPair> ccy2s = new ArrayList<>();
        ccy2s.add(new CurrencyPair("USDARS","USD","ARS",true,2,2,true,true,Status.ENABLED,true,0D,0D,0,0));
        ccy2s.add(new CurrencyPair("ARSAUR","ARS","AUR",true,2,2,true,true,Status.ENABLED,true,0D,0D,0,0));

        Currency currency = new Currency();
        currency.setName("USD");

        Mockito.when(currencyPairRepository.findByCcy1(currency.getName())).thenReturn(ccy1s);
        Mockito.when(currencyPairRepository.findByCcy2(currency.getName())).thenReturn(ccy2s);
        
        Set<CurrencyPair> pairs = currencyPairService.findByCurrencyName(currency.getName());
 
        Set<CurrencyPair> expectedPairs = new HashSet<>();
        expectedPairs.addAll(ccy2s);
        expectedPairs.addAll(ccy1s);

        assertThat(pairs).containsExactlyInAnyOrder(expectedPairs.toArray(new CurrencyPair[3]));
    }
    
    @Test(expected=EntityNotFoundException.class)
    public void shouldNotDeleteANonExistingCurrencyPair() {
        Mockito.when(currencyPairRepository.findById(1)).thenReturn(Optional.empty());
        currencyPairService.deleteById(1);
    }
    
    @Test(expected=EntityReferenceException.class)
    public void shouldNotDeleteAReferencedCurrencyPairByCurrencyPairSettleDates() {
        Set<CurrencyPairSettleDate> settleDates = new HashSet<>();
        settleDates.add(new CurrencyPairSettleDate());
        CurrencyPair cp = new CurrencyPair();
        cp.setName("USDUSD");

        Mockito.when(currencyPairRepository.findById(1)).thenReturn(Optional.of(cp));
        Mockito.when(currencyPairSettleDateService.findByCurrencyPairName(cp.getName())).thenReturn(settleDates);

        currencyPairService.deleteById(1);
    }

    @Test
    public void shouldDeleteANonReferencedCurrencyPair() {
        CurrencyPair currencyPair = getNewTestEntity();
        currencyPair.setId(1);

        Mockito.when(currencyPairRepository.findById(currencyPair.getId())).thenReturn(Optional.of(currencyPair));
        Mockito.when(currencyPairSettleDateService.findByCurrencyPairName("name")).thenReturn(new HashSet<>());

        currencyPairService.deleteById(currencyPair.getId());
        
        Mockito.verify(currencyPairRepository, Mockito.times(1)).deleteById(currencyPair.getId());
        Mockito.verify(currencyPairRepository, Mockito.times(1)).findById(currencyPair.getId());
    }

}