package com.example.ex.controller;

import static org.hamcrest.Matchers.containsString;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import com.example.ex.model.entity.admin.AppJvm;
import com.example.ex.model.entity.admin.AppServer;
import com.example.ex.model.type.Status;

import net.bytebuddy.utility.RandomString;

@WebMvcTest
public class AppJvmControllerTest extends AdminControllerTest<AppJvm> {
    
    @Override
    public void initController() {
        super.baseUri = AppJvmController.BASE_URI;
        super.service = this.appJvmService;
        super.clazz = AppJvm.class;
    }
    
    @Override
    public List<AppJvm> getListOfEntities() {
        List<AppJvm> appJvms = new ArrayList<>();
        
        AppServer server = new AppServer();
        server.setHandle("fo");
        appJvms.add(new AppJvm("seqr",server.getHandle(),"seqr",1,"8G",null,null,"INFO",Status.ENABLED));
        appJvms.add(new AppJvm("seqr2",server.getHandle(),"seqr2",2,"12G","8","non null note","INFO",Status.ENABLED));
        return appJvms;
    }

    @Override
    public AppJvm getNewEntity() {
        AppServer server = new AppServer();
        server.setHandle("bo");
        return new AppJvm("seqr3",server.getHandle(),"seqr2",3,"16G",null,null,"INFO",Status.ENABLED);
    }

    @Test
    public void shouldReturnBadRequestWhenCreateWithNullHandle() throws Exception {
        AppJvm appJvm = getNewEntity();
        appJvm.setHandle(null);
        super.shouldReturnBadRequestWhenCreate(appJvm,containsString("NotNull.appJvm.handle"));
    }

