package com.example.ex.repository.admin.integration;

import org.junit.Test;
import javax.validation.ConstraintViolationException;
import org.springframework.test.annotation.IfProfileValue;

import com.example.ex.model.entity.admin.AppType;
import com.example.ex.repository.admin.AppTypeRepositoryTest;
import com.example.ex.repository.admin.BaseJpaTest;

@IfProfileValue(name = BaseJpaTest.INTEGRATION_TESTS_PROFILE, value=BaseJpaTest.JPA_PROFILE)
public class AppTypeJpaIntegrationTest extends AppTypeRepositoryTest {
     
    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveAppTypeWithIsSymbolBasedGreaterThan127() {
        AppType appType = getNewTestEntity();
        appType.setIsSymbolBased(Byte.MAX_VALUE+1);
        getImplementationRepository().save(appType);
    }
    
    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveAppTypeWithIsSessionBasedGreaterThan127() {
        AppType appType = getNewTestEntity();
        appType.setIsSessionBased(Byte.MAX_VALUE+1);
        getImplementationRepository().save(appType);
    }
    
    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveAppTypeWithNeedRefChannelGreaterThan127() {
        AppType appType = getNewTestEntity();
        appType.setNeedRefChannelOne(Byte.MAX_VALUE+1);
        getImplementationRepository().save(appType);
    }

}