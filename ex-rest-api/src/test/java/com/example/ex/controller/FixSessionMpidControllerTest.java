package com.example.ex.controller;

import static org.hamcrest.Matchers.containsString;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import com.example.ex.model.entity.admin.FixSessionMpid;
import com.example.ex.model.type.InstructionType;
import com.example.ex.model.type.Status;

import net.bytebuddy.utility.RandomString;

@WebMvcTest
public class FixSessionMpidControllerTest extends AdminControllerTest<FixSessionMpid> {

    @Override
    public void initController() {
        super.baseUri = FixSessionMpidController.BASE_URI;
        super.service = this.fixSessionMpidService;
        super.clazz = FixSessionMpid.class;
    }

    @Override
    public List<FixSessionMpid> getListOfEntities() {
        List<FixSessionMpid> fixSessionMpids = new ArrayList<>();

        fixSessionMpids.add(new FixSessionMpid("exxx-cg","exxx-cg","exx","--","OO",InstructionType.COMPOSITE,Status.ENABLED));
        fixSessionMpids.add(new FixSessionMpid("scot1-cg","scot1-cg","scotia","--","S1",InstructionType.COMPOSITE,Status.ENABLED));
        return fixSessionMpids;
    }

    @Override
    public FixSessionMpid getNewEntity() {
        return new FixSessionMpid("exxx-cg","exxx-cg","exx","--","OO",InstructionType.COMPOSITE,Status.ENABLED);
    }
    
    @Test
    public void shouldReturnBadRequestWhenCreateWithNullHandle() throws Exception {
        FixSessionMpid fixSessionMpid = getNewEntity();
        fixSessionMpid.setHandle(null);
        super.shouldReturnBadRequestWhenCreate(fixSessionMpid,containsString("NotNull.fixSessionMpid.handle"));
    }

