package com.example.ex.controller;

import static org.hamcrest.Matchers.containsString;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import com.example.ex.model.entity.admin.ScheduledEvent;
import com.example.ex.model.type.DataMasterType;
import com.example.ex.model.type.FieldNameType;
import com.example.ex.model.type.FieldValueType;
import com.example.ex.model.type.Status;

import net.bytebuddy.utility.RandomString;

@WebMvcTest
public class ScheduledEventControllerTest extends AdminControllerTest<ScheduledEvent> {

    @Override
    public void initController() {
        super.baseUri = ScheduledEventController.BASE_URI;
        super.service = this.scheduledEventService;
        super.clazz = ScheduledEvent.class;
    }

    @Override
    public List<ScheduledEvent> getListOfEntities() {
        List<ScheduledEvent> scheduledEvents = new ArrayList<>();
        
        scheduledEvents.add(new ScheduledEvent("sysopen-2","System Open","00:03:00",DataMasterType.VENUE_REF_DATA,(int)Short.MAX_VALUE,FieldNameType.MARKET_SESSION,FieldValueType.MARKET_OPEN,"EXXG",Status.ENABLED));
        scheduledEvents.add(new ScheduledEvent("mktopen-2",null,"00:05:00",DataMasterType.NONE,1,FieldNameType.NONE,FieldValueType.SYSTEM_CLOSE,null,Status.DISABLED));
        
        return scheduledEvents;
    }

    @Override
    public ScheduledEvent getNewEntity() {
        return new ScheduledEvent("mktclose-6","Market close","10:03:00",DataMasterType.NONE,4,FieldNameType.MARKET_SESSION,FieldValueType.MARKET_CLOSE,"test",Status.DISABLED);
    }
    
    @Test
    public void shouldReturnBadRequestWhenCreateWithNullHandle() throws Exception {
        ScheduledEvent scheduledEvent = getNewEntity();
        scheduledEvent.setHandle(null);
        super.shouldReturnBadRequestWhenCreate(scheduledEvent,containsString("NotNull.scheduledEvent.handle"));
    }

    @Test
    public void shouldReturnBadRequestWhenCreateWithInvalidHandleSize() throws Exception {
        ScheduledEvent scheduledEvent = getNewEntity();
        scheduledEvent.setHandle(RandomString.make(13));
        super.shouldReturnBadRequestWhenCreate(scheduledEvent,containsString("Size.scheduledEvent.handle"));
    }
    
    @Test
    public void shouldReturnCreatedWhenCreateWithNullName() throws Exception {
        ScheduledEvent scheduledEvent = getNewEntity();
        scheduledEvent.setName(null);
        super.shouldReturnCreated(scheduledEvent);
    }

