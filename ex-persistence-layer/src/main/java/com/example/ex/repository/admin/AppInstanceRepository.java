package com.example.ex.repository.admin;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.ex.model.entity.admin.AppInstance;

/**
 * Data access object for the AppInstane entity
 * 
 * It covers only basic operations inherited from JpaRepository
 * 
 * @author Sergio Pintos <spintos@gmail.com>
 */
@Repository
public interface AppInstanceRepository extends JpaRepository<AppInstance, Integer> {

    AppInstance findByAppId(String appId);

    List<AppInstance> findByAppType(String name);

    List<AppInstance> findByFirstJvm(String handle);
    
    List<AppInstance> findBySecondJvm(String handle);
}