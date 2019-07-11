package com.example.ex.controller;

import static org.hamcrest.Matchers.containsString;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.ex.model.entity.admin.Firm;
import com.example.ex.model.type.Status;

import net.bytebuddy.utility.RandomString;

@RunWith(SpringRunner.class)
@WebMvcTest
public class FirmControllerTest extends AdminControllerTest<Firm> {

    @Override
    public void initController() {
        super.baseUri = FirmController.BASE_URI;
        super.service = super.firmService;
        super.clazz = Firm.class;
    }

    @Override
    public List<Firm> getListOfEntities() {
        List<Firm> firms = new ArrayList<>();
        firms.add(new Firm("exx", "EXX", "Example Markets", 0, 0, 0, 0, 0, 0, 0, Status.ENABLED));
        firms.add(new Firm("scotia", "SCOT", "Scotia Bank", 1, 0, 0, 0, 0, 0, 0, Status.ENABLED));
        return firms;
    }

    @Override
    public Firm getNewEntity() {
        return new Firm("exx", "EXX", "Example Markets", 0, 0, 0, 0, 0, 0, 0, Status.ENABLED);
    }
    
    @Test
    public void shouldReturnBadRequestWhenCreateWithNullHandle() throws Exception {
        Firm firm = getNewEntity();
        firm.setHandle(null);
        super.shouldReturnBadRequestWhenCreate(firm,containsString("NotNull.firm.handle"));
    }

