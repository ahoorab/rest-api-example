package com.example.ex.repository.admin;

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
import com.example.ex.model.type.BrokerType;
import com.example.ex.model.type.ConnectionType;
import com.example.ex.model.type.DcType;
import com.example.ex.model.type.InstructionType;
import com.example.ex.model.type.OrderCapacity;
import com.example.ex.model.type.Status;
import com.example.ex.model.type.Symbology;

import net.bytebuddy.utility.RandomString;

public class FixSessionMpidRepositoryTest extends BaseJpaTest<FixSessionMpid, Integer> {

    @Autowired
    private FixSessionMpidRepository fixSessionMpidRepository;

    @Override
    protected JpaRepository<FixSessionMpid, Integer> getImplementationRepository() {
        return fixSessionMpidRepository;
    }
    
    @Override
    public List<BaseEntity> getDependencies() {
        List<BaseEntity> dependencies = new ArrayList<>();
        dependencies.add(new AppType("ADMN",1,1,0,Status.ENABLED));
        dependencies.add(new AppServer("fo", "172.31.45.24", "34.199.149.130", "fo.dev", "dev-ex-fo.exx.nyc", "dev-ex-cg.exx.nyc", 1, 1, 1, "empy note", 1));
        dependencies.add(new AppJvm("SEQR","fo","SEQR",1,"8G","nose","test","INFO",Status.ENABLED));
        dependencies.add(new AppJvm("SEQR2","fo","SEQR2",1,"8G","nose","test","INFO",Status.ENABLED));
        dependencies.add(new AppInstance("CLGW01","ADMN",-1,"SEQR","SEQR2",10,15,Status.ENABLED));
        dependencies.add(new Firm("exx", "exx", "exx", 0, 0, 0, 0, 0, 0, 0, Status.ENABLED));
        dependencies.add(new DealCode("exx","Example","EXXX","",DcType.EXX,"exx","exx","exx","ledger","ledgerAccount",Status.ENABLED));
        dependencies.add(new Mpid("exx","exx","exx","exx",null,BrokerType.NONE,BrokerType.NONE,1,0L,"US",Status.ENABLED));
        dependencies.add(new Mpid("scotia","scotia","scotia","exx",null,BrokerType.NONE,BrokerType.NONE,1,0L,"US",Status.ENABLED));
        dependencies.add(new FixSession("exxx-cg","exxx-cg","CLGW01","exx","EXXX","EXXG","127.0.0.1",".*",9001,ConnectionType.ACCEPTOR,"A",Status.ENABLED,1,1,1,100000,100000L,30,"FIX.4.4",OrderCapacity.PRINCIPAL,0,Symbology.INET,""));
        dependencies.add(new FixSession("scot1-cg","scot1-cg","CLGW01","exx","EXXX","EXXG","127.0.0.1",".*",9001,ConnectionType.ACCEPTOR,"A",Status.ENABLED,1,1,1,10000,100000L,30,"FIX.4.4",OrderCapacity.PRINCIPAL,0,Symbology.INET,""));
        
        return dependencies;
    }
    
    @Override
    protected FixSessionMpid getNewTestEntity() {
        return new FixSessionMpid("exxx-cg","exxx-cg","exx","--","OO",InstructionType.COMPOSITE,Status.ENABLED);
    }

    @Override
    protected void updateEntity(FixSessionMpid entity) {
        entity.setHandle("scot1-cg");
        entity.setBrokerSession("scot1-cg");
        entity.setMpid("scotia");
        entity.setAgencyAiqGroup("fg");
        entity.setPrincipalAiqGroup("");
        entity.setMinqtyInstruction(InstructionType.NONE);
        entity.setStatus(Status.DISABLED);
    }

    @Test(expected=DataIntegrityViolationException.class)
    public void shouldNotSaveFixSessionMpidWithDuplicatedHandle() {
        FixSessionMpid fixSessionMpid = getNewTestEntity();
        fixSessionMpid.setHandle("duplicated");
        try {
            fixSessionMpidRepository.save(fixSessionMpid);
        } catch (Exception e) {
            fail("should save entity: " + e.getMessage());
        }

        fixSessionMpid = new FixSessionMpid();
        updateEntity(fixSessionMpid);
        fixSessionMpid.setHandle("duplicated");
        fixSessionMpidRepository.save(fixSessionMpid);
    }
    
    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveFixSessionMpidWithNullHandle() {
        FixSessionMpid fixSessionMpid = getNewTestEntity();
        fixSessionMpid.setHandle(null);

        fixSessionMpidRepository.save(fixSessionMpid);
    }

    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveFixSessionMpidWithHandleSizeGreaterThan12() {
        FixSessionMpid fixSessionMpid = getNewTestEntity();
        fixSessionMpid.setHandle(RandomString.make(13));
        fixSessionMpidRepository.save(fixSessionMpid);
    }

    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveFixSessionMpidWithNullBrokerSession() {
        FixSessionMpid fixSessionMpid = getNewTestEntity();
        fixSessionMpid.setBrokerSession(null);
        fixSessionMpidRepository.save(fixSessionMpid);
    }

    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveFixSessionMpidWithBrokerSessionSizeGreaterThan12() {
        FixSessionMpid fixSessionMpid = getNewTestEntity();
        fixSessionMpid.setBrokerSession(RandomString.make(13));
        fixSessionMpidRepository.save(fixSessionMpid);
    }
    
    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveFixSessionMpidWithNullMpid() {
        FixSessionMpid fixSessionMpid = getNewTestEntity();
        fixSessionMpid.setMpid(null);
        fixSessionMpidRepository.save(fixSessionMpid);
    }

    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveFixSessionMpidWithMpidSizeGreaterThan12() {
        FixSessionMpid fixSessionMpid = getNewTestEntity();
        fixSessionMpid.setMpid(RandomString.make(13));
        fixSessionMpidRepository.save(fixSessionMpid);
    }

    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveFixSessionMpidWithNullAgency() {
        FixSessionMpid fixSessionMpid = getNewTestEntity();
        fixSessionMpid.setAgencyAiqGroup(null);
        fixSessionMpidRepository.save(fixSessionMpid);
    }

    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveFixSessionMpidWithAgencySizeGreaterThan2() {
        FixSessionMpid fixSessionMpid = getNewTestEntity();
        fixSessionMpid.setAgencyAiqGroup(RandomString.make(3));
        fixSessionMpidRepository.save(fixSessionMpid);
    }
    
    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveFixSessionMpidWithNullPrincipal() {
        FixSessionMpid fixSessionMpid = getNewTestEntity();
        fixSessionMpid.setPrincipalAiqGroup(null);
        fixSessionMpidRepository.save(fixSessionMpid);
    }

    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveFixSessionMpidWithPrincipalSizeGreaterThan2() {
        FixSessionMpid fixSessionMpid = getNewTestEntity();
        fixSessionMpid.setPrincipalAiqGroup(RandomString.make(3));
        fixSessionMpidRepository.save(fixSessionMpid);
    }

    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveFixSessionMpidWithNullMinqtyInstruction() {
        FixSessionMpid fixSessionMpid = getNewTestEntity();
        fixSessionMpid.setMinqtyInstruction(null);
        fixSessionMpidRepository.save(fixSessionMpid);
    }

    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveFixSessionMpidWithNullStatus() {
        FixSessionMpid fixSessionMpid = getNewTestEntity();
        fixSessionMpid.setStatus(null);
        fixSessionMpidRepository.save(fixSessionMpid);
    }
}