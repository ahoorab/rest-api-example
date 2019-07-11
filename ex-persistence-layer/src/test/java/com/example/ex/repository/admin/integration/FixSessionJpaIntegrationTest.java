package com.example.ex.repository.admin.integration;

import org.junit.Test;
import javax.validation.ConstraintViolationException;
import org.springframework.test.annotation.IfProfileValue;

import com.example.ex.model.entity.admin.FixSession;
import com.example.ex.repository.admin.BaseJpaTest;
import com.example.ex.repository.admin.FixSessionRepositoryTest;

@IfProfileValue(name = BaseJpaTest.INTEGRATION_TESTS_PROFILE, value=BaseJpaTest.JPA_PROFILE)
public class FixSessionJpaIntegrationTest extends FixSessionRepositoryTest {

    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveFixSessionWithPortSizeGreaterThan127() {
        FixSession fixSession = getNewTestEntity();
        fixSession.setCancelOnDisconnect(Byte.MAX_VALUE+1);
        getImplementationRepository().save(fixSession);
    }

    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveFixSessionWithSupportsBustsSizeGreaterThan127() {
        FixSession fixSession = getNewTestEntity();
        fixSession.setSupportsBusts(Byte.MAX_VALUE+1);
        getImplementationRepository().save(fixSession);
    }

    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveFixSessionWithSupportsAllowMktOrdersSizeGreaterThan127() {
        FixSession fixSession = getNewTestEntity();
        fixSession.setAllowMktOrders(Byte.MAX_VALUE+1);
        getImplementationRepository().save(fixSession);
    }

    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveFixSessionWithHeartbeatIntervalSizeGreaterThan32767() {
        FixSession fixSession = getNewTestEntity();
        fixSession.setHeartbeatInterval(Short.MAX_VALUE+1);
        getImplementationRepository().save(fixSession);
    }
    
    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveFixSessionWithOnlyAllowTestSymbolsSizeGreaterThan127() {
        FixSession fixSession = getNewTestEntity();
        fixSession.setOnlyAllowTestSymbols(Byte.MAX_VALUE+1);
        getImplementationRepository().save(fixSession);
    }

}