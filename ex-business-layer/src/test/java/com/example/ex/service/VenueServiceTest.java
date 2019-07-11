package com.example.ex.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.ex.exception.EntityAlreadyExistsException;
import com.example.ex.exception.EntityNotFoundException;
import com.example.ex.model.entity.admin.Venue;
import com.example.ex.model.type.Status;
import com.example.ex.repository.admin.VenueRepository;

@RunWith(SpringRunner.class)
public class VenueServiceTest {

    @TestConfiguration
    static class VenueServiceTestContextConfiguration extends ServiceTestContextConfiguration {
        @Bean
        public VenueService venueService() {
            return new VenueService();
        }
    }

    @MockBean
    private VenueRepository venueRepository;
    @Autowired
    private VenueService venueService;
    
    private Venue getTestVenue() {
        return new Venue("exx", "EXX", "Example Markets", "US", "None", "None", "None", 1.23f, true, 12345, 67890, 0, 1, Status.ENABLED);
    }
    
    @Test(expected=EntityAlreadyExistsException.class)
    public void shouldNotSaveVenueWithDuplicatedHandle() {
        Venue existingVenue = getTestVenue();
        existingVenue.setId(1);
        
        Mockito.when(venueRepository.findByHandle(existingVenue.getHandle())).thenReturn(existingVenue);

        Venue venue = getTestVenue();
        venueService.save(venue);
    }

    @Test(expected=EntityAlreadyExistsException.class)
    public void shouldNotSaveVenueWithDuplicatedMnemonic() {
        Venue existingVenue = getTestVenue();
        existingVenue.setId(1);
        
        Mockito.when(venueRepository.findByMnemonic(existingVenue.getMnemonic())).thenReturn(existingVenue);

        Venue venue = getTestVenue();
        venueService.save(venue);
    }
    
    @Test
    public void shouldSaveVenue() {
        Venue venue = getTestVenue();

        Mockito.when(venueRepository.save(venue)).thenReturn(venue);

        Venue savedVenue = venueService.save(venue);
        
        assertThat(savedVenue).isEqualTo(venue);
    }
    
    @Test
    public void shouldDeleteVenue() {
        Venue venue = getTestVenue();
        venue.setId(1);

        Mockito.when(venueRepository.findById(venue.getId())).thenReturn(Optional.of(venue));

        venueService.deleteById(venue.getId());
        
        Mockito.verify(venueRepository, Mockito.times(1)).deleteById(venue.getId());
    }
    
    @Test(expected=EntityNotFoundException.class)
    public void shouldNotDeleteANonExistingVenue() {
        Mockito.when(venueRepository.findById(1)).thenReturn(Optional.empty());
        venueService.deleteById(1);
    }
    
}