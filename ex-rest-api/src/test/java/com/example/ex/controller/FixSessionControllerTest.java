package com.example.ex.controller;

import static org.hamcrest.Matchers.containsString;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import com.example.ex.model.entity.admin.FixSession;
import com.example.ex.model.type.ConnectionType;
import com.example.ex.model.type.OrderCapacity;
import com.example.ex.model.type.Status;
import com.example.ex.model.type.Symbology;

import net.bytebuddy.utility.RandomString;

@WebMvcTest
public class FixSessionControllerTest extends AdminControllerTest<FixSession> {

    @Override
    public void initController() {
        super.baseUri = FixSessionController.BASE_URI;
        super.service = this.fixSessionService;
        super.clazz = FixSession.class;
    }

    @Override
    public List<FixSession> getListOfEntities() {
        List<FixSession> fixSessions = new ArrayList<>();

        fixSessions.add(new FixSession("exxx-cg","exxx-cg","CLGW01","exx","EXXX","EXXG","127.0.0.1",".*",9001,ConnectionType.ACCEPTOR,"A",Status.ENABLED,1,1,1,Integer.MAX_VALUE,Long.MAX_VALUE,(int)Short.MAX_VALUE,"FIX.4.4",OrderCapacity.PRINCIPAL,0,Symbology.INET,""));
        fixSessions.add(new FixSession("scot-cg","scot-cg","CLGW01","scotia","SCOT","EXXG","127.0.0.1",".*",9002,ConnectionType.ACCEPTOR,"A",Status.ENABLED,0,0,0,0,0L,(int)Short.MAX_VALUE,"FIX.4.4",OrderCapacity.NONE,0,Symbology.INET,""));
        return fixSessions;
    }

    @Override
    public FixSession getNewEntity() {
        return new FixSession("tdbk-cg","tdbk-cg","CLGW01","tdbank","TDBK","EXXG","127.0.0.1",".*",9002,ConnectionType.ACCEPTOR,"A",Status.ENABLED,1,1,1,Integer.MAX_VALUE,Long.MAX_VALUE,(int)Short.MAX_VALUE,"FIX.4.4",OrderCapacity.NONE,0,Symbology.INET,"");
    }
    
    @Test
    public void shouldReturnBadRequestWhenCreateWithNullHandle() throws Exception {
        FixSession fixSession = getNewEntity();
        fixSession.setHandle(null);
        super.shouldReturnBadRequestWhenCreate(fixSession,containsString("NotNull.fixSession.handle"));
    }

    @Test
    public void shouldReturnBadRequestWhenCreateWithInvalidHandleSize() throws Exception {
        FixSession fixSession = getNewEntity();
        fixSession.setHandle(RandomString.make(13));
        super.shouldReturnBadRequestWhenCreate(fixSession,containsString("Size.fixSession.handle"));
    }
    
    @Test
    public void shouldReturnCreatedWhenCreateWithNullName() throws Exception {
        FixSession fixSession = getNewEntity();
        fixSession.setName(null);
        super.shouldReturnCreated(fixSession);
    }

    @Test
    public void shouldReturnBadRequestWhenCreateWithInvalidNameSize() throws Exception {
        FixSession fixSession = getNewEntity();
        fixSession.setName(RandomString.make(65));
        super.shouldReturnBadRequestWhenCreate(fixSession,containsString("Size.fixSession.name"));
    }

    @Test
    public void shouldReturnBadRequestWhenCreateWithNullAppInstance() throws Exception {
        FixSession fixSession = getNewEntity();
        fixSession.setAppInstance(null);
        super.shouldReturnBadRequestWhenCreate(fixSession,containsString("NotNull.fixSession.appInstance"));
    }

