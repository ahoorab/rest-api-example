package com.example.ex.controller;

import static org.hamcrest.Matchers.containsString;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import com.example.ex.model.entity.admin.AppType;
import com.example.ex.model.type.Status;

import net.bytebuddy.utility.RandomString;

@WebMvcTest
public class AppTypeControllerTest extends AdminControllerTest<AppType>{
    
    @Override
    public void initController() {
        super.baseUri = AppTypeController.BASE_URI;
        super.service = this.appTypeService;
        super.clazz = AppType.class;
    }
    
    @Override
    public List<AppType> getListOfEntities() {
        List<AppType> appTypes = new ArrayList<>();
        appTypes.add(new AppType("ADMN",0,0,0,Status.ENABLED));
        appTypes.add(new AppType("RDIS",0,0,0,Status.DISABLED));
        return appTypes;
    }

    @Override
    public AppType getNewEntity() {
        return new AppType("SIGN",1,0,1,Status.ENABLED);
    }
    
    @Test
    public void shouldReturnBadRequestWhenCreateWithNullName() throws Exception {
        AppType appType = getNewEntity();
        appType.setName(null);
        super.shouldReturnBadRequestWhenCreate(appType,containsString("NotNull.appType.name"));
    }

    @Test
    public void shouldReturnBadRequestWhenCreateWithInvalidNameSize() throws Exception {
        AppType appType = getNewEntity();
        appType.setName(RandomString.make(5));
        super.shouldReturnBadRequestWhenCreate(appType,containsString("Size.appType.name"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenCreateWithNullIsSymbolBased() throws Exception {
        AppType appType = getNewEntity();
        appType.setIsSymbolBased(null);
        super.shouldReturnBadRequestWhenCreate(appType,containsString("NotNull.appType.isSymbolBased"));
    }

    @Test
    public void shouldReturnBadRequestWhenCreateWithInvalidIsSymbolBasedSize() throws Exception {
        AppType appType = getNewEntity();
        appType.setIsSymbolBased(Byte.MAX_VALUE+1);
        super.shouldReturnBadRequestWhenCreate(appType,containsString("Max.appType.isSymbolBased"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenCreateWithNullIsSessionBased() throws Exception {
        AppType appType = getNewEntity();
        appType.setIsSessionBased(null);
        super.shouldReturnBadRequestWhenCreate(appType,containsString("NotNull.appType.isSessionBased"));
    }

    @Test
    public void shouldReturnBadRequestWhenCreateWithInvalidIsSessionBasedSize() throws Exception {
        AppType appType = getNewEntity();
        appType.setIsSessionBased(Byte.MAX_VALUE+1);
        super.shouldReturnBadRequestWhenCreate(appType,containsString("Max.appType.isSessionBased"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenCreateWithNullNeedRefChannel() throws Exception {
        AppType appType = getNewEntity();
        appType.setNeedRefChannelOne(null);
        super.shouldReturnBadRequestWhenCreate(appType,containsString("NotNull.appType.needRefChannelOne"));
    }

    @Test
    public void shouldReturnBadRequestWhenCreateWithInvalidNeedRefChannelSize() throws Exception {
        AppType appType = getNewEntity();
        appType.setNeedRefChannelOne(Byte.MAX_VALUE+1);
        super.shouldReturnBadRequestWhenCreate(appType,containsString("Max.appType.needRefChannelOne"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenCreateWithNullStatus() throws Exception {
        AppType appType = getNewEntity();
        appType.setStatus(null);
        super.shouldReturnBadRequestWhenCreate(appType,containsString("NotNull.appType.status"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenCreateWithInvalidStatus() throws Exception {
        AppType appType = getNewEntity();
        byte[] json = super.jsonReplace(appType, "status", "INVALID");
        super.shouldReturnBadRequestWhenCreate(json,appType,containsString("Status` from String \"INVALID\": value not one of declared Enum instance names"));
    }

    @Test
    public void shouldReturnBadRequestWhenUpdateWithNullName() throws Exception {
        AppType appType = getNewUpdateEntity();
        appType.setName(null);
        super.shouldReturnBadRequestWhenUpdate(appType,containsString("NotNull.appType.name"));
    }

    @Test
    public void shouldReturnBadRequestWhenUpdateWithInvalidNameSize() throws Exception {
        AppType appType = getNewUpdateEntity();
        appType.setName(RandomString.make(5));
        super.shouldReturnBadRequestWhenUpdate(appType,containsString("Size.appType.name"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenUpdateWithNullIsSymbolBased() throws Exception {
        AppType appType = getNewUpdateEntity();
        appType.setIsSymbolBased(null);
        super.shouldReturnBadRequestWhenUpdate(appType,containsString("NotNull.appType.isSymbolBased"));
    }

    @Test
    public void shouldReturnBadRequestWhenUpdateWithInvalidIsSymbolBasedSize() throws Exception {
        AppType appType = getNewUpdateEntity();
        appType.setIsSymbolBased(Byte.MAX_VALUE+1);
        super.shouldReturnBadRequestWhenUpdate(appType,containsString("Max.appType.isSymbolBased"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenUpdateWithNullIsSessionBased() throws Exception {
        AppType appType = getNewUpdateEntity();
        appType.setIsSessionBased(null);
        super.shouldReturnBadRequestWhenUpdate(appType,containsString("NotNull.appType.isSessionBased"));
    }

    @Test
    public void shouldReturnBadRequestWhenUpdateWithInvalidIsSessionBasedSize() throws Exception {
        AppType appType = getNewUpdateEntity();
        appType.setIsSessionBased(Byte.MAX_VALUE+1);
        super.shouldReturnBadRequestWhenUpdate(appType,containsString("Max.appType.isSessionBased"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenUpdateWithNullNeedRefChannel() throws Exception {
        AppType appType = getNewUpdateEntity();
        appType.setNeedRefChannelOne(null);
        super.shouldReturnBadRequestWhenUpdate(appType,containsString("NotNull.appType.needRefChannelOne"));
    }

    @Test
    public void shouldReturnBadRequestWhenUpdateWithInvalidNeedRefChannelSize() throws Exception {
        AppType appType = getNewUpdateEntity();
        appType.setNeedRefChannelOne(Byte.MAX_VALUE+1);
        super.shouldReturnBadRequestWhenUpdate(appType,containsString("Max.appType.needRefChannelOne"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenUpdateWithNullStatus() throws Exception {
        AppType appType = getNewUpdateEntity();
        appType.setStatus(null);
        super.shouldReturnBadRequestWhenUpdate(appType,containsString("NotNull.appType.status"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenUpdateWithInvalidStatus() throws Exception {
        AppType appType = getNewUpdateEntity();
        byte[] json = super.jsonReplace(appType, "status", "INVALID");
        super.shouldReturnBadRequestWhenUpdate(json,appType,containsString("Status` from String \"INVALID\": value not one of declared Enum instance names"));
    }

}