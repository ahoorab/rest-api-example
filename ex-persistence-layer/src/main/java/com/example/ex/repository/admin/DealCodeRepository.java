package com.example.ex.repository.admin;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.ex.model.entity.admin.DealCode;

/**
 * Data access object for the Deal Code entity
 * 
 * It covers only basic operations inherited from JpaRepository
 * 
 * @author Sergio Pintos <spintos@gmail.com>
 */
@Repository
public interface DealCodeRepository extends JpaRepository<DealCode, Integer> {

    DealCode findByHandle(String handle);

    DealCode findByMnemonic(String mnemonic);

    List<DealCode> findByFirm(String firmName);

    List<DealCode> findByPbFirm(String firmName);

    List<DealCode> findBySubPbFirm(String firmName);
}