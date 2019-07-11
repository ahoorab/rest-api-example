package com.example.ex.repository.admin.integration;

import org.junit.Test;
import javax.validation.ConstraintViolationException;
import org.springframework.test.annotation.IfProfileValue;

import com.example.ex.model.entity.admin.AppJvm;
import com.example.ex.repository.admin.AppJvmRepositoryTest;
import com.example.ex.repository.admin.BaseJpaTest;

@IfProfileValue(name = BaseJpaTest.INTEGRATION_TESTS_PROFILE, value=BaseJpaTest.JPA_PROFILE)
public class AppJvmJpaIntegrationTest extends AppJvmRepositoryTest {

    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveAppJvmWithLaunchSeqSizeGreaterThan32767() {
        AppJvm appJvm = getNewTestEntity();
        appJvm.setLaunchSeq(Short.MAX_VALUE+1);
        getImplementationRepository().save(appJvm);
    }
    
}