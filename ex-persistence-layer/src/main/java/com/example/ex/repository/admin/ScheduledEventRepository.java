package com.example.ex.repository.admin;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.ex.model.entity.admin.ScheduledEvent;

/**
 * Data access object for the Schedule Event entity
 * 
 * It covers only basic operations inherited from JpaRepository
 * 
 * @author Sergio Pintos <spintos@gmail.com>
 */
@Repository
public interface ScheduledEventRepository extends JpaRepository<ScheduledEvent, Integer> {

    ScheduledEvent findByHandle(String handle);

}