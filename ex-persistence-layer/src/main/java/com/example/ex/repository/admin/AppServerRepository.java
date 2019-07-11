package com.example.ex.repository.admin;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.ex.model.entity.admin.AppServer;

/**
 * Data access object for the AppServer entity
 * 
 * It covers only basic operations inherited from JpaRepository
 * @author Sergio Pintos <spintos@gmail.com>
 */
@Repository
public interface AppServerRepository extends JpaRepository<AppServer, Integer> {

    public AppServer findByHandle(String handle);

}
