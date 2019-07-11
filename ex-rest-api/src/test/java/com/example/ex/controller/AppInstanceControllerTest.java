package com.example.ex.controller;

import static org.hamcrest.Matchers.containsString;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import com.example.ex.model.entity.admin.AppInstance;
import com.example.ex.model.entity.admin.AppJvm;
import com.example.ex.model.entity.admin.AppType;
import com.example.ex.model.type.Status;

import net.bytebuddy.utility.RandomString;

@WebMvcTest
public class AppInstanceControllerTest extends AdminControllerTest<AppInstance> {
    
    @Override
    public void initController() {
        super.baseUri = AppInstanceController.BASE_URI;
        super.service = this.appInstanceService;
        super.clazz = AppInstance.class;
    }
    
    @Override
    public List<AppInstance> getListOfEntities() {
        AppType admn = new AppType();
        admn.setName("ADMN");
        AppType rdis = new AppType();
        rdis.setName("RDIS");

        AppJvm seqr = new AppJvm();
        seqr.setHandle("seqr");
        AppJvm exch = new AppJvm();
        exch.setHandle("exch");
        
        List<AppInstance> instances = new ArrayList<>();   
        instances.add(new AppInstance("CLGW01",admn.getName(),-1,seqr.getHandle(),null,100,null,Status.ENABLED));
        instances.add(new AppInstance("RDIS01",rdis.getName(),-1,exch.getHandle(),null,100,null,Status.ENABLED));
        
        return instances;
    }

    @Override
    public AppInstance getNewEntity() {
        return new AppInstance("RDIS01","RDIS",-1,"EXCH","EXCH2",10,11,Status.ENABLED);
    }
    
    @Test
    public void shouldReturnBadRequestWhenCreateWithNullAppId() throws Exception {
        AppInstance appInstance = getNewEntity();
        appInstance.setAppId(null);
        super.shouldReturnBadRequestWhenCreate(appInstance,containsString("NotNull.appInstance.appId"));
    }

