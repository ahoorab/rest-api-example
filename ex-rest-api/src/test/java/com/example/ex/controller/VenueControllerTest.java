package com.example.ex.controller;

import static org.hamcrest.Matchers.containsString;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.ex.model.entity.admin.Venue;
import com.example.ex.model.type.Status;

import net.bytebuddy.utility.RandomString;

@RunWith(SpringRunner.class)
@WebMvcTest
public class VenueControllerTest extends AdminControllerTest<Venue> {

    @Override
    public void initController() {
        super.baseUri = VenueController.BASE_URI;
        super.service = super.venueService;
        super.clazz = Venue.class;
    }

    @Override
    public List<Venue> getListOfEntities() {
        List<Venue> venues = new ArrayList<>();
        venues.add(new Venue("exx", "EXX", "Example Markets", "US", "None", "None", "None", 9999990f, true, Integer.MAX_VALUE, Integer.MAX_VALUE, (int)Short.MAX_VALUE, (int)Short.MAX_VALUE, Status.ENABLED));
        venues.add(new Venue("test", "TEST", "TEST Markets", "TEST", "None", "None", "None", 9999990f, true, 99999999, 999999999, 999999, 999999, Status.DISABLED));
        return venues;
    }

    @Override
    public Venue getNewEntity() {
        return new Venue("test", "TEST", "TEST Markets", "TEST", "None", "None", "None", 99999.1f, true, Integer.MAX_VALUE, Integer.MAX_VALUE, (int)Short.MAX_VALUE, (int)Short.MAX_VALUE, Status.ENABLED);
    }
    
    @Test
    public void shouldReturnBadRequestWhenCreateWithNullHandle() throws Exception {
        Venue venue = getNewEntity();
        venue.setHandle(null);
        super.shouldReturnBadRequestWhenCreate(venue,containsString("NotNull.venue.handle"));
    }

