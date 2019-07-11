package com.example.ex.repository.admin.integration;

import org.junit.Test;
import javax.validation.ConstraintViolationException;
import org.springframework.test.annotation.IfProfileValue;

import com.example.ex.model.entity.admin.AppInstance;
import com.example.ex.repository.admin.AppInstanceRepositoryTest;
import com.example.ex.repository.admin.BaseJpaTest;

@IfProfileValue(name = BaseJpaTest.INTEGRATION_TESTS_PROFILE, value=BaseJpaTest.JPA_PROFILE)
public class AppInstanceJpaIntegrationTest extends AppInstanceRepositoryTest {

    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveAppInstanceWithThreadNumberSizeGreaterThan127() {
        AppInstance appInstance = getNewTestEntity();
        appInstance.setThreadNumber(Byte.MAX_VALUE+1);
        appInstanceRepository.save(appInstance);
    }
    
    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveAppInstanceWithSecondSequenceSizeGreaterThan32767() {
        AppInstance appInstance = getNewTestEntity();
        appInstance.setSecondSequence(Short.MAX_VALUE+1);
        appInstanceRepository.save(appInstance);
    }

    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveAppInstanceWithFirstSequenceSizeGreaterThan32767() {
        AppInstance appInstance = getNewTestEntity();
        appInstance.setFirstSequence(Short.MAX_VALUE+1);
        appInstanceRepository.save(appInstance);
    }
}