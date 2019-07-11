package com.example.ex.repository.admin;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.ex.model.entity.admin.AppJvm;

/**
 * Data access object for the AppJvm entity
 * 
 * It covers only basic operations inherited from JpaRepository
 * @author Sergio Pintos <spintos@gmail.com>
 */
@Repository
public interface AppJvmRepository extends JpaRepository<AppJvm, Integer> {
    
    AppJvm findByHandle(String handle);
    
    List<AppJvm> findByServer(String handle);
}