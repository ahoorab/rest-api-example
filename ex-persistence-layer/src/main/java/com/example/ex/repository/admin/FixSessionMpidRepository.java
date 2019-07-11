package com.example.ex.repository.admin;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.ex.model.entity.admin.FixSessionMpid;

/**
 * Data access object for the Fix Session Mpid entity
 * 
 * It covers only basic operations inherited from JpaRepository
 * 
 * @author Sergio Pintos <spintos@gmail.com>
 */
@Repository
public interface FixSessionMpidRepository extends JpaRepository<FixSessionMpid, Integer> {

    FixSessionMpid findByHandle(String handle);

    Set<FixSessionMpid> findByBrokerSession(String handle);

    Set<FixSessionMpid> findByMpid(String handle);
}