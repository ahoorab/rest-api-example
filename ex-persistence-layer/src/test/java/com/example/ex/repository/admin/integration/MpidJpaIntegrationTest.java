package com.example.ex.repository.admin.integration;

import org.junit.Test;
import javax.validation.ConstraintViolationException;
import org.springframework.test.annotation.IfProfileValue;

import com.example.ex.model.entity.admin.Mpid;
import com.example.ex.repository.admin.BaseJpaTest;
import com.example.ex.repository.admin.MpidRepositoryTest;

@IfProfileValue(name = BaseJpaTest.INTEGRATION_TESTS_PROFILE, value=BaseJpaTest.JPA_PROFILE)
public class MpidJpaIntegrationTest extends MpidRepositoryTest {

    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveMpidWithIsFinraNumberSizeGreaterThan127() {
        Mpid mpid = getNewTestEntity();
        mpid.setIsFinraMember(Byte.MAX_VALUE+1);
        getImplementationRepository().save(mpid);
    }

}