    @Test
    public void shouldReturnBadRequestWhenCreateWithInvalidAppIdSize() throws Exception {
        AppInstance appInstance = getNewEntity();
        appInstance.setAppId(RandomString.make(7));
        super.shouldReturnBadRequestWhenCreate(appInstance,containsString("Size.appInstance.appId"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenCreateWithNullAppType() throws Exception {
        AppInstance appInstance = getNewEntity();
        appInstance.setAppType(null);
        super.shouldReturnBadRequestWhenCreate(appInstance,containsString("NotNull.appInstance.appType"));
    }

    @Test
    public void shouldReturnBadRequestWhenCreateWithInvalidAppTypeSize() throws Exception {
        AppInstance appInstance = getNewEntity();
        appInstance.setAppType(RandomString.make(5));
        super.shouldReturnBadRequestWhenCreate(appInstance,containsString("Size.appInstance.appType"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenCreateWithNullThreadNumber() throws Exception {
        AppInstance appInstance = getNewEntity();
        appInstance.setThreadNumber(null);
        super.shouldReturnBadRequestWhenCreate(appInstance,containsString("NotNull.appInstance.threadNumber"));
    }

    @Test
    public void shouldReturnBadRequestWhenCreateWithInvalidThreadNumberSize() throws Exception {
        AppInstance appInstance = getNewEntity();
        appInstance.setThreadNumber(Short.MAX_VALUE+1);
        super.shouldReturnBadRequestWhenCreate(appInstance,containsString("Max.appInstance.threadNumber"));
    }
    
    @Test
    public void shouldReturnCreatedWhenCreateWithNullFirstJvm() throws Exception {
        AppInstance appInstance = getNewEntity();
        appInstance.setFirstJvm(null);
        super.shouldReturnCreated(appInstance);
    }

    @Test
    public void shouldReturnBadRequestWhenCreateWithInvalidFirstJvmSize() throws Exception {
        AppInstance appInstance = getNewEntity();
        appInstance.setFirstJvm(RandomString.make(13));
        super.shouldReturnBadRequestWhenCreate(appInstance,containsString("Size.appInstance.firstJvm"));
    }
    
    @Test
    public void shouldReturnCreatedWhenCreateWithNullSecondJvm() throws Exception {
        AppInstance appInstance = getNewEntity();
        appInstance.setSecondJvm(null);
        super.shouldReturnCreated(appInstance);
    }

    @Test
    public void shouldReturnBadRequestWhenCreateWithInvalidSecondJvmSize() throws Exception {
        AppInstance appInstance = getNewEntity();
        appInstance.setSecondJvm(RandomString.make(13));
        super.shouldReturnBadRequestWhenCreate(appInstance,containsString("Size.appInstance.secondJvm"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenCreateWithNullFirstSequence() throws Exception {
        AppInstance appInstance = getNewEntity();
        appInstance.setFirstSequence(null);
        super.shouldReturnBadRequestWhenCreate(appInstance,containsString("NotNull.appInstance.firstSequence"));
    }

    @Test
    public void shouldReturnBadRequestWhenCreateWithInvalidFirstSequenceSize() throws Exception {
        AppInstance appInstance = getNewEntity();
        appInstance.setFirstSequence(Short.MAX_VALUE+1);
        super.shouldReturnBadRequestWhenCreate(appInstance,containsString("Max.appInstance.firstSequence"));
    }
    
    @Test
    public void shouldReturnCreatedWhenCreateWithNullSecondSequence() throws Exception {
        AppInstance appInstance = getNewEntity();
        appInstance.setSecondSequence(null);
        super.shouldReturnCreated(appInstance);
    }

    @Test
    public void shouldReturnBadRequestWhenCreateWithInvalidSecondSequenceSize() throws Exception {
        AppInstance appInstance = getNewEntity();
        appInstance.setSecondSequence(Short.MAX_VALUE+1);
        super.shouldReturnBadRequestWhenCreate(appInstance,containsString("Max.appInstance.secondSequence"));
    }
    
    @Test
    public void shouldReturnCreatedWhenCreateWithNullStatus() throws Exception {
        AppInstance appInstance = getNewEntity();
        appInstance.setStatus(null);
        super.shouldReturnCreated(appInstance);
    }
    
    @Test
    public void shouldReturnBadRequestWhenCreateWithInvalidStatus() throws Exception {
        AppInstance appInstance = getNewEntity();
        byte[] json = super.jsonReplace(appInstance, "status", "INVALID");
        super.shouldReturnBadRequestWhenCreate(json,appInstance,containsString("Status` from String \"INVALID\": value not one of declared Enum instance names"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenUpdateWithNullAppId() throws Exception {
        AppInstance appInstance = getNewUpdateEntity();
        appInstance.setAppId(null);
        super.shouldReturnBadRequestWhenUpdate(appInstance,containsString("NotNull.appInstance.appId"));
    }

    @Test
    public void shouldReturnBadRequestWhenUpdateWithInvalidAppIdSize() throws Exception {
        AppInstance appInstance = getNewUpdateEntity();
        appInstance.setAppId(RandomString.make(7));
        super.shouldReturnBadRequestWhenUpdate(appInstance,containsString("Size.appInstance.appId"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenUpdateWithNullAppType() throws Exception {
        AppInstance appInstance = getNewUpdateEntity();
        appInstance.setAppType(null);
        super.shouldReturnBadRequestWhenUpdate(appInstance,containsString("NotNull.appInstance.appType"));
    }

    @Test
    public void shouldReturnBadRequestWhenUpdateWithInvalidAppTypeSize() throws Exception {
        AppInstance appInstance = getNewUpdateEntity();
        appInstance.setAppType(RandomString.make(5));
        super.shouldReturnBadRequestWhenUpdate(appInstance,containsString("Size.appInstance.appType"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenUpdateWithNullThreadNumber() throws Exception {
        AppInstance appInstance = getNewUpdateEntity();
        appInstance.setThreadNumber(null);
        super.shouldReturnBadRequestWhenUpdate(appInstance,containsString("NotNull.appInstance.threadNumber"));
    }

    @Test
    public void shouldReturnBadRequestWhenUpdateWithInvalidThreadNumberSize() throws Exception {
        AppInstance appInstance = getNewUpdateEntity();
        appInstance.setThreadNumber(Short.MAX_VALUE+1);
        super.shouldReturnBadRequestWhenUpdate(appInstance,containsString("Max.appInstance.threadNumber"));
    }
    
    @Test
    public void shouldReturnUpdatedWhenUpdateWithNullFirstJvm() throws Exception {
        AppInstance appInstance = getNewUpdateEntity();
        appInstance.setFirstJvm(null);

        Mockito.when(service.findById(appInstance.getId())).thenReturn(Optional.of(appInstance));

        super.shouldReturnUpdated(appInstance);
    }

    @Test
    public void shouldReturnBadRequestWhenUpdateWithInvalidFirstJvmSize() throws Exception {
        AppInstance appInstance = getNewUpdateEntity();
        appInstance.setFirstJvm(RandomString.make(13));
        super.shouldReturnBadRequestWhenUpdate(appInstance,containsString("Size.appInstance.firstJvm"));
    }
    
    @Test
    public void shouldReturnUpdatedWhenUpdateWithNullSecondJvm() throws Exception {
        AppInstance appInstance = getNewUpdateEntity();
        appInstance.setSecondJvm(null);

        Mockito.when(service.findById(appInstance.getId())).thenReturn(Optional.of(appInstance));

        super.shouldReturnUpdated(appInstance);
    }

    @Test
    public void shouldReturnBadRequestWhenUpdateWithInvalidSecondJvmSize() throws Exception {
        AppInstance appInstance = getNewUpdateEntity();
        appInstance.setSecondJvm(RandomString.make(13));
        super.shouldReturnBadRequestWhenUpdate(appInstance,containsString("Size.appInstance.secondJvm"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenUpdateWithNullFirstSequence() throws Exception {
        AppInstance appInstance = getNewUpdateEntity();
        appInstance.setFirstSequence(null);
        super.shouldReturnBadRequestWhenUpdate(appInstance,containsString("NotNull.appInstance.firstSequence"));
    }

    @Test
    public void shouldReturnBadRequestWhenUpdateWithInvalidFirstSequenceSize() throws Exception {
        AppInstance appInstance = getNewUpdateEntity();
        appInstance.setFirstSequence(Short.MAX_VALUE+1);
        super.shouldReturnBadRequestWhenUpdate(appInstance,containsString("Max.appInstance.firstSequence"));
    }
    
    @Test
    public void shouldReturnUpdatedWhenUpdateWithNullSecondSequence() throws Exception {
        AppInstance appInstance = getNewUpdateEntity();
        appInstance.setSecondSequence(null);

        Mockito.when(service.findById(appInstance.getId())).thenReturn(Optional.of(appInstance));

        super.shouldReturnUpdated(appInstance);
    }

    @Test
    public void shouldReturnBadRequestWhenUpdateWithInvalidSecondSequenceSize() throws Exception {
        AppInstance appInstance = getNewUpdateEntity();
        appInstance.setSecondSequence(Short.MAX_VALUE+1);
        super.shouldReturnBadRequestWhenUpdate(appInstance,containsString("Max.appInstance.secondSequence"));
    }
    
    @Test
    public void shouldReturnUpdatedWhenUpdateWithNullStatus() throws Exception {
        AppInstance appInstance = getNewUpdateEntity();
        appInstance.setStatus(null);

        Mockito.when(service.findById(appInstance.getId())).thenReturn(Optional.of(appInstance));

        super.shouldReturnUpdated(appInstance);
    }
    
    @Test
    public void shouldReturnBadRequestWhenUpdateWithInvalidStatus() throws Exception {
        AppInstance appInstance = getNewUpdateEntity();
        byte[] json = super.jsonReplace(appInstance, "status", "INVALID");
        super.shouldReturnBadRequestWhenUpdate(json,appInstance,containsString("Status` from String \"INVALID\": value not one of declared Enum instance names"));
    }
}