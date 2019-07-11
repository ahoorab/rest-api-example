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
import com.example.ex.model.type.DcType;
import com.example.ex.model.type.Status;

import net.bytebuddy.utility.RandomString;

public class DealCodeRepositoryTest extends BaseJpaTest<DealCode, Integer> {

    @Autowired
    private DealCodeRepository dealCodeRepository;

    @Override
    protected JpaRepository<DealCode, Integer> getImplementationRepository() {
        return dealCodeRepository;
    }
    
    @Override
    public List<BaseEntity> getDependencies() {
        List<BaseEntity> dependencies = new ArrayList<>();
        dependencies.add(new Firm("exx", "exx", "exx", 0, 0, 0, 0, 0, 0, 0, Status.ENABLED));
        dependencies.add(new Firm("rbct", "rbct", "rbct", 0, 0, 0, 0, 0, 0, 0, Status.ENABLED));

        return dependencies;
    }
    
    @Override
    protected DealCode getNewTestEntity() {
        return new DealCode("exx","Example","EXXX","",DcType.EXX,"exx","exx","exx","ledger","ledgerAccount",Status.ENABLED);
    }

    @Override
    protected void updateEntity(DealCode entity) {
        entity.setHandle("rbct");
        entity.setName("Royal Bank of Canada");
        entity.setMnemonic("RBC");
        entity.setDescription("description");
        entity.setDcType(DcType.PRINCIPAL);
        entity.setFirm("rbct");
        entity.setPbFirm("rbct");
        entity.setSubPbFirm("rbct");
        entity.setLedgerCpId("302a300506032b6570032100b844d90e9c1089e672b74a82cc3caa70e532aefc1404d010234346f6f2013945");
        entity.setLedgerTpAccountId("7ee5bd9d-b7c2-462d-9b69-8820535e87b6");
        entity.setStatus(Status.ENABLED);
    }

    @Test(expected=DataIntegrityViolationException.class)
    public void shouldNotSaveDealCodeWithDuplicatedHandle() {
        DealCode dc = getNewTestEntity();
        dc.setHandle("duplicated");
        try {
            dealCodeRepository.save(dc);
        } catch (Exception e) {
            fail("should save entity: " + e.getMessage());
        }
        
        dc = new DealCode();
        updateEntity(dc);
        dc.setHandle("duplicated");
        dealCodeRepository.save(dc);
    }
    
    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveDealCodeWithNullHandle() {
        DealCode dc = getNewTestEntity();
        dc.setHandle(null);

        dealCodeRepository.save(dc);
    }

    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveDealCodeWithHandleSizeGreaterThan12() {
        DealCode dc = getNewTestEntity();
        dc.setHandle(RandomString.make(13));
        dealCodeRepository.save(dc);
    }
    

    @Test(expected=DataIntegrityViolationException.class)
    public void shouldNotSaveDealCodeWithDuplicatedMnemonic() {
        DealCode dc = getNewTestEntity();
        dc.setMnemonic("mnemonic");
        dealCodeRepository.save(dc);

        dc = new DealCode();
        updateEntity(dc);
        dc.setMnemonic("mnemonic");
        dealCodeRepository.save(dc);
    }

    @Test
    public void shouldSaveDealCodeWithNullName() {
        DealCode dc = getNewTestEntity();
        dc.setName(null);

        DealCode savedDealCode = dealCodeRepository.save(dc);
        assertThat(savedDealCode).isEqualTo(dc);
    }

    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveDealCodeWithNameSizeGreaterThan64() {
        DealCode dc = getNewTestEntity();
        dc.setName(RandomString.make(65));
        dealCodeRepository.save(dc);
    }

    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveDealCodeWithNullMnemonic() {
        DealCode dc = getNewTestEntity();
        dc.setMnemonic(null);
        dealCodeRepository.save(dc);
    }

    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveDealCodeWithMnemonicSizeGreaterThan16() {
        DealCode dc = getNewTestEntity();
        dc.setMnemonic(RandomString.make(17));
        dealCodeRepository.save(dc);
    }

    @Test
    public void shouldSaveDealCodeWithNullDescription() {
        DealCode dc = getNewTestEntity();
        dc.setDescription(null);

        DealCode savedDealCode = dealCodeRepository.save(dc);
        assertThat(savedDealCode).isEqualTo(dc);
    }

    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveDealCodeWithDescriptionSizeGreaterThan80() {
        DealCode dc = getNewTestEntity();
        dc.setDescription(RandomString.make(81));
        dealCodeRepository.save(dc);
    }

    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveDealCodeWithNullDcType() {
        DealCode dc = getNewTestEntity();
        dc.setDcType(null);
        dealCodeRepository.save(dc);
    }
    
    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveDealCodeWithNullFirm() {
        DealCode dc = getNewTestEntity();
        dc.setFirm(null);
        dealCodeRepository.save(dc);
    }

    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveDealCodeWithFirmSizeGreaterThan12() {
        DealCode dc = getNewTestEntity();
        dc.setFirm(RandomString.make(13));
        dealCodeRepository.save(dc);
    }
    
    @Test
    public void shouldSaveDealCodeWithNullPbFirm() {
        DealCode dc = getNewTestEntity();
        dc.setPbFirm(null);

        DealCode savedDealCode = dealCodeRepository.save(dc);
        assertThat(savedDealCode).isEqualTo(dc);
    }

    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveDealCodeWithPbFirmSizeGreaterThan12() {
        DealCode dc = getNewTestEntity();
        dc.setPbFirm(RandomString.make(13));
        dealCodeRepository.save(dc);
    }

    @Test
    public void shouldSaveDealCodeWithNullSubPbFirm() {
        DealCode dc = getNewTestEntity();
        dc.setSubPbFirm(null);

        DealCode savedDealCode = dealCodeRepository.save(dc);
        assertThat(savedDealCode).isEqualTo(dc);
    }

    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveDealCodeWithSubPbFirmSizeGreaterThan12() {
        DealCode dc = getNewTestEntity();
        dc.setSubPbFirm(RandomString.make(13));
        dealCodeRepository.save(dc);
    }

    @Test
    public void shouldSaveDealCodeWithNullLedgerCp() {
        DealCode dc = getNewTestEntity();
        dc.setLedgerCpId(null);

        DealCode savedDealCode = dealCodeRepository.save(dc);
        assertThat(savedDealCode).isEqualTo(dc);
    }

    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveDealCodeWithLedgerCpSizeGreaterThan300() {
        DealCode dc = getNewTestEntity();
        dc.setDescription(RandomString.make(301));
        dealCodeRepository.save(dc);
    }

    @Test
    public void shouldSaveDealCodeWithNullLedgerTpAccount() {
        DealCode dc = getNewTestEntity();
        dc.setLedgerTpAccountId(null);

        DealCode savedDealCode = dealCodeRepository.save(dc);
        assertThat(savedDealCode).isEqualTo(dc);
    }

    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveDealCodeWithLedgerTpAccountSizeGreaterThan300() {
        DealCode dc = getNewTestEntity();
        dc.setLedgerTpAccountId(RandomString.make(301));
        dealCodeRepository.save(dc);
    }

    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveDealCodeWithNullStatus() {
        DealCode dc = getNewTestEntity();
        dc.setStatus(null);
        dealCodeRepository.save(dc);
    }

}