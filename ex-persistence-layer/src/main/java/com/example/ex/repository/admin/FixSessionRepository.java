package com.example.ex.repository.admin;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.ex.model.entity.admin.FixSession;

/**
 * Data access object for the Fix Session entity
 * 
 * It covers only basic operations inherited from JpaRepository
 * 
 * @author Sergio Pintos <spintos@gmail.com>
 */
@Repository
public interface FixSessionRepository extends JpaRepository<FixSession, Integer> {

    FixSession findByHandle(String handle);

    Set<FixSession> findByAppInstance(String appId);

    Set<FixSession> findByMpid(String mpid);
}