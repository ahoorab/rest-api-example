package com.example.ex.repository.admin.integration;

import org.junit.Test;
import javax.validation.ConstraintViolationException;
import org.springframework.test.annotation.IfProfileValue;

import com.example.ex.model.entity.admin.Venue;
import com.example.ex.repository.admin.BaseJpaTest;
import com.example.ex.repository.admin.VenueRepositoryTest;

@IfProfileValue(name = BaseJpaTest.INTEGRATION_TESTS_PROFILE, value=BaseJpaTest.JPA_PROFILE)
public class VenueJpaIntegrationTest extends VenueRepositoryTest {

    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveVenueWithRankGreaterThan32767() {
        Venue venue = getNewTestEntity();
        venue.setRank(Short.MAX_VALUE+1);
        getImplementationRepository().save(venue);
    }    
    
    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveVenueWithCostToTakeGreaterThan32767() {
        Venue venue = getNewTestEntity();
        venue.setCostToTake(Short.MAX_VALUE+1);
        getImplementationRepository().save(venue);
    }   
    
}