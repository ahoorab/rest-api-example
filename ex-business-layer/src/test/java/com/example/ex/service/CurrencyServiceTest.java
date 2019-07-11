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
import com.example.ex.model.entity.admin.Holiday;
import com.example.ex.model.entity.admin.SystemState;
import com.example.ex.model.type.Status;
import com.example.ex.repository.admin.CurrencyRepository;

@RunWith(SpringRunner.class)
public class CurrencyServiceTest {

    @TestConfiguration
    static class CurrencyServiceTestContextConfiguration extends ServiceTestContextConfiguration {
        @Bean
        public CurrencyService currencyService() {
            return new CurrencyService();
        }
    }

    @MockBean
    private CurrencyRepository currencyRepository;
    @Autowired
    private CurrencyService currencyService;
    @Autowired
    private CurrencyPairService currencyPairService;
    @Autowired
    private SystemStateService systemStateService;
    @Autowired
    private HolidayService holidayService;
    
    private Currency getTestCurrency() {
        return new Currency("ARS", 1, true, 0, 1, 1, Status.ENABLED);
    }
    
    @Test(expected=EntityAlreadyExistsException.class)
    public void shouldNotSaveCurrencyWithDuplicatedName() {
        Currency existingCurrency = getTestCurrency();
        existingCurrency.setId(1);
        
        Mockito.when(currencyRepository.findByName(existingCurrency.getName())).thenReturn(existingCurrency);

        Currency currency = getTestCurrency();
        currencyService.save(currency);
    }

    @Test
    public void shouldSaveCurrency() {
        Currency currency = getTestCurrency();

        Mockito.when(currencyRepository.save(currency)).thenReturn(currency);

        Currency savedCurrency = currencyService.save(currency);
        
        assertThat(savedCurrency).isEqualTo(currency);
    }
    
    @Test(expected=EntityReferenceException.class)
    public void shouldNotDeleteAnCurrencyReferencedByCurrencyPair() {
        Currency currency = getTestCurrency();
        currency.setId(1);
        
        Set<CurrencyPair> pairs = new HashSet<>();
        pairs.add(new CurrencyPair());

        Mockito.when(currencyRepository.findById(currency.getId())).thenReturn(Optional.of(currency));
        Mockito.when(currencyPairService.findByCurrencyName(currency.getName())).thenReturn(pairs);
        Mockito.when(systemStateService.findByCurrencyName(currency.getName())).thenReturn(new ArrayList<>());
        Mockito.when(holidayService.findByCurrencyName(currency.getName())).thenReturn(new ArrayList<>());

        currencyService.deleteById(currency.getId());
    }
    
    @Test(expected=EntityReferenceException.class)
    public void shouldNotDeleteAnCurrencyReferencedBySystemState() {
        Currency currency = getTestCurrency();
        currency.setId(1);
        
        List<SystemState> states = new ArrayList<>();
        states.add(new SystemState());

        Mockito.when(currencyRepository.findById(currency.getId())).thenReturn(Optional.of(currency));
        Mockito.when(currencyPairService.findByCurrencyName(currency.getName())).thenReturn(new HashSet<>());
        Mockito.when(systemStateService.findByCurrencyName(currency.getName())).thenReturn(states);
        Mockito.when(holidayService.findByCurrencyName(currency.getName())).thenReturn(new ArrayList<>());

        currencyService.deleteById(currency.getId());
    }
    
    @Test(expected=EntityReferenceException.class)
    public void shouldNotDeleteAnCurrencyReferencedByHoliday() {
        Currency currency = getTestCurrency();
        currency.setId(1);
        
        List<Holiday> holidays = new ArrayList<>();
        holidays.add(new Holiday());

        Mockito.when(currencyRepository.findById(currency.getId())).thenReturn(Optional.of(currency));
        Mockito.when(currencyPairService.findByCurrencyName(currency.getName())).thenReturn(new HashSet<>());
        Mockito.when(systemStateService.findByCurrencyName(currency.getName())).thenReturn(new ArrayList<>());
        Mockito.when(holidayService.findByCurrencyName(currency.getName())).thenReturn(holidays);

        currencyService.deleteById(currency.getId());
    }
    
    @Test
    public void shouldDeleteCurrency() {
        Currency currency = getTestCurrency();
        currency.setId(1);

        Mockito.when(currencyRepository.findById(currency.getId())).thenReturn(Optional.of(currency));
        Mockito.when(currencyPairService.findByCurrencyName(currency.getName())).thenReturn(new HashSet<>());
        Mockito.when(systemStateService.findByCurrencyName(currency.getName())).thenReturn(new ArrayList<>());
        Mockito.when(holidayService.findByCurrencyName(currency.getName())).thenReturn(new ArrayList<>());

        currencyService.deleteById(currency.getId());
        
        Mockito.verify(currencyRepository, Mockito.times(1)).deleteById(currency.getId());
    }
    
    @Test(expected=EntityNotFoundException.class)
    public void shouldNotDeleteANonExistingCurrency() {
        Mockito.when(currencyRepository.findById(1)).thenReturn(Optional.empty());
        currencyService.deleteById(1);
    }
    
}