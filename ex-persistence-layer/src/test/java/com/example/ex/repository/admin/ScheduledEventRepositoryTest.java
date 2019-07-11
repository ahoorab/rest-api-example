package com.example.ex.repository.admin;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;

import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ex.model.entity.admin.ScheduledEvent;
import com.example.ex.model.type.DataMasterType;
import com.example.ex.model.type.FieldNameType;
import com.example.ex.model.type.FieldValueType;
import com.example.ex.model.type.Status;

import net.bytebuddy.utility.RandomString;

public class ScheduledEventRepositoryTest extends BaseJpaTest<ScheduledEvent, Integer> {

    @Autowired
    private ScheduledEventRepository scheduledEventRepository;

    @Override
    protected JpaRepository<ScheduledEvent, Integer> getImplementationRepository() {
        return scheduledEventRepository;
    }
    
    @Override
    protected ScheduledEvent getNewTestEntity() {
        return new ScheduledEvent("sysopen-2","System Open","00:03:00",DataMasterType.VENUE_REF_DATA,(int)Short.MAX_VALUE,FieldNameType.MARKET_SESSION,FieldValueType.MARKET_OPEN,"EXXG",Status.ENABLED);
    }
    
    @Override
    protected void updateEntity(ScheduledEvent entity) {
        entity.setHandle("mktopen-2");
        entity.setName("market open");
        entity.setStartTime("00:05:00");
        entity.setRefDataMaster(DataMasterType.NONE);
        entity.setRefChannelId(1);
        entity.setFieldName(FieldNameType.NONE);
        entity.setFieldValue(FieldValueType.SYSTEM_OPEN);
        entity.setFieldAddlInfo("");
        entity.setStatus(Status.DISABLED);
    }
    
    @Test(expected=DataIntegrityViolationException.class)
    public void shouldNotSaveScheduledEventWithDuplicatedHandle() {
        ScheduledEvent scheduledEvent = getNewTestEntity();
        scheduledEvent.setHandle("duplicated");
        try {
            scheduledEventRepository.save(scheduledEvent);
        } catch (Exception e) {
            fail("should save entity: " + e.getMessage());
        }

        scheduledEvent = new ScheduledEvent();
        updateEntity(scheduledEvent);
        scheduledEvent.setHandle("duplicated");
        scheduledEventRepository.save(scheduledEvent);
    }
    
    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveScheduledEventWithNullHandle() {
        ScheduledEvent scheduledEvent = getNewTestEntity();
        scheduledEvent.setHandle(null);

        scheduledEventRepository.save(scheduledEvent);
    }

    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveScheduledEventWithHandleSizeGreaterThan12() {
        ScheduledEvent scheduledEvent = getNewTestEntity();
        scheduledEvent.setHandle(RandomString.make(13));
        scheduledEventRepository.save(scheduledEvent);
    }
    
    @Test
    public void shouldSaveScheduledEventWithNullName() {
        ScheduledEvent scheduledEvent = getNewTestEntity();
        scheduledEvent.setName(null);
        
        ScheduledEvent savedScheduledEvent = scheduledEventRepository.save(scheduledEvent);
        
        assertThat(savedScheduledEvent).isEqualTo(scheduledEvent);
    }

    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveScheduledEventWithNameSizeGreaterThan64() {
        ScheduledEvent scheduledEvent = getNewTestEntity();
        scheduledEvent.setName(RandomString.make(65));
        scheduledEventRepository.save(scheduledEvent);
    }
    
    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveScheduledEventWithNullStartTime() {
        ScheduledEvent scheduledEvent = getNewTestEntity();
        scheduledEvent.setStartTime(null);
        scheduledEventRepository.save(scheduledEvent);
    }

    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveScheduledEventWithStartTimeSizeGreaterThan8() {
        ScheduledEvent scheduledEvent = getNewTestEntity();
        scheduledEvent.setStartTime(RandomString.make(9));
        scheduledEventRepository.save(scheduledEvent);
    }
    
    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveScheduledEventWithNullRefDataMaster() {
        ScheduledEvent scheduledEvent = getNewTestEntity();
        scheduledEvent.setRefDataMaster(null);
        scheduledEventRepository.save(scheduledEvent);
    }
    
    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveScheduledEventWithNullRefChannelId() {
        ScheduledEvent scheduledEvent = getNewTestEntity();
        scheduledEvent.setRefChannelId(null);
        scheduledEventRepository.save(scheduledEvent);
    }
    
    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveScheduledEventWithNullFieldName() {
        ScheduledEvent scheduledEvent = getNewTestEntity();
        scheduledEvent.setFieldName(null);
        scheduledEventRepository.save(scheduledEvent);
    }
    
    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveScheduledEventWithNullFieldValue() {
        ScheduledEvent scheduledEvent = getNewTestEntity();
        scheduledEvent.setFieldValue(null);
        scheduledEventRepository.save(scheduledEvent);
    }
    
    @Test
    public void shouldSaveScheduledEventWithNullAddlInfo() {
        ScheduledEvent scheduledEvent = getNewTestEntity();
        scheduledEvent.setFieldAddlInfo(null);
        
        ScheduledEvent savedScheduledEvent = scheduledEventRepository.save(scheduledEvent);
        
        assertThat(savedScheduledEvent).isEqualTo(scheduledEvent);
    }

    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveScheduledEventWithAddlInfoGreaterThan256() {
        ScheduledEvent scheduledEvent = getNewTestEntity();
        scheduledEvent.setName(RandomString.make(257));
        scheduledEventRepository.save(scheduledEvent);
    }
    
    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveScheduledEventWithNullStatus() {
        ScheduledEvent scheduledEvent = getNewTestEntity();
        scheduledEvent.setStatus(null);
        scheduledEventRepository.save(scheduledEvent);
    }
}