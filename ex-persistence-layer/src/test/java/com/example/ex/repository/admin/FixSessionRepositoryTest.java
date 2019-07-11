package com.example.ex.repository.admin;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ex.model.entity.admin.AppInstance;
import com.example.ex.model.entity.admin.AppJvm;
import com.example.ex.model.entity.admin.AppServer;
import com.example.ex.model.entity.admin.AppType;
import com.example.ex.model.entity.admin.BaseEntity;
import com.example.ex.model.entity.admin.DealCode;
import com.example.ex.model.entity.admin.Firm;
import com.example.ex.model.entity.admin.FixSession;
import com.example.ex.model.entity.admin.Mpid;
import com.example.ex.model.type.BrokerType;
import com.example.ex.model.type.ConnectionType;
import com.example.ex.model.type.DcType;
import com.example.ex.model.type.OrderCapacity;
import com.example.ex.model.type.Status;
import com.example.ex.model.type.Symbology;

import net.bytebuddy.utility.RandomString;

public class FixSessionRepositoryTest extends BaseJpaTest<FixSession, Integer> {

    @Autowired
    private FixSessionRepository fixSessionRepository;

    @Override
    protected JpaRepository<FixSession, Integer> getImplementationRepository() {
        return fixSessionRepository;
    }
    
    @Override
    public List<BaseEntity> getDependencies() {
        List<BaseEntity> dependencies = new ArrayList<>();
        dependencies.add(new AppType("ADMN",1,1,0,Status.ENABLED));
        dependencies.add(new AppServer("fo", "172.31.45.24", "34.199.149.130", "fo.dev", "dev-ex-fo.exx.nyc", "dev-ex-cg.exx.nyc", 1, 1, 1, "empy note", 1));
        dependencies.add(new AppJvm("SEQR","fo","SEQR",1,"8G","nose","test","INFO",Status.ENABLED));
        dependencies.add(new AppJvm("SEQR2","fo","SEQR2",1,"8G","nose","test","INFO",Status.ENABLED));
        dependencies.add(new AppInstance("CLGW01","ADMN",-1,"SEQR","SEQR2",99,110,Status.ENABLED));
        dependencies.add(new Firm("exx", "exx", "exx", 0, 0, 0, 0, 0, 0, 0, Status.ENABLED));
        dependencies.add(new DealCode("exx","Example","EXXX","",DcType.EXX,"exx","exx","exx","ledger","ledgerAccount",Status.ENABLED));
        dependencies.add(new Mpid("exx","exx","exx","exx",null,BrokerType.NONE,BrokerType.NONE,1,0L,"US",Status.ENABLED));
        dependencies.add(new Mpid("scotia","scotia","scotia","exx",null,BrokerType.NONE,BrokerType.NONE,1,0L,"US",Status.ENABLED));
        
        return dependencies;
    }
    
    @Override
    protected FixSession getNewTestEntity() {
        return new FixSession("exxx-cg","exxx-cg","CLGW01","exx","EXXX","EXXG","127.0.0.1",".*",9999,ConnectionType.ACCEPTOR,"A",Status.ENABLED,1,1,1,Integer.MAX_VALUE,Long.MAX_VALUE,(int)Short.MAX_VALUE,"FIX.4.4",OrderCapacity.PRINCIPAL,1,Symbology.INET,"");
    }

    @Override
    protected void updateEntity(FixSession entity) {
        entity.setHandle("scot1-cg");
        entity.setName("scot1-cg");
        entity.setAppInstance("CLGW01");
        entity.setMpid("scotia");
        entity.setSenderCompId("SCOT");
        entity.setTargetCompId("EXXG");
        entity.setLocalIp("127.0.0.1");
        entity.setRemoteIp(".*");
        entity.setPort(9002);
        entity.setConnectionType(ConnectionType.ACCEPTOR);
        entity.setDcMsgTypes("A");
        entity.setStatus(Status.DISABLED);
        entity.setCancelOnDisconnect(1);
        entity.setSupportsBusts(1);
        entity.setAllowMktOrders(1);
        entity.setMaxOrderQty(12345);
        entity.setMaxNotionalOrderQty(1000000000000L);
        entity.setHeartbeatInterval(30);
        entity.setBeginString("FIX");
        entity.setDefaultOrderCapacity(OrderCapacity.NONE);
        entity.setOnlyAllowTestSymbols(0);
        entity.setSymbology(Symbology.INET);
        entity.setPassword("XXXX");
    }

