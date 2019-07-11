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

import com.example.ex.model.entity.admin.BaseEntity;
import com.example.ex.model.entity.admin.DealCode;
import com.example.ex.model.entity.admin.Firm;
import com.example.ex.model.entity.admin.Mpid;
import com.example.ex.model.type.BrokerType;
import com.example.ex.model.type.DcType;
import com.example.ex.model.type.Status;

import net.bytebuddy.utility.RandomString;

public class MpidRepositoryTest extends BaseJpaTest<Mpid, Integer> {

    @Autowired
    private MpidRepository mpidRepository;

    @Override
    protected JpaRepository<Mpid, Integer> getImplementationRepository() {
        return mpidRepository;
    }
    
    @Override
    public List<BaseEntity> getDependencies() {
        List<BaseEntity> dependencies = new ArrayList<>();
        dependencies.add(new Firm("exx", "exx", "exx", 0, 0, 0, 0, 0, 0, 0, Status.ENABLED));
        dependencies.add(new DealCode("frst","frst","frst","",DcType.EXX,"exx","exx","exx","ledger","ledgerAccount",Status.ENABLED));
        dependencies.add(new DealCode("grnl","grnl","grnl","",DcType.EXX,"exx","exx","exx","ledger","ledgerAccount",Status.ENABLED));
        dependencies.add(new Mpid("tdbank","tdbank","exx","frst",null,BrokerType.NONE,BrokerType.NONE,1,0L,"US",Status.ENABLED));
        dependencies.add(new Mpid("scotia","scotia","exx","frst",null,BrokerType.NONE,BrokerType.NONE,1,0L,"US",Status.ENABLED));
        
        return dependencies;
    }
    
    @Override
    protected Mpid getNewTestEntity() {
        return new Mpid("first","First eagle","exx","frst","scotia",BrokerType.NONE,BrokerType.NONE,1,Long.MAX_VALUE,"US",Status.ENABLED);
    }

    @Override
    protected void updateEntity(Mpid entity) {
        entity.setHandle("grnl");
        entity.setName("Greenlight");
        entity.setMpid("GRNL");
        entity.setDealCode("grnl");
        entity.setParentMpid("tdbank");
        entity.setBrokerPriorityType(BrokerType.NONE);
        entity.setSelfTradeType(BrokerType.NONE);
        entity.setIsFinraMember(0);
        entity.setCrd(1L);
        entity.setCountry("CA");
        entity.setStatus(Status.DISABLED);
    }

    @Test(expected=DataIntegrityViolationException.class)
    public void shouldNotSaveMpidWithDuplicatedHandle() {
        Mpid mpid = getNewTestEntity();
        mpid.setHandle("duplicated");
        try {
            mpidRepository.save(mpid);
        } catch (Exception e) {
            fail("should save entity: " + e.getMessage());
        }

        mpid = new Mpid();
        updateEntity(mpid);
        mpid.setHandle("duplicated");
        mpidRepository.save(mpid);
    }
    
    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveMpidWithNullHandle() {
        Mpid mpid = getNewTestEntity();
        mpid.setHandle(null);

        mpidRepository.save(mpid);
    }

    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveMpidWithHandleSizeGreaterThan12() {
        Mpid mpid = getNewTestEntity();
        mpid.setHandle(RandomString.make(13));
        mpidRepository.save(mpid);
    }
    
    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveMpidWithNullName() {
        Mpid mpid = getNewTestEntity();
        mpid.setName(null);
        mpidRepository.save(mpid);
    }

    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveMpidWithNameSizeGreaterThan64() {
        Mpid mpid = getNewTestEntity();
        mpid.setName(RandomString.make(65));
        mpidRepository.save(mpid);
    }

    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveMpidWithNullMpid() {
        Mpid mpid = getNewTestEntity();
        mpid.setMpid(null);
        mpidRepository.save(mpid);
    }

    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveMpidWithMpidSizeGreaterThan12() {
        Mpid mpid = getNewTestEntity();
        mpid.setMpid(RandomString.make(13));
        mpidRepository.save(mpid);
    }

    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveMpidWithNullDealCode() {
        Mpid mpid = getNewTestEntity();
        mpid.setDealCode(null);
        mpidRepository.save(mpid);
    }

    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveMpidWithDealCodeSizeGreaterThan12() {
        Mpid mpid = getNewTestEntity();
        mpid.setDealCode(RandomString.make(13));
        mpidRepository.save(mpid);
    }
    
    @Test
    public void shouldSaveMpidWithNullParent() {
        Mpid mpid = getNewTestEntity();
        mpid.setParentMpid(null);

        Mpid savedMpid = mpidRepository.save(mpid);
        assertThat(savedMpid).isEqualTo(mpid);
    }

    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveMpidWithParentSizeGreaterThan12() {
        Mpid mpid = getNewTestEntity();
        mpid.setParentMpid(RandomString.make(13));
        mpidRepository.save(mpid);
    }

    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveMpidWithNullBrokerPriorityType() {
        Mpid mpid = getNewTestEntity();
        mpid.setBrokerPriorityType(null);
        mpidRepository.save(mpid);
    }

    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveMpidWithNullSelfTradeType() {
        Mpid mpid = getNewTestEntity();
        mpid.setSelfTradeType(null);
        mpidRepository.save(mpid);
    }

    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveMpidWithNullIsFinraNumber() {
        Mpid mpid = getNewTestEntity();
        mpid.setIsFinraMember(null);
        mpidRepository.save(mpid);
    }

    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveMpidWithNullCrd() {
        Mpid mpid = getNewTestEntity();
        mpid.setCrd(null);
        mpidRepository.save(mpid);
    }

    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveMpidWithNullCountry() {
        Mpid mpid = getNewTestEntity();
        mpid.setCountry(null);
        mpidRepository.save(mpid);
    }

    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveMpidWithCountrySizeGreaterThan2() {
        Mpid mpid = getNewTestEntity();
        mpid.setCountry(RandomString.make(3));
        mpidRepository.save(mpid);
    }

    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveMpidWithNullStatus() {
        Mpid mpid = getNewTestEntity();
        mpid.setStatus(null);
        mpidRepository.save(mpid);
    }

}