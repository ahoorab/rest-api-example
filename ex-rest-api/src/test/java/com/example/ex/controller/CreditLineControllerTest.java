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

import com.example.ex.model.entity.admin.CreditLine;
import com.example.ex.model.type.CreditLineType;
import com.example.ex.model.type.Status;

import net.bytebuddy.utility.RandomString;

@RunWith(SpringRunner.class)
@WebMvcTest
public class CreditLineControllerTest extends AdminControllerTest<CreditLine> {

    @Override
    public void initController() {
        super.baseUri = CreditLineController.BASE_URI;
        super.service = super.creditLineService;
        super.clazz = CreditLine.class;
    }

    @Override
    public List<CreditLine> getListOfEntities() {
        List<CreditLine> creditLines = new ArrayList<>();
        creditLines.add(new CreditLine("scot-rbc","scotia:RBC",CreditLineType.PRINCIPAL,"scotia","rbct","scot-rb",Status.ENABLED));
        creditLines.add(new CreditLine("tdbk-rbc","RDBank-RBC",CreditLineType.PBCLIENT,"tdbank","rbct","tdbk-rb",Status.DISABLED));
        return creditLines;
    }

    @Override
    public CreditLine getNewEntity() {
        return new CreditLine("frst-scnd","FRST-SB:SCND",CreditLineType.SUB_PB_CLIENT,"frst","scnd","first-scnd",Status.DISABLED);
    }
    
    @Test
    public void shouldReturnBadRequestWhenCreateWithNullHandle() throws Exception {
        CreditLine creditLine = getNewEntity();
        creditLine.setHandle(null);
        super.shouldReturnBadRequestWhenCreate(creditLine,containsString("NotNull.creditLine.handle"));
    }

    @Test
    public void shouldReturnBadRequestWhenCreateWithInvalidHandleSize() throws Exception {
        CreditLine creditLine = getNewEntity();
        creditLine.setHandle(RandomString.make(13));
        super.shouldReturnBadRequestWhenCreate(creditLine,containsString("Size.creditLine.handle"));
    }
    
    @Test
    public void shouldReturnCreatedWhenCreateWithNullName() throws Exception {
        CreditLine creditLine = getNewEntity();
        creditLine.setName(null);
        super.shouldReturnCreated(creditLine);
    }
    
