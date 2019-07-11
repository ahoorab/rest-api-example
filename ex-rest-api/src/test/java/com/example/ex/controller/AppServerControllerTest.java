package com.example.ex.controller;

import static org.hamcrest.Matchers.containsString;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import com.example.ex.model.entity.admin.AppServer;

import net.bytebuddy.utility.RandomString;

@WebMvcTest(AppServerController.class)
public class AppServerControllerTest extends AdminControllerTest<AppServer> {
    
    @Override
    public void initController() {
        super.baseUri = AppServerController.BASE_URI;
        super.service = this.appServerService;
        super.clazz = AppServer.class;
    }
    
    @Override
    public List<AppServer> getListOfEntities() {
        List<AppServer> appServers = new ArrayList<>();
        
        appServers.add(new AppServer("fo","172.31.45.24","34.199.149.130","fo.dev","dev-ex-fo.exx.nyc","dev-ex-cg.exx.nyc",1,1,1,"empy note",1));
        appServers.add(new AppServer("bo","172.31.33.42","34.203.165.205","bo.dev","dev-ex-bo.exx.nyc","",1,1,1,"empy note",1));
        return appServers;
    }

    @Override
    public AppServer getNewEntity() {
        return new AppServer("no","172.26.22.12","34.233.100.88","no.dev","fake.hostname","fake.uri",1,1,1,"Fake server",1);
    }

    @Test
    public void shouldReturnBadRequestWhenCreateWithNullHandle() throws Exception {
        AppServer appServer = getNewEntity();
        appServer.setHandle(null);
        super.shouldReturnBadRequestWhenCreate(appServer,containsString("NotNull.appServer.handle"));
    }

