package com.example.ex.repository.admin.integration;

import org.junit.Test;
import javax.validation.ConstraintViolationException;
import org.springframework.test.annotation.IfProfileValue;

import com.example.ex.model.entity.admin.CreditPool;
import com.example.ex.repository.admin.BaseJpaTest;
import com.example.ex.repository.admin.CreditPoolRepositoryTest;

@IfProfileValue(name = BaseJpaTest.INTEGRATION_TESTS_PROFILE, value=BaseJpaTest.JPA_PROFILE)
public class CreditPoolJpaIntegrationTest extends CreditPoolRepositoryTest {
    
    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveCreditPoolWithWarningGreaterThan127() {
        CreditPool creditPool = getNewTestEntity();
        creditPool.setWarningPct(Byte.MAX_VALUE+1);
        getImplementationRepository().save(creditPool);
    }

}