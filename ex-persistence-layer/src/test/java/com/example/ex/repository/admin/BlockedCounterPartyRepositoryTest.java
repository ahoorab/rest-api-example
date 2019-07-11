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
import com.example.ex.model.entity.admin.BlockedCounterParty;
import com.example.ex.model.entity.admin.DealCode;
import com.example.ex.model.entity.admin.Firm;
import com.example.ex.model.type.DcType;
import com.example.ex.model.type.Status;

import net.bytebuddy.utility.RandomString;

public class BlockedCounterPartyRepositoryTest extends BaseJpaTest<BlockedCounterParty, Integer> {

    @Autowired
    private BlockedCounterPartyRepository blockedCounterPartyRepository;

    @Override
    protected JpaRepository<BlockedCounterParty, Integer> getImplementationRepository() {
        return blockedCounterPartyRepository;
    }
    
    @Override
    public List<BaseEntity> getDependencies() {
        List<BaseEntity> dependencies = new ArrayList<>();
        dependencies.add(new Firm("pbFirm1", "pbFirm1", "Example Markets", 0, 0, 0, 0, 0, 0, 0, Status.ENABLED));
        dependencies.add(new Firm("pbFirm2", "pbFirm2", "Example Markets", 0, 0, 0, 0, 0, 0, 0, Status.ENABLED));
        dependencies.add(new Firm("tradingFirm1", "tradingFirm1", "Example Markets", 0, 0, 0, 0, 0, 0, 0, Status.ENABLED));
        dependencies.add(new Firm("tradingFirm2", "tradingFirm2", "Example Markets", 0, 0, 0, 0, 0, 0, 0, Status.ENABLED));
        dependencies.add(new Firm("scotpb", "scotpb", "Example Markets", 0, 0, 0, 0, 0, 0, 0, Status.ENABLED));
        dependencies.add(new DealCode("hera","hera","hera","",DcType.PRINCIPAL,"pbFirm1","pbFirm2","scotpb","ledger","ledgerAccount",Status.ENABLED));
        dependencies.add(new DealCode("scnd","scnd","scnd","",DcType.PRINCIPAL,"pbFirm1","pbFirm2","scotpb","ledger","ledgerAccount",Status.ENABLED));
        dependencies.add(new DealCode("dealCode1","dealCode1","dealCode1","",DcType.PBCLIENT,"tradingFirm1","tradingFirm2","scotpb","ledger","ledgerAccount",Status.ENABLED));

        return dependencies;
    }

    @Override
    protected BlockedCounterParty getNewTestEntity() {
        return new BlockedCounterParty("hera-scnd","hera-scnd","hera","pbFirm1","tradingFirm1","scnd","pbFirm2","tradingFirm2","note",Status.ENABLED);
    }

    @Override
    protected void updateEntity(BlockedCounterParty entity) {
        entity.setHandle("scot-dc");
        entity.setMnemonic("scotpb-hera");
        entity.setDealCode1("dealCode1");
        entity.setPbFirm1("scotpb");
        entity.setTradingFirm1("tradingFirm1");
        entity.setDealCode2("hera");
        entity.setPbFirm2("pbFirm2");
        entity.setTradingFirm2("tradingFirm2");
        entity.setNotes("");
        entity.setStatus(Status.DISABLED);
    }
    
    @Test(expected=DataIntegrityViolationException.class)
    public void shouldNotSaveBlockedCounterPartyWithDuplicatedHandle() {
        BlockedCounterParty blockedCounterParty = getNewTestEntity();
        blockedCounterParty.setHandle("duplicated");
        
        try {
            blockedCounterPartyRepository.save(blockedCounterParty);
        } catch (Exception e) {
            fail("should save entity: " + e.getMessage());
        }

        blockedCounterParty = new BlockedCounterParty();
        updateEntity(blockedCounterParty);
        blockedCounterParty.setHandle("duplicated");
        
        blockedCounterPartyRepository.save(blockedCounterParty);
    }
    
    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveBlockedCounterPartyWithNullHandle() {
        BlockedCounterParty blockedCounterParty = getNewTestEntity();
        blockedCounterParty.setHandle(null);
        blockedCounterPartyRepository.save(blockedCounterParty);
    }

    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveBlockedCounterPartyWithHandleSizeGreaterThan12() {
        BlockedCounterParty blockedCounterParty = getNewTestEntity();
        blockedCounterParty.setHandle(RandomString.make(13));
        blockedCounterPartyRepository.save(blockedCounterParty);
    }

    @Test(expected=DataIntegrityViolationException.class)
    public void shouldNotSaveBlockedCounterPartyWithDuplicatedMnemonic() {
        BlockedCounterParty blockedCounterParty = getNewTestEntity();
        blockedCounterParty.setMnemonic("duplicated");
        try {
            blockedCounterPartyRepository.save(blockedCounterParty);
        } catch (Exception e) {
            fail("should save entity: " + e.getMessage());
        }

        blockedCounterParty = new BlockedCounterParty();
        updateEntity(blockedCounterParty);
        blockedCounterParty.setMnemonic("duplicated");
        blockedCounterPartyRepository.save(blockedCounterParty);
    }
    
    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveBlockedCounterPartyWithNullMnemonic() {
        BlockedCounterParty blockedCounterParty = getNewTestEntity();
        blockedCounterParty.setMnemonic(null);
        blockedCounterPartyRepository.save(blockedCounterParty);
    }

    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveBlockedCounterPartyWithMnemonicSizeGreaterThan16() {
        BlockedCounterParty blockedCounterParty = getNewTestEntity();
        blockedCounterParty.setMnemonic(RandomString.make(17));
        blockedCounterPartyRepository.save(blockedCounterParty);
    }