    @Test
    public void shouldReturnBadRequestWhenCreateWithInvalidAppInstanceSize() throws Exception {
        FixSession fixSession = getNewEntity();
        fixSession.setAppInstance(RandomString.make(7));
        super.shouldReturnBadRequestWhenCreate(fixSession,containsString("Size.fixSession.appInstance"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenCreateWithNullMpid() throws Exception {
        FixSession fixSession = getNewEntity();
        fixSession.setMpid(null);
        super.shouldReturnBadRequestWhenCreate(fixSession,containsString("NotNull.fixSession.mpid"));
    }

    @Test
    public void shouldReturnBadRequestWhenCreateWithInvalidMpidSize() throws Exception {
        FixSession fixSession = getNewEntity();
        fixSession.setMpid(RandomString.make(13));
        super.shouldReturnBadRequestWhenCreate(fixSession,containsString("Size.fixSession.mpid"));
    }

    @Test
    public void shouldReturnBadRequestWhenCreateWithNullSenderComp() throws Exception {
        FixSession fixSession = getNewEntity();
        fixSession.setSenderCompId(null);
        super.shouldReturnBadRequestWhenCreate(fixSession,containsString("NotNull.fixSession.senderCompId"));
    }

    @Test
    public void shouldReturnBadRequestWhenCreateWithInvalidSenderCompSize() throws Exception {
        FixSession fixSession = getNewEntity();
        fixSession.setSenderCompId(RandomString.make(17));
        super.shouldReturnBadRequestWhenCreate(fixSession,containsString("Size.fixSession.senderCompId"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenCreateWithNullTargetCompId() throws Exception {
        FixSession fixSession = getNewEntity();
        fixSession.setTargetCompId(null);
        super.shouldReturnBadRequestWhenCreate(fixSession,containsString("NotNull.fixSession.targetCompId"));
    }

    @Test
    public void shouldReturnBadRequestWhenCreateWithInvalidTargetCompIdSize() throws Exception {
        FixSession fixSession = getNewEntity();
        fixSession.setTargetCompId(RandomString.make(17));
        super.shouldReturnBadRequestWhenCreate(fixSession,containsString("Size.fixSession.targetCompId"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenCreateWithNullLocalIp() throws Exception {
        FixSession fixSession = getNewEntity();
        fixSession.setLocalIp(null);
        super.shouldReturnBadRequestWhenCreate(fixSession,containsString("NotNull.fixSession.localIp"));
    }

    @Test
    public void shouldReturnBadRequestWhenCreateWithInvalidLocalIpSize() throws Exception {
        FixSession fixSession = getNewEntity();
        fixSession.setLocalIp(RandomString.make(16));
        super.shouldReturnBadRequestWhenCreate(fixSession,containsString("Size.fixSession.localIp"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenCreateWithNullRemoteIp() throws Exception {
        FixSession fixSession = getNewEntity();
        fixSession.setRemoteIp(null);
        super.shouldReturnBadRequestWhenCreate(fixSession,containsString("NotNull.fixSession.remoteIp"));
    }

    @Test
    public void shouldReturnBadRequestWhenCreateWithInvalidRemoteIpSize() throws Exception {
        FixSession fixSession = getNewEntity();
        fixSession.setRemoteIp(RandomString.make(16));
        super.shouldReturnBadRequestWhenCreate(fixSession,containsString("Size.fixSession.remoteIp"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenCreateWithNullPort() throws Exception {
        FixSession fixSession = getNewEntity();
        fixSession.setPort(null);
        super.shouldReturnBadRequestWhenCreate(fixSession,containsString("NotNull.fixSession.port"));
    }

    @Test
    public void shouldReturnBadRequestWhenCreateWithInvalidPortSize() throws Exception {
        FixSession fixSession = getNewEntity();
        fixSession.setPort(new Random().nextInt(99999 - 10000) + 10000);
        super.shouldReturnBadRequestWhenCreate(fixSession,containsString("Digits.fixSession.port"));
    }

    @Test
    public void shouldReturnBadRequestWhenCreateWithNullConnectionType() throws Exception {
        FixSession fixSession = getNewEntity();
        fixSession.setConnectionType(null);
        super.shouldReturnBadRequestWhenCreate(fixSession,containsString("NotNull.fixSession.connectionType"));
    }
 
    @Test
    public void shouldReturnBadRequestWhenCreateWithInvalidConnectionType() throws Exception {
        FixSession fixSession = getNewEntity();
        byte[] json = super.jsonReplace(fixSession, "connectionType", "INVALID");
        super.shouldReturnBadRequestWhenCreate(json,fixSession,containsString("ConnectionType` from String \"INVALID\": value not one of declared Enum instance names"));
    }

    @Test
    public void shouldReturnBadRequestWhenCreateWithNullDcMsgTypes() throws Exception {
        FixSession fixSession = getNewEntity();
        fixSession.setDcMsgTypes(null);
        super.shouldReturnBadRequestWhenCreate(fixSession,containsString("NotNull.fixSession.dcMsgTypes"));
    }

    @Test
    public void shouldReturnBadRequestWhenCreateWithInvalidDcMsgTypesSize() throws Exception {
        FixSession fixSession = getNewEntity();
        fixSession.setDcMsgTypes(RandomString.make(33));
        super.shouldReturnBadRequestWhenCreate(fixSession,containsString("Size.fixSession.dcMsgTypes"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenCreateWithNullStatus() throws Exception {
        FixSession fixSession = getNewEntity();
        fixSession.setStatus(null);
        super.shouldReturnBadRequestWhenCreate(fixSession,containsString("NotNull.fixSession.status"));
    }
 
    @Test
    public void shouldReturnBadRequestWhenCreateWithInvalidStatus() throws Exception {
        FixSession fixSession = getNewEntity();
        byte[] json = super.jsonReplace(fixSession, "status", "INVALID");
        super.shouldReturnBadRequestWhenCreate(json,fixSession,containsString("Status` from String \"INVALID\": value not one of declared Enum instance names"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenCreateWithNullCancelOnDisconnect() throws Exception {
        FixSession fixSession = getNewEntity();
        fixSession.setCancelOnDisconnect(null);
        super.shouldReturnBadRequestWhenCreate(fixSession,containsString("NotNull.fixSession.cancelOnDisconnect"));
    }

    @Test
    public void shouldReturnBadRequestWhenCreateWithInvalidCancelOnDisconnectSize() throws Exception {
        FixSession fixSession = getNewEntity();
        fixSession.setCancelOnDisconnect(2);
        super.shouldReturnBadRequestWhenCreate(fixSession,containsString("Max.fixSession.cancelOnDisconnect"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenCreateWithNullSupportsBusts() throws Exception {
        FixSession fixSession = getNewEntity();
        fixSession.setSupportsBusts(null);
        super.shouldReturnBadRequestWhenCreate(fixSession,containsString("NotNull.fixSession.supportsBusts"));
    }

    @Test
    public void shouldReturnBadRequestWhenCreateWithInvalidSupportsBustsSize() throws Exception {
        FixSession fixSession = getNewEntity();
        fixSession.setSupportsBusts(2);
        super.shouldReturnBadRequestWhenCreate(fixSession,containsString("Max.fixSession.supportsBusts"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenCreateWithNullAllowMktOrders() throws Exception {
        FixSession fixSession = getNewEntity();
        fixSession.setAllowMktOrders(null);
        super.shouldReturnBadRequestWhenCreate(fixSession,containsString("NotNull.fixSession.allowMktOrders"));
    }

    @Test
    public void shouldReturnBadRequestWhenCreateWithInvalidAllowMktOrdersSize() throws Exception {
        FixSession fixSession = getNewEntity();
        fixSession.setAllowMktOrders(2);
        super.shouldReturnBadRequestWhenCreate(fixSession,containsString("Max.fixSession.allowMktOrders"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenCreateWithNullMaxOrderQty() throws Exception {
        FixSession fixSession = getNewEntity();
        fixSession.setMaxOrderQty(null);
        super.shouldReturnBadRequestWhenCreate(fixSession,containsString("NotNull.fixSession.maxOrderQty"));
    }

    @Test
    public void shouldReturnBadRequestWhenCreateWithInvalidMaxOrderQtySize() throws Exception {
        FixSession fixSession = getNewEntity();
        byte[] json = super.jsonReplace(fixSession, "maxOrderQty", Long.toString(Integer.MAX_VALUE+1L));
        super.shouldReturnBadRequestWhenCreate(json,fixSession,containsString("maxOrderQty"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenCreateWithNullMaxNotionalOrderQty() throws Exception {
        FixSession fixSession = getNewEntity();
        fixSession.setMaxNotionalOrderQty(null);
        super.shouldReturnBadRequestWhenCreate(fixSession,containsString("NotNull.fixSession.maxNotionalOrderQty"));
    }

    @Test
    public void shouldReturnBadRequestWhenCreateWithInvalidMaxNotionalOrderQtySize() throws Exception {
        FixSession fixSession = getNewEntity();
        byte[] json = super.jsonReplace(fixSession, "maxNotionalOrderQty", "9223372036854775808");
        super.shouldReturnBadRequestWhenCreate(json,fixSession,containsString("maxNotionalOrderQty"));
    }

    @Test
    public void shouldReturnBadRequestWhenCreateWithNullHeartBeatInterval() throws Exception {
        FixSession fixSession = getNewEntity();
        fixSession.setHeartbeatInterval(null);
        super.shouldReturnBadRequestWhenCreate(fixSession,containsString("NotNull.fixSession.heartbeatInterval"));
    }

    @Test
    public void shouldReturnBadRequestWhenCreateWithInvalidHeartBeatIntervalSize() throws Exception {
        FixSession fixSession = getNewEntity();
        fixSession.setHeartbeatInterval(Short.MAX_VALUE+1);
        super.shouldReturnBadRequestWhenCreate(fixSession,containsString("Max.fixSession.heartbeatInterval"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenCreateWithNullBeginString() throws Exception {
        FixSession fixSession = getNewEntity();
        fixSession.setBeginString(null);
        super.shouldReturnBadRequestWhenCreate(fixSession,containsString("NotNull.fixSession.beginString"));
    }

    @Test
    public void shouldReturnBadRequestWhenCreateWithInvalidBeginStringSize() throws Exception {
        FixSession fixSession = getNewEntity();
        fixSession.setBeginString(RandomString.make(9));
        super.shouldReturnBadRequestWhenCreate(fixSession,containsString("Size.fixSession.beginString"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenCreateWithNullDefaultOrderCapacity() throws Exception {
        FixSession fixSession = getNewEntity();
        fixSession.setDefaultOrderCapacity(null);
        super.shouldReturnBadRequestWhenCreate(fixSession,containsString("NotNull.fixSession.defaultOrderCapacity"));
    }

    @Test
    public void shouldReturnBadRequestWhenCreateWithInvalidDefaultOrderCapacitySize() throws Exception {
        FixSession fixSession = getNewEntity();
        byte[] json = super.jsonReplace(fixSession, "defaultOrderCapacity", "INVALID");
        super.shouldReturnBadRequestWhenCreate(json,fixSession,containsString("OrderCapacity` from String \"INVALID\": value not one of declared Enum instance names"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenCreateWithNullOnlyAllowTestSymbols() throws Exception {
        FixSession fixSession = getNewEntity();
        fixSession.setOnlyAllowTestSymbols(null);
        super.shouldReturnBadRequestWhenCreate(fixSession,containsString("NotNull.fixSession.onlyAllowTestSymbols"));
    }

    @Test
    public void shouldReturnBadRequestWhenCreateWithInvalidOnlyAllowTestSymbolsSize() throws Exception {
        FixSession fixSession = getNewEntity();
        fixSession.setOnlyAllowTestSymbols(2);
        super.shouldReturnBadRequestWhenCreate(fixSession,containsString("Max.fixSession.onlyAllowTestSymbols"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenCreateWithNullSymbology() throws Exception {
        FixSession fixSession = getNewEntity();
        fixSession.setSymbology(null);
        super.shouldReturnBadRequestWhenCreate(fixSession,containsString("NotNull.fixSession.symbology"));
    }

    @Test
    public void shouldReturnBadRequestWhenCreateWithInvalidSymbology() throws Exception {
        FixSession fixSession = getNewEntity();
        byte[] json = super.jsonReplace(fixSession, "symbology", "INVALID");
        super.shouldReturnBadRequestWhenCreate(json,fixSession,containsString("Symbology` from String \"INVALID\": value not one of declared Enum instance names"));
    }
    
    @Test
    public void shouldReturnCreatedWhenCreateWithNullPassword() throws Exception {
        FixSession fixSession = getNewEntity();
        fixSession.setPassword(null);
        super.shouldReturnCreated(fixSession);
    }

    @Test
    public void shouldReturnBadRequestWhenCreateWithInvalidPasswordSize() throws Exception {
        FixSession fixSession = getNewEntity();
        fixSession.setPassword(RandomString.make(33));
        super.shouldReturnBadRequestWhenCreate(fixSession,containsString("Size.fixSession.password"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenUpdateWithNullHandle() throws Exception {
        FixSession fixSession = getNewUpdateEntity();
        fixSession.setHandle(null);
        super.shouldReturnBadRequestWhenUpdate(fixSession,containsString("NotNull.fixSession.handle"));
    }

    @Test
    public void shouldReturnBadRequestWhenUpdateWithInvalidHandleSize() throws Exception {
        FixSession fixSession = getNewUpdateEntity();
        fixSession.setHandle(RandomString.make(13));
        super.shouldReturnBadRequestWhenUpdate(fixSession,containsString("Size.fixSession.handle"));
    }
    
    @Test
    public void shouldReturnUpdateWhenUpdateWithNullName() throws Exception {
        FixSession fixSession = getNewUpdateEntity();
        fixSession.setName(null);
        
        Mockito.when(service.findById(fixSession.getId())).thenReturn(Optional.of(fixSession));

        super.shouldReturnUpdated(fixSession);
    }

    @Test
    public void shouldReturnBadRequestWhenUpdateWithInvalidNameSize() throws Exception {
        FixSession fixSession = getNewUpdateEntity();
        fixSession.setName(RandomString.make(65));
        super.shouldReturnBadRequestWhenUpdate(fixSession,containsString("Size.fixSession.name"));
    }

    @Test
    public void shouldReturnBadRequestWhenUpdateWithNullAppInstance() throws Exception {
        FixSession fixSession = getNewUpdateEntity();
        fixSession.setAppInstance(null);
        super.shouldReturnBadRequestWhenUpdate(fixSession,containsString("NotNull.fixSession.appInstance"));
    }

    @Test
    public void shouldReturnBadRequestWhenUpdateWithInvalidAppInstanceSize() throws Exception {
        FixSession fixSession = getNewUpdateEntity();
        fixSession.setAppInstance(RandomString.make(7));
        super.shouldReturnBadRequestWhenUpdate(fixSession,containsString("Size.fixSession.appInstance"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenUpdateWithNullMpid() throws Exception {
        FixSession fixSession = getNewUpdateEntity();
        fixSession.setMpid(null);
        super.shouldReturnBadRequestWhenUpdate(fixSession,containsString("NotNull.fixSession.mpid"));
    }

    @Test
    public void shouldReturnBadRequestWhenUpdateWithInvalidMpidSize() throws Exception {
        FixSession fixSession = getNewUpdateEntity();
        fixSession.setMpid(RandomString.make(13));
        super.shouldReturnBadRequestWhenUpdate(fixSession,containsString("Size.fixSession.mpid"));
    }

    @Test
    public void shouldReturnBadRequestWhenUpdateWithNullSenderComp() throws Exception {
        FixSession fixSession = getNewUpdateEntity();
        fixSession.setSenderCompId(null);
        super.shouldReturnBadRequestWhenUpdate(fixSession,containsString("NotNull.fixSession.senderCompId"));
    }

    @Test
    public void shouldReturnBadRequestWhenUpdateWithInvalidSenderCompSize() throws Exception {
        FixSession fixSession = getNewUpdateEntity();
        fixSession.setSenderCompId(RandomString.make(17));
        super.shouldReturnBadRequestWhenUpdate(fixSession,containsString("Size.fixSession.senderCompId"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenUpdateWithNullTargetComp() throws Exception {
        FixSession fixSession = getNewUpdateEntity();
        fixSession.setTargetCompId(null);
        super.shouldReturnBadRequestWhenUpdate(fixSession,containsString("NotNull.fixSession.targetCompId"));
    }

    @Test
    public void shouldReturnBadRequestWhenUpdateWithInvalidTargetCompSize() throws Exception {
        FixSession fixSession = getNewUpdateEntity();
        fixSession.setTargetCompId(RandomString.make(17));
        super.shouldReturnBadRequestWhenUpdate(fixSession,containsString("Size.fixSession.targetCompId"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenUpdateWithNullLocalIp() throws Exception {
        FixSession fixSession = getNewUpdateEntity();
        fixSession.setLocalIp(null);
        super.shouldReturnBadRequestWhenUpdate(fixSession,containsString("NotNull.fixSession.localIp"));
    }

    @Test
    public void shouldReturnBadRequestWhenUpdateWithInvalidLocalIpSize() throws Exception {
        FixSession fixSession = getNewUpdateEntity();
        fixSession.setLocalIp(RandomString.make(16));
        super.shouldReturnBadRequestWhenUpdate(fixSession,containsString("Size.fixSession.localIp"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenUpdateWithNullRemoteIp() throws Exception {
        FixSession fixSession = getNewUpdateEntity();
        fixSession.setRemoteIp(null);
        super.shouldReturnBadRequestWhenUpdate(fixSession,containsString("NotNull.fixSession.remoteIp"));
    }

    @Test
    public void shouldReturnBadRequestWhenUpdateWithInvalidRemoteIpSize() throws Exception {
        FixSession fixSession = getNewUpdateEntity();
        fixSession.setRemoteIp(RandomString.make(16));
        super.shouldReturnBadRequestWhenUpdate(fixSession,containsString("Size.fixSession.remoteIp"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenUpdateWithNullPort() throws Exception {
        FixSession fixSession = getNewUpdateEntity();
        fixSession.setPort(null);
        super.shouldReturnBadRequestWhenUpdate(fixSession,containsString("NotNull.fixSession.port"));
    }

    @Test
    public void shouldReturnBadRequestWhenUpdateWithInvalidPortSize() throws Exception {
        FixSession fixSession = getNewUpdateEntity();
        fixSession.setPort(new Random().nextInt(99999 - 10000) + 10000);
        super.shouldReturnBadRequestWhenUpdate(fixSession,containsString("Digits.fixSession.port"));
    }

    @Test
    public void shouldReturnBadRequestWhenUpdateWithNullConnectionType() throws Exception {
        FixSession fixSession = getNewUpdateEntity();
        fixSession.setConnectionType(null);
        super.shouldReturnBadRequestWhenUpdate(fixSession,containsString("NotNull.fixSession.connectionType"));
    }
 
    @Test
    public void shouldReturnBadRequestWhenUpdateWithInvalidConnectionType() throws Exception {
        FixSession fixSession = getNewUpdateEntity();
        byte[] json = super.jsonReplace(fixSession, "connectionType", "INVALID");
        super.shouldReturnBadRequestWhenUpdate(json,fixSession,containsString("ConnectionType` from String \"INVALID\": value not one of declared Enum instance names"));
    }

    @Test
    public void shouldReturnBadRequestWhenUpdateWithNullDcMsgTypes() throws Exception {
        FixSession fixSession = getNewUpdateEntity();
        fixSession.setDcMsgTypes(null);
        super.shouldReturnBadRequestWhenUpdate(fixSession,containsString("NotNull.fixSession.dcMsgTypes"));
    }

    @Test
    public void shouldReturnBadRequestWhenUpdateWithInvalidDcMsgTypesSize() throws Exception {
        FixSession fixSession = getNewUpdateEntity();
        fixSession.setDcMsgTypes(RandomString.make(33));
        super.shouldReturnBadRequestWhenUpdate(fixSession,containsString("Size.fixSession.dcMsgTypes"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenUpdateWithNullStatus() throws Exception {
        FixSession fixSession = getNewUpdateEntity();
        fixSession.setStatus(null);
        super.shouldReturnBadRequestWhenUpdate(fixSession,containsString("NotNull.fixSession.status"));
    }
 
    @Test
    public void shouldReturnBadRequestWhenUpdateWithInvalidStatus() throws Exception {
        FixSession fixSession = getNewUpdateEntity();
        byte[] json = super.jsonReplace(fixSession, "status", "INVALID");
        super.shouldReturnBadRequestWhenUpdate(json,fixSession,containsString("Status` from String \"INVALID\": value not one of declared Enum instance names"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenUpdateWithNullCancelOnDisconnect() throws Exception {
        FixSession fixSession = getNewUpdateEntity();
        fixSession.setCancelOnDisconnect(null);
        super.shouldReturnBadRequestWhenUpdate(fixSession,containsString("NotNull.fixSession.cancelOnDisconnect"));
    }

    @Test
    public void shouldReturnBadRequestWhenUpdateWithInvalidCancelOnDisconnectSize() throws Exception {
        FixSession fixSession = getNewUpdateEntity();
        fixSession.setCancelOnDisconnect(2);
        super.shouldReturnBadRequestWhenUpdate(fixSession,containsString("Max.fixSession.cancelOnDisconnect"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenUpdateWithNullSupportsBusts() throws Exception {
        FixSession fixSession = getNewUpdateEntity();
        fixSession.setSupportsBusts(null);
        super.shouldReturnBadRequestWhenUpdate(fixSession,containsString("NotNull.fixSession.supportsBusts"));
    }

    @Test
    public void shouldReturnBadRequestWhenUpdateWithInvalidSupportsBustsSize() throws Exception {
        FixSession fixSession = getNewUpdateEntity();
        fixSession.setSupportsBusts(2);
        super.shouldReturnBadRequestWhenUpdate(fixSession,containsString("Max.fixSession.supportsBusts"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenUpdateWithNullAllowMktOrders() throws Exception {
        FixSession fixSession = getNewUpdateEntity();
        fixSession.setAllowMktOrders(null);
        super.shouldReturnBadRequestWhenUpdate(fixSession,containsString("NotNull.fixSession.allowMktOrders"));
    }

    @Test
    public void shouldReturnBadRequestWhenUpdateWithInvalidAllowMktOrdersSize() throws Exception {
        FixSession fixSession = getNewUpdateEntity();
        fixSession.setAllowMktOrders(2);
        super.shouldReturnBadRequestWhenUpdate(fixSession,containsString("Max.fixSession.allowMktOrders"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenUpdateWithNullMaxOrderQty() throws Exception {
        FixSession fixSession = getNewUpdateEntity();
        fixSession.setMaxOrderQty(null);
        super.shouldReturnBadRequestWhenUpdate(fixSession,containsString("NotNull.fixSession.maxOrderQty"));
    }

    @Test
    public void shouldReturnBadRequestWhenUpdateWithInvalidMaxOrderQtySize() throws Exception {
        FixSession fixSession = getNewUpdateEntity();
        byte[] json = super.jsonReplace(fixSession, "maxOrderQty", Long.toString(Integer.MAX_VALUE+1L));
        super.shouldReturnBadRequestWhenUpdate(json,fixSession,containsString("maxOrderQty"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenUpdateWithNullMaxNotionalOrderQty() throws Exception {
        FixSession fixSession = getNewUpdateEntity();
        fixSession.setMaxNotionalOrderQty(null);
        super.shouldReturnBadRequestWhenUpdate(fixSession,containsString("NotNull.fixSession.maxNotionalOrderQty"));
    }

    @Test
    public void shouldReturnBadRequestWhenUpdateWithInvalidMaxNotionalOrderQtySize() throws Exception {
        FixSession fixSession = getNewUpdateEntity();
        byte[] json = super.jsonReplace(fixSession, "maxNotionalOrderQty", "9223372036854775808");
        super.shouldReturnBadRequestWhenUpdate(json,fixSession,containsString("maxNotionalOrderQty"));
    }

    @Test
    public void shouldReturnBadRequestWhenUpdateWithNullHeartBeatInterval() throws Exception {
        FixSession fixSession = getNewUpdateEntity();
        fixSession.setHeartbeatInterval(null);
        super.shouldReturnBadRequestWhenUpdate(fixSession,containsString("NotNull.fixSession.heartbeatInterval"));
    }

    @Test
    public void shouldReturnBadRequestWhenUpdateWithInvalidHeartBeatIntervalSize() throws Exception {
        FixSession fixSession = getNewUpdateEntity();
        fixSession.setHeartbeatInterval(Short.MAX_VALUE+1);
        super.shouldReturnBadRequestWhenUpdate(fixSession,containsString("Max.fixSession.heartbeatInterval"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenUpdateWithNullBeginString() throws Exception {
        FixSession fixSession = getNewUpdateEntity();
        fixSession.setBeginString(null);
        super.shouldReturnBadRequestWhenUpdate(fixSession,containsString("NotNull.fixSession.beginString"));
    }

    @Test
    public void shouldReturnBadRequestWhenUpdateWithInvalidBeginStringSize() throws Exception {
        FixSession fixSession = getNewUpdateEntity();
        fixSession.setBeginString(RandomString.make(9));
        super.shouldReturnBadRequestWhenUpdate(fixSession,containsString("Size.fixSession.beginString"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenUpdateWithNullDefaultOrderCapacity() throws Exception {
        FixSession fixSession = getNewUpdateEntity();
        fixSession.setDefaultOrderCapacity(null);
        super.shouldReturnBadRequestWhenUpdate(fixSession,containsString("NotNull.fixSession.defaultOrderCapacity"));
    }

    @Test
    public void shouldReturnBadRequestWhenUpdateWithInvalidDefaultOrderCapacitySize() throws Exception {
        FixSession fixSession = getNewUpdateEntity();
        byte[] json = super.jsonReplace(fixSession, "defaultOrderCapacity", "INVALID");
        super.shouldReturnBadRequestWhenUpdate(json,fixSession,containsString("OrderCapacity` from String \"INVALID\": value not one of declared Enum instance names"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenUpdateWithNullOnlyAllowTestSymbols() throws Exception {
        FixSession fixSession = getNewUpdateEntity();
        fixSession.setOnlyAllowTestSymbols(null);
        super.shouldReturnBadRequestWhenUpdate(fixSession,containsString("NotNull.fixSession.onlyAllowTestSymbols"));
    }

    @Test
    public void shouldReturnBadRequestWhenUpdateWithInvalidOnlyAllowTestSymbolsSize() throws Exception {
        FixSession fixSession = getNewUpdateEntity();
        fixSession.setOnlyAllowTestSymbols(2);
        super.shouldReturnBadRequestWhenUpdate(fixSession,containsString("Max.fixSession.onlyAllowTestSymbols"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenUpdateWithNullSymbology() throws Exception {
        FixSession fixSession = getNewUpdateEntity();
        fixSession.setSymbology(null);
        super.shouldReturnBadRequestWhenUpdate(fixSession,containsString("NotNull.fixSession.symbology"));
    }

    @Test
    public void shouldReturnBadRequestWhenUpdateWithInvalidSymbology() throws Exception {
        FixSession fixSession = getNewUpdateEntity();
        byte[] json = super.jsonReplace(fixSession, "symbology", "INVALID");
        super.shouldReturnBadRequestWhenUpdate(json,fixSession,containsString("Symbology` from String \"INVALID\": value not one of declared Enum instance names"));
    }
    
    @Test
    public void shouldReturnUpdatedWhenUpdateWithNullPassword() throws Exception {
        FixSession fixSession = getNewUpdateEntity();
        fixSession.setPassword(null);
        
        Mockito.when(service.findById(fixSession.getId())).thenReturn(Optional.of(fixSession));
        
        super.shouldReturnUpdated(fixSession);
    }

    @Test
    public void shouldReturnBadRequestWhenUpdateWithInvalidPasswordSize() throws Exception {
        FixSession fixSession = getNewUpdateEntity();
        fixSession.setPassword(RandomString.make(33));
        super.shouldReturnBadRequestWhenUpdate(fixSession,containsString("Size.fixSession.password"));
    } 
    
}