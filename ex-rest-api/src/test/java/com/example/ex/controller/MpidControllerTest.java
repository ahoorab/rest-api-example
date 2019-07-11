package com.example.ex.controller;

import static org.hamcrest.Matchers.containsString;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import com.example.ex.model.entity.admin.Mpid;
import com.example.ex.model.type.BrokerType;
import com.example.ex.model.type.Status;

import net.bytebuddy.utility.RandomString;

@WebMvcTest
public class MpidControllerTest extends AdminControllerTest<Mpid> {

    @Override
    public void initController() {
        super.baseUri = MpidController.BASE_URI;
        super.service = this.mpidService;
        super.clazz = Mpid.class;
    }

    @Override
    public List<Mpid> getListOfEntities() {
        List<Mpid> mpids = new ArrayList<>();

        mpids.add(new Mpid("first","First eagle","FRST","frst","scotia",BrokerType.NONE,BrokerType.NONE,1,0L,"US",Status.ENABLED));
        mpids.add(new Mpid("exx","Example","EXXX","exx",null,BrokerType.NONE,BrokerType.NONE,1,0L,"US",Status.ENABLED));
        return mpids;
    }

    @Override
    public Mpid getNewEntity() {
        return new Mpid("scotia","Scotia Bank","SCOT","scotia",null,BrokerType.BROKER,BrokerType.BROKER,1,0L,"US",Status.ENABLED);
    }
    
    @Test
    public void shouldReturnBadRequestWhenCreateWithNullHandle() throws Exception {
        Mpid mpid = getNewEntity();
        mpid.setHandle(null);
        super.shouldReturnBadRequestWhenCreate(mpid,containsString("NotNull.mpid.handle"));
    }

