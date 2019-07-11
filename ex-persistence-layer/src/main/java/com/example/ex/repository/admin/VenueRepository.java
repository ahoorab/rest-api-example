package com.example.ex.repository.admin;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.ex.model.entity.admin.Venue;

/**
 * Data access object for the Venue entity
 * 
 * It covers only basic operations inherited from JpaRepository
 * @author Sergio Pintos <spintos@gmail.com>
 */
@Repository
public interface VenueRepository extends JpaRepository<Venue, Integer> {
    
    Venue findByHandle(String handle);

    Venue findByMnemonic(String handle);
}