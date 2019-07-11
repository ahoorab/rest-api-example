package com.example.ex.controller;

import static org.hamcrest.Matchers.containsString;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.ex.model.entity.admin.BlockedCounterParty;
import com.example.ex.model.type.Status;

import net.bytebuddy.utility.RandomString;

@RunWith(SpringRunner.class)
@WebMvcTest
public class BlockedCounterPartyControllerTest extends AdminControllerTest<BlockedCounterParty> {

    @Override
    public void initController() {
        super.baseUri = BlockedCounterPartyController.BASE_URI;
        super.service = super.blockedCounterPartyService;
        super.clazz = BlockedCounterParty.class;
    }

    @Override
    public List<BlockedCounterParty> getListOfEntities() {
        List<BlockedCounterParty> bcps = new ArrayList<>();
        bcps.add(new BlockedCounterParty("hera-scnd","hera-scnd","hera","pbfirm1","tradingFirm1","scnd","pbFirm2","tradingFirm2","note",Status.ENABLED));
        bcps.add(new BlockedCounterParty("scot-dc","scotpb-hera","dealcode1","scotpb","tradingFirm1","hera","pbFirm2","tradingFirm2","",Status.DISABLED));
        return bcps;
    }

    @Override
    public BlockedCounterParty getNewEntity() {
        return new BlockedCounterParty("handle","mnemonic","dealcode1","pbfirm1","tradingFirm1","dealcode2","pbFirm2","tradingFirm2","   ",Status.DISABLED);
    }
    
    @Test
    public void shouldReturnBadRequestWhenCreateWithNullHandle() throws Exception {
        BlockedCounterParty blockedCounterParty = getNewEntity();
        blockedCounterParty.setHandle(null);
        super.shouldReturnBadRequestWhenCreate(blockedCounterParty,containsString("NotNull.blockedCounterParty.handle"));
    }