    @Test
    public void shouldReturnBadRequestWhenCreateWithInvalidHandleSize() throws Exception {
        Mpid mpid = getNewEntity();
        mpid.setHandle(RandomString.make(13));
        super.shouldReturnBadRequestWhenCreate(mpid,containsString("Size.mpid.handle"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenCreateWithNullName() throws Exception {
        Mpid mpid = getNewEntity();
        mpid.setName(null);
        super.shouldReturnBadRequestWhenCreate(mpid,containsString("NotNull.mpid.name"));
    }

    @Test
    public void shouldReturnBadRequestWhenCreateWithInvalidNameSize() throws Exception {
        Mpid mpid = getNewEntity();
        mpid.setName(RandomString.make(65));
        super.shouldReturnBadRequestWhenCreate(mpid,containsString("Size.mpid.name"));
    }

    @Test
    public void shouldReturnBadRequestWhenCreateWithNullMpid() throws Exception {
        Mpid mpid = getNewEntity();
        mpid.setMpid(null);
        super.shouldReturnBadRequestWhenCreate(mpid,containsString("NotNull.mpid.mpid"));
    }

    @Test
    public void shouldReturnBadRequestWhenCreateWithInvalidMpidSize() throws Exception {
        Mpid mpid = getNewEntity();
        mpid.setMpid(RandomString.make(13));
        super.shouldReturnBadRequestWhenCreate(mpid,containsString("Size.mpid.mpid"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenCreateWithNullDealCode() throws Exception {
        Mpid mpid = getNewEntity();
        mpid.setDealCode(null);
        super.shouldReturnBadRequestWhenCreate(mpid,containsString("NotNull.mpid.dealCode"));
    }

    @Test
    public void shouldReturnBadRequestWhenCreateWithInvalidDealCodeSize() throws Exception {
        Mpid mpid = getNewEntity();
        mpid.setDealCode(RandomString.make(13));
        super.shouldReturnBadRequestWhenCreate(mpid,containsString("Size.mpid.dealCode"));
    }
    
    @Test
    public void shouldReturnCreatedWhenCreateWithNullParent() throws Exception {
        Mpid mpid = getNewEntity();
        mpid.setParentMpid(null);
        super.shouldReturnCreated(mpid);
    }

    @Test
    public void shouldReturnBadRequestWhenCreateWithInvalidParentSize() throws Exception {
        Mpid mpid = getNewEntity();
        mpid.setParentMpid(RandomString.make(13));
        super.shouldReturnBadRequestWhenCreate(mpid,containsString("Size.mpid.parentMpid"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenCreateWithNullBrokerPriority() throws Exception {
        Mpid mpid = getNewEntity();
        mpid.setBrokerPriorityType(null);
        super.shouldReturnBadRequestWhenCreate(mpid,containsString("NotNull.mpid.brokerPriorityType"));
    }

    @Test
    public void shouldReturnBadRequestWhenCreateWithInvalidBrokerPriority() throws Exception {
        Mpid mpid = getNewEntity();
        byte[] json = super.jsonReplace(mpid, "brokerPriorityType", "INVALID");
        super.shouldReturnBadRequestWhenCreate(json,mpid,containsString("BrokerType` from String \"INVALID\": value not one of declared Enum instance names"));
    }

    @Test
    public void shouldReturnBadRequestWhenCreateWithNullSelfTrade() throws Exception {
        Mpid mpid = getNewEntity();
        mpid.setSelfTradeType(null);
        super.shouldReturnBadRequestWhenCreate(mpid,containsString("NotNull.mpid.selfTradeType"));
    }

    @Test
    public void shouldReturnBadRequestWhenCreateWithInvalidSelfTrade() throws Exception {
        Mpid mpid = getNewEntity();
        byte[] json = super.jsonReplace(mpid, "selfTradeType", "INVALID");
        super.shouldReturnBadRequestWhenCreate(json,mpid,containsString("BrokerType` from String \"INVALID\": value not one of declared Enum instance names"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenCreateWithNullFinraMember() throws Exception {
        Mpid mpid = getNewEntity();
        mpid.setIsFinraMember(null);
        super.shouldReturnBadRequestWhenCreate(mpid,containsString("NotNull.mpid.isFinraMember"));
    }

    @Test
    public void shouldReturnBadRequestWhenCreateWithInvalidFinraMemberSize() throws Exception {
        Mpid mpid = getNewEntity();
        mpid.setIsFinraMember(2);
        super.shouldReturnBadRequestWhenCreate(mpid,containsString("Max.mpid.isFinraMember"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenCreateWithNullCrd() throws Exception {
        Mpid mpid = getNewEntity();
        mpid.setCrd(null);
        super.shouldReturnBadRequestWhenCreate(mpid,containsString("NotNull.mpid.crd"));
    }

    @Test
    public void shouldReturnBadRequestWhenCreateWithInvalidCrdSize() throws Exception {
        Mpid mpid = getNewEntity();
        byte[] json = super.jsonReplace(mpid, "crd", "9223372036854775808");
        super.shouldReturnBadRequestWhenCreate(json,mpid,containsString("crd"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenCreateWithNullCountry() throws Exception {
        Mpid mpid = getNewEntity();
        mpid.setCountry(null);
        super.shouldReturnBadRequestWhenCreate(mpid,containsString("NotNull.mpid.country"));
    }

    @Test
    public void shouldReturnBadRequestWhenCreateWithInvalidCountrySize() throws Exception {
        Mpid mpid = getNewEntity();
        mpid.setCountry(RandomString.make(3));
        super.shouldReturnBadRequestWhenCreate(mpid,containsString("Size.mpid.country"));
    }

    @Test
    public void shouldReturnBadRequestWhenCreateWithNullStatus() throws Exception {
        Mpid mpid = getNewEntity();
        mpid.setStatus(null);
        super.shouldReturnBadRequestWhenCreate(mpid,containsString("NotNull.mpid.status"));
    }

    @Test
    public void shouldReturnBadRequestWhenCreateWithInvalidStatus() throws Exception {
        Mpid mpid = getNewEntity();
        byte[] json = super.jsonReplace(mpid, "status", "INVALID");
        super.shouldReturnBadRequestWhenCreate(json,mpid,containsString("Status` from String \"INVALID\": value not one of declared Enum instance names"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenUpdateWithNullHandle() throws Exception {
        Mpid mpid = getNewUpdateEntity();
        mpid.setHandle(null);
        super.shouldReturnBadRequestWhenUpdate(mpid,containsString("NotNull.mpid.handle"));
    }

    @Test
    public void shouldReturnBadRequestWhenUpdateWithInvalidHandleSize() throws Exception {
        Mpid mpid = getNewUpdateEntity();
        mpid.setHandle(RandomString.make(13));
        super.shouldReturnBadRequestWhenUpdate(mpid,containsString("Size.mpid.handle"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenUpdateWithNullName() throws Exception {
        Mpid mpid = getNewUpdateEntity();
        mpid.setName(null);
        super.shouldReturnBadRequestWhenUpdate(mpid,containsString("NotNull.mpid.name"));
    }

    @Test
    public void shouldReturnBadRequestWhenUpdateWithInvalidNameSize() throws Exception {
        Mpid mpid = getNewUpdateEntity();
        mpid.setName(RandomString.make(65));
        super.shouldReturnBadRequestWhenUpdate(mpid,containsString("Size.mpid.name"));
    }

    @Test
    public void shouldReturnBadRequestWhenUpdateWithNullMpid() throws Exception {
        Mpid mpid = getNewUpdateEntity();
        mpid.setMpid(null);
        super.shouldReturnBadRequestWhenUpdate(mpid,containsString("NotNull.mpid.mpid"));
    }

    @Test
    public void shouldReturnBadRequestWhenUpdateWithInvalidMpidSize() throws Exception {
        Mpid mpid = getNewUpdateEntity();
        mpid.setMpid(RandomString.make(13));
        super.shouldReturnBadRequestWhenUpdate(mpid,containsString("Size.mpid.mpid"));
    }

    @Test
    public void shouldReturnBadRequestWhenUpdateWithNullDealCode() throws Exception {
        Mpid mpid = getNewUpdateEntity();
        mpid.setDealCode(null);
        super.shouldReturnBadRequestWhenUpdate(mpid,containsString("NotNull.mpid.dealCode"));
    }

    @Test
    public void shouldReturnBadRequestWhenUpdateWithInvalidDealCodeSize() throws Exception {
        Mpid mpid = getNewUpdateEntity();
        mpid.setDealCode(RandomString.make(13));
        super.shouldReturnBadRequestWhenUpdate(mpid,containsString("Size.mpid.dealCode"));
    }
    
    @Test
    public void shouldReturnUpdatedWhenUpdateWithNullParent() throws Exception {
        Mpid mpid = getNewUpdateEntity();
        mpid.setParentMpid(null);

        Mockito.when(service.findById(mpid.getId())).thenReturn(Optional.of(mpid));

        super.shouldReturnUpdated(mpid);
    }
    
    @Test
    public void shouldReturnBadRequestWhenUpdateWithInvalidParentSize() throws Exception {
        Mpid mpid = getNewUpdateEntity();
        mpid.setParentMpid(RandomString.make(13));
        super.shouldReturnBadRequestWhenUpdate(mpid,containsString("Size.mpid.parentMpid"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenUpdateWithNullBrokerPriority() throws Exception {
        Mpid mpid = getNewUpdateEntity();
        mpid.setBrokerPriorityType(null);
        super.shouldReturnBadRequestWhenUpdate(mpid,containsString("NotNull.mpid.brokerPriorityType"));
    }

    @Test
    public void shouldReturnBadRequestWhenUpdateWithInvalidBrokerPriority() throws Exception {
        Mpid mpid = getNewUpdateEntity();
        byte[] json = super.jsonReplace(mpid, "brokerPriorityType", "INVALID");
        super.shouldReturnBadRequestWhenUpdate(json,mpid,containsString("BrokerType` from String \"INVALID\": value not one of declared Enum instance names"));
    }

    @Test
    public void shouldReturnBadRequestWhenUpdateWithNullSelfTrade() throws Exception {
        Mpid mpid = getNewUpdateEntity();
        mpid.setSelfTradeType(null);
        super.shouldReturnBadRequestWhenUpdate(mpid,containsString("NotNull.mpid.selfTradeType"));
    }

    @Test
    public void shouldReturnBadRequestWhenUpdateWithInvalidSelfTrade() throws Exception {
        Mpid mpid = getNewUpdateEntity();
        byte[] json = super.jsonReplace(mpid, "selfTradeType", "INVALID");
        super.shouldReturnBadRequestWhenUpdate(json,mpid,containsString("BrokerType` from String \"INVALID\": value not one of declared Enum instance names"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenUpdateWithNullFinraMember() throws Exception {
        Mpid mpid = getNewUpdateEntity();
        mpid.setIsFinraMember(null);
        super.shouldReturnBadRequestWhenUpdate(mpid,containsString("NotNull.mpid.isFinraMember"));
    }

    @Test
    public void shouldReturnBadRequestWhenUpdateWithInvalidFinraMemberSize() throws Exception {
        Mpid mpid = getNewUpdateEntity();
        mpid.setIsFinraMember(2);
        super.shouldReturnBadRequestWhenUpdate(mpid,containsString("Max.mpid.isFinraMember"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenUpdateWithNullCrd() throws Exception {
        Mpid mpid = getNewUpdateEntity();
        mpid.setCrd(null);
        super.shouldReturnBadRequestWhenUpdate(mpid,containsString("NotNull.mpid.crd"));
    }

    @Test
    public void shouldReturnBadRequestWhenUpdateWithInvalidCrdSize() throws Exception {
        Mpid mpid = getNewUpdateEntity();
        byte[] json = super.jsonReplace(mpid, "crd", "9223372036854775808");
        super.shouldReturnBadRequestWhenUpdate(json,mpid,containsString("crd"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenUpdateWithNullCountry() throws Exception {
        Mpid mpid = getNewUpdateEntity();
        mpid.setCountry(null);
        super.shouldReturnBadRequestWhenUpdate(mpid,containsString("NotNull.mpid.country"));
    }

    @Test
    public void shouldReturnBadRequestWhenUpdateWithInvalidCountrySize() throws Exception {
        Mpid mpid = getNewUpdateEntity();
        mpid.setCountry(RandomString.make(3));
        super.shouldReturnBadRequestWhenUpdate(mpid,containsString("Size.mpid.country"));
    }

    @Test
    public void shouldReturnBadRequestWhenUpdateWithNullStatus() throws Exception {
        Mpid mpid = getNewUpdateEntity();
        mpid.setStatus(null);
        super.shouldReturnBadRequestWhenUpdate(mpid,containsString("NotNull.mpid.status"));
    }

    @Test
    public void shouldReturnBadRequestWhenUpdateWithInvalidStatus() throws Exception {
        Mpid mpid = getNewUpdateEntity();
        byte[] json = super.jsonReplace(mpid, "status", "INVALID");
        super.shouldReturnBadRequestWhenUpdate(json,mpid,containsString("Status` from String \"INVALID\": value not one of declared Enum instance names"));
    }

}