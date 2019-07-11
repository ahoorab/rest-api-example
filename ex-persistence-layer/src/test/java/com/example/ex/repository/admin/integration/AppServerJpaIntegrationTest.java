package com.example.ex.repository.admin.integration;

import org.junit.Test;
import javax.validation.ConstraintViolationException;
import org.springframework.test.annotation.IfProfileValue;

import com.example.ex.model.entity.admin.AppServer;
import com.example.ex.repository.admin.AppServerRepositoryTest;
import com.example.ex.repository.admin.BaseJpaTest;

@IfProfileValue(name = BaseJpaTest.INTEGRATION_TESTS_PROFILE, value=BaseJpaTest.JPA_PROFILE)
public class AppServerJpaIntegrationTest extends AppServerRepositoryTest {

    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveAppServerWithIsCoreSizeGreaterThan127() {
        AppServer appServer = getNewTestEntity();
        appServer.setIsCore(Byte.MAX_VALUE+1);
        getImplementationRepository().save(appServer);
    }
    
    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveAppServerWithIsEdgeSizeGreaterThan127() {
        AppServer appServer = getNewTestEntity();
        appServer.setIsEdge(Byte.MAX_VALUE+1);
        getImplementationRepository().save(appServer);
    }
    
    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveAppServerWithIsBoSizeGreaterThan127() {
        AppServer appServer = getNewTestEntity();
        appServer.setIsBo(Byte.MAX_VALUE+1);
        getImplementationRepository().save(appServer);
    }
    
    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveAppServerWithIsActiveSizeGreaterThan127() {
        AppServer appServer = getNewTestEntity();
        appServer.setIsActive(Byte.MAX_VALUE+1);
        getImplementationRepository().save(appServer);
    }
    
}