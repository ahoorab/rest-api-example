package com.example.ex.repository.admin.integration;

import org.junit.Test;
import javax.validation.ConstraintViolationException;
import org.springframework.test.annotation.IfProfileValue;

import com.example.ex.model.entity.admin.CurrencyPairSettleDate;
import com.example.ex.repository.admin.BaseJpaTest;
import com.example.ex.repository.admin.CurrencyPairSettleDateRepositoryTest;

@IfProfileValue(name = BaseJpaTest.INTEGRATION_TESTS_PROFILE, value=BaseJpaTest.JPA_PROFILE)
public class CurrencyPairSettleDateJpaIntegrationTest extends CurrencyPairSettleDateRepositoryTest {

    @Test(expected = ConstraintViolationException.class)
    public void shouldNotSaveCurrencyPairSettleDateWithHolidayCountGreaterThan127() {
        CurrencyPairSettleDate cp = getNewTestEntity();
        cp.setHolidayCount(Byte.MAX_VALUE+1);
        getImplementationRepository().save(cp);
    }
}