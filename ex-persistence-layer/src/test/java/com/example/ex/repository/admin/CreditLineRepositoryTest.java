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
import com.example.ex.model.entity.admin.CreditLine;
import com.example.ex.model.entity.admin.CreditPool;
import com.example.ex.model.entity.admin.Firm;
import com.example.ex.model.type.CreditLineType;
import com.example.ex.model.type.CreditMethod;
import com.example.ex.model.type.Status;

import net.bytebuddy.utility.RandomString;

public class CreditLineRepositoryTest extends BaseJpaTest<CreditLine, Integer> {

    @Autowired
    private CreditLineRepository creditLineRepository;

    @Override
    protected JpaRepository<CreditLine, Integer> getImplementationRepository() {
        return creditLineRepository;
    }
    
    @Override
    public List<BaseEntity> getDependencies() {
        List<BaseEntity> dependencies = new ArrayList<>();
        dependencies.add(new Firm("scotia", "scotia", "scotia", 0, 0, 0, 0, 0, 0, 0, Status.ENABLED));
        dependencies.add(new Firm("tdbank", "tdbank", "tdbank", 0, 0, 0, 0, 0, 0, 0, Status.ENABLED));
        dependencies.add(new Firm("rbct", "rbct", "rbct", 0, 0, 0, 0, 0, 0, 0, Status.ENABLED));
        dependencies.add(new Firm("rbct2", "rbct2", "rbct2", 0, 0, 0, 0, 0, 0, 0, Status.ENABLED));
        dependencies.add(new Firm("duplicated", "duplicated", "duplicated", 0, 0, 0, 0, 0, 0, 0, Status.ENABLED));
        dependencies.add(new CreditPool("scot-rb","scot-rb","scotia","scotia bank",CreditMethod.NET_SHORTS,100000000,300000000,75,Status.ENABLED));
        dependencies.add(new CreditPool("tdbk-rbc","tdbk-rbc","tdbank","td bank",CreditMethod.NET_SHORTS,100000000,300000000,75,Status.ENABLED));
        dependencies.add(new CreditPool("duplicated","dupliacted","duplicated","duplicated",CreditMethod.NET_SHORTS,100000000,300000000,75,Status.ENABLED));

        return dependencies;
    }

    @Override
    protected CreditLine getNewTestEntity() {
        return new CreditLine("scot-rbc","scotia:RBC",CreditLineType.PRINCIPAL,"scotia","rbct","scot-rb",Status.ENABLED);
    }

    @Override
    protected void updateEntity(CreditLine entity) {
        entity.setHandle("tdbk-rbc");
        entity.setName("tdbank-rbc");
        entity.setType(CreditLineType.PBCLIENT);
        entity.setGrantorFirm("tdbank");
        entity.setGranteeFirm("rbct2");
        entity.setCreditPool("tdbk-rbc");
        entity.setStatus(Status.DISABLED);
    }
    
    @Test(expected=DataIntegrityViolationException.class)
    public void shouldNotSaveCreditLineWithDuplicatedHandle() {
        CreditLine creditLine = getNewTestEntity();
        creditLine.setHandle("duplicated");
        try {
            creditLineRepository.save(creditLine);
        } catch (Exception e) {
            fail("should save entity: " + e.getMessage());
        }

        creditLine = new CreditLine();
        updateEntity(creditLine);
        creditLine.setHandle("duplicated");
        creditLineRepository.save(creditLine);
    }
    
    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveCreditLineWithNullHandle() {
        CreditLine creditLine = getNewTestEntity();
        creditLine.setHandle(null);

        creditLineRepository.save(creditLine);
    }

    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveCreditLineWithHandleSizeGreaterThan12() {
        CreditLine creditLine = getNewTestEntity();
        creditLine.setHandle(RandomString.make(13));
        creditLineRepository.save(creditLine);
    }

    @Test
    public void shouldSaveCreditLineWithNullName() {
        CreditLine creditLine = getNewTestEntity();
        creditLine.setName(null);

        CreditLine savedCreditLine = creditLineRepository.save(creditLine);

        assertThat(savedCreditLine).isEqualTo(creditLine);
    }
    
    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveCreditLineWithNameSizeGreaterThan64() {
        CreditLine creditLine = getNewTestEntity();
        creditLine.setName(RandomString.make(65));
        creditLineRepository.save(creditLine);
    }
    
    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveCreditLineWithNullType() {
        CreditLine creditLine = getNewTestEntity();
        creditLine.setType(null);

        creditLineRepository.save(creditLine);
    }
    
    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveCreditLineWithNullGrantorFirm() {
        CreditLine creditLine = getNewTestEntity();
        creditLine.setGrantorFirm(null);

        creditLineRepository.save(creditLine);
    }

    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveCreditLineWithGrantorFirmSizeGreaterThan12() {
        CreditLine creditLine = getNewTestEntity();
        creditLine.setGrantorFirm(RandomString.make(13));
        creditLineRepository.save(creditLine);
    }
    
    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveCreditLineWithNullGranteeFirm() {
        CreditLine creditLine = getNewTestEntity();
        creditLine.setGranteeFirm(null);

        creditLineRepository.save(creditLine);
    }

    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveCreditLineWithGranteeFirmSizeGreaterThan12() {
        CreditLine creditLine = getNewTestEntity();
        creditLine.setGranteeFirm(RandomString.make(13));
        creditLineRepository.save(creditLine);
    }
    
    @Test(expected=DataIntegrityViolationException.class)
    public void shouldNotSaveCreditLineWithDuplicatedGrantorGrantee() {
        CreditLine creditLine = getNewTestEntity();
        creditLine.setCreditPool("duplicated");
        creditLine.setGrantorFirm("duplicated");
        creditLine.setGranteeFirm("duplicated");
        try {
            creditLineRepository.save(creditLine);
        } catch (Exception e) {
            fail("should save entity: " + e.getMessage());
        }

        creditLine = new CreditLine();
        updateEntity(creditLine);
        creditLine.setCreditPool("duplicated");
        creditLine.setGrantorFirm("duplicated");
        creditLine.setGranteeFirm("duplicated");
        creditLineRepository.save(creditLine);
    }
    
    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveCreditLineWithNullCreditPool() {
        CreditLine creditLine = getNewTestEntity();
        creditLine.setCreditPool(null);

        creditLineRepository.save(creditLine);
    }

    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveCreditLineWithCreditPoolSizeGreaterThan12() {
        CreditLine creditLine = getNewTestEntity();
        creditLine.setCreditPool(RandomString.make(13));
        creditLineRepository.save(creditLine);
    }
    
    @Test
    public void shouldSaveCreditLineWithNullStatus() {
        CreditLine creditLine = getNewTestEntity();
        creditLine.setStatus(null);

        CreditLine savedCreditLine = creditLineRepository.save(creditLine);

        assertThat(savedCreditLine).isEqualTo(creditLine);
    }

}