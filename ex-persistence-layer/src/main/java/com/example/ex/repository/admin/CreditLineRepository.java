package com.example.ex.repository.admin;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.ex.model.entity.admin.CreditLine;

/**
 * Data access object for the CreditLine entity
 * 
 * It covers only basic operations inherited from JpaRepository
 * @author Sergio Pintos <spintos@gmail.com>
 */
@Repository
public interface CreditLineRepository extends JpaRepository<CreditLine, Integer> {
    
    CreditLine findByHandle(String handle);

    CreditLine findByGrantorFirmAndGranteeFirm(String grantorHandle, String granteeHandle);

    List<CreditLine> findByGranteeFirm(String handle);

    List<CreditLine> findByGrantorFirm(String handle);

    List<CreditLine> findByGrantorFirmAndHandle(String grantorHandle, String handle);

    List<CreditLine> findByCreditPool(String handle);

    @Query(value = "select distinct grantor_firm from creditline", nativeQuery = true)
    Set<String> findDistinctByGrantorFirm();

    @Query(value = "select distinct grantee_firm from creditline", nativeQuery = true)
    Set<String> findDistinctByGranteeFirm();
}