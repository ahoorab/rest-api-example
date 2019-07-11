package com.example.ex.repository.admin;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.ex.model.entity.admin.Holiday;

/**
 * Data access object for the Holiday entity
 * 
 * It covers only basic operations inherited from JpaRepository
 * 
 * @author Sergio Pintos <spintos@gmail.com>
 */
@Repository
public interface HolidayRepository extends JpaRepository<Holiday, Integer> {
    
    Holiday findByDateAndCurrency(Date date, String currency);

    List<Holiday> findByCurrency(String name);
}