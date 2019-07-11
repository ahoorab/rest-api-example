package com.example.ex.repository.admin;

import static org.junit.Assert.fail;

import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ex.model.entity.admin.Venue;
import com.example.ex.model.type.Status;

import net.bytebuddy.utility.RandomString;

public class VenueRepositoryTest extends BaseJpaTest<Venue, Integer> {

    @Autowired
    private VenueRepository venueRepository;

    @Override
    protected JpaRepository<Venue, Integer> getImplementationRepository() {
        return venueRepository;
    }

    @Override
    protected Venue getNewTestEntity() {
        return new Venue("exx", "EXX", "Example Markets", "US", "None", "None", "None", 9999990f, true, Integer.MAX_VALUE, Integer.MAX_VALUE, (int)Short.MAX_VALUE, (int)Short.MAX_VALUE, Status.ENABLED);
    }

    @Override
    protected void updateEntity(Venue entity) {
        entity.setHandle("exx1");
        entity.setMnemonic("EXX1");
        entity.setName("Example Markets1");
        entity.setCountry("ARG");
        entity.setMarketSession("");
        entity.setTapeId("");
        entity.setOatsId("abc");
        entity.setVenuePostRatio(0.33f);
        entity.setIsAts(false);
        entity.setMaxSharesPerOrder(1234567890);
        entity.setMaxNotionalPerOrder(999999999);
        entity.setRank(9);
        entity.setCostToTake(9);
        entity.setStatus(Status.DISABLED);
    }
    
    @Test(expected=DataIntegrityViolationException.class)
    public void shouldNotSaveVenueWithDuplicatedHandle() {
        Venue venue = getNewTestEntity();
        venue.setHandle("duplicated");
        try {
            venueRepository.save(venue);
        } catch (Exception e) {
            fail("should save entity: " + e.getMessage());
        }

        venue = new Venue();
        updateEntity(venue);
        venue.setHandle("duplicated");
        venueRepository.save(venue);
    }
    
    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveVenueWithNullHandle() {
        Venue venue = getNewTestEntity();
        venue.setHandle(null);

        venueRepository.save(venue);
    }

    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveVenueWithHandleSizeGreaterThan12() {
        Venue venue = getNewTestEntity();
        venue.setHandle(RandomString.make(13));
        venueRepository.save(venue);
    }

    @Test(expected=DataIntegrityViolationException.class)
    public void shouldNotSaveVenueWithDuplicatedMnemonic() {
        Venue venue = getNewTestEntity();
        venue.setMnemonic("duplicated");
        try {
            venueRepository.save(venue);
        } catch (Exception e) {
            fail("should save entity: " + e.getMessage());
        }

        venue = new Venue();
        updateEntity(venue);
        venue.setMnemonic("duplicated");
        venueRepository.save(venue);
    }
    
    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveVenueWithNullMnemonic() {
        Venue venue = getNewTestEntity();
        venue.setMnemonic(null);
        venueRepository.save(venue);
    }

    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveVenueWithMnemonicSizeGreaterThan16() {
        Venue venue = getNewTestEntity();
        venue.setMnemonic(RandomString.make(17));
        venueRepository.save(venue);
    }
    
    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveVenueWithNullName() {
        Venue venue = getNewTestEntity();
        venue.setName(null);
        venueRepository.save(venue);
    }
    
    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveVenueWithNameSizeGreaterThan64() {
        Venue venue = getNewTestEntity();
        venue.setName(RandomString.make(65));
        venueRepository.save(venue);
    }

    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveVenueWithNullCountry() {
        Venue venue = getNewTestEntity();
        venue.setCountry(null);
        venueRepository.save(venue);
    }
    
    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveVenueWithCountrySizeGreaterThan4() {
        Venue venue = getNewTestEntity();
        venue.setCountry(RandomString.make(5));
        venueRepository.save(venue);
    }

    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveVenueWithNullMarketSession() {
        Venue venue = getNewTestEntity();
        venue.setMarketSession(null);
        venueRepository.save(venue);
    }
    
    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveVenueWithMarketSessionSizeGreaterThan16() {
        Venue venue = getNewTestEntity();
        venue.setMarketSession(RandomString.make(17));
        venueRepository.save(venue);
    }

    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveVenueWithNullTape() {
        Venue venue = getNewTestEntity();
        venue.setTapeId(null);
        venueRepository.save(venue);
    }
    
    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveVenueWithTapeSizeGreaterThan4() {
        Venue venue = getNewTestEntity();
        venue.setTapeId(RandomString.make(5));
        venueRepository.save(venue);
    }
    
    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveVenueWithNullOats() {
        Venue venue = getNewTestEntity();
        venue.setOatsId(null);
        venueRepository.save(venue);
    }
    
    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveVenueWithOatsSizeGreaterThan4() {
        Venue venue = getNewTestEntity();
        venue.setOatsId(RandomString.make(5));
        venueRepository.save(venue);
    }

    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveVenueWithNullVenue() {
        Venue venue = getNewTestEntity();
        venue.setVenuePostRatio(null);
        venueRepository.save(venue);
    }
    
    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveVenueWithIsAts() {
        Venue venue = getNewTestEntity();
        venue.setIsAts(null);
        venueRepository.save(venue);
    }

    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveVenueWithNullMaxShares() {
        Venue venue = getNewTestEntity();
        venue.setMaxSharesPerOrder(null);
        venueRepository.save(venue);
    }

    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveVenueWithNullMaxNotional() {
        Venue venue = getNewTestEntity();
        venue.setMaxNotionalPerOrder(null);
        venueRepository.save(venue);
    }

    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveVenueWithNullRank() {
        Venue venue = getNewTestEntity();
        venue.setRank(null);
        venueRepository.save(venue);
    }

    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveVenueWithNullCostToTake() {
        Venue venue = getNewTestEntity();
        venue.setCostToTake(null);
        venueRepository.save(venue);
    }

    @Test(expected=ConstraintViolationException.class)
    public void shouldSaveVenueWithNullStatus() {
        Venue venue = getNewTestEntity();
        venue.setStatus(null);
        venueRepository.save(venue);
    }

}