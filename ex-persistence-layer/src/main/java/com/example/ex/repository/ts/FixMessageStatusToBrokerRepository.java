package com.example.ex.repository.ts;

import org.springframework.stereotype.Repository;

import com.example.ex.model.entity.ts.FixMessageStatusToBroker;

/**
 * Data access object for the Fix Message Status To Broker
 * 
 * It covers only basic operations inherited from JpaRepository
 * @author Sergio Pintos <spintos@gmail.com>
 */
@Repository
public interface FixMessageStatusToBrokerRepository extends FixMessageRepository<FixMessageStatusToBroker> {
    
}