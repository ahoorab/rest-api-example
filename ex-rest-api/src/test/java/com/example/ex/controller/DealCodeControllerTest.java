package com.example.ex.controller;

import static org.hamcrest.Matchers.containsString;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import com.example.ex.model.entity.admin.DealCode;
import com.example.ex.model.type.DcType;
import com.example.ex.model.type.Status;

import net.bytebuddy.utility.RandomString;

@WebMvcTest
public class DealCodeControllerTest extends AdminControllerTest<DealCode> {

    @Override
    public void initController() {
        super.baseUri = DealCodeController.BASE_URI;
        super.service = this.dealCodeService;
        super.clazz = DealCode.class;
    }

    @Override
    public List<DealCode> getListOfEntities() {
        List<DealCode> dealCodes = new ArrayList<>();

        dealCodes.add(new DealCode("exx", "Example", "EXXX", "", DcType.EXX, "exx", "exx", "exx", "", "", Status.ENABLED));
        dealCodes.add(new DealCode("rbct", "Royal Bank of Canada", "RBC", "description", DcType.PRINCIPAL, "rbct", "rbct",
                "rbct", "302a300506032b6570032100b844d90e9c1089e672b74a82cc3caa70e532aefc1404d010234346f6f2013945",
                "7ee5bd9d-b7c2-462d-9b69-8820535e87b6", Status.ENABLED));
        return dealCodes;
    }

    @Override
    public DealCode getNewEntity() {
        return new DealCode("rbct", "Royal Bank of Canada", "RBC", "description", DcType.PRINCIPAL, "rbct", "rbct",
                "rbct", "302a300506032b6570032100b844d90e9c1089e672b74a82cc3caa70e532aefc1404d010234346f6f2013945",
                "7ee5bd9d-b7c2-462d-9b69-8820535e87b6", Status.ENABLED);
    }
    
    @Test
    public void shouldReturnBadRequestWhenCreateWithNullHandle() throws Exception {
        DealCode dc = getNewEntity();
        dc.setHandle(null);
        super.shouldReturnBadRequestWhenCreate(dc,containsString("NotNull.dealCode.handle"));
    }

    @Test
    public void shouldReturnBadRequestWhenCreateWithInvalidHandleSize() throws Exception {
        DealCode dc = getNewEntity();
        dc.setHandle(RandomString.make(13));
        super.shouldReturnBadRequestWhenCreate(dc,containsString("Size.dealCode.handle"));
    }

    @Test
    public void shouldReturnCreatedWhenCreateWithNullName() throws Exception {
        DealCode dc = getNewEntity();
        dc.setName(null);
        super.shouldReturnCreated(dc);
    }
    
    @Test
    public void shouldReturnBadRequestWhenCreateWithInvalidNameSize() throws Exception {
        DealCode dc = getNewEntity();
        dc.setName(RandomString.make(65));
        super.shouldReturnBadRequestWhenCreate(dc,containsString("Size.dealCode.name"));
    }

    @Test
    public void shouldReturnBadRequestWhenCreateWithNullMnemonic() throws Exception {
        DealCode dc = getNewEntity();
        dc.setMnemonic(null);
        super.shouldReturnBadRequestWhenCreate(dc,containsString("NotNull.dealCode.mnemonic"));
    }

    @Test
    public void shouldReturnBadRequestWhenCreateWithInvalidMnemonicSize() throws Exception {
        DealCode dc = getNewEntity();
        dc.setMnemonic(RandomString.make(17));
        super.shouldReturnBadRequestWhenCreate(dc,containsString("Size.dealCode.mnemonic"));
    }
    
    @Test
    public void shouldReturnCreatedWhenCreateWithNullDescription() throws Exception {
        DealCode dc = getNewEntity();
        dc.setDescription(null);
        super.shouldReturnCreated(dc);
    }
    
    @Test
    public void shouldReturnBadRequestWhenCreateWithInvalidDescriptionSize() throws Exception {
        DealCode dc = getNewEntity();
        dc.setDescription(RandomString.make(81));
        super.shouldReturnBadRequestWhenCreate(dc,containsString("Size.dealCode.description"));
    }

