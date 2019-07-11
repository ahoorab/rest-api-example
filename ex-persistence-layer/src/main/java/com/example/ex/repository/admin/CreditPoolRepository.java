package com.example.ex.repository.admin;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.ex.model.entity.admin.CreditPool;

/**
 * Data access object for the CreditPool entity
 * 
 * It covers only basic operations inherited from JpaRepository
 * @author Sergio Pintos <spintos@gmail.com>
 */
@Repository
public interface CreditPoolRepository extends JpaRepository<CreditPool, Integer> {
    
    CreditPool findByHandle(String handle);

    CreditPool findByMnemonic(String handle);

    List<CreditPool> findByGrantorFirm(String handle);

    CreditPool findByHandleAndGrantorFirm(String handle, String firmHandle);
}