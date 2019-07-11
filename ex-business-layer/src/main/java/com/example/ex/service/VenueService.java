package com.example.ex.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.example.ex.exception.EntityNotFoundException;
import com.example.ex.model.entity.admin.Venue;
import com.example.ex.repository.admin.VenueRepository;

/**
 * Data access object for the Venue entity
 * 
 * It covers basic operations inherited from JpaRepository
 * and applies business validations
 * @author Sergio Pintos <spintos@gmail.com>
 */
@Service
public class VenueService extends BaseService<Venue> {

    private static final Logger LOGGER = LoggerFactory.getLogger(VenueService.class);
    
    @Autowired
    private VenueRepository venueRepository;

    @Override
    public Venue save(Venue venue) {
        super.validateUnique(venueRepository::findByHandle,venue,venue.getHandle());
        super.validateUnique(venueRepository::findByMnemonic,venue,venue.getMnemonic());
        return venueRepository.save(venue);
    }

    @Override
    public void deleteById(Integer id) {
        Optional<Venue> venue = venueRepository.findById(id);
        if (!venue.isPresent()) {
            throw new EntityNotFoundException("Venue with id <" + id + "> was not found");
        }
        venueRepository.deleteById(id);
    }

    public Venue findByHandle(String handle) {
        return venueRepository.findByHandle(handle);
    }

    @Override
    protected JpaRepository<Venue, Integer> getRepository() {
        return venueRepository;
    }

}