    @Test
    public void shouldReturnBadRequestWhenCreateWithInvalidHandleSize() throws Exception {
        AppServer appServer = getNewEntity();
        appServer.setHandle(RandomString.make(13));
        super.shouldReturnBadRequestWhenCreate(appServer,containsString("Size.appServer.handle"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenCreateWithNullInternalIp() throws Exception {
        AppServer appServer = getNewEntity();
        appServer.setInternalIp(null);
        super.shouldReturnBadRequestWhenCreate(appServer,containsString("NotNull.appServer.internalIp"));
    }

    @Test
    public void shouldReturnBadRequestWhenCreateWithInvalidInternalIpSize() throws Exception {
        AppServer appServer = getNewEntity();
        appServer.setInternalIp(RandomString.make(17));
        super.shouldReturnBadRequestWhenCreate(appServer,containsString("Size.appServer.internalIp"));
    }
    
    @Test
    public void shouldReturnCreatedWhenCreateWithNullExternalIp() throws Exception {
        AppServer appServer = getNewEntity();
        appServer.setExternalIp(null);
        super.shouldReturnCreated(appServer);
    }

    @Test
    public void shouldReturnBadRequestWhenCreateWithInvalidExternalIpSize() throws Exception {
        AppServer appServer = getNewEntity();
        appServer.setExternalIp(RandomString.make(17));
        super.shouldReturnBadRequestWhenCreate(appServer,containsString("Size.appServer.externalIp"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenCreateWithNullHostname() throws Exception {
        AppServer appServer = getNewEntity();
        appServer.setHostname(null);
        super.shouldReturnBadRequestWhenCreate(appServer,containsString("NotNull.appServer.hostname"));
    }

    @Test
    public void shouldReturnBadRequestWhenCreateWithInvalidHostnameSize() throws Exception {
        AppServer appServer = getNewEntity();
        appServer.setHostname(RandomString.make(33));
        super.shouldReturnBadRequestWhenCreate(appServer,containsString("Size.appServer.hostname"));
    }
    
    @Test
    public void shouldReturnCreatedWhenCreateWithNullUri() throws Exception {
        AppServer appServer = getNewEntity();
        appServer.setUri(null);
        super.shouldReturnCreated(appServer);
    }

    @Test
    public void shouldReturnBadRequestWhenCreateWithInvalidUriSize() throws Exception {
        AppServer appServer = getNewEntity();
        appServer.setUri(RandomString.make(33));
        super.shouldReturnBadRequestWhenCreate(appServer,containsString("Size.appServer.uri"));
    }
    
    @Test
    public void shouldReturnCreatedWhenCreateWithNullUriCg() throws Exception {
        AppServer appServer = getNewEntity();
        appServer.setUriCg(null);
        super.shouldReturnCreated(appServer);
    }

    @Test
    public void shouldReturnBadRequestWhenCreateWithInvalidUriCgSize() throws Exception {
        AppServer appServer = getNewEntity();
        appServer.setUriCg(RandomString.make(33));
        super.shouldReturnBadRequestWhenCreate(appServer,containsString("Size.appServer.uriCg"));
    }

    @Test
    public void shouldReturnBadRequestWhenCreateWithNullIsCore() throws Exception {
        AppServer appServer = getNewEntity();
        appServer.setIsCore(null);
        super.shouldReturnBadRequestWhenCreate(appServer,containsString("NotNull.appServer.isCore"));
    }    
    
    @Test
    public void shouldReturnBadRequestWhenCreateWithInvalidIsCoreSize() throws Exception {
        AppServer appServer = getNewEntity();
        appServer.setIsCore(Byte.MAX_VALUE+1);
        super.shouldReturnBadRequestWhenCreate(appServer,containsString("Max.appServer.isCore"));
    }

    @Test
    public void shouldReturnBadRequestWhenCreateWithNullIsEdge() throws Exception {
        AppServer appServer = getNewEntity();
        appServer.setIsEdge(null);
        super.shouldReturnBadRequestWhenCreate(appServer,containsString("NotNull.appServer.isEdge"));
    }    
    
    @Test
    public void shouldReturnBadRequestWhenCreateWithInvalidIsEdgeSize() throws Exception {
        AppServer appServer = getNewEntity();
        appServer.setIsEdge(Byte.MAX_VALUE+1);
        super.shouldReturnBadRequestWhenCreate(appServer,containsString("Max.appServer.isEdge"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenCreateWithNullIsBo() throws Exception {
        AppServer appServer = getNewEntity();
        appServer.setIsBo(null);
        super.shouldReturnBadRequestWhenCreate(appServer,containsString("NotNull.appServer.isBo"));
    }    
    
    @Test
    public void shouldReturnBadRequestWhenCreateWithInvalidIsBoSize() throws Exception {
        AppServer appServer = getNewEntity();
        appServer.setIsBo(Byte.MAX_VALUE+1);
        super.shouldReturnBadRequestWhenCreate(appServer,containsString("Max.appServer.isBo"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenCreateWithNullIsActive() throws Exception {
        AppServer appServer = getNewEntity();
        appServer.setIsActive(null);
        super.shouldReturnBadRequestWhenCreate(appServer,containsString("NotNull.appServer.isActive"));
    }    
    
    @Test
    public void shouldReturnBadRequestWhenCreateWithInvalidIsActiveSize() throws Exception {
        AppServer appServer = getNewEntity();
        appServer.setIsActive(Byte.MAX_VALUE+1);
        super.shouldReturnBadRequestWhenCreate(appServer,containsString("Max.appServer.isActive"));
    }
    
    @Test
    public void shouldReturnCreatedWhenCreateWithNullNotes() throws Exception {
        AppServer appServer = getNewEntity();
        appServer.setNotes(null);
        super.shouldReturnCreated(appServer);
    }

    @Test
    public void shouldReturnBadRequestWhenCreateWithInvalidNotesSize() throws Exception {
        AppServer appServer = getNewEntity();
        appServer.setNotes(RandomString.make(129));
        super.shouldReturnBadRequestWhenCreate(appServer,containsString("Size.appServer.notes"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenUpdateWithNullHandle() throws Exception {
        AppServer appServer = getNewUpdateEntity();
        appServer.setHandle(null);
        super.shouldReturnBadRequestWhenUpdate(appServer,containsString("NotNull.appServer.handle"));
    }

    @Test
    public void shouldReturnBadRequestWhenUpdateWithInvalidHandleSize() throws Exception {
        AppServer appServer = getNewUpdateEntity();
        appServer.setHandle(RandomString.make(13));
        super.shouldReturnBadRequestWhenUpdate(appServer,containsString("Size.appServer.handle"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenUpdateWithNullInternalIp() throws Exception {
        AppServer appServer = getNewUpdateEntity();
        appServer.setInternalIp(null);
        super.shouldReturnBadRequestWhenUpdate(appServer,containsString("NotNull.appServer.internalIp"));
    }

    @Test
    public void shouldReturnBadRequestWhenUpdateWithInvalidInternalIpSize() throws Exception {
        AppServer appServer = getNewUpdateEntity();
        appServer.setInternalIp(RandomString.make(17));
        super.shouldReturnBadRequestWhenUpdate(appServer,containsString("Size.appServer.internalIp"));
    }
    
    @Test
    public void shouldReturnUpdatedWhenUpdateWithNullExternalIp() throws Exception {
        AppServer appServer = getNewUpdateEntity();
        appServer.setExternalIp(null);
        
        Mockito.when(service.findById(appServer.getId())).thenReturn(Optional.of(appServer));

        super.shouldReturnUpdated(appServer);
    }

    @Test
    public void shouldReturnBadRequestWhenUpdateWithInvalidExternalIpSize() throws Exception {
        AppServer appServer = getNewUpdateEntity();
        appServer.setExternalIp(RandomString.make(17));
        super.shouldReturnBadRequestWhenUpdate(appServer,containsString("Size.appServer.externalIp"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenUpdateWithNullHostname() throws Exception {
        AppServer appServer = getNewUpdateEntity();
        appServer.setHostname(null);
        super.shouldReturnBadRequestWhenUpdate(appServer,containsString("NotNull.appServer.hostname"));
    }

    @Test
    public void shouldReturnBadRequestWhenUpdateWithInvalidHostnameSize() throws Exception {
        AppServer appServer = getNewUpdateEntity();
        appServer.setHostname(RandomString.make(33));
        super.shouldReturnBadRequestWhenUpdate(appServer,containsString("Size.appServer.hostname"));
    }
    
    @Test
    public void shouldReturnUpdatedWhenUpdateWithNullUri() throws Exception {
        AppServer appServer = getNewUpdateEntity();
        appServer.setUri(null);

        Mockito.when(service.findById(appServer.getId())).thenReturn(Optional.of(appServer));

        super.shouldReturnUpdated(appServer);
    }

    @Test
    public void shouldReturnBadRequestWhenUpdateWithInvalidUriSize() throws Exception {
        AppServer appServer = getNewUpdateEntity();
        appServer.setUri(RandomString.make(33));
        super.shouldReturnBadRequestWhenUpdate(appServer,containsString("Size.appServer.uri"));
    }
    
    @Test
    public void shouldReturnUpdatedWhenUpdateWithNullUriCg() throws Exception {
        AppServer appServer = getNewUpdateEntity();
        appServer.setUriCg(null);

        Mockito.when(service.findById(appServer.getId())).thenReturn(Optional.of(appServer));

        super.shouldReturnUpdated(appServer);
    }

    @Test
    public void shouldReturnBadRequestWhenUpdateWithInvalidUriCgSize() throws Exception {
        AppServer appServer = getNewUpdateEntity();
        appServer.setUriCg(RandomString.make(33));
        super.shouldReturnBadRequestWhenUpdate(appServer,containsString("Size.appServer.uriCg"));
    }

    @Test
    public void shouldReturnBadRequestWhenUpdateWithNullIsCore() throws Exception {
        AppServer appServer = getNewUpdateEntity();
        appServer.setIsCore(null);
        super.shouldReturnBadRequestWhenUpdate(appServer,containsString("NotNull.appServer.isCore"));
    }    
    
    @Test
    public void shouldReturnBadRequestWhenUpdateWithInvalidIsCoreSize() throws Exception {
        AppServer appServer = getNewUpdateEntity();
        appServer.setIsCore(Byte.MAX_VALUE+1);
        super.shouldReturnBadRequestWhenUpdate(appServer,containsString("Max.appServer.isCore"));
    }

    @Test
    public void shouldReturnBadRequestWhenUpdateWithNullIsEdge() throws Exception {
        AppServer appServer = getNewUpdateEntity();
        appServer.setIsEdge(null);
        super.shouldReturnBadRequestWhenUpdate(appServer,containsString("NotNull.appServer.isEdge"));
    }    
    
    @Test
    public void shouldReturnBadRequestWhenUpdateWithInvalidIsEdgeSize() throws Exception {
        AppServer appServer = getNewUpdateEntity();
        appServer.setIsEdge(Byte.MAX_VALUE+1);
        super.shouldReturnBadRequestWhenUpdate(appServer,containsString("Max.appServer.isEdge"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenUpdateWithNullIsBo() throws Exception {
        AppServer appServer = getNewUpdateEntity();
        appServer.setIsBo(null);
        super.shouldReturnBadRequestWhenUpdate(appServer,containsString("NotNull.appServer.isBo"));
    }    
    
    @Test
    public void shouldReturnBadRequestWhenUpdateWithInvalidIsBoSize() throws Exception {
        AppServer appServer = getNewUpdateEntity();
        appServer.setIsBo(Byte.MAX_VALUE+1);
        super.shouldReturnBadRequestWhenUpdate(appServer,containsString("Max.appServer.isBo"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenUpdateWithNullIsActive() throws Exception {
        AppServer appServer = getNewUpdateEntity();
        appServer.setIsActive(null);
        super.shouldReturnBadRequestWhenUpdate(appServer,containsString("NotNull.appServer.isActive"));
    }    
    
    @Test
    public void shouldReturnBadRequestWhenUpdateWithInvalidIsActiveSize() throws Exception {
        AppServer appServer = getNewUpdateEntity();
        appServer.setIsActive(Byte.MAX_VALUE+1);
        super.shouldReturnBadRequestWhenUpdate(appServer,containsString("Max.appServer.isActive"));
    }
    
    @Test
    public void shouldReturnUpdatedWhenUpdateWithNullNotes() throws Exception {
        AppServer appServer = getNewUpdateEntity();
        appServer.setNotes(null);

        Mockito.when(service.findById(appServer.getId())).thenReturn(Optional.of(appServer));
        
        super.shouldReturnUpdated(appServer);
    }

    @Test
    public void shouldReturnBadRequestWhenUpdateWithInvalidNotesSize() throws Exception {
        AppServer appServer = getNewUpdateEntity();
        appServer.setNotes(RandomString.make(129));
        super.shouldReturnBadRequestWhenUpdate(appServer,containsString("Size.appServer.notes"));
    }
}