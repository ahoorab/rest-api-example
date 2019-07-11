package com.example.ex.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.example.ex.exception.EntityNotFoundException;
import com.example.ex.exception.EntityReferenceException;
import com.example.ex.model.entity.admin.Currency;
import com.example.ex.model.entity.admin.CurrencyPair;
import com.example.ex.model.entity.admin.Holiday;
import com.example.ex.model.entity.admin.SystemState;
import com.example.ex.repository.admin.CurrencyRepository;

/**
 * Data access object for the Currency entity
 * 
 * It covers basic operations inherited from JpaRepository
 * and applies business validations
 * @author Sergio Pintos <spintos@gmail.com>
 */
@Service
public class CurrencyService extends BaseService<Currency> {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(CurrencyService.class);
    
    @Autowired
    private CurrencyRepository currencyRepository;
    @Autowired
    private CurrencyPairService currencyPairService;
    @Autowired
    private SystemStateService systemStateService;
    @Autowired
    private HolidayService holidayService;

    @Override
    public Currency save(Currency currency) {
        super.validateUnique(currencyRepository::findByName,currency,currency.getName());

        return currencyRepository.save(currency);
    }

    @Override
    public void deleteById(Integer id) {
        Optional<Currency> currency = currencyRepository.findById(id);
        if (!currency.isPresent()) {
            throw new EntityNotFoundException("Currency with id <" + id + "> was not found");
        }

        Set<CurrencyPair> cps = currencyPairService.findByCurrencyName(currency.get().getName());
        if (!cps.isEmpty()) {
            throw new EntityReferenceException("Currency with id <" + id + "> is being referenced by Currency Pairs");
        }

        List<SystemState> ss = systemStateService.findByCurrencyName(currency.get().getName());
        if (!ss.isEmpty()) {
            throw new EntityReferenceException("Currency with id <" + id + "> is being referenced by System States");
        }

        List<Holiday> holidays = holidayService.findByCurrencyName(currency.get().getName());
        if (!holidays.isEmpty()) {
            throw new EntityReferenceException("Currency with id <" + id + "> is being referenced by Holidays");
        }
        currencyRepository.deleteById(id);
    }
    
    public Currency findByName(String name) {
        return currencyRepository.findByName(name);
    }

    @Override
    protected JpaRepository<Currency, Integer> getRepository() {
        return currencyRepository;
    }

}