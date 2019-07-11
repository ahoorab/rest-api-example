package com.example.ex.repository.admin;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.ex.model.entity.admin.Firm;

/**
 * Data access object for the Firm entity
 * 
 * It covers only basic operations inherited from JpaRepository
 * @author Sergio Pintos <spintos@gmail.com>
 */
@Repository
public interface FirmRepository extends JpaRepository<Firm, Integer> {
    
    Firm findByHandle(String handle);

    Firm findByMnemonic(String handle);
}