    @Test
    public void shouldSaveBlockedCounterPartyWithNullDealCode1() {
        BlockedCounterParty blockedCounterParty = getNewTestEntity();
        blockedCounterParty.setDealCode1(null);
        BlockedCounterParty savedBlockedCounterParty = blockedCounterPartyRepository.save(blockedCounterParty);
        
        assertThat(savedBlockedCounterParty).isEqualTo(blockedCounterParty);
    }
    
    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveBlockedCounterPartyWithDealCode1SizeGreaterThan12() {
        BlockedCounterParty blockedCounterParty = getNewTestEntity();
        blockedCounterParty.setDealCode1(RandomString.make(13));
        blockedCounterPartyRepository.save(blockedCounterParty);
    }
    
    @Test
    public void shouldSaveBlockedCounterPartyWithNullPbFirm1() {
        BlockedCounterParty blockedCounterParty = getNewTestEntity();
        blockedCounterParty.setPbFirm1(null);
        BlockedCounterParty savedBlockedCounterParty = blockedCounterPartyRepository.save(blockedCounterParty);
        
        assertThat(savedBlockedCounterParty).isEqualTo(blockedCounterParty);
    }
    
    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveBlockedCounterPartyWithPbFirm1SizeGreaterThan12() {
        BlockedCounterParty blockedCounterParty = getNewTestEntity();
        blockedCounterParty.setPbFirm1(RandomString.make(13));
        blockedCounterPartyRepository.save(blockedCounterParty);
    }    
    
    @Test
    public void shouldSaveBlockedCounterPartyWithNullTradingFirm1() {
        BlockedCounterParty blockedCounterParty = getNewTestEntity();
        blockedCounterParty.setTradingFirm1(null);
        BlockedCounterParty savedBlockedCounterParty = blockedCounterPartyRepository.save(blockedCounterParty);
        
        assertThat(savedBlockedCounterParty).isEqualTo(blockedCounterParty);
    }
    
    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveBlockedCounterPartyWithTradingFirm1SizeGreaterThan12() {
        BlockedCounterParty blockedCounterParty = getNewTestEntity();
        blockedCounterParty.setTradingFirm1(RandomString.make(13));
        blockedCounterPartyRepository.save(blockedCounterParty);
    }    

    @Test
    public void shouldSaveBlockedCounterPartyWithNullDealCode2() {
        BlockedCounterParty blockedCounterParty = getNewTestEntity();
        blockedCounterParty.setDealCode2(null);
        BlockedCounterParty savedBlockedCounterParty = blockedCounterPartyRepository.save(blockedCounterParty);
        
        assertThat(savedBlockedCounterParty).isEqualTo(blockedCounterParty);
    }
    
    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveBlockedCounterPartyWithDealCode2SizeGreaterThan12() {
        BlockedCounterParty blockedCounterParty = getNewTestEntity();
        blockedCounterParty.setDealCode2(RandomString.make(13));
        blockedCounterPartyRepository.save(blockedCounterParty);
    }
    
    @Test
    public void shouldSaveBlockedCounterPartyWithNullPbFirm2() {
        BlockedCounterParty blockedCounterParty = getNewTestEntity();
        blockedCounterParty.setPbFirm2(null);
        BlockedCounterParty savedBlockedCounterParty = blockedCounterPartyRepository.save(blockedCounterParty);
        
        assertThat(savedBlockedCounterParty).isEqualTo(blockedCounterParty);
    }
    
    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveBlockedCounterPartyWithPbFirm2SizeGreaterThan12() {
        BlockedCounterParty blockedCounterParty = getNewTestEntity();
        blockedCounterParty.setPbFirm2(RandomString.make(13));
        blockedCounterPartyRepository.save(blockedCounterParty);
    }    
    
    @Test
    public void shouldSaveBlockedCounterPartyWithNullTradingFirm2() {
        BlockedCounterParty blockedCounterParty = getNewTestEntity();
        blockedCounterParty.setTradingFirm2(null);
        BlockedCounterParty savedBlockedCounterParty = blockedCounterPartyRepository.save(blockedCounterParty);
        
        assertThat(savedBlockedCounterParty).isEqualTo(blockedCounterParty);
    }
    
    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveBlockedCounterPartyWithTradingFirm2SizeGreaterThan12() {
        BlockedCounterParty blockedCounterParty = getNewTestEntity();
        blockedCounterParty.setTradingFirm2(RandomString.make(13));
        blockedCounterPartyRepository.save(blockedCounterParty);
    }    
    
    @Test(expected=ConstraintViolationException.class)
    public void shouldSaveBlockedCounterPartyWithNullStatus() {
        BlockedCounterParty blockedCounterParty = getNewTestEntity();
        blockedCounterParty.setStatus(null);
        blockedCounterPartyRepository.save(blockedCounterParty);
    }

}