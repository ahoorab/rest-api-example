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
import com.example.ex.model.entity.admin.CreditPool;
import com.example.ex.model.entity.admin.Firm;
import com.example.ex.model.type.CreditMethod;
import com.example.ex.model.type.Status;

import net.bytebuddy.utility.RandomString;

public class CreditPoolRepositoryTest extends BaseJpaTest<CreditPool, Integer> {

    @Autowired
    private CreditPoolRepository creditPoolRepository;

    @Override
    protected JpaRepository<CreditPool, Integer> getImplementationRepository() {
        return creditPoolRepository;
    }
    
    @Override
    public List<BaseEntity> getDependencies() {
        List<BaseEntity> dependencies = new ArrayList<>();
        dependencies.add(new Firm("scotia", "scotia", "scotia", 0, 0, 0, 0, 0, 0, 0, Status.ENABLED));
        dependencies.add(new Firm("scotia2", "scotia2", "scotia2", 0, 0, 0, 0, 0, 0, 0, Status.ENABLED));

        return dependencies;
    }

    @Override
    protected CreditPool getNewTestEntity() {
        return new CreditPool("scot-tdbk","scot-tdb","scotia","scotia td bank",CreditMethod.NET_SHORTS,Integer.MAX_VALUE,Integer.MAX_VALUE,127,Status.ENABLED);
    }

    @Override
    protected void updateEntity(CreditPool entity) {
        entity.setHandle("scot-rb");
        entity.setMnemonic("scot-rbc");
        entity.setGrantorFirm("scotia2");
        entity.setName("scotia rbc");
        entity.setCreditMethod(CreditMethod.NET_GREATER);
        entity.setLimitNop(0);
        entity.setLimitDaily(Integer.MAX_VALUE);
        entity.setWarningPct(90);
        entity.setStatus(Status.DISABLED);
    }
    
    @Test(expected=DataIntegrityViolationException.class)
    public void shouldNotSaveCreditPoolWithDuplicatedHandle() {
        CreditPool creditPool = getNewTestEntity();
        creditPool.setHandle("duplicated");
        try {
            creditPoolRepository.save(creditPool);
        } catch (Exception e) {
            fail("should save entity: " + e.getMessage());
        }

        creditPool = new CreditPool();
        updateEntity(creditPool);
        creditPool.setHandle("duplicated");
        creditPoolRepository.save(creditPool);
    }
    
    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveCreditPoolWithNullHandle() {
        CreditPool creditPool = getNewTestEntity();
        creditPool.setHandle(null);

        creditPoolRepository.save(creditPool);
    }

    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveCreditPoolWithHandleSizeGreaterThan12() {
        CreditPool creditPool = getNewTestEntity();
        creditPool.setHandle(RandomString.make(13));
        creditPoolRepository.save(creditPool);
    }

    @Test(expected=DataIntegrityViolationException.class)
    public void shouldNotSaveCreditPoolWithDuplicatedMnemonic() {
        CreditPool creditPool = getNewTestEntity();
        creditPool.setMnemonic("duplicated");
        try {
            creditPoolRepository.save(creditPool);
        } catch (Exception e) {
            fail("should save entity: " + e.getMessage());
        }

        creditPool = new CreditPool();
        updateEntity(creditPool);
        creditPool.setMnemonic("duplicated");
        creditPoolRepository.save(creditPool);
    }
    
    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveCreditPoolWithNullMnemonic() {
        CreditPool creditPool = getNewTestEntity();
        creditPool.setMnemonic(null);

        creditPoolRepository.save(creditPool);
    }

    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveCreditPoolWithMnemonicSizeGreaterThan16() {
        CreditPool creditPool = getNewTestEntity();
        creditPool.setMnemonic(RandomString.make(17));
        creditPoolRepository.save(creditPool);
    }
    
    @Test
    public void shouldSaveCreditPoolWithNullName() {
        CreditPool creditPool = getNewTestEntity();
        creditPool.setName(null);

        CreditPool savedCreditPool = creditPoolRepository.save(creditPool);

        assertThat(savedCreditPool).isEqualTo(creditPool);
    }
    
    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveCreditPoolWithNameSizeGreaterThan64() {
        CreditPool creditPool = getNewTestEntity();
        creditPool.setName(RandomString.make(65));
        creditPoolRepository.save(creditPool);
    }
    
    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveCreditPoolWithNullGrantorFirm() {
        CreditPool creditPool = getNewTestEntity();
        creditPool.setGrantorFirm(null);

        creditPoolRepository.save(creditPool);
    }

    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveCreditPoolWithGrantorFirmSizeGreaterThan12() {
        CreditPool creditPool = getNewTestEntity();
        creditPool.setGrantorFirm(RandomString.make(13));
        creditPoolRepository.save(creditPool);
    }
    
    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveCreditPoolWithNullCreditMethod() {
        CreditPool creditPool = getNewTestEntity();
        creditPool.setCreditMethod(null);

        creditPoolRepository.save(creditPool);
    }
    
    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveCreditPoolWithNullLimitNop() {
        CreditPool creditPool = getNewTestEntity();
        creditPool.setLimitNop(null);

        creditPoolRepository.save(creditPool);
    }
    
    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveCreditPoolWithNullLimitDaily() {
        CreditPool creditPool = getNewTestEntity();
        creditPool.setLimitDaily(null);

        creditPoolRepository.save(creditPool);
    }
    
    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveCreditPoolWithNullWarning() {
        CreditPool creditPool = getNewTestEntity();
        creditPool.setWarningPct(null);

        creditPoolRepository.save(creditPool);
    }
    
    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveCreditPoolWithNullStatus() {
        CreditPool creditPool = getNewTestEntity();
        creditPool.setStatus(null);

        creditPoolRepository.save(creditPool);
    }

}