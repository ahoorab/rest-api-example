package com.example.ex.service;

import java.util.HashSet;
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
import com.example.ex.model.entity.admin.CurrencyPairSettleDate;
import com.example.ex.repository.admin.CurrencyPairRepository;

/**
 * Business layer object for the Currency Pair entity
 * 
 * It covers basic operations inherited from JpaRepository and applies business
 * validations
 * 
 * @author Sergio Pintos <spintos@gmail.com>
 */

@Service
public class CurrencyPairService extends BaseService<CurrencyPair> {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(CurrencyPairService.class);

    @Autowired
    private CurrencyPairRepository currencyPairRepository;
    @Autowired
    private CurrencyService currencyService;
    @Autowired
    private CurrencyPairSettleDateService currencyPairSettleDateService;

    @Override
    public CurrencyPair save(CurrencyPair pair) {
        super.validateUnique(currencyPairRepository::findByName,pair,pair.getName());

        super.validateDependency(currencyService::findByName,Currency.class,pair.getCcy1());
        super.validateDependency(currencyService::findByName,Currency.class,pair.getCcy2());
        
        LOGGER.debug("Saving currency pair <{}>",pair);
        return currencyPairRepository.save(pair);
    }
    
    @Override
    public void deleteById(Integer id) {
        Optional<CurrencyPair> currencyPair = currencyPairRepository.findById(id);
        if (!currencyPair.isPresent()) {
            throw new EntityNotFoundException("Currency Pair with id <" + id + "> was not found");
        }
        Set<CurrencyPairSettleDate> cps = currencyPairSettleDateService.findByCurrencyPairName(currencyPair.get().getName());
        if (!cps.isEmpty()) {
            throw new EntityReferenceException("Currency Pair with id <" + id + "> is being referenced by Currency Pairs Settle Dates");
        }
        currencyPairRepository.deleteById(id);
    }

    public CurrencyPair findByName(String name) {
        return currencyPairRepository.findByName(name);
    }
    
    public Set<CurrencyPair> findByCurrencyName(String name) {
        Set<CurrencyPair> pairs = new HashSet<>();
        pairs.addAll(currencyPairRepository.findByCcy1(name));
        pairs.addAll(currencyPairRepository.findByCcy2(name));
        return pairs;
    }

    @Override
    protected JpaRepository<CurrencyPair, Integer> getRepository() {
        return currencyPairRepository;
    }

}