    @Test
    public void shouldReturnBadRequestWhenCreateWithInvalidHandleSize() throws Exception {
        AppJvm appJvm = getNewEntity();
        appJvm.setHandle(RandomString.make(13));
        super.shouldReturnBadRequestWhenCreate(appJvm,containsString("Size.appJvm.handle"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenCreateWithNullServer() throws Exception {
        AppJvm appJvm = getNewEntity();
        appJvm.setServer(null);
        super.shouldReturnBadRequestWhenCreate(appJvm,containsString("NotNull.appJvm.server"));
    }

    @Test
    public void shouldReturnBadRequestWhenCreateWithInvalidServerSize() throws Exception {
        AppJvm appJvm = getNewEntity();
        appJvm.setServer(RandomString.make(13));
        super.shouldReturnBadRequestWhenCreate(appJvm,containsString("Size.appJvm.server"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenCreateWithNullLaunchName() throws Exception {
        AppJvm appJvm = getNewEntity();
        appJvm.setLaunchName(null);
        super.shouldReturnBadRequestWhenCreate(appJvm,containsString("NotNull.appJvm.launchName"));
    }

    @Test
    public void shouldReturnBadRequestWhenCreateWithInvalidLaunchNameSize() throws Exception {
        AppJvm appJvm = getNewEntity();
        appJvm.setLaunchName(RandomString.make(9));
        super.shouldReturnBadRequestWhenCreate(appJvm,containsString("Size.appJvm.launchName"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenCreateWithNullLaunchSeq() throws Exception {
        AppJvm appJvm = getNewEntity();
        appJvm.setLaunchSeq(null);
        super.shouldReturnBadRequestWhenCreate(appJvm,containsString("NotNull.appJvm.launchSeq"));
    }

    @Test
    public void shouldReturnBadRequestWhenCreateWithInvalidLaunchSeqSize() throws Exception {
        AppJvm appJvm = getNewEntity();
        appJvm.setLaunchSeq(Short.MAX_VALUE+1);
        super.shouldReturnBadRequestWhenCreate(appJvm,containsString("Max.appJvm.launchSeq"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenCreateWithNullXmem() throws Exception {
        AppJvm appJvm = getNewEntity();
        appJvm.setXmem(null);
        super.shouldReturnBadRequestWhenCreate(appJvm,containsString("NotNull.appJvm.xmem"));
    }

    @Test
    public void shouldReturnBadRequestWhenCreateWithInvalidXmemSize() throws Exception {
        AppJvm appJvm = getNewEntity();
        appJvm.setXmem(RandomString.make(9));
        super.shouldReturnBadRequestWhenCreate(appJvm,containsString("Size.appJvm.xmem"));
    }
    
    @Test
    public void shouldReturnCreatedWhenCreateWithNullJvmthreads() throws Exception {
        AppJvm appJvm = getNewEntity();
        appJvm.setJvmthreads(null);
        super.shouldReturnCreated(appJvm);
    }

    @Test
    public void shouldReturnBadRequestWhenCreateWithInvalidJvmthreadsSize() throws Exception {
        AppJvm appJvm = getNewEntity();
        appJvm.setJvmthreads(RandomString.make(9));
        super.shouldReturnBadRequestWhenCreate(appJvm,containsString("Size.appJvm.jvmthreads"));
    }
    
    @Test
    public void shouldReturnCreatedWhenCreateWithNullNotes() throws Exception {
        AppJvm appJvm = getNewEntity();
        appJvm.setNotes(null);
        super.shouldReturnCreated(appJvm);
    }

    @Test
    public void shouldReturnBadRequestWhenCreateWithInvalidNotesSize() throws Exception {
        AppJvm appJvm = getNewEntity();
        appJvm.setNotes(RandomString.make(129));
        super.shouldReturnBadRequestWhenCreate(appJvm,containsString("Size.appJvm.notes"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenCreateWithNullLogWait() throws Exception {
        AppJvm appJvm = getNewEntity();
        appJvm.setLogWait(null);
        super.shouldReturnBadRequestWhenCreate(appJvm,containsString("NotNull.appJvm.logWait"));
    }

    @Test
    public void shouldReturnBadRequestWhenCreateWithInvalidLogWait() throws Exception {
        AppJvm appJvm = getNewEntity();
        appJvm.setLogWait(RandomString.make(129));
        super.shouldReturnBadRequestWhenCreate(appJvm,containsString("Size.appJvm.logWait"));
    }
    
    @Test
    public void shouldReturnCreatedWhenCreateWithNullStatus() throws Exception {
        AppJvm appJvm = getNewEntity();
        appJvm.setStatus(null);
        super.shouldReturnCreated(appJvm);
    }
    
    @Test
    public void shouldReturnBadRequestWhenCreateWithInvalidStatus() throws Exception {
        AppJvm appJvm = getNewEntity();
        byte[] json = super.jsonReplace(appJvm, "status", "INVALID");
        super.shouldReturnBadRequestWhenCreate(json,appJvm,containsString("Status` from String \"INVALID\": value not one of declared Enum instance names"));
    }

    @Test
    public void shouldReturnBadRequestWhenUpdateWithNullHandle() throws Exception {
        AppJvm appJvm = getNewUpdateEntity();
        appJvm.setHandle(null);
        super.shouldReturnBadRequestWhenUpdate(appJvm,containsString("NotNull.appJvm.handle"));
    }

    @Test
    public void shouldReturnBadRequestWhenUpdateWithInvalidHandleSize() throws Exception {
        AppJvm appJvm = getNewUpdateEntity();
        appJvm.setHandle(RandomString.make(13));
        super.shouldReturnBadRequestWhenUpdate(appJvm,containsString("Size.appJvm.handle"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenUpdateWithNullServer() throws Exception {
        AppJvm appJvm = getNewUpdateEntity();
        appJvm.setServer(null);
        super.shouldReturnBadRequestWhenUpdate(appJvm,containsString("NotNull.appJvm.server"));
    }

    @Test
    public void shouldReturnBadRequestWhenUpdateWithInvalidServerSize() throws Exception {
        AppJvm appJvm = getNewUpdateEntity();
        appJvm.setServer(RandomString.make(13));
        super.shouldReturnBadRequestWhenUpdate(appJvm,containsString("Size.appJvm.server"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenUpdateWithNullLaunchName() throws Exception {
        AppJvm appJvm = getNewUpdateEntity();
        appJvm.setLaunchName(null);
        super.shouldReturnBadRequestWhenUpdate(appJvm,containsString("NotNull.appJvm.launchName"));
    }

    @Test
    public void shouldReturnBadRequestWhenUpdateWithInvalidLaunchNameSize() throws Exception {
        AppJvm appJvm = getNewUpdateEntity();
        appJvm.setLaunchName(RandomString.make(9));
        super.shouldReturnBadRequestWhenUpdate(appJvm,containsString("Size.appJvm.launchName"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenUpdateWithNullLaunchSeq() throws Exception {
        AppJvm appJvm = getNewUpdateEntity();
        appJvm.setLaunchSeq(null);
        super.shouldReturnBadRequestWhenUpdate(appJvm,containsString("NotNull.appJvm.launchSeq"));
    }

    @Test
    public void shouldReturnBadRequestWhenUpdateWithInvalidLaunchSeqSize() throws Exception {
        AppJvm appJvm = getNewUpdateEntity();
        appJvm.setLaunchSeq(Short.MAX_VALUE+1);
        super.shouldReturnBadRequestWhenUpdate(appJvm,containsString("Max.appJvm.launchSeq"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenUpdateWithNullXmem() throws Exception {
        AppJvm appJvm = getNewUpdateEntity();
        appJvm.setXmem(null);
        super.shouldReturnBadRequestWhenUpdate(appJvm,containsString("NotNull.appJvm.xmem"));
    }

    @Test
    public void shouldReturnBadRequestWhenUpdateWithInvalidXmemSize() throws Exception {
        AppJvm appJvm = getNewUpdateEntity();
        appJvm.setXmem(RandomString.make(9));
        super.shouldReturnBadRequestWhenUpdate(appJvm,containsString("Size.appJvm.xmem"));
    }
    
    @Test
    public void shouldReturnUpdatedWhenUpdateWithNullJvmthreads() throws Exception {
        AppJvm appJvm = getNewUpdateEntity();
        appJvm.setJvmthreads(null);
        
        Mockito.when(service.findById(appJvm.getId())).thenReturn(Optional.of(appJvm));

        super.shouldReturnUpdated(appJvm);
    }

    @Test
    public void shouldReturnBadRequestWhenUpdateWithInvalidJvmthreadsSize() throws Exception {
        AppJvm appJvm = getNewUpdateEntity();
        appJvm.setJvmthreads(RandomString.make(9));
        super.shouldReturnBadRequestWhenUpdate(appJvm,containsString("Size.appJvm.jvmthreads"));
    }
    
    @Test
    public void shouldReturnUpdatedWhenUpdateWithNullNotes() throws Exception {
        AppJvm appJvm = getNewUpdateEntity();
        appJvm.setNotes(null);

        Mockito.when(service.findById(appJvm.getId())).thenReturn(Optional.of(appJvm));

        super.shouldReturnUpdated(appJvm);
    }

    @Test
    public void shouldReturnBadRequestWhenUpdateWithInvalidNotesSize() throws Exception {
        AppJvm appJvm = getNewUpdateEntity();
        appJvm.setNotes(RandomString.make(129));
        super.shouldReturnBadRequestWhenUpdate(appJvm,containsString("Size.appJvm.notes"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenUpdateWithNullLogWait() throws Exception {
        AppJvm appJvm = getNewUpdateEntity();
        appJvm.setLogWait(null);
        super.shouldReturnBadRequestWhenUpdate(appJvm,containsString("NotNull.appJvm.logWait"));
    }

    @Test
    public void shouldReturnBadRequestWhenUpdateWithInvalidLogWait() throws Exception {
        AppJvm appJvm = getNewUpdateEntity();
        appJvm.setLogWait(RandomString.make(129));
        super.shouldReturnBadRequestWhenUpdate(appJvm,containsString("Size.appJvm.logWait"));
    }
    
    @Test
    public void shouldReturnUpdatedWhenUpdateWithNullStatus() throws Exception {
        AppJvm appJvm = getNewUpdateEntity();
        appJvm.setStatus(null);

        Mockito.when(service.findById(appJvm.getId())).thenReturn(Optional.of(appJvm));

        super.shouldReturnUpdated(appJvm);
    }
    
    @Test
    public void shouldReturnBadRequestWhenUpdateWithInvalidStatus() throws Exception {
        AppJvm appJvm = getNewUpdateEntity();
        byte[] json = super.jsonReplace(appJvm, "status", "INVALID");
        super.shouldReturnBadRequestWhenUpdate(json,appJvm,containsString("Status` from String \"INVALID\": value not one of declared Enum instance names"));
    }
}