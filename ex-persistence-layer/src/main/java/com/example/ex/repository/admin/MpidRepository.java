package com.example.ex.repository.admin;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.ex.model.entity.admin.Mpid;

/**
 * Data access object for the Mpid entity
 * 
 * It covers only basic operations inherited from JpaRepository
 * 
 * @author Sergio Pintos <spintos@gmail.com>
 */
@Repository
public interface MpidRepository extends JpaRepository<Mpid, Integer> {
    
    Mpid findByHandle(String handle);

    List<Mpid> findByDealCode(String dealCode);

}