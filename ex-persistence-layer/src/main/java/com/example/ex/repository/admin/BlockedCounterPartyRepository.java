package com.example.ex.repository.admin;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.ex.model.entity.admin.BlockedCounterParty;

/**
 * Data access object for the Blocked Counter Party entity
 * 
 * It covers only basic operations inherited from JpaRepository
 * @author Sergio Pintos <spintos@gmail.com>
 */
@Repository
public interface BlockedCounterPartyRepository extends JpaRepository<BlockedCounterParty, Integer> {
    
    BlockedCounterParty findByHandle(String handle);

    BlockedCounterParty findByMnemonic(String mnemonic);

    List<BlockedCounterParty> findByPbFirm1(String handle);

    List<BlockedCounterParty> findByPbFirm2(String handle);

    List<BlockedCounterParty> findByTradingFirm1(String handle);

    List<BlockedCounterParty> findByTradingFirm2(String handle);

    List<BlockedCounterParty> findByDealCode1(String handle);

    List<BlockedCounterParty> findByDealCode2(String handle);
}