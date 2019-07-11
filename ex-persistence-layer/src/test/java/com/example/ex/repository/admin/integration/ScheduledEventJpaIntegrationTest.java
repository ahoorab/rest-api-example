package com.example.ex.repository.admin.integration;

import org.junit.Test;
import javax.validation.ConstraintViolationException;
import org.springframework.test.annotation.IfProfileValue;

import com.example.ex.model.entity.admin.ScheduledEvent;
import com.example.ex.repository.admin.BaseJpaTest;
import com.example.ex.repository.admin.ScheduledEventRepositoryTest;

@IfProfileValue(name = BaseJpaTest.INTEGRATION_TESTS_PROFILE, value=BaseJpaTest.JPA_PROFILE)
public class ScheduledEventJpaIntegrationTest extends ScheduledEventRepositoryTest {

    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveScheduledEventWithRefChannelIdGreaterThan3Digits() {
        ScheduledEvent scheduledEvent = getNewTestEntity();
        scheduledEvent.setRefChannelId(Short.MAX_VALUE+1);
        getImplementationRepository().save(scheduledEvent);
    }

}