    @Test(expected=DataIntegrityViolationException.class)
    public void shouldNotSaveFixSessionWithDuplicatedHandle() {
        FixSession fixSession = getNewTestEntity();
        fixSession.setHandle("duplicated");
        try {
            fixSessionRepository.save(fixSession);
        } catch (Exception e) {
            fail("should save entity: " + e.getMessage());
        }

        fixSession = new FixSession();
        updateEntity(fixSession);
        fixSession.setHandle("duplicated");
        fixSessionRepository.save(fixSession);
    }
    
    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveFixSessionWithNullHandle() {
        FixSession fixSession = getNewTestEntity();
        fixSession.setHandle(null);

        fixSessionRepository.save(fixSession);
    }

    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveFixSessionWithHandleSizeGreaterThan12() {
        FixSession fixSession = getNewTestEntity();
        fixSession.setHandle(RandomString.make(13));
        fixSessionRepository.save(fixSession);
    }
    
    @Test
    public void shouldSaveFixSessionWithNullName() {
        FixSession fixSession = getNewTestEntity();
        fixSession.setName(null);

        FixSession savedFixSession = fixSessionRepository.save(fixSession);
        assertThat(savedFixSession).isEqualTo(fixSession);
    }

    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveFixSessionWithNameSizeGreaterThan64() {
        FixSession fixSession = getNewTestEntity();
        fixSession.setName(RandomString.make(65));
        fixSessionRepository.save(fixSession);
    }

    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveFixSessionWithNullAppInstance() {
        FixSession fixSession = getNewTestEntity();
        fixSession.setAppInstance(null);
        fixSessionRepository.save(fixSession);
    }

    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveFixSessionWithAppInstanceSizeGreaterThan6() {
        FixSession fixSession = getNewTestEntity();
        fixSession.setAppInstance(RandomString.make(7));
        fixSessionRepository.save(fixSession);
    }

    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveFixSessionWithNullMpid() {
        FixSession fixSession = getNewTestEntity();
        fixSession.setMpid(null);
        fixSessionRepository.save(fixSession);
    }

    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveFixSessionWithMpidSizeGreaterThan12() {
        FixSession fixSession = getNewTestEntity();
        fixSession.setMpid(RandomString.make(13));
        fixSessionRepository.save(fixSession);
    }

    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveFixSessionWithNullSender() {
        FixSession fixSession = getNewTestEntity();
        fixSession.setSenderCompId(null);
        fixSessionRepository.save(fixSession);
    }
    
    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveFixSessionWithSenderSizeGreaterThan16() {
        FixSession fixSession = getNewTestEntity();
        fixSession.setSenderCompId(RandomString.make(17));
        fixSessionRepository.save(fixSession);
    }

    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveFixSessionWithNullTarget() {
        FixSession fixSession = getNewTestEntity();
        fixSession.setTargetCompId(null);
        fixSessionRepository.save(fixSession);
    }
    
    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveFixSessionWithTargetSizeGreaterThan16() {
        FixSession fixSession = getNewTestEntity();
        fixSession.setTargetCompId(RandomString.make(17));
        fixSessionRepository.save(fixSession);
    }
    
    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveFixSessionWithNullLocalIp() {
        FixSession fixSession = getNewTestEntity();
        fixSession.setLocalIp(null);
        fixSessionRepository.save(fixSession);
    }
    
    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveFixSessionWithLocalIpSizeGreaterThan15() {
        FixSession fixSession = getNewTestEntity();
        fixSession.setLocalIp(RandomString.make(16));
        fixSessionRepository.save(fixSession);
    }

    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveFixSessionWithNullRemoteIp() {
        FixSession fixSession = getNewTestEntity();
        fixSession.setRemoteIp(null);
        fixSessionRepository.save(fixSession);
    }
    
    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveFixSessionWithRemoteIpSizeGreaterThan15() {
        FixSession fixSession = getNewTestEntity();
        fixSession.setRemoteIp(RandomString.make(16));
        fixSessionRepository.save(fixSession);
    }
    
    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveFixSessionWithNullPort() {
        FixSession fixSession = getNewTestEntity();
        fixSession.setPort(null);
        fixSessionRepository.save(fixSession);
    }

    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveFixSessionWithNullConnectionType() {
        FixSession fixSession = getNewTestEntity();
        fixSession.setConnectionType(null);
        fixSessionRepository.save(fixSession);
    }

    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveFixSessionWithNullDcMsgTypes() {
        FixSession fixSession = getNewTestEntity();
        fixSession.setDcMsgTypes(null);
        fixSessionRepository.save(fixSession);
    }

    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveFixSessionWithDcMsgTypesSizeGreaterThan32() {
        FixSession fixSession = getNewTestEntity();
        fixSession.setDcMsgTypes(RandomString.make(33));
        fixSessionRepository.save(fixSession);
    }

    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveFixSessionWithNullStatus() {
        FixSession fixSession = getNewTestEntity();
        fixSession.setStatus(null);
        fixSessionRepository.save(fixSession);
    }
    
    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveFixSessionWithNullCancelOnDisconnect() {
        FixSession fixSession = getNewTestEntity();
        fixSession.setCancelOnDisconnect(null);
        fixSessionRepository.save(fixSession);
    }

    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveFixSessionWithNullSupportsBusts() {
        FixSession fixSession = getNewTestEntity();
        fixSession.setSupportsBusts(null);
        fixSessionRepository.save(fixSession);
    }

    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveFixSessionWithNullAllowMktOrders() {
        FixSession fixSession = getNewTestEntity();
        fixSession.setAllowMktOrders(null);
        fixSessionRepository.save(fixSession);
    }

    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveFixSessionWithNullMaxOrderQty() {
        FixSession fixSession = getNewTestEntity();
        fixSession.setMaxOrderQty(null);
        fixSessionRepository.save(fixSession);
    }

    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveFixSessionWithNullMaxNotorialOrderQty() {
        FixSession fixSession = getNewTestEntity();
        fixSession.setMaxNotionalOrderQty(null);
        fixSessionRepository.save(fixSession);
    }

    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveFixSessionWithNullHeartbeatInterval() {
        FixSession fixSession = getNewTestEntity();
        fixSession.setHeartbeatInterval(null);
        fixSessionRepository.save(fixSession);
    }

    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveFixSessionWithNullBeginString() {
        FixSession fixSession = getNewTestEntity();
        fixSession.setBeginString(null);
        fixSessionRepository.save(fixSession);
    }

    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveFixSessionWithBeginStringSizeGreaterThan8() {
        FixSession fixSession = getNewTestEntity();
        fixSession.setBeginString(RandomString.make(9));
        fixSessionRepository.save(fixSession);
    }

    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveFixSessionWithNullDefaultOrderCapacity() {
        FixSession fixSession = getNewTestEntity();
        fixSession.setDefaultOrderCapacity(null);
        fixSessionRepository.save(fixSession);
    }

    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveFixSessionWithNullOnllyAllowTestSymbols() {
        FixSession fixSession = getNewTestEntity();
        fixSession.setOnlyAllowTestSymbols(null);
        fixSessionRepository.save(fixSession);
    }

    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveFixSessionWithNullSymbology() {
        FixSession fixSession = getNewTestEntity();
        fixSession.setSymbology(null);
        fixSessionRepository.save(fixSession);
    }

    @Test
    public void shouldSaveFixSessionWithNullPassword() {
        FixSession fixSession = getNewTestEntity();
        fixSession.setPassword(null);

        FixSession savedFixSession = fixSessionRepository.save(fixSession);
        assertThat(savedFixSession).isEqualTo(fixSession);
    }

    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveFixSessionWithPasswordSizeGreaterThan32() {
        FixSession fixSession = getNewTestEntity();
        fixSession.setPassword(RandomString.make(33));
        fixSessionRepository.save(fixSession);
    }
}