    @Test
    public void shouldReturnBadRequestWhenCreateWithInvalidHandleSize() throws Exception {
        FixSessionMpid fixSessionMpid = getNewEntity();
        fixSessionMpid.setHandle(RandomString.make(13));
        super.shouldReturnBadRequestWhenCreate(fixSessionMpid,containsString("Size.fixSessionMpid.handle"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenCreateWithNullBrokerSession() throws Exception {
        FixSessionMpid fixSessionMpid = getNewEntity();
        fixSessionMpid.setBrokerSession(null);
        super.shouldReturnBadRequestWhenCreate(fixSessionMpid,containsString("NotNull.fixSessionMpid.brokerSession"));
    }

    @Test
    public void shouldReturnBadRequestWhenCreateWithInvalidBrokerSessionSize() throws Exception {
        FixSessionMpid fixSessionMpid = getNewEntity();
        fixSessionMpid.setBrokerSession(RandomString.make(13));
        super.shouldReturnBadRequestWhenCreate(fixSessionMpid,containsString("Size.fixSessionMpid.brokerSession"));
    }

    @Test
    public void shouldReturnBadRequestWhenCreateWithNullMpid() throws Exception {
        FixSessionMpid fixSessionMpid = getNewEntity();
        fixSessionMpid.setMpid(null);
        super.shouldReturnBadRequestWhenCreate(fixSessionMpid,containsString("NotNull.fixSessionMpid.mpid"));
    }

    @Test
    public void shouldReturnBadRequestWhenCreateWithInvalidMpidSize() throws Exception {
        FixSessionMpid fixSessionMpid = getNewEntity();
        fixSessionMpid.setMpid(RandomString.make(13));
        super.shouldReturnBadRequestWhenCreate(fixSessionMpid,containsString("Size.fixSessionMpid.mpid"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenCreateWithNullAgency() throws Exception {
        FixSessionMpid fixSessionMpid = getNewEntity();
        fixSessionMpid.setAgencyAiqGroup(null);
        super.shouldReturnBadRequestWhenCreate(fixSessionMpid,containsString("NotNull.fixSessionMpid.agencyAiqGroup"));
    }

    @Test
    public void shouldReturnBadRequestWhenCreateWithInvalidAgencySize() throws Exception {
        FixSessionMpid fixSessionMpid = getNewEntity();
        fixSessionMpid.setAgencyAiqGroup(RandomString.make(3));
        super.shouldReturnBadRequestWhenCreate(fixSessionMpid,containsString("Size.fixSessionMpid.agencyAiqGroup"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenCreateWithNullPrincipal() throws Exception {
        FixSessionMpid fixSessionMpid = getNewEntity();
        fixSessionMpid.setPrincipalAiqGroup(null);
        super.shouldReturnBadRequestWhenCreate(fixSessionMpid,containsString("NotNull.fixSessionMpid.principalAiqGroup"));
    }

    @Test
    public void shouldReturnBadRequestWhenCreateWithInvalidPrincipalSize() throws Exception {
        FixSessionMpid fixSessionMpid = getNewEntity();
        fixSessionMpid.setPrincipalAiqGroup(RandomString.make(3));
        super.shouldReturnBadRequestWhenCreate(fixSessionMpid,containsString("Size.fixSessionMpid.principalAiqGroup"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenCreateWithNullMinqtyInstruction() throws Exception {
        FixSessionMpid fixSessionMpid = getNewEntity();
        fixSessionMpid.setMinqtyInstruction(null);
        super.shouldReturnBadRequestWhenCreate(fixSessionMpid,containsString("NotNull.fixSessionMpid.minqtyInstruction"));
    }
 
    @Test
    public void shouldReturnBadRequestWhenCreateWithInvalidMinqtyInstruction() throws Exception {
        FixSessionMpid fixSessionMpid = getNewEntity();
        byte[] json = super.jsonReplace(fixSessionMpid, "minqtyInstruction", "INVALID");
        super.shouldReturnBadRequestWhenCreate(json,fixSessionMpid,containsString("InstructionType` from String \"INVALID\": value not one of declared Enum instance names"));
    }

    @Test
    public void shouldReturnBadRequestWhenCreateWithNullStatus() throws Exception {
        FixSessionMpid fixSessionMpid = getNewEntity();
        fixSessionMpid.setStatus(null);
        super.shouldReturnBadRequestWhenCreate(fixSessionMpid,containsString("NotNull.fixSessionMpid.status"));
    }
 
    @Test
    public void shouldReturnBadRequestWhenCreateWithInvalidStatus() throws Exception {
        FixSessionMpid fixSessionMpid = getNewEntity();
        byte[] json = super.jsonReplace(fixSessionMpid, "status", "INVALID");
        super.shouldReturnBadRequestWhenCreate(json,fixSessionMpid,containsString("Status` from String \"INVALID\": value not one of declared Enum instance names"));
    }
     
    @Test
    public void shouldReturnBadRequestWhenUpdateWithNullHandle() throws Exception {
        FixSessionMpid fixSessionMpid = getNewUpdateEntity();
        fixSessionMpid.setHandle(null);
        super.shouldReturnBadRequestWhenUpdate(fixSessionMpid,containsString("NotNull.fixSessionMpid.handle"));
    }

    @Test
    public void shouldReturnBadRequestWhenUpdateWithInvalidHandleSize() throws Exception {
        FixSessionMpid fixSessionMpid = getNewUpdateEntity();
        fixSessionMpid.setHandle(RandomString.make(13));
        super.shouldReturnBadRequestWhenUpdate(fixSessionMpid,containsString("Size.fixSessionMpid.handle"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenUpdateWithNullBrokerSession() throws Exception {
        FixSessionMpid fixSessionMpid = getNewUpdateEntity();
        fixSessionMpid.setBrokerSession(null);
        super.shouldReturnBadRequestWhenUpdate(fixSessionMpid,containsString("NotNull.fixSessionMpid.brokerSession"));
    }

    @Test
    public void shouldReturnBadRequestWhenUpdateWithInvalidBrokerSessionSize() throws Exception {
        FixSessionMpid fixSessionMpid = getNewUpdateEntity();
        fixSessionMpid.setBrokerSession(RandomString.make(13));
        super.shouldReturnBadRequestWhenUpdate(fixSessionMpid,containsString("Size.fixSessionMpid.brokerSession"));
    }

    @Test
    public void shouldReturnBadRequestWhenUpdateWithNullMpid() throws Exception {
        FixSessionMpid fixSessionMpid = getNewUpdateEntity();
        fixSessionMpid.setMpid(null);
        super.shouldReturnBadRequestWhenUpdate(fixSessionMpid,containsString("NotNull.fixSessionMpid.mpid"));
    }

    @Test
    public void shouldReturnBadRequestWhenUpdateWithInvalidMpidSize() throws Exception {
        FixSessionMpid fixSessionMpid = getNewUpdateEntity();
        fixSessionMpid.setMpid(RandomString.make(13));
        super.shouldReturnBadRequestWhenUpdate(fixSessionMpid,containsString("Size.fixSessionMpid.mpid"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenUpdateWithNullAgencyAiqGroup() throws Exception {
        FixSessionMpid fixSessionMpid = getNewUpdateEntity();
        fixSessionMpid.setAgencyAiqGroup(null);
        super.shouldReturnBadRequestWhenUpdate(fixSessionMpid,containsString("NotNull.fixSessionMpid.agencyAiqGroup"));
    }

    @Test
    public void shouldReturnBadRequestWhenUpdateWithInvalidAgencyAiqGroupSize() throws Exception {
        FixSessionMpid fixSessionMpid = getNewUpdateEntity();
        fixSessionMpid.setAgencyAiqGroup(RandomString.make(3));
        super.shouldReturnBadRequestWhenUpdate(fixSessionMpid,containsString("Size.fixSessionMpid.agencyAiqGroup"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenUpdateWithNullPrincipal() throws Exception {
        FixSessionMpid fixSessionMpid = getNewUpdateEntity();
        fixSessionMpid.setPrincipalAiqGroup(null);
        super.shouldReturnBadRequestWhenUpdate(fixSessionMpid,containsString("NotNull.fixSessionMpid.principalAiqGroup"));
    }

    @Test
    public void shouldReturnBadRequestWhenUpdateWithInvalidPrincipalSize() throws Exception {
        FixSessionMpid fixSessionMpid = getNewUpdateEntity();
        fixSessionMpid.setPrincipalAiqGroup(RandomString.make(3));
        super.shouldReturnBadRequestWhenUpdate(fixSessionMpid,containsString("Size.fixSessionMpid.principalAiqGroup"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenUpdateWithNullMinqtyInstruction() throws Exception {
        FixSessionMpid fixSessionMpid = getNewUpdateEntity();
        fixSessionMpid.setMinqtyInstruction(null);
        super.shouldReturnBadRequestWhenUpdate(fixSessionMpid,containsString("NotNull.fixSessionMpid.minqtyInstruction"));
    }
 
    @Test
    public void shouldReturnBadRequestWhenUpdateWithInvalidMinqtyInstruction() throws Exception {
        FixSessionMpid fixSessionMpid = getNewUpdateEntity();
        byte[] json = super.jsonReplace(fixSessionMpid, "minqtyInstruction", "INVALID");
        super.shouldReturnBadRequestWhenUpdate(json,fixSessionMpid,containsString("InstructionType` from String \"INVALID\": value not one of declared Enum instance names"));
    }

    @Test
    public void shouldReturnBadRequestWhenUpdateWithNullStatus() throws Exception {
        FixSessionMpid fixSessionMpid = getNewUpdateEntity();
        fixSessionMpid.setStatus(null);
        super.shouldReturnBadRequestWhenUpdate(fixSessionMpid,containsString("NotNull.fixSessionMpid.status"));
    }
 
    @Test
    public void shouldReturnBadRequestWhenUpdateWithInvalidStatus() throws Exception {
        FixSessionMpid fixSessionMpid = getNewUpdateEntity();
        byte[] json = super.jsonReplace(fixSessionMpid, "status", "INVALID");
        super.shouldReturnBadRequestWhenUpdate(json,fixSessionMpid,containsString("Status` from String \"INVALID\": value not one of declared Enum instance names"));
    }
}