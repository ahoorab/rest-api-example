package com.example.ex.repository.admin;

import java.sql.Date;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.ex.model.entity.admin.CurrencyPairSettleDate;

/**
 * Data access object for the Currency Pair Settle Date entity
 * 
 * It covers only basic operations inherited from JpaRepository
 * 
 * @author Sergio Pintos <spintos@gmail.com>
 */
@Repository
public interface CurrencyPairSettleDateRepository extends JpaRepository<CurrencyPairSettleDate, Integer> {

    CurrencyPairSettleDate findByCurrencyPairAndTradeDate(String currencyPair, Date tradeDate);
    
    Set<CurrencyPairSettleDate> findByCurrencyPair(String currencyPairName);

}