    @Test
    public void shouldReturnBadRequestWhenCreateWithInvalidNameSize() throws Exception {
        ScheduledEvent scheduledEvent = getNewEntity();
        scheduledEvent.setName(RandomString.make(65));
        super.shouldReturnBadRequestWhenCreate(scheduledEvent,containsString("Size.scheduledEvent.name"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenCreateWithNullStartTime() throws Exception {
        ScheduledEvent scheduledEvent = getNewEntity();
        scheduledEvent.setStartTime(null);
        super.shouldReturnBadRequestWhenCreate(scheduledEvent,containsString("NotNull.scheduledEvent.startTime"));
    }

    @Test
    public void shouldReturnBadRequestWhenCreateWithInvalidStartTimeSize() throws Exception {
        ScheduledEvent scheduledEvent = getNewEntity();
        scheduledEvent.setStartTime(RandomString.make(9));
        super.shouldReturnBadRequestWhenCreate(scheduledEvent,containsString("Size.scheduledEvent.startTime"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenCreateWithNullRefDataMaster() throws Exception {
        ScheduledEvent scheduledEvent = getNewEntity();
        scheduledEvent.setRefDataMaster(null);
        super.shouldReturnBadRequestWhenCreate(scheduledEvent,containsString("NotNull.scheduledEvent.refDataMaster"));
    }
 
    @Test
    public void shouldReturnBadRequestWhenCreateWithInvalidRefDataMaster() throws Exception {
        ScheduledEvent scheduledEvent = getNewEntity();
        byte[] json = super.jsonReplace(scheduledEvent, "refDataMaster", "INVALID");
        super.shouldReturnBadRequestWhenCreate(json,scheduledEvent,containsString("DataMasterType` from String \"INVALID\": value not one of declared Enum instance names"));
    }

    @Test
    public void shouldReturnBadRequestWhenCreateWithNullRefChannel() throws Exception {
        ScheduledEvent scheduledEvent = getNewEntity();
        scheduledEvent.setRefChannelId(null);
        super.shouldReturnBadRequestWhenCreate(scheduledEvent,containsString("NotNull.scheduledEvent.refChannelId"));
    }

    @Test
    public void shouldReturnBadRequestWhenCreateWithInvalidRefChannelSize() throws Exception {
        ScheduledEvent scheduledEvent = getNewEntity();
        scheduledEvent.setRefChannelId(Short.MAX_VALUE+1);
        super.shouldReturnBadRequestWhenCreate(scheduledEvent,containsString("Max.scheduledEvent.refChannelId"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenCreateWithNullFieldName() throws Exception {
        ScheduledEvent scheduledEvent = getNewEntity();
        scheduledEvent.setFieldName(null);
        super.shouldReturnBadRequestWhenCreate(scheduledEvent,containsString("NotNull.scheduledEvent.fieldName"));
    }
 
    @Test
    public void shouldReturnBadRequestWhenCreateWithInvalidFieldName() throws Exception {
        ScheduledEvent scheduledEvent = getNewEntity();
        byte[] json = super.jsonReplace(scheduledEvent, "fieldName", "INVALID");
        super.shouldReturnBadRequestWhenCreate(json,scheduledEvent,containsString("FieldNameType` from String \"INVALID\": value not one of declared Enum instance names"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenCreateWithNullFieldValue() throws Exception {
        ScheduledEvent scheduledEvent = getNewEntity();
        scheduledEvent.setFieldValue(null);
        super.shouldReturnBadRequestWhenCreate(scheduledEvent,containsString("NotNull.scheduledEvent.fieldValue"));
    }
 
    @Test
    public void shouldReturnBadRequestWhenCreateWithInvalidFieldValue() throws Exception {
        ScheduledEvent scheduledEvent = getNewEntity();
        byte[] json = super.jsonReplace(scheduledEvent, "fieldValue", "INVALID");
        super.shouldReturnBadRequestWhenCreate(json,scheduledEvent,containsString("FieldValueType` from String \"INVALID\": value not one of declared Enum instance names"));
    }
    
    public void shouldReturnCreatedWhenCreateWithNullAddlInfo() throws Exception {
        ScheduledEvent scheduledEvent = getNewEntity();
        scheduledEvent.setFieldAddlInfo(null);
        super.shouldReturnBadRequestWhenCreate(scheduledEvent,containsString("NotNull.scheduledEvent.fieldAddlInfo"));
    }

    @Test
    public void shouldReturnBadRequestWhenCreateWithInvalidAddlInfoSize() throws Exception {
        ScheduledEvent scheduledEvent = getNewEntity();
        scheduledEvent.setFieldAddlInfo(RandomString.make(257));
        super.shouldReturnBadRequestWhenCreate(scheduledEvent,containsString("Size.scheduledEvent.fieldAddlInfo"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenCreateWithNullStatus() throws Exception {
        ScheduledEvent scheduledEvent = getNewEntity();
        scheduledEvent.setStatus(null);
        super.shouldReturnBadRequestWhenCreate(scheduledEvent,containsString("NotNull.scheduledEvent.status"));
    }
 
    @Test
    public void shouldReturnBadRequestWhenCreateWithInvalidStatus() throws Exception {
        ScheduledEvent scheduledEvent = getNewEntity();
        byte[] json = super.jsonReplace(scheduledEvent, "status", "INVALID");
        super.shouldReturnBadRequestWhenCreate(json,scheduledEvent,containsString("Status` from String \"INVALID\": value not one of declared Enum instance names"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenUpdateWithNullHandle() throws Exception {
        ScheduledEvent scheduledEvent = getNewUpdateEntity();
        scheduledEvent.setHandle(null);
        super.shouldReturnBadRequestWhenUpdate(scheduledEvent,containsString("NotNull.scheduledEvent.handle"));
    }

    @Test
    public void shouldReturnBadRequestWhenUpdateWithInvalidHandleSize() throws Exception {
        ScheduledEvent scheduledEvent = getNewUpdateEntity();
        scheduledEvent.setHandle(RandomString.make(13));
        super.shouldReturnBadRequestWhenUpdate(scheduledEvent,containsString("Size.scheduledEvent.handle"));
    }
    
    @Test
    public void shouldReturnUpdatedWhenUpdateWithNullName() throws Exception {
        ScheduledEvent scheduledEvent = getNewUpdateEntity();
        scheduledEvent.setName(null);

        Mockito.when(service.findById(scheduledEvent.getId())).thenReturn(Optional.of(scheduledEvent));

        super.shouldReturnUpdated(scheduledEvent);
    }

    @Test
    public void shouldReturnBadRequestWhenUpdateWithInvalidNameSize() throws Exception {
        ScheduledEvent scheduledEvent = getNewUpdateEntity();
        scheduledEvent.setName(RandomString.make(65));
        super.shouldReturnBadRequestWhenUpdate(scheduledEvent,containsString("Size.scheduledEvent.name"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenUpdateWithNullStartTime() throws Exception {
        ScheduledEvent scheduledEvent = getNewUpdateEntity();
        scheduledEvent.setStartTime(null);
        super.shouldReturnBadRequestWhenUpdate(scheduledEvent,containsString("NotNull.scheduledEvent.startTime"));
    }

    @Test
    public void shouldReturnBadRequestWhenUpdateWithInvalidStartTimeSize() throws Exception {
        ScheduledEvent scheduledEvent = getNewUpdateEntity();
        scheduledEvent.setStartTime(RandomString.make(9));
        super.shouldReturnBadRequestWhenUpdate(scheduledEvent,containsString("Size.scheduledEvent.startTime"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenUpdateWithNullRefDataMaster() throws Exception {
        ScheduledEvent scheduledEvent = getNewUpdateEntity();
        scheduledEvent.setRefDataMaster(null);
        super.shouldReturnBadRequestWhenUpdate(scheduledEvent,containsString("NotNull.scheduledEvent.refDataMaster"));
    }
 
    @Test
    public void shouldReturnBadRequestWhenUpdateWithInvalidRefDataMaster() throws Exception {
        ScheduledEvent scheduledEvent = getNewUpdateEntity();
        scheduledEvent.setId(1);
        byte[] json = super.jsonReplace(scheduledEvent, "refDataMaster", "INVALID");
        super.shouldReturnBadRequestWhenUpdate(json,scheduledEvent,containsString("DataMasterType` from String \"INVALID\": value not one of declared Enum instance names"));
    }

    @Test
    public void shouldReturnBadRequestWhenUpdateWithNullRefChannel() throws Exception {
        ScheduledEvent scheduledEvent = getNewUpdateEntity();
        scheduledEvent.setRefChannelId(null);
        super.shouldReturnBadRequestWhenUpdate(scheduledEvent,containsString("NotNull.scheduledEvent.refChannelId"));
    }

    @Test
    public void shouldReturnBadRequestWhenUpdateWithInvalidRefChannelSize() throws Exception {
        ScheduledEvent scheduledEvent = getNewUpdateEntity();
        scheduledEvent.setRefChannelId(Short.MAX_VALUE+1);
        super.shouldReturnBadRequestWhenUpdate(scheduledEvent,containsString("Max.scheduledEvent.refChannelId"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenUpdateWithNullFieldName() throws Exception {
        ScheduledEvent scheduledEvent = getNewUpdateEntity();
        scheduledEvent.setFieldName(null);
        super.shouldReturnBadRequestWhenUpdate(scheduledEvent,containsString("NotNull.scheduledEvent.fieldName"));
    }
 
    @Test
    public void shouldReturnBadRequestWhenUpdateWithInvalidFieldName() throws Exception {
        ScheduledEvent scheduledEvent = getNewUpdateEntity();
        byte[] json = super.jsonReplace(scheduledEvent, "fieldName", "INVALID");
        super.shouldReturnBadRequestWhenUpdate(json,scheduledEvent,containsString("FieldNameType` from String \"INVALID\": value not one of declared Enum instance names"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenUpdateWithNullFieldValue() throws Exception {
        ScheduledEvent scheduledEvent = getNewUpdateEntity();
        scheduledEvent.setFieldValue(null);
        super.shouldReturnBadRequestWhenUpdate(scheduledEvent,containsString("NotNull.scheduledEvent.fieldValue"));
    }
 
    @Test
    public void shouldReturnBadRequestWhenUpdateWithInvalidFieldValue() throws Exception {
        ScheduledEvent scheduledEvent = getNewUpdateEntity();
        byte[] json = super.jsonReplace(scheduledEvent, "fieldValue", "INVALID");
        super.shouldReturnBadRequestWhenUpdate(json,scheduledEvent,containsString("FieldValueType` from String \"INVALID\": value not one of declared Enum instance names"));
    }
    
    public void shouldReturnUpdatedWhenUpdateWithNullAddlInfo() throws Exception {
        ScheduledEvent scheduledEvent = getNewUpdateEntity();
        scheduledEvent.setFieldAddlInfo(null);

        Mockito.when(service.findById(scheduledEvent.getId())).thenReturn(Optional.of(scheduledEvent));

        super.shouldReturnUpdated(scheduledEvent);
    }

    @Test
    public void shouldReturnBadRequestWhenUpdateWithInvalidAddlInfoSize() throws Exception {
        ScheduledEvent scheduledEvent = getNewUpdateEntity();
        scheduledEvent.setFieldAddlInfo(RandomString.make(257));
        super.shouldReturnBadRequestWhenUpdate(scheduledEvent,containsString("Size.scheduledEvent.fieldAddlInfo"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenUpdateWithNullStatus() throws Exception {
        ScheduledEvent scheduledEvent = getNewUpdateEntity();
        scheduledEvent.setStatus(null);
        super.shouldReturnBadRequestWhenUpdate(scheduledEvent,containsString("NotNull.scheduledEvent.status"));
    }
 
    @Test
    public void shouldReturnBadRequestWhenUpdateWithInvalidStatus() throws Exception {
        ScheduledEvent scheduledEvent = getNewUpdateEntity();
        byte[] json = super.jsonReplace(scheduledEvent, "status", "INVALID");
        super.shouldReturnBadRequestWhenUpdate(json,scheduledEvent,containsString("Status` from String \"INVALID\": value not one of declared Enum instance names"));
    }
     
}