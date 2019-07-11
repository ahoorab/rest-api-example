package com.example.ex.repository.ts;

import org.springframework.stereotype.Repository;

import com.example.ex.model.entity.ts.FixMessageOrderCxlRejectToBroker;

/**
 * Data access object for the Fix Message Order CXL Reject To Broker
 * 
 * It covers only basic operations inherited from JpaRepository
 * @author Sergio Pintos <spintos@gmail.com>
 */
@Repository
public interface FixMessageOrderCxlRejectToBrokerRepository extends FixMessageRepository<FixMessageOrderCxlRejectToBroker> {
    
}