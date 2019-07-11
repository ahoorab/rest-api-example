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
import com.example.ex.model.entity.admin.CurrencyPair;
import com.example.ex.model.entity.admin.CurrencyPairSettleDate;
import com.example.ex.repository.admin.CurrencyPairSettleDateRepository;

/**
 * Business layer object for the Currency Pair Settle Date entity
 * 
 * It covers basic operations inherited from JpaRepository and applies business
 * validations
 * 
 * @author Sergio Pintos <spintos@gmail.com>
 */

@Service
public class CurrencyPairSettleDateService extends BaseService<CurrencyPairSettleDate> {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(CurrencyPairSettleDateService.class);

    @Autowired
    private CurrencyPairSettleDateRepository currencyPairSettleDateRepository;
    @Autowired
    private CurrencyPairService currencyPairService;

    @Override
    public CurrencyPairSettleDate save(CurrencyPairSettleDate pairDate) {
        super.validateUnique(currencyPairSettleDateRepository::findByCurrencyPairAndTradeDate,pairDate,pairDate.getCurrencyPair(),pairDate.getTradeDate());

        super.validateDependency(currencyPairService::findByName,CurrencyPair.class,pairDate.getCurrencyPair());
        
        LOGGER.debug("Saving currency pair settle date <{}>",pairDate);
        return currencyPairSettleDateRepository.save(pairDate);
    }
    
    @Override
    public void deleteById(Integer id) {
        Optional<CurrencyPairSettleDate> settleDate = this.findById(id);
        if (!settleDate.isPresent()) {
            throw new EntityNotFoundException("Currency pair settle date with id <" + id + "> was not found");
        }
        currencyPairSettleDateRepository.deleteById(id);
    }

    public Set<CurrencyPairSettleDate> findByCurrencyPairName(String name) {
        Set<CurrencyPairSettleDate> pairs = new HashSet<>();
        pairs.addAll(currencyPairSettleDateRepository.findByCurrencyPair(name));
        return pairs;
    }

    @Override
    protected JpaRepository<CurrencyPairSettleDate, Integer> getRepository() {
        return currencyPairSettleDateRepository;
    }

}