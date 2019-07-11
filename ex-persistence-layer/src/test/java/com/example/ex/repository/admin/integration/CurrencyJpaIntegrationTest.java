package com.example.ex.repository.admin.integration;

import org.junit.Test;
import javax.validation.ConstraintViolationException;
import org.springframework.test.annotation.IfProfileValue;

import com.example.ex.model.entity.admin.Currency;
import com.example.ex.repository.admin.BaseJpaTest;
import com.example.ex.repository.admin.CurrencyRepositoryTest;

@IfProfileValue(name = BaseJpaTest.INTEGRATION_TESTS_PROFILE, value=BaseJpaTest.JPA_PROFILE)
public class CurrencyJpaIntegrationTest extends CurrencyRepositoryTest {

    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveCurrencyWithAmountDpGreaterThan127() {
        Currency currency = getNewTestEntity();
        currency.setAmountDp(Byte.MAX_VALUE+1);
        getImplementationRepository().save(currency);
    }
    
}