    @Test
    public void shouldReturnBadRequestWhenCreateWithInvalidNameSize() throws Exception {
        CreditLine creditLine = getNewEntity();
        creditLine.setName(RandomString.make(65));
        super.shouldReturnBadRequestWhenCreate(creditLine,containsString("Size.creditLine.name"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenCreateWithNullType() throws Exception {
        CreditLine creditLine = getNewEntity();
        creditLine.setType(null);
        super.shouldReturnBadRequestWhenCreate(creditLine,containsString("NotNull.creditLine.type"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenCreateWithInvalidType() throws Exception {
        CreditLine creditLine = getNewEntity();
        byte[] json = super.jsonReplace(creditLine, "type", "INVALID");
        super.shouldReturnBadRequestWhenCreate(json,creditLine,containsString("CreditLineType` from String \"INVALID\": value not one of declared Enum instance names"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenCreateWithNullGrantorFirm() throws Exception {
        CreditLine creditLine = getNewEntity();
        creditLine.setGrantorFirm(null);
        super.shouldReturnBadRequestWhenCreate(creditLine,containsString("NotNull.creditLine.grantorFirm"));
    }

    @Test
    public void shouldReturnBadRequestWhenCreateWithInvalidGrantorFirmSize() throws Exception {
        CreditLine creditLine = getNewEntity();
        creditLine.setGrantorFirm(RandomString.make(13));
        super.shouldReturnBadRequestWhenCreate(creditLine,containsString("Size.creditLine.grantorFirm"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenCreateWithNullGranteeFirm() throws Exception {
        CreditLine creditLine = getNewEntity();
        creditLine.setGranteeFirm(null);
        super.shouldReturnBadRequestWhenCreate(creditLine,containsString("NotNull.creditLine.granteeFirm"));
    }

    @Test
    public void shouldReturnBadRequestWhenCreateWithInvalidGranteeFirmSize() throws Exception {
        CreditLine creditLine = getNewEntity();
        creditLine.setGranteeFirm(RandomString.make(13));
        super.shouldReturnBadRequestWhenCreate(creditLine,containsString("Size.creditLine.granteeFirm"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenCreateWithNullCreditPool() throws Exception {
        CreditLine creditLine = getNewEntity();
        creditLine.setCreditPool(null);
        super.shouldReturnBadRequestWhenCreate(creditLine,containsString("NotNull.creditLine.creditPool"));
    }

    @Test
    public void shouldReturnBadRequestWhenCreateWithInvalidCreditPoolSize() throws Exception {
        CreditLine creditLine = getNewEntity();
        creditLine.setCreditPool(RandomString.make(13));
        super.shouldReturnBadRequestWhenCreate(creditLine,containsString("Size.creditLine.creditPool"));
    }

    @Test
    public void shouldReturnCreatedWhenCreateWithNullStatus() throws Exception {
        CreditLine creditLine = getNewEntity();
        creditLine.setStatus(null);
        super.shouldReturnCreated(creditLine);
    }

    @Test
    public void shouldReturnBadRequestWhenCreateWithStatus() throws Exception {
        CreditLine creditLine = getNewEntity();
        byte[] json = super.jsonReplace(creditLine, "status", "INVALID");
        super.shouldReturnBadRequestWhenCreate(json,creditLine,containsString("Status` from String \"INVALID\": value not one of declared Enum instance names"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenUpdateWithNullHandle() throws Exception {
        CreditLine creditLine = getNewUpdateEntity();
        creditLine.setHandle(null);
        super.shouldReturnBadRequestWhenUpdate(creditLine,containsString("NotNull.creditLine.handle"));
    }

    @Test
    public void shouldReturnBadRequestWhenUpdateWithInvalidHandleSize() throws Exception {
        CreditLine creditLine = getNewUpdateEntity();
        creditLine.setHandle(RandomString.make(13));
        super.shouldReturnBadRequestWhenUpdate(creditLine,containsString("Size.creditLine.handle"));
    }
    
    @Test
    public void shouldReturnUpdatedWhenUpdateWithNullName() throws Exception {
        CreditLine creditLine = getNewUpdateEntity();
        creditLine.setName(null);
        
        Mockito.when(service.findById(creditLine.getId())).thenReturn(Optional.of(creditLine));
        
        super.shouldReturnUpdated(creditLine);
    }
    
    @Test
    public void shouldReturnBadRequestWhenUpdateWithInvalidNameSize() throws Exception {
        CreditLine creditLine = getNewUpdateEntity();
        creditLine.setName(RandomString.make(65));
        super.shouldReturnBadRequestWhenUpdate(creditLine,containsString("Size.creditLine.name"));
    }

    @Test
    public void shouldReturnBadRequestWhenUpdateWithNullType() throws Exception {
        CreditLine creditLine = getNewUpdateEntity();
        creditLine.setType(null);
        super.shouldReturnBadRequestWhenUpdate(creditLine,containsString("NotNull.creditLine.type"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenUpdateWithInvalidType() throws Exception {
        CreditLine creditLine = getNewUpdateEntity();
        byte[] json = super.jsonReplace(creditLine, "type", "INVALID");
        super.shouldReturnBadRequestWhenUpdate(json,creditLine,containsString("CreditLineType` from String \"INVALID\": value not one of declared Enum instance names"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenUpdateWithNullGrantorFirm() throws Exception {
        CreditLine creditLine = getNewUpdateEntity();
        creditLine.setGrantorFirm(null);
        super.shouldReturnBadRequestWhenUpdate(creditLine,containsString("NotNull.creditLine.grantorFirm"));
    }

    @Test
    public void shouldReturnBadRequestWhenUpdateWithInvalidGrantorFirmSize() throws Exception {
        CreditLine creditLine = getNewUpdateEntity();
        creditLine.setGrantorFirm(RandomString.make(13));
        super.shouldReturnBadRequestWhenUpdate(creditLine,containsString("Size.creditLine.grantorFirm"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenUpdateWithNullGranteeFirm() throws Exception {
        CreditLine creditLine = getNewUpdateEntity();
        creditLine.setGranteeFirm(null);
        super.shouldReturnBadRequestWhenUpdate(creditLine,containsString("NotNull.creditLine.granteeFirm"));
    }

    @Test
    public void shouldReturnBadRequestWhenUpdateWithInvalidGranteeFirmSize() throws Exception {
        CreditLine creditLine = getNewUpdateEntity();
        creditLine.setGranteeFirm(RandomString.make(13));
        super.shouldReturnBadRequestWhenUpdate(creditLine,containsString("Size.creditLine.granteeFirm"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenUpdateWithNullCreditPool() throws Exception {
        CreditLine creditLine = getNewUpdateEntity();
        creditLine.setCreditPool(null);
        super.shouldReturnBadRequestWhenUpdate(creditLine,containsString("NotNull.creditLine.creditPool"));
    }

    @Test
    public void shouldReturnBadRequestWhenUpdateWithInvalidCreditPoolSize() throws Exception {
        CreditLine creditLine = getNewUpdateEntity();
        creditLine.setCreditPool(RandomString.make(13));
        super.shouldReturnBadRequestWhenUpdate(creditLine,containsString("Size.creditLine.creditPool"));
    }
    
    @Test
    public void shouldReturnUpdatedWhenUpdateWithNullStatus() throws Exception {
        CreditLine creditLine = getNewUpdateEntity();
        creditLine.setStatus(null);
        
        Mockito.when(service.findById(creditLine.getId())).thenReturn(Optional.of(creditLine));
        
        super.shouldReturnUpdated(creditLine);
    }
    
    @Test
    public void shouldReturnBadRequestWhenUpdateWithInvalidStatus() throws Exception {
        CreditLine creditLine = getNewUpdateEntity();
        byte[] json = super.jsonReplace(creditLine, "status", "INVALID");
        super.shouldReturnBadRequestWhenUpdate(json,creditLine,containsString("Status` from String \"INVALID\": value not one of declared Enum instance names"));
    }
}