    @Test
    public void shouldReturnBadRequestWhenCreateWithInvalidHandleSize() throws Exception {
        BlockedCounterParty blockedCounterParty = getNewEntity();
        blockedCounterParty.setHandle(RandomString.make(13));
        super.shouldReturnBadRequestWhenCreate(blockedCounterParty,containsString("Size.blockedCounterParty.handle"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenCreateWithNullMnemonic() throws Exception {
        BlockedCounterParty blockedCounterParty = getNewEntity();
        blockedCounterParty.setMnemonic(null);
        super.shouldReturnBadRequestWhenCreate(blockedCounterParty,containsString("NotNull.blockedCounterParty.mnemonic"));
    }

    @Test
    public void shouldReturnBadRequestWhenCreateWithInvalidMnemonicSize() throws Exception {
        BlockedCounterParty blockedCounterParty = getNewEntity();
        blockedCounterParty.setMnemonic(RandomString.make(17));
        super.shouldReturnBadRequestWhenCreate(blockedCounterParty,containsString("Size.blockedCounterParty.mnemonic"));
    }

    @Test
    public void shouldReturnCreatedWhenCreateWithNullDealCode1() throws Exception {
        BlockedCounterParty blockedCounterParty = getNewEntity();
        blockedCounterParty.setDealCode1(null);
        super.shouldReturnCreated(blockedCounterParty);
    }
    
    @Test
    public void shouldReturnBadRequestWhenCreateWithInvalidDealCode1Size() throws Exception {
        BlockedCounterParty blockedCounterParty = getNewEntity();
        blockedCounterParty.setDealCode1(RandomString.make(13));
        super.shouldReturnBadRequestWhenCreate(blockedCounterParty,containsString("Size.blockedCounterParty.dealCode1"));
    }
    
    @Test
    public void shouldReturnCreatedWhenCreateWithNullDealCode2() throws Exception {
        BlockedCounterParty blockedCounterParty = getNewEntity();
        blockedCounterParty.setDealCode2(null);
        super.shouldReturnCreated(blockedCounterParty);
    }
    
    @Test
    public void shouldReturnBadRequestWhenCreateWithInvalidDealCode2Size() throws Exception {
        BlockedCounterParty blockedCounterParty = getNewEntity();
        blockedCounterParty.setDealCode2(RandomString.make(13));
        super.shouldReturnBadRequestWhenCreate(blockedCounterParty,containsString("Size.blockedCounterParty.dealCode2"));
    }
    
    @Test
    public void shouldReturnCreatedWhenCreateWithNullPbFirm1() throws Exception {
        BlockedCounterParty blockedCounterParty = getNewEntity();
        blockedCounterParty.setPbFirm1(null);
        super.shouldReturnCreated(blockedCounterParty);
    }
    
    @Test
    public void shouldReturnBadRequestWhenCreateWithInvalidPbFirm1Size() throws Exception {
        BlockedCounterParty blockedCounterParty = getNewEntity();
        blockedCounterParty.setPbFirm1(RandomString.make(13));
        super.shouldReturnBadRequestWhenCreate(blockedCounterParty,containsString("Size.blockedCounterParty.pbFirm1"));
    }
    
    @Test
    public void shouldReturnCreatedWhenCreateWithNullPbFirm2() throws Exception {
        BlockedCounterParty blockedCounterParty = getNewEntity();
        blockedCounterParty.setPbFirm2(null);
        super.shouldReturnCreated(blockedCounterParty);
    }
    
    @Test
    public void shouldReturnBadRequestWhenCreateWithInvalidPbFirm2Size() throws Exception {
        BlockedCounterParty blockedCounterParty = getNewEntity();
        blockedCounterParty.setPbFirm2(RandomString.make(13));
        super.shouldReturnBadRequestWhenCreate(blockedCounterParty,containsString("Size.blockedCounterParty.pbFirm2"));
    }
    
    @Test
    public void shouldReturnCreatedWhenCreateWithNullTradingFirm1() throws Exception {
        BlockedCounterParty blockedCounterParty = getNewEntity();
        blockedCounterParty.setTradingFirm1(null);
        super.shouldReturnCreated(blockedCounterParty);
    }
    
    @Test
    public void shouldReturnBadRequestWhenCreateWithInvalidTradingFirm1Size() throws Exception {
        BlockedCounterParty blockedCounterParty = getNewEntity();
        blockedCounterParty.setTradingFirm1(RandomString.make(13));
        super.shouldReturnBadRequestWhenCreate(blockedCounterParty,containsString("Size.blockedCounterParty.tradingFirm1"));
    }
    
    @Test
    public void shouldReturnCreatedWhenCreateWithNullTradingFirm2() throws Exception {
        BlockedCounterParty blockedCounterParty = getNewEntity();
        blockedCounterParty.setTradingFirm2(null);
        super.shouldReturnCreated(blockedCounterParty);
    }
    
    @Test
    public void shouldReturnBadRequestWhenCreateWithInvalidTradingFirm2Size() throws Exception {
        BlockedCounterParty blockedCounterParty = getNewEntity();
        blockedCounterParty.setTradingFirm2(RandomString.make(13));
        super.shouldReturnBadRequestWhenCreate(blockedCounterParty,containsString("Size.blockedCounterParty.tradingFirm2"));
    }
    
    @Test
    public void shouldReturnCreatedWhenCreateWithNullNotes() throws Exception {
        BlockedCounterParty blockedCounterParty = getNewEntity();
        blockedCounterParty.setNotes(null);
        super.shouldReturnCreated(blockedCounterParty);
    }
    
    @Test
    public void shouldReturnBadRequestWhenCreateWithInvalidNotesSize() throws Exception {
        BlockedCounterParty blockedCounterParty = getNewEntity();
        blockedCounterParty.setNotes(RandomString.make(65));
        super.shouldReturnBadRequestWhenCreate(blockedCounterParty,containsString("Size.blockedCounterParty.notes"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenCreateWithNullStatus() throws Exception {
        BlockedCounterParty blockedCounterParty = getNewEntity();
        blockedCounterParty.setStatus(null);
        super.shouldReturnBadRequestWhenCreate(blockedCounterParty,containsString("NotNull.blockedCounterParty.status"));
    }

    @Test
    public void shouldReturnBadRequestWhenCreateWithInvalidStatus() throws Exception {
        BlockedCounterParty blockedCounterParty = getNewEntity();
        byte[] json = super.jsonReplace(blockedCounterParty, "status", "INVALID");
        super.shouldReturnBadRequestWhenCreate(json,blockedCounterParty,containsString("Status` from String \"INVALID\": value not one of declared Enum instance names"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenUpdateWithNullHandle() throws Exception {
        BlockedCounterParty blockedCounterParty = getNewUpdateEntity();
        blockedCounterParty.setHandle(null);
        super.shouldReturnBadRequestWhenUpdate(blockedCounterParty,containsString("NotNull.blockedCounterParty.handle"));
    }

    @Test
    public void shouldReturnBadRequestWhenUpdateWithInvalidHandleSize() throws Exception {
        BlockedCounterParty blockedCounterParty = getNewUpdateEntity();
        blockedCounterParty.setHandle(RandomString.make(13));
        super.shouldReturnBadRequestWhenUpdate(blockedCounterParty,containsString("Size.blockedCounterParty.handle"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenUpdateWithNullMnemonic() throws Exception {
        BlockedCounterParty blockedCounterParty = getNewUpdateEntity();
        blockedCounterParty.setMnemonic(null);
        super.shouldReturnBadRequestWhenUpdate(blockedCounterParty,containsString("NotNull.blockedCounterParty.mnemonic"));
    }

    @Test
    public void shouldReturnBadRequestWhenUpdateWithInvalidMnemonicSize() throws Exception {
        BlockedCounterParty blockedCounterParty = getNewUpdateEntity();
        blockedCounterParty.setMnemonic(RandomString.make(17));
        super.shouldReturnBadRequestWhenUpdate(blockedCounterParty,containsString("Size.blockedCounterParty.mnemonic"));
    }

    @Test
    public void shouldReturnUpdatedWhenUpdateWithNullDealCode1() throws Exception {
        BlockedCounterParty blockedCounterParty = getNewUpdateEntity();
        blockedCounterParty.setDealCode1(null);

        Mockito.when(service.findById(blockedCounterParty.getId())).thenReturn(Optional.of(blockedCounterParty));

        super.shouldReturnUpdated(blockedCounterParty);
    }
    
    @Test
    public void shouldReturnBadRequestWhenUpdateWithInvalidDealCode1Size() throws Exception {
        BlockedCounterParty blockedCounterParty = getNewUpdateEntity();
        blockedCounterParty.setDealCode1(RandomString.make(13));
        super.shouldReturnBadRequestWhenUpdate(blockedCounterParty,containsString("Size.blockedCounterParty.dealCode1"));
    }
    
    @Test
    public void shouldReturnUpdatedWhenUpdateWithNullDealCode2() throws Exception {
        BlockedCounterParty blockedCounterParty = getNewUpdateEntity();
        blockedCounterParty.setDealCode2(null);

        Mockito.when(service.findById(blockedCounterParty.getId())).thenReturn(Optional.of(blockedCounterParty));

        super.shouldReturnUpdated(blockedCounterParty);
    }
    
    @Test
    public void shouldReturnBadRequestWhenUpdateWithInvalidDealCode2Size() throws Exception {
        BlockedCounterParty blockedCounterParty = getNewUpdateEntity();
        blockedCounterParty.setDealCode2(RandomString.make(13));
        super.shouldReturnBadRequestWhenUpdate(blockedCounterParty,containsString("Size.blockedCounterParty.dealCode2"));
    }
    
    @Test
    public void shouldReturnUpdatedWhenUpdateWithNullPbFirm1() throws Exception {
        BlockedCounterParty blockedCounterParty = getNewUpdateEntity();
        blockedCounterParty.setPbFirm1(null);

        Mockito.when(service.findById(blockedCounterParty.getId())).thenReturn(Optional.of(blockedCounterParty));

        super.shouldReturnUpdated(blockedCounterParty);
    }
    
    @Test
    public void shouldReturnBadRequestWhenUpdateWithInvalidPbFirm1Size() throws Exception {
        BlockedCounterParty blockedCounterParty = getNewUpdateEntity();
        blockedCounterParty.setPbFirm1(RandomString.make(13));
        super.shouldReturnBadRequestWhenUpdate(blockedCounterParty,containsString("Size.blockedCounterParty.pbFirm1"));
    }
    
    @Test
    public void shouldReturnUpdatedWhenUpdateWithNullPbFirm2() throws Exception {
        BlockedCounterParty blockedCounterParty = getNewUpdateEntity();
        blockedCounterParty.setPbFirm2(null);

        Mockito.when(service.findById(blockedCounterParty.getId())).thenReturn(Optional.of(blockedCounterParty));

        super.shouldReturnUpdated(blockedCounterParty);
    }
    
    @Test
    public void shouldReturnBadRequestWhenUpdateWithInvalidPbFirm2Size() throws Exception {
        BlockedCounterParty blockedCounterParty = getNewUpdateEntity();
        blockedCounterParty.setPbFirm2(RandomString.make(13));
        super.shouldReturnBadRequestWhenUpdate(blockedCounterParty,containsString("Size.blockedCounterParty.pbFirm2"));
    }
    
    @Test
    public void shouldReturnUpdatedWhenUpdateWithNullTradingFirm1() throws Exception {
        BlockedCounterParty blockedCounterParty = getNewUpdateEntity();
        blockedCounterParty.setTradingFirm1(null);

        Mockito.when(service.findById(blockedCounterParty.getId())).thenReturn(Optional.of(blockedCounterParty));

        super.shouldReturnUpdated(blockedCounterParty);
    }
    
    @Test
    public void shouldReturnBadRequestWhenUpdateWithInvalidTradingFirm1Size() throws Exception {
        BlockedCounterParty blockedCounterParty = getNewUpdateEntity();
        blockedCounterParty.setTradingFirm1(RandomString.make(13));
        super.shouldReturnBadRequestWhenUpdate(blockedCounterParty,containsString("Size.blockedCounterParty.tradingFirm1"));
    }
    
    @Test
    public void shouldReturnUpdatedWhenUpdateWithNullTradingFirm2() throws Exception {
        BlockedCounterParty blockedCounterParty = getNewUpdateEntity();
        blockedCounterParty.setTradingFirm2(null);

        Mockito.when(service.findById(blockedCounterParty.getId())).thenReturn(Optional.of(blockedCounterParty));

        super.shouldReturnUpdated(blockedCounterParty);
    }
    
    @Test
    public void shouldReturnBadRequestWhenUpdateWithInvalidTradingFirm2Size() throws Exception {
        BlockedCounterParty blockedCounterParty = getNewUpdateEntity();
        blockedCounterParty.setTradingFirm2(RandomString.make(13));
        super.shouldReturnBadRequestWhenUpdate(blockedCounterParty,containsString("Size.blockedCounterParty.tradingFirm2"));
    }
    
    @Test
    public void shouldReturnUpdatedWhenUpdateWithNullNotes() throws Exception {
        BlockedCounterParty blockedCounterParty = getNewUpdateEntity();
        blockedCounterParty.setNotes(null);

        Mockito.when(service.findById(blockedCounterParty.getId())).thenReturn(Optional.of(blockedCounterParty));

        super.shouldReturnUpdated(blockedCounterParty);
    }
    
    @Test
    public void shouldReturnBadRequestWhenUpdateWithInvalidNotesSize() throws Exception {
        BlockedCounterParty blockedCounterParty = getNewUpdateEntity();
        blockedCounterParty.setNotes(RandomString.make(65));
        super.shouldReturnBadRequestWhenUpdate(blockedCounterParty,containsString("Size.blockedCounterParty.notes"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenUpdateWithNullStatus() throws Exception {
        BlockedCounterParty blockedCounterParty = getNewUpdateEntity();
        blockedCounterParty.setStatus(null);
        super.shouldReturnBadRequestWhenUpdate(blockedCounterParty,containsString("NotNull.blockedCounterParty.status"));
    }

    @Test
    public void shouldReturnBadRequestWhenUpdateWithInvalidStatus() throws Exception {
        BlockedCounterParty blockedCounterParty = getNewUpdateEntity();
        byte[] json = super.jsonReplace(blockedCounterParty, "status", "INVALID");
        super.shouldReturnBadRequestWhenUpdate(json,blockedCounterParty,containsString("Status` from String \"INVALID\": value not one of declared Enum instance names"));
    }
    
}