    @Test
    public void shouldReturnBadRequestWhenCreateWithNullDcType() throws Exception {
        DealCode dc = getNewEntity();
        dc.setDcType(null);
        super.shouldReturnBadRequestWhenCreate(dc,containsString("NotNull.dealCode.dcType"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenCreateWithInvalidDcType() throws Exception {
        DealCode dc = getNewEntity();
        dc.setDcType(null);
        byte[] json = super.jsonReplace(dc, "dcType", "INVALID");
        super.shouldReturnBadRequestWhenCreate(json,dc,containsString("DcType` from String \"INVALID\": value not one of declared Enum instance names"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenCreateWithNullFirm() throws Exception {
        DealCode dc = getNewEntity();
        dc.setFirm(null);
        super.shouldReturnBadRequestWhenCreate(dc,containsString("NotNull.dealCode.firm"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenCreateWithInvalidFirmSize() throws Exception {
        DealCode dc = getNewEntity();
        dc.setFirm(RandomString.make(13));
        super.shouldReturnBadRequestWhenCreate(dc,containsString("Size.dealCode.firm"));
    }
    
    @Test
    public void shouldReturnCreatedWhenCreateWithNullPbFirm() throws Exception {
        DealCode dc = getNewEntity();
        dc.setPbFirm(null);
        super.shouldReturnCreated(dc);
    }

    @Test
    public void shouldReturnBadRequestWhenCreateWithInvalidPbFirmSize() throws Exception {
        DealCode dc = getNewEntity();
        dc.setPbFirm(RandomString.make(13));
        super.shouldReturnBadRequestWhenCreate(dc,containsString("Size.dealCode.pbFirm"));
    }
    
    @Test
    public void shouldReturnCreatedWhenCreateWithNullSubPbFirm() throws Exception {
        DealCode dc = getNewEntity();
        dc.setSubPbFirm(null);
        super.shouldReturnCreated(dc);
    }

    @Test
    public void shouldReturnBadRequestWhenCreateWithInvalidSubPbFirmSize() throws Exception {
        DealCode dc = getNewEntity();
        dc.setSubPbFirm(RandomString.make(13));
        super.shouldReturnBadRequestWhenCreate(dc,containsString("Size.dealCode.subPbFirm"));
    }
    
    @Test
    public void shouldReturnCreatedWhenCreateWithNullLedgerCp() throws Exception {
        DealCode dc = getNewEntity();
        dc.setLedgerCpId(null);
        super.shouldReturnCreated(dc);
    }

    @Test
    public void shouldReturnBadRequestWhenCreateWithInvalidLedgerCpSize() throws Exception {
        DealCode dc = getNewEntity();
        dc.setLedgerCpId(RandomString.make(301));
        super.shouldReturnBadRequestWhenCreate(dc,containsString("Size.dealCode.ledgerCpId"));
    }
    
    @Test
    public void shouldReturnCreatedWhenCreateWithNullLedgerTpAccount() throws Exception {
        DealCode dc = getNewEntity();
        dc.setLedgerTpAccountId(null);
        super.shouldReturnCreated(dc);
    }

    @Test
    public void shouldReturnBadRequestWhenCreateWithInvalidLedgerTpAccount() throws Exception {
        DealCode dc = getNewEntity();
        dc.setLedgerTpAccountId(RandomString.make(301));
        super.shouldReturnBadRequestWhenCreate(dc,containsString("Size.dealCode.ledgerTpAccountId"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenCreateWithNullStatus() throws Exception {
        DealCode dc = getNewEntity();
        dc.setStatus(null);
        super.shouldReturnBadRequestWhenCreate(dc,containsString("NotNull.dealCode.status"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenCreateWithInvalidStatus() throws Exception {
        DealCode dc = getNewEntity();
        dc.setStatus(null);
        byte[] json = super.jsonReplace(dc, "status", "INVALID");
        super.shouldReturnBadRequestWhenCreate(json,dc,containsString("Status` from String \"INVALID\": value not one of declared Enum instance names"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenUpdateWithNullHandle() throws Exception {
        DealCode dc = getNewUpdateEntity();
        dc.setHandle(null);
        super.shouldReturnBadRequestWhenUpdate(dc,containsString("NotNull.dealCode.handle"));
    }

    @Test
    public void shouldReturnBadRequestWhenUpdateWithInvalidHandleSize() throws Exception {
        DealCode dc = getNewUpdateEntity();
        dc.setHandle(RandomString.make(13));
        super.shouldReturnBadRequestWhenUpdate(dc,containsString("Size.dealCode.handle"));
    }

    @Test
    public void shouldReturnUpdatedWhenUpdateWithNullName() throws Exception {
        DealCode dc = getNewUpdateEntity();
        dc.setName(null);

        Mockito.when(service.findById(dc.getId())).thenReturn(Optional.of(dc));

        super.shouldReturnUpdated(dc);
    }
    
    @Test
    public void shouldReturnBadRequestWhenUpdateWithInvalidNameSize() throws Exception {
        DealCode dc = getNewUpdateEntity();
        dc.setName(RandomString.make(65));
        super.shouldReturnBadRequestWhenUpdate(dc,containsString("Size.dealCode.name"));
    }

    @Test
    public void shouldReturnBadRequestWhenUpdateWithNullMnemonic() throws Exception {
        DealCode dc = getNewUpdateEntity();
        dc.setMnemonic(null);
        super.shouldReturnBadRequestWhenUpdate(dc,containsString("NotNull.dealCode.mnemonic"));
    }

    @Test
    public void shouldReturnBadRequestWhenUpdateWithInvalidMnemonicSize() throws Exception {
        DealCode dc = getNewUpdateEntity();
        dc.setMnemonic(RandomString.make(17));
        super.shouldReturnBadRequestWhenUpdate(dc,containsString("Size.dealCode.mnemonic"));
    }
    
    @Test
    public void shouldReturnUpdatedWhenUpdateWithNullDescription() throws Exception {
        DealCode dc = getNewUpdateEntity();
        dc.setDescription(null);

        Mockito.when(service.findById(dc.getId())).thenReturn(Optional.of(dc));

        super.shouldReturnUpdated(dc);
    }
    
    @Test
    public void shouldReturnBadRequestWhenUpdateWithInvalidDescriptionSize() throws Exception {
        DealCode dc = getNewUpdateEntity();
        dc.setDescription(RandomString.make(81));
        super.shouldReturnBadRequestWhenUpdate(dc,containsString("Size.dealCode.description"));
    }

    @Test
    public void shouldReturnBadRequestWhenUpdateWithNullDcType() throws Exception {
        DealCode dc = getNewUpdateEntity();
        dc.setDcType(null);
        super.shouldReturnBadRequestWhenUpdate(dc,containsString("NotNull.dealCode.dcType"));
    }

    @Test
    public void shouldReturnBadRequestWhenUpdateWithInvalidDcType() throws Exception {
        DealCode dc = getNewUpdateEntity();
        dc.setDcType(null);
        byte[] json = super.jsonReplace(dc, "dcType", "INVALID");
        super.shouldReturnBadRequestWhenUpdate(json,dc,containsString("DcType` from String \"INVALID\": value not one of declared Enum instance names"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenUpdateWithNullFirm() throws Exception {
        DealCode dc = getNewUpdateEntity();
        dc.setFirm(null);
        super.shouldReturnBadRequestWhenUpdate(dc,containsString("NotNull.dealCode.firm"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenUpdateWithInvalidFirmSize() throws Exception {
        DealCode dc = getNewUpdateEntity();
        dc.setFirm(RandomString.make(13));
        super.shouldReturnBadRequestWhenUpdate(dc,containsString("Size.dealCode.firm"));
    }
    
    @Test
    public void shouldReturnUpdatedWhenUpdateWithNullPbFirm() throws Exception {
        DealCode dc = getNewUpdateEntity();
        dc.setPbFirm(null);

        Mockito.when(service.findById(dc.getId())).thenReturn(Optional.of(dc));

        super.shouldReturnUpdated(dc);
    }

    @Test
    public void shouldReturnBadRequestWhenUpdateWithInvalidPbFirmSize() throws Exception {
        DealCode dc = getNewUpdateEntity();
        dc.setPbFirm(RandomString.make(13));
        super.shouldReturnBadRequestWhenUpdate(dc,containsString("Size.dealCode.pbFirm"));
    }
    
    @Test
    public void shouldReturnUpdatedWhenUpdateWithNullSubPbFirm() throws Exception {
        DealCode dc = getNewUpdateEntity();
        dc.setSubPbFirm(null);

        Mockito.when(service.findById(dc.getId())).thenReturn(Optional.of(dc));

        super.shouldReturnUpdated(dc);
    }

    @Test
    public void shouldReturnBadRequestWhenUpdateWithInvalidSubPbFirmSize() throws Exception {
        DealCode dc = getNewUpdateEntity();
        dc.setSubPbFirm(RandomString.make(13));
        super.shouldReturnBadRequestWhenUpdate(dc,containsString("Size.dealCode.subPbFirm"));
    }
    
    @Test
    public void shouldReturnUpdatedWhenUpdateWithNullLedgerCp() throws Exception {
        DealCode dc = getNewUpdateEntity();
        dc.setLedgerCpId(null);

        Mockito.when(service.findById(dc.getId())).thenReturn(Optional.of(dc));

        super.shouldReturnUpdated(dc);
    }

    @Test
    public void shouldReturnBadRequestWhenUpdateWithInvalidLedgerCpSize() throws Exception {
        DealCode dc = getNewUpdateEntity();
        dc.setLedgerCpId(RandomString.make(301));
        super.shouldReturnBadRequestWhenUpdate(dc,containsString("Size.dealCode.ledgerCpId"));
    }
    
    @Test
    public void shouldReturnUpdatedWhenUpdateWithNullLedgerTpAccount() throws Exception {
        DealCode dc = getNewUpdateEntity();
        dc.setLedgerTpAccountId(null);

        Mockito.when(service.findById(dc.getId())).thenReturn(Optional.of(dc));

        super.shouldReturnUpdated(dc);
    }

    @Test
    public void shouldReturnBadRequestWhenUpdateWithInvalidLedgerTpAccount() throws Exception {
        DealCode dc = getNewUpdateEntity();
        dc.setLedgerTpAccountId(RandomString.make(301));
        super.shouldReturnBadRequestWhenUpdate(dc,containsString("Size.dealCode.ledgerTpAccountId"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenUpdateWithNullStatus() throws Exception {
        DealCode dc = getNewUpdateEntity();
        dc.setStatus(null);
        super.shouldReturnBadRequestWhenUpdate(dc,containsString("NotNull.dealCode.status"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenUpdateWithInvalidStatus() throws Exception {
        DealCode dc = getNewUpdateEntity();
        dc.setStatus(null);
        byte[] json = super.jsonReplace(dc, "status", "INVALID");
        super.shouldReturnBadRequestWhenUpdate(json,dc,containsString("Status` from String \"INVALID\": value not one of declared Enum instance names"));
    }

}