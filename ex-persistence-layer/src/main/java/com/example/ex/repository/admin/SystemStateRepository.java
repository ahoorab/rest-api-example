package com.example.ex.repository.admin;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.ex.model.entity.admin.SystemState;

/**
 * Data access object for the System State entity
 * 
 * It covers only basic operations inherited from JpaRepository
 * @author Sergio Pintos <spintos@gmail.com>
 */
@Repository
public interface SystemStateRepository extends JpaRepository<SystemState, Integer> {

    List<SystemState> findByBaseCcy(String ccy);
}
