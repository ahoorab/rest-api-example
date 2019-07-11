package com.example.ex.repository.admin.integration;

import org.junit.Test;
import javax.validation.ConstraintViolationException;
import org.springframework.test.annotation.IfProfileValue;

import com.example.ex.model.entity.admin.CurrencyPair;
import com.example.ex.repository.admin.BaseJpaTest;
import com.example.ex.repository.admin.CurrencyPairRepositoryTest;

@IfProfileValue(name = BaseJpaTest.INTEGRATION_TESTS_PROFILE, value=BaseJpaTest.JPA_PROFILE)
public class CurrencyPairJpaIntegrationTest extends CurrencyPairRepositoryTest {

    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveCurrencyPairWithDaysToSpotSizeGreaterThan127() {
        CurrencyPair cp = getNewTestEntity();
        cp.setDaysToSpot(Byte.MAX_VALUE+1);
        getImplementationRepository().save(cp);
    }
    
    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveCurrencyPairWithRatePrecisionSizeGreaterThan127() {
        CurrencyPair cp = getNewTestEntity();
        cp.setRatePrecision(Byte.MAX_VALUE+1);
        getImplementationRepository().save(cp);
    }
    
}