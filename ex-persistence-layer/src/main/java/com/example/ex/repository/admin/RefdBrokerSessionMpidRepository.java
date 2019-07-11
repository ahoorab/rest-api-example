package com.example.ex.repository.admin;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.ex.model.entity.admin.RefdBrokerSessionMpid;

/**
 * Data access object for the view Refd Broker Session Mpid
 * 
 * It covers only basic operations inherited from JpaRepository
 * 
 * @author Sergio Pintos <spintos@gmail.com>
 */
@Repository
public interface RefdBrokerSessionMpidRepository extends JpaRepository<RefdBrokerSessionMpid, Integer> {

}