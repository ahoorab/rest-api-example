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
import com.example.ex.model.entity.admin.FixSessionMpid;
import com.example.ex.model.entity.admin.Mpid;
import com.example.ex.model.entity.admin.TradeNotificationRule;
import com.example.ex.model.type.BrokerType;
import com.example.ex.model.type.ConnectionType;
import com.example.ex.model.type.DcType;
import com.example.ex.model.type.InstructionType;
import com.example.ex.model.type.NotificationType;
import com.example.ex.model.type.OrderCapacity;
import com.example.ex.model.type.RoleType;
import com.example.ex.model.type.Status;
import com.example.ex.model.type.Symbology;

import net.bytebuddy.utility.RandomString;

public class TradeNotificationRuleRepositoryTest extends BaseJpaTest<TradeNotificationRule, Integer> {

    @Autowired
    private TradeNotificationRuleRepository tradeNotificationRuleRepository;

    @Override
    protected JpaRepository<TradeNotificationRule, Integer> getImplementationRepository() {
        return tradeNotificationRuleRepository;
    }
    
    @Override
    public List<BaseEntity> getDependencies() {
        List<BaseEntity> dependencies = new ArrayList<>();
        dependencies.add(new Firm("tdbank", "tdbank", "tdbank", 0, 0, 0, 0, 0, 0, 0, Status.ENABLED));
        dependencies.add(new Firm("firm", "firm", "firm", 0, 0, 0, 0, 0, 0, 0, Status.ENABLED));
        dependencies.add(new Firm("exx", "exx", "exx", 0, 0, 0, 0, 0, 0, 0, Status.ENABLED));
        dependencies.add(new AppType("ADMN",1,1,0,Status.ENABLED));
        dependencies.add(new AppServer("fo", "172.31.45.24", "34.199.149.130", "fo.dev", "dev-ex-fo.exx.nyc", "dev-ex-cg.exx.nyc", 1, 1, 1, "empy note", 1));
        dependencies.add(new AppJvm("SEQR","fo","SEQR",1,"8G","nose","test","INFO",Status.ENABLED));
        dependencies.add(new AppJvm("SEQR2","fo","SEQR2",1,"8G","nose","test","INFO",Status.ENABLED));
        dependencies.add(new AppInstance("CLGW01","ADMN",-1,"SEQR","SEQR2",1,115,Status.ENABLED));
        dependencies.add(new DealCode("exx","Example","EXXX","",DcType.EXX,"exx","exx","exx","ledger","ledgerAccount",Status.ENABLED));
        dependencies.add(new Mpid("exx","exx","exx","exx",null,BrokerType.NONE,BrokerType.NONE,1,0L,"US",Status.ENABLED));
        dependencies.add(new Mpid("scotia","scotia","scotia","exx",null,BrokerType.NONE,BrokerType.NONE,1,0L,"US",Status.ENABLED));
        dependencies.add(new Mpid("mpid","mpid","mpid","exx",null,BrokerType.NONE,BrokerType.NONE,1,0L,"US",Status.ENABLED));
        dependencies.add(new FixSession("hera-dc","hera-dc","CLGW01","exx","EXXX","EXXG","127.0.0.1",".*",9001,ConnectionType.ACCEPTOR,"A",Status.ENABLED,1,1,1,1000000,10000000L,30,"FIX.4.4",OrderCapacity.PRINCIPAL,0,Symbology.INET,""));
        dependencies.add(new FixSession("scot-dc","scot-dc","CLGW01","exx","EXXX","EXXG","127.0.0.1",".*",9001,ConnectionType.ACCEPTOR,"A",Status.ENABLED,1,1,1,1000000,10000000L,30,"FIX.4.4",OrderCapacity.PRINCIPAL,0,Symbology.INET,""));
        dependencies.add(new FixSession("fs","fs","CLGW01","exx","EXXX","EXXG","127.0.0.1",".*",9001,ConnectionType.ACCEPTOR,"A",Status.ENABLED,1,1,1,1000000,10000000L,30,"FIX.4.4",OrderCapacity.PRINCIPAL,0,Symbology.INET,""));
        dependencies.add(new FixSessionMpid("scot-dc","scot-dc","exx","--","OO",InstructionType.COMPOSITE,Status.ENABLED));
        dependencies.add(new FixSessionMpid("fsmpid","fs","mpid","--","OO",InstructionType.COMPOSITE,Status.ENABLED));
        return dependencies;
    }
    
    @Override
    protected TradeNotificationRule getNewTestEntity() {
        return new TradeNotificationRule("hera-dc", "Heraeus trades", NotificationType.DROPCOPY, "tdbank", RoleType.TRADING, "fsmpid", "hera-dc", Status.ENABLED);
    }

    @Override
    protected void updateEntity(TradeNotificationRule entity) {
        entity.setHandle("scot-dc");
        entity.setName("Scotia trades");
        entity.setNotificationType(NotificationType.NONE);
        entity.setFirm("firm");
        entity.setRole(RoleType.BROKER);
        entity.setOnlyForFixSessionMpid("fsmpid");
        entity.setFixSession("scot-dc");
        entity.setStatus(Status.DISABLED);
    }
    