    @Test
    public void shouldReturnBadRequestWhenCreateWithInvalidHandleSize() throws Exception {
        Firm firm = getNewEntity();
        firm.setHandle(RandomString.make(13));
        super.shouldReturnBadRequestWhenCreate(firm,containsString("Size.firm.handle"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenCreateWithNullMnemonic() throws Exception {
        Firm firm = getNewEntity();
        firm.setMnemonic(null);
        super.shouldReturnBadRequestWhenCreate(firm,containsString("NotNull.firm.mnemonic"));
    }

    @Test
    public void shouldReturnBadRequestWhenCreateWithInvalidMnemonicSize() throws Exception {
        Firm firm = getNewEntity();
        firm.setMnemonic(RandomString.make(17));
        super.shouldReturnBadRequestWhenCreate(firm,containsString("Size.firm.mnemonic"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenCreateWithNullName() throws Exception {
        Firm firm = getNewEntity();
        firm.setName(null);
        super.shouldReturnBadRequestWhenCreate(firm,containsString("NotNull.firm.name"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenCreateWithInvalidNameSize() throws Exception {
        Firm firm = getNewEntity();
        firm.setName(RandomString.make(65));
        super.shouldReturnBadRequestWhenCreate(firm,containsString("Size.firm.name"));
    }

    @Test
    public void shouldReturnBadRequestWhenCreateWithNullFirmType() throws Exception {
        Firm firm = getNewEntity();
        firm.setFirmType(null);
        super.shouldReturnBadRequestWhenCreate(firm,containsString("NotNull.firm.firmType"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenCreateWithInvalidFirmTypeSize() throws Exception {
        Firm firm = getNewEntity();
        firm.setFirmType(Byte.MAX_VALUE+1);
        super.shouldReturnBadRequestWhenCreate(firm,containsString("Max.firm.firmType"));
    }

    @Test
    public void shouldReturnCreatedWhenCreateWithNullHub() throws Exception {
        Firm firm = getNewEntity();
        firm.setIsHub(null);
        super.shouldReturnCreated(firm);
    }

    @Test
    public void shouldReturnBadRequestWhenCreateWithInvalidHubSize() throws Exception {
        Firm firm = getNewEntity();
        firm.setIsHub(2);
        super.shouldReturnBadRequestWhenCreate(firm,containsString("Max.firm.isHub"));
    }

    @Test
    public void shouldReturnCreatedWhenCreateWithNullPrincipal() throws Exception {
        Firm firm = getNewEntity();
        firm.setIsPrincipal(null);
        super.shouldReturnCreated(firm);
    }

    @Test
    public void shouldReturnBadRequestWhenCreateWithInvalidPrincipalSize() throws Exception {
        Firm firm = getNewEntity();
        firm.setIsPrincipal(2);
        super.shouldReturnBadRequestWhenCreate(firm,containsString("Max.firm.isPrincipal"));
    }

    @Test
    public void shouldReturnCreatedWhenCreateWithNullPb() throws Exception {
        Firm firm = getNewEntity();
        firm.setIsPb(null);
        super.shouldReturnCreated(firm);
    }

    @Test
    public void shouldReturnBadRequestWhenCreateWithInvalidPbSize() throws Exception {
        Firm firm = getNewEntity();
        firm.setIsPb(2);
        super.shouldReturnBadRequestWhenCreate(firm,containsString("Max.firm.isPb"));
    }
    
    @Test
    public void shouldReturnCreatedWhenCreateWithNullSubPb() throws Exception {
        Firm firm = getNewEntity();
        firm.setIsSubpb(null);
        super.shouldReturnCreated(firm);
    }

    @Test
    public void shouldReturnBadRequestWhenCreateWithInvalidSubPbSize() throws Exception {
        Firm firm = getNewEntity();
        firm.setIsSubpb(2);
        super.shouldReturnBadRequestWhenCreate(firm,containsString("Max.firm.isSubpb"));
    }
    
    @Test
    public void shouldReturnCreatedWhenCreateWithNullPbClient() throws Exception {
        Firm firm = getNewEntity();
        firm.setIsPbClient(null);
        super.shouldReturnCreated(firm);
    }

    @Test
    public void shouldReturnBadRequestWhenCreateWithInvalidPbClient() throws Exception {
        Firm firm = getNewEntity();
        firm.setIsPbClient(2);
        super.shouldReturnBadRequestWhenCreate(firm,containsString("Max.firm.isPbClient"));
    }
    
    @Test
    public void shouldReturnCreatedWhenCreateWithNullSubPbClient() throws Exception {
        Firm firm = getNewEntity();
        firm.setIsSubpbClient(null);
        super.shouldReturnCreated(firm);
    }

    @Test
    public void shouldReturnBadRequestWhenCreateWithInvalidSubPbClient() throws Exception {
        Firm firm = getNewEntity();
        firm.setIsSubpbClient(2);
        super.shouldReturnBadRequestWhenCreate(firm,containsString("Max.firm.isSubpbClient"));
    }
    
    @Test
    public void shouldReturnCreatedWhenCreateWithNullStatus() throws Exception {
        Firm firm = getNewEntity();
        firm.setStatus(null);
        super.shouldReturnCreated(firm);
    }

    @Test
    public void shouldReturnBadRequestWhenCreateWithStatus() throws Exception {
        Firm firm = getNewEntity();
        byte[] json = super.jsonReplace(firm, "status", "INVALID");
        super.shouldReturnBadRequestWhenCreate(json,firm,containsString("Status` from String \"INVALID\": value not one of declared Enum instance names"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenUpdateWithNullHandle() throws Exception {
        Firm firm = getNewUpdateEntity();
        firm.setHandle(null);
        super.shouldReturnBadRequestWhenUpdate(firm,containsString("NotNull.firm.handle"));
    }

    @Test
    public void shouldReturnBadRequestWhenUpdateWithInvalidHandleSize() throws Exception {
        Firm firm = getNewUpdateEntity();
        firm.setHandle(RandomString.make(13));
        super.shouldReturnBadRequestWhenUpdate(firm,containsString("Size.firm.handle"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenUpdateWithNullMnemonic() throws Exception {
        Firm firm = getNewUpdateEntity();
        firm.setMnemonic(null);
        super.shouldReturnBadRequestWhenUpdate(firm,containsString("NotNull.firm.mnemonic"));
    }

    @Test
    public void shouldReturnBadRequestWhenUpdateWithInvalidMnemonicSize() throws Exception {
        Firm firm = getNewUpdateEntity();
        firm.setMnemonic(RandomString.make(17));
        super.shouldReturnBadRequestWhenUpdate(firm,containsString("Size.firm.mnemonic"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenUpdateWithNullName() throws Exception {
        Firm firm = getNewUpdateEntity();
        firm.setName(null);
        super.shouldReturnBadRequestWhenUpdate(firm,containsString("NotNull.firm.name"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenUpdateWithInvalidNameSize() throws Exception {
        Firm firm = getNewUpdateEntity();
        firm.setName(RandomString.make(65));
        super.shouldReturnBadRequestWhenUpdate(firm,containsString("Size.firm.name"));
    }

    @Test
    public void shouldReturnBadRequestWhenUpdateWithNullFirmType() throws Exception {
        Firm firm = getNewUpdateEntity();
        firm.setFirmType(null);
        super.shouldReturnBadRequestWhenUpdate(firm,containsString("NotNull.firm.firmType"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenUpdateWithInvalidFirmTypeSize() throws Exception {
        Firm firm = getNewUpdateEntity();
        firm.setFirmType(Byte.MAX_VALUE+1);
        super.shouldReturnBadRequestWhenUpdate(firm,containsString("Max.firm.firmType"));
    }

    @Test
    public void shouldReturnUpdatedWhenUpdateWithNullHub() throws Exception {
        Firm firm = getNewUpdateEntity();
        firm.setIsHub(null);

        Mockito.when(service.findById(firm.getId())).thenReturn(Optional.of(firm));

        super.shouldReturnUpdated(firm);
    }

    @Test
    public void shouldReturnBadRequestWhenUpdateWithInvalidHubSize() throws Exception {
        Firm firm = getNewUpdateEntity();
        firm.setIsHub(2);
        super.shouldReturnBadRequestWhenUpdate(firm,containsString("Max.firm.isHub"));
    }

    @Test
    public void shouldReturnUpdatedWhenUpdateWithNullPrincipal() throws Exception {
        Firm firm = getNewUpdateEntity();
        firm.setIsPrincipal(null);

        Mockito.when(service.findById(firm.getId())).thenReturn(Optional.of(firm));

        super.shouldReturnUpdated(firm);
    }

    @Test
    public void shouldReturnBadRequestWhenUpdateWithInvalidPrincipalSize() throws Exception {
        Firm firm = getNewUpdateEntity();
        firm.setIsPrincipal(2);
        super.shouldReturnBadRequestWhenUpdate(firm,containsString("Max.firm.isPrincipal"));
    }

    @Test
    public void shouldReturnUpdatedWhenUpdateWithNullPb() throws Exception {
        Firm firm = getNewUpdateEntity();
        firm.setIsPb(null);

        Mockito.when(service.findById(firm.getId())).thenReturn(Optional.of(firm));

        super.shouldReturnUpdated(firm);
    }

    @Test
    public void shouldReturnBadRequestWhenUpdateWithInvalidPbSize() throws Exception {
        Firm firm = getNewUpdateEntity();
        firm.setIsPb(2);
        super.shouldReturnBadRequestWhenUpdate(firm,containsString("Max.firm.isPb"));
    }
    
    @Test
    public void shouldReturnUpdatedWhenUpdateWithNullSubPb() throws Exception {
        Firm firm = getNewUpdateEntity();
        firm.setIsSubpb(null);

        Mockito.when(service.findById(firm.getId())).thenReturn(Optional.of(firm));

        super.shouldReturnUpdated(firm);
    }

    @Test
    public void shouldReturnBadRequestWhenUpdateWithInvalidSubPbSize() throws Exception {
        Firm firm = getNewUpdateEntity();
        firm.setIsSubpb(2);
        super.shouldReturnBadRequestWhenUpdate(firm,containsString("Max.firm.isSubpb"));
    }
    
    @Test
    public void shouldReturnUpdatedWhenUpdateWithNullPbClient() throws Exception {
        Firm firm = getNewUpdateEntity();
        firm.setIsPbClient(null);
        
        Mockito.when(service.findById(firm.getId())).thenReturn(Optional.of(firm));

        super.shouldReturnUpdated(firm);
    }

    @Test
    public void shouldReturnBadRequestWhenUpdateWithInvalidPbClient() throws Exception {
        Firm firm = getNewUpdateEntity();
        firm.setIsPbClient(2);
        super.shouldReturnBadRequestWhenUpdate(firm,containsString("Max.firm.isPbClient"));
    }
    
    @Test
    public void shouldReturnUpdatedWhenUpdateWithNullSubPbClient() throws Exception {
        Firm firm = getNewUpdateEntity();
        firm.setIsSubpbClient(null);

        Mockito.when(service.findById(firm.getId())).thenReturn(Optional.of(firm));
        
        super.shouldReturnUpdated(firm);
    }

    @Test
    public void shouldReturnBadRequestWhenUpdateWithInvalidSubPbClient() throws Exception {
        Firm firm = getNewUpdateEntity();
        firm.setIsSubpbClient(2);
        super.shouldReturnBadRequestWhenUpdate(firm,containsString("Max.firm.isSubpbClient"));
    }
    
    @Test
    public void shouldReturnUpdatedWhenUpdateWithNullStatus() throws Exception {
        Firm firm = getNewUpdateEntity();
        firm.setStatus(null);
        
        Mockito.when(service.findById(firm.getId())).thenReturn(Optional.of(firm));
        
        super.shouldReturnUpdated(firm);
    }

    @Test
    public void shouldReturnBadRequestWhenUpdateWithStatus() throws Exception {
        Firm firm = getNewUpdateEntity();
        byte[] json = super.jsonReplace(firm, "status", "INVALID");
        super.shouldReturnBadRequestWhenUpdate(json,firm,containsString("Status` from String \"INVALID\": value not one of declared Enum instance names"));
    }
}