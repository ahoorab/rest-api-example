package com.example.ex.repository.admin;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.ex.model.entity.admin.CurrencyPair;

/**
 * Data access object for the Currency Pair entity
 * 
 * It covers only basic operations inherited from JpaRepository
 * @author Sergio Pintos <spintos@gmail.com>
 */
@Repository
public interface CurrencyPairRepository extends JpaRepository<CurrencyPair, Integer> {

    CurrencyPair findByName(String name);

    List<CurrencyPair> findByCcy1(String ccy);

    List<CurrencyPair> findByCcy2(String ccy);
}