    @Test(expected=DataIntegrityViolationException.class)
    public void shouldNotSaveTradeNotificationRuleWithDuplicatedHandle() {
        TradeNotificationRule tradeNotificationRule = getNewTestEntity();
        tradeNotificationRule.setHandle("duplicated");
        try {
            tradeNotificationRuleRepository.save(tradeNotificationRule);
        } catch (Exception e) {
            fail("should save entity: " + e.getMessage());
        }

        tradeNotificationRule = new TradeNotificationRule();
        updateEntity(tradeNotificationRule);
        tradeNotificationRule.setHandle("duplicated");
        tradeNotificationRuleRepository.save(tradeNotificationRule);
    }
    
    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveTradeNotificationRuleWithNullHandle() {
        TradeNotificationRule tradeNotificationRule = getNewTestEntity();
        tradeNotificationRule.setHandle(null);

        tradeNotificationRuleRepository.save(tradeNotificationRule);
    }

    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveTradeNotificationRuleWithHandleSizeGreaterThan12() {
        TradeNotificationRule tradeNotificationRule = getNewTestEntity();
        tradeNotificationRule.setHandle(RandomString.make(13));
        tradeNotificationRuleRepository.save(tradeNotificationRule);
    }

    @Test(expected=DataIntegrityViolationException.class)
    public void shouldNotSaveTradeNotificationRuleWithDuplicatedRole_NotificationType_FixSession_Firm_OnlyForFixSessionMpid() {
        TradeNotificationRule tradeNotificationRule = getNewTestEntity();
        tradeNotificationRule.setRole(RoleType.BROKER);
        tradeNotificationRule.setNotificationType(NotificationType.DROPCOPY);
        tradeNotificationRule.setFixSession("fs");
        tradeNotificationRule.setFirm("firm");
        tradeNotificationRule.setOnlyForFixSessionMpid("fsmpid");
        try {
            tradeNotificationRuleRepository.save(tradeNotificationRule);
        } catch (Exception e) {
            fail("should save entity: " + e.getMessage());
        }

        tradeNotificationRule = new TradeNotificationRule();
        updateEntity(tradeNotificationRule);
        tradeNotificationRule.setRole(RoleType.BROKER);
        tradeNotificationRule.setNotificationType(NotificationType.DROPCOPY);
        tradeNotificationRule.setFixSession("fs");
        tradeNotificationRule.setFirm("firm");
        tradeNotificationRule.setOnlyForFixSessionMpid("fsmpid");

        tradeNotificationRuleRepository.save(tradeNotificationRule);
    }
    
    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveTradeNotificationRuleWithNullName() {
        TradeNotificationRule tradeNotificationRule = getNewTestEntity();
        tradeNotificationRule.setName(null);
        tradeNotificationRuleRepository.save(tradeNotificationRule);
    }
    
    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveTradeNotificationRuleWithNameSizeGreaterThan64() {
        TradeNotificationRule tradeNotificationRule = getNewTestEntity();
        tradeNotificationRule.setName(RandomString.make(65));
        tradeNotificationRuleRepository.save(tradeNotificationRule);
    }

    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveTradeNotificationRuleWithNullNotificationType() {
        TradeNotificationRule tradeNotificationRule = getNewTestEntity();
        tradeNotificationRule.setNotificationType(null);
        tradeNotificationRuleRepository.save(tradeNotificationRule);
    }
    
    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveTradeNotificationRuleWithNullFirm() {
        TradeNotificationRule tradeNotificationRule = getNewTestEntity();
        tradeNotificationRule.setFirm(null);
        tradeNotificationRuleRepository.save(tradeNotificationRule);
    }
    
    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveTradeNotificationRuleWithFirmSizeGreaterThan12() {
        TradeNotificationRule tradeNotificationRule = getNewTestEntity();
        tradeNotificationRule.setFirm(RandomString.make(13));
        tradeNotificationRuleRepository.save(tradeNotificationRule);
    }

    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveTradeNotificationRuleWithNullRole() {
        TradeNotificationRule tradeNotificationRule = getNewTestEntity();
        tradeNotificationRule.setRole(null);
        tradeNotificationRuleRepository.save(tradeNotificationRule);
    }
    
    @Test
    public void shouldSaveTradeNotificationRuleWithNullOnlyForFixSessionMpid() {
        TradeNotificationRule tradeNotificationRule = getNewTestEntity();
        tradeNotificationRule.setOnlyForFixSessionMpid(null);
        TradeNotificationRule savedTradeNotificationRule = tradeNotificationRuleRepository.save(tradeNotificationRule);
        
        assertThat(savedTradeNotificationRule).isEqualTo(tradeNotificationRule);
    }
    
    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveTradeNotificationRuleWithOnlyForFixSessionMpidSizeGreaterThan12() {
        TradeNotificationRule tradeNotificationRule = getNewTestEntity();
        tradeNotificationRule.setOnlyForFixSessionMpid(RandomString.make(13));
        tradeNotificationRuleRepository.save(tradeNotificationRule);
    }

    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveTradeNotificationRuleWithNullFixSession() {
        TradeNotificationRule tradeNotificationRule = getNewTestEntity();
        tradeNotificationRule.setFixSession(null);
        tradeNotificationRuleRepository.save(tradeNotificationRule);
    }
    
    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveTradeNotificationRuleWithFixSessionGreaterThan12Digits() {
        TradeNotificationRule tradeNotificationRule = getNewTestEntity();
        tradeNotificationRule.setFixSession(RandomString.make(13));
        tradeNotificationRuleRepository.save(tradeNotificationRule);
    }   
    
    @Test(expected=ConstraintViolationException.class)
    public void shouldSaveTradeNotificationRuleWithNullStatus() {
        TradeNotificationRule tradeNotificationRule = getNewTestEntity();
        tradeNotificationRule.setStatus(null);
        tradeNotificationRuleRepository.save(tradeNotificationRule);
    }

}