    @Test
    public void shouldReturnBadRequestWhenCreateWithInvalidHandleSize() throws Exception {
        Venue venue = getNewEntity();
        venue.setHandle(RandomString.make(13));
        super.shouldReturnBadRequestWhenCreate(venue,containsString("Size.venue.handle"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenCreateWithNullMnemonic() throws Exception {
        Venue venue = getNewEntity();
        venue.setMnemonic(null);
        super.shouldReturnBadRequestWhenCreate(venue,containsString("NotNull.venue.mnemonic"));
    }

    @Test
    public void shouldReturnBadRequestWhenCreateWithInvalidMnemonicSize() throws Exception {
        Venue venue = getNewEntity();
        venue.setMnemonic(RandomString.make(17));
        super.shouldReturnBadRequestWhenCreate(venue,containsString("Size.venue.mnemonic"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenCreateWithNullName() throws Exception {
        Venue venue = getNewEntity();
        venue.setName(null);
        super.shouldReturnBadRequestWhenCreate(venue,containsString("NotNull.venue.name"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenCreateWithInvalidNameSize() throws Exception {
        Venue venue = getNewEntity();
        venue.setName(RandomString.make(65));
        super.shouldReturnBadRequestWhenCreate(venue,containsString("Size.venue.name"));
    }

    @Test
    public void shouldReturnBadRequestWhenCreateWithNullCountry() throws Exception {
        Venue venue = getNewEntity();
        venue.setCountry(null);
        super.shouldReturnBadRequestWhenCreate(venue,containsString("NotNull.venue.country"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenCreateWithInvalidCountrySize() throws Exception {
        Venue venue = getNewEntity();
        venue.setCountry(RandomString.make(5));
        super.shouldReturnBadRequestWhenCreate(venue,containsString("Size.venue.country"));
    }

    @Test
    public void shouldReturnBadRequestWhenCreateWithNullMarketSession() throws Exception {
        Venue venue = getNewEntity();
        venue.setMarketSession(null);
        super.shouldReturnBadRequestWhenCreate(venue,containsString("NotNull.venue.marketSession"));
    }

    @Test
    public void shouldReturnBadRequestWhenCreateWithInvalidMarketSessionSize() throws Exception {
        Venue venue = getNewEntity();
        venue.setMarketSession(RandomString.make(17));
        super.shouldReturnBadRequestWhenCreate(venue,containsString("Size.venue.marketSession"));
    }

    @Test
    public void shouldReturnBadRequestWhenCreateWithNullTape() throws Exception {
        Venue venue = getNewEntity();
        venue.setTapeId(null);
        super.shouldReturnBadRequestWhenCreate(venue,containsString("NotNull.venue.tapeId"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenCreateWithInvalidTapeSize() throws Exception {
        Venue venue = getNewEntity();
        venue.setTapeId(RandomString.make(5));
        super.shouldReturnBadRequestWhenCreate(venue,containsString("Size.venue.tapeId"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenCreateWithNullOats() throws Exception {
        Venue venue = getNewEntity();
        venue.setOatsId(null);
        super.shouldReturnBadRequestWhenCreate(venue,containsString("NotNull.venue.oatsId"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenCreateWithInvalidOatsSize() throws Exception {
        Venue venue = getNewEntity();
        venue.setOatsId(RandomString.make(5));
        super.shouldReturnBadRequestWhenCreate(venue,containsString("Size.venue.oatsId"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenCreateWithNullPostRatio() throws Exception {
        Venue venue = getNewEntity();
        venue.setVenuePostRatio(null);
        super.shouldReturnBadRequestWhenCreate(venue,containsString("NotNull.venue.venuePostRatio"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenCreateWithNullIsAts() throws Exception {
        Venue venue = getNewEntity();
        venue.setIsAts(null);
        super.shouldReturnBadRequestWhenCreate(venue,containsString("NotNull.venue.isAts"));
    }

    @Test
    public void shouldReturnBadRequestWhenCreateWithInvalidIsAts() throws Exception {
        Venue venue = getNewEntity();
        byte[] json = super.jsonReplace(venue, "isAts", "INVALID");
        super.shouldReturnBadRequestWhenCreate(json,venue,containsString(" only \"true\" or \"false\" recognized"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenCreateWithNullMaxShares() throws Exception {
        Venue venue = getNewEntity();
        venue.setMaxSharesPerOrder(null);
        super.shouldReturnBadRequestWhenCreate(venue,containsString("NotNull.venue.maxSharesPerOrder"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenCreateWithInvalidMaxSharesSize() throws Exception {
        Venue venue = getNewEntity();
        byte[] json = super.jsonReplace(venue, "maxSharesPerOrder", Long.toString(Integer.MAX_VALUE+1L));
        super.shouldReturnBadRequestWhenCreate(json,venue,containsString("maxSharesPerOrder"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenCreateWithNullMaxNotional() throws Exception {
        Venue venue = getNewEntity();
        venue.setMaxNotionalPerOrder(null);
        super.shouldReturnBadRequestWhenCreate(venue,containsString("NotNull.venue.maxNotionalPerOrder"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenCreateWithInvalidMaxNotionalSize() throws Exception {
        Venue venue = getNewEntity();
        byte[] json = super.jsonReplace(venue, "maxNotionalPerOrder", Long.toString(Integer.MAX_VALUE+1L));
        super.shouldReturnBadRequestWhenCreate(json,venue,containsString("maxNotionalPerOrder"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenCreateWithNullRank() throws Exception {
        Venue venue = getNewEntity();
        venue.setRank(null);
        super.shouldReturnBadRequestWhenCreate(venue,containsString("NotNull.venue.rank"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenCreateWithInvalidRankSize() throws Exception {
        Venue venue = getNewEntity();
        venue.setRank(Short.MAX_VALUE+1);
        super.shouldReturnBadRequestWhenCreate(venue,containsString("Max.venue.rank"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenCreateWithNullCostToTake() throws Exception {
        Venue venue = getNewEntity();
        venue.setCostToTake(null);
        super.shouldReturnBadRequestWhenCreate(venue,containsString("NotNull.venue.costToTake"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenCreateWithInvalidCostToTakeSize() throws Exception {
        Venue venue = getNewEntity();
        venue.setCostToTake(Short.MAX_VALUE+1);
        super.shouldReturnBadRequestWhenCreate(venue,containsString("Max.venue.costToTake"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenCreateWithNullStatus() throws Exception {
        Venue venue = getNewEntity();
        venue.setStatus(null);
        super.shouldReturnBadRequestWhenCreate(venue,containsString("NotNull.venue.status"));
    }

    @Test
    public void shouldReturnBadRequestWhenCreateWithInvalidStatus() throws Exception {
        Venue venue = getNewEntity();
        byte[] json = super.jsonReplace(venue, "status", "INVALID");
        super.shouldReturnBadRequestWhenCreate(json,venue,containsString("Status` from String \"INVALID\": value not one of declared Enum instance names"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenUpdateWithNullHandle() throws Exception {
        Venue venue = getNewUpdateEntity();
        venue.setHandle(null);
        super.shouldReturnBadRequestWhenUpdate(venue,containsString("NotNull.venue.handle"));
    }

    @Test
    public void shouldReturnBadRequestWhenUpdateWithInvalidHandleSize() throws Exception {
        Venue venue = getNewUpdateEntity();
        venue.setHandle(RandomString.make(13));
        super.shouldReturnBadRequestWhenUpdate(venue,containsString("Size.venue.handle"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenUpdateWithNullMnemonic() throws Exception {
        Venue venue = getNewUpdateEntity();
        venue.setMnemonic(null);
        super.shouldReturnBadRequestWhenUpdate(venue,containsString("NotNull.venue.mnemonic"));
    }

    @Test
    public void shouldReturnBadRequestWhenUpdateWithInvalidMnemonicSize() throws Exception {
        Venue venue = getNewUpdateEntity();
        venue.setMnemonic(RandomString.make(17));
        super.shouldReturnBadRequestWhenUpdate(venue,containsString("Size.venue.mnemonic"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenUpdateWithNullName() throws Exception {
        Venue venue = getNewUpdateEntity();
        venue.setName(null);
        super.shouldReturnBadRequestWhenUpdate(venue,containsString("NotNull.venue.name"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenUpdateWithInvalidNameSize() throws Exception {
        Venue venue = getNewUpdateEntity();
        venue.setName(RandomString.make(65));
        super.shouldReturnBadRequestWhenUpdate(venue,containsString("Size.venue.name"));
    }

    @Test
    public void shouldReturnBadRequestWhenUpdateWithNullCountry() throws Exception {
        Venue venue = getNewUpdateEntity();
        venue.setCountry(null);
        super.shouldReturnBadRequestWhenUpdate(venue,containsString("NotNull.venue.country"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenUpdateWithInvalidCountrySize() throws Exception {
        Venue venue = getNewUpdateEntity();
        venue.setCountry(RandomString.make(5));
        super.shouldReturnBadRequestWhenUpdate(venue,containsString("Size.venue.country"));
    }

    @Test
    public void shouldReturnBadRequestWhenUpdateWithNullMarketSession() throws Exception {
        Venue venue = getNewUpdateEntity();
        venue.setMarketSession(null);
        super.shouldReturnBadRequestWhenUpdate(venue,containsString("NotNull.venue.marketSession"));
    }

    @Test
    public void shouldReturnBadRequestWhenUpdateWithInvalidMarketSessionSize() throws Exception {
        Venue venue = getNewUpdateEntity();
        venue.setMarketSession(RandomString.make(17));
        super.shouldReturnBadRequestWhenUpdate(venue,containsString("Size.venue.marketSession"));
    }

    @Test
    public void shouldReturnBadRequestWhenUpdateWithNullTape() throws Exception {
        Venue venue = getNewUpdateEntity();
        venue.setTapeId(null);
        super.shouldReturnBadRequestWhenUpdate(venue,containsString("NotNull.venue.tapeId"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenUpdateWithInvalidTapeSize() throws Exception {
        Venue venue = getNewUpdateEntity();
        venue.setTapeId(RandomString.make(5));
        super.shouldReturnBadRequestWhenUpdate(venue,containsString("Size.venue.tapeId"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenUpdateWithNullOats() throws Exception {
        Venue venue = getNewUpdateEntity();
        venue.setOatsId(null);
        super.shouldReturnBadRequestWhenUpdate(venue,containsString("NotNull.venue.oatsId"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenUpdateWithInvalidOatsSize() throws Exception {
        Venue venue = getNewUpdateEntity();
        venue.setOatsId(RandomString.make(5));
        super.shouldReturnBadRequestWhenUpdate(venue,containsString("Size.venue.oatsId"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenUpdateWithNullPostRatio() throws Exception {
        Venue venue = getNewUpdateEntity();
        venue.setVenuePostRatio(null);
        super.shouldReturnBadRequestWhenUpdate(venue,containsString("NotNull.venue.venuePostRatio"));
    }

    @Test
    public void shouldReturnBadRequestWhenUpdateWithNullIsAts() throws Exception {
        Venue venue = getNewUpdateEntity();
        venue.setIsAts(null);
        super.shouldReturnBadRequestWhenUpdate(venue,containsString("NotNull.venue.isAts"));
    }

    @Test
    public void shouldReturnBadRequestWhenUpdateWithInvalidIsAts() throws Exception {
        Venue venue = getNewUpdateEntity();
        byte[] json = super.jsonReplace(venue, "isAts", "INVALID");
        super.shouldReturnBadRequestWhenUpdate(json,venue,containsString(" only \"true\" or \"false\" recognized"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenUpdateWithNullMaxShares() throws Exception {
        Venue venue = getNewUpdateEntity();
        venue.setMaxSharesPerOrder(null);
        super.shouldReturnBadRequestWhenUpdate(venue,containsString("NotNull.venue.maxSharesPerOrder"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenUpdateWithInvalidMaxSharesSize() throws Exception {
        Venue venue = getNewUpdateEntity();
        byte[] json = super.jsonReplace(venue, "maxSharesPerOrder", Long.toString(Integer.MAX_VALUE+1L));
        super.shouldReturnBadRequestWhenUpdate(json,venue,containsString("maxSharesPerOrder"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenUpdateWithNullMaxNotional() throws Exception {
        Venue venue = getNewUpdateEntity();
        venue.setMaxNotionalPerOrder(null);
        super.shouldReturnBadRequestWhenUpdate(venue,containsString("NotNull.venue.maxNotionalPerOrder"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenUpdateWithInvalidMaxNotionalSize() throws Exception {
        Venue venue = getNewUpdateEntity();
        byte[] json = super.jsonReplace(venue, "maxNotionalPerOrder", Long.toString(Integer.MAX_VALUE+1L));
        super.shouldReturnBadRequestWhenUpdate(json,venue,containsString("maxNotionalPerOrder"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenUpdateWithNullRank() throws Exception {
        Venue venue = getNewUpdateEntity();
        venue.setRank(null);
        super.shouldReturnBadRequestWhenUpdate(venue,containsString("NotNull.venue.rank"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenUpdateWithInvalidRankSize() throws Exception {
        Venue venue = getNewUpdateEntity();
        venue.setRank(Short.MAX_VALUE+1);
        super.shouldReturnBadRequestWhenUpdate(venue,containsString("Max.venue.rank"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenUpdateWithNullCostToTake() throws Exception {
        Venue venue = getNewUpdateEntity();
        venue.setCostToTake(null);
        super.shouldReturnBadRequestWhenUpdate(venue,containsString("NotNull.venue.costToTake"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenUpdateWithInvalidCostToTakeSize() throws Exception {
        Venue venue = getNewUpdateEntity();
        venue.setCostToTake(Short.MAX_VALUE+1);
        super.shouldReturnBadRequestWhenUpdate(venue,containsString("Max.venue.costToTake"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenUpdateWithNullStatus() throws Exception {
        Venue venue = getNewUpdateEntity();
        venue.setStatus(null);
        super.shouldReturnBadRequestWhenUpdate(venue,containsString("NotNull.venue.status"));
    }

    @Test
    public void shouldReturnBadRequestWhenUpdateWithInvalidStatus() throws Exception {
        Venue venue = getNewUpdateEntity();
        byte[] json = super.jsonReplace(venue, "status", "INVALID");
        super.shouldReturnBadRequestWhenUpdate(json,venue,containsString("Status` from String \"INVALID\": value not one of declared Enum instance names"));
    }
}