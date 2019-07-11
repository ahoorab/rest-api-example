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

import com.example.ex.model.entity.admin.CreditPool;
import com.example.ex.model.type.CreditMethod;
import com.example.ex.model.type.Status;

import net.bytebuddy.utility.RandomString;

@RunWith(SpringRunner.class)
@WebMvcTest
public class CreditPoolControllerTest extends AdminControllerTest<CreditPool> {

    @Override
    public void initController() {
        super.baseUri = CreditPoolController.BASE_URI;
        super.service = super.creditPoolService;
        super.clazz = CreditPool.class;
    }

    @Override
    public List<CreditPool> getListOfEntities() {
        List<CreditPool> creditPools = new ArrayList<>();
        creditPools.add(new CreditPool("scot-tdbk","scot-tdb","scotia","scotia td bank",CreditMethod.NET_SHORTS,0,Integer.MAX_VALUE,127,Status.ENABLED));
        creditPools.add(new CreditPool("scot-rb","scot-rbc","scotia","scotia rbc",CreditMethod.NET_GREATER,Integer.MAX_VALUE,0,0,Status.DISABLED));
        return creditPools;
    }

    @Override
    public CreditPool getNewEntity() {
        return new CreditPool("ktco-scot","ktco-sco","kitco","kitco to scotia",CreditMethod.NET_LONG,120,1000,100,Status.DISABLED);
    }
    
    @Test
    public void shouldReturnBadRequestWhenCreateWithNullHandle() throws Exception {
        CreditPool creditPool = getNewEntity();
        creditPool.setHandle(null);
        super.shouldReturnBadRequestWhenCreate(creditPool,containsString("NotNull.creditPool.handle"));
    }

    @Test
    public void shouldReturnBadRequestWhenCreateWithInvalidHandleSize() throws Exception {
        CreditPool creditPool = getNewEntity();
        creditPool.setHandle(RandomString.make(13));
        super.shouldReturnBadRequestWhenCreate(creditPool,containsString("Size.creditPool.handle"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenCreateWithNullMnemonic() throws Exception {
        CreditPool creditPool = getNewEntity();
        creditPool.setMnemonic(null);
        super.shouldReturnBadRequestWhenCreate(creditPool,containsString("NotNull.creditPool.mnemonic"));
    }

    @Test
    public void shouldReturnBadRequestWhenCreateWithInvalidMnemonicSize() throws Exception {
        CreditPool creditPool = getNewEntity();
        creditPool.setMnemonic(RandomString.make(17));
        super.shouldReturnBadRequestWhenCreate(creditPool,containsString("Size.creditPool.mnemonic"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenCreateWithNullGrantorFirm() throws Exception {
        CreditPool creditPool = getNewEntity();
        creditPool.setGrantorFirm(null);
        super.shouldReturnBadRequestWhenCreate(creditPool,containsString("NotNull.creditPool.grantorFirm"));
    }

    @Test
    public void shouldReturnBadRequestWhenCreateWithInvalidGrantorFirmSize() throws Exception {
        CreditPool creditPool = getNewEntity();
        creditPool.setGrantorFirm(RandomString.make(13));
        super.shouldReturnBadRequestWhenCreate(creditPool,containsString("Size.creditPool.grantorFirm"));
    }
    
    @Test
    public void shouldReturnCreatedWhenCreateWithNullName() throws Exception {
        CreditPool creditPool = getNewEntity();
        creditPool.setName(null);
        super.shouldReturnCreated(creditPool);
    }
    
    @Test
    public void shouldReturnBadRequestWhenCreateWithInvalidNameSize() throws Exception {
        CreditPool creditPool = getNewEntity();
        creditPool.setName(RandomString.make(65));
        super.shouldReturnBadRequestWhenCreate(creditPool,containsString("Size.creditPool.name"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenCreateWithNullCreditMethodType() throws Exception {
        CreditPool creditPool = getNewEntity();
        creditPool.setCreditMethod(null);
        super.shouldReturnBadRequestWhenCreate(creditPool,containsString("NotNull.creditPool.creditMethod"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenCreateWithInvalidCreditMethodType() throws Exception {
        CreditPool creditPool = getNewEntity();
        byte[] json = super.jsonReplace(creditPool, "creditMethod", "INVALID");
        super.shouldReturnBadRequestWhenCreate(json,creditPool,containsString("CreditMethod` from String \"INVALID\": value not one of declared Enum instance names"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenCreateWithNullLimitNop() throws Exception {
        CreditPool creditPool = getNewEntity();
        creditPool.setLimitNop(null);
        super.shouldReturnBadRequestWhenCreate(creditPool,containsString("NotNull.creditPool.limitNop"));
    }

    @Test
    public void shouldReturnBadRequestWhenCreateWithInvalidLimitNopSize() throws Exception {
        CreditPool creditPool = getNewEntity();
        byte[] json = super.jsonReplace(creditPool, "limitNop", Long.toString(Integer.MAX_VALUE+1L));
        super.shouldReturnBadRequestWhenCreate(json,creditPool,containsString("limitNop"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenCreateWithNullLimitDaily() throws Exception {
        CreditPool creditPool = getNewEntity();
        creditPool.setLimitDaily(null);
        super.shouldReturnBadRequestWhenCreate(creditPool,containsString("NotNull.creditPool.limitDaily"));
    }

    @Test
    public void shouldReturnBadRequestWhenCreateWithInvalidLimitDailySize() throws Exception {
        CreditPool creditPool = getNewEntity();
        byte[] json = super.jsonReplace(creditPool, "limitDaily", Long.toString(Integer.MAX_VALUE+1L));
        super.shouldReturnBadRequestWhenCreate(json,creditPool,containsString("limitDaily"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenCreateWithNullWarningPct() throws Exception {
        CreditPool creditPool = getNewEntity();
        creditPool.setWarningPct(null);
        super.shouldReturnBadRequestWhenCreate(creditPool,containsString("NotNull.creditPool.warningPct"));
    }

    @Test
    public void shouldReturnBadRequestWhenCreateWithInvalidWarningPctSize() throws Exception {
        CreditPool creditPool = getNewEntity();
        creditPool.setWarningPct(Byte.MAX_VALUE+1);
        super.shouldReturnBadRequestWhenCreate(creditPool,containsString("Max.creditPool.warningPct"));
    }

    @Test
    public void shouldReturnBadRequestWhenCreateWithNullStatus() throws Exception {
        CreditPool creditPool = getNewEntity();
        creditPool.setStatus(null);
        super.shouldReturnBadRequestWhenCreate(creditPool,containsString("NotNull.creditPool.status"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenCreateWithStatus() throws Exception {
        CreditPool creditPool = getNewEntity();
        byte[] json = super.jsonReplace(creditPool, "status", "INVALID");
        super.shouldReturnBadRequestWhenCreate(json,creditPool,containsString("Status` from String \"INVALID\": value not one of declared Enum instance names"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenUpdateWithNullHandle() throws Exception {
        CreditPool creditPool = getNewUpdateEntity();
        creditPool.setHandle(null);
        super.shouldReturnBadRequestWhenUpdate(creditPool,containsString("NotNull.creditPool.handle"));
    }

    @Test
    public void shouldReturnBadRequestWhenUpdateWithInvalidHandleSize() throws Exception {
        CreditPool creditPool = getNewUpdateEntity();
        creditPool.setHandle(RandomString.make(13));
        super.shouldReturnBadRequestWhenUpdate(creditPool,containsString("Size.creditPool.handle"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenUpdateWithNullMnemonic() throws Exception {
        CreditPool creditPool = getNewUpdateEntity();
        creditPool.setMnemonic(null);
        super.shouldReturnBadRequestWhenUpdate(creditPool,containsString("NotNull.creditPool.mnemonic"));
    }

    @Test
    public void shouldReturnBadRequestWhenUpdateWithInvalidMnemonicSize() throws Exception {
        CreditPool creditPool = getNewUpdateEntity();
        creditPool.setMnemonic(RandomString.make(17));
        super.shouldReturnBadRequestWhenUpdate(creditPool,containsString("Size.creditPool.mnemonic"));
    }
    
    @Test
    public void shouldReturnUpdatedWhenUpdateWithNullName() throws Exception {
        CreditPool creditPool = getNewUpdateEntity();
        creditPool.setName(null);
        
        Mockito.when(service.findById(creditPool.getId())).thenReturn(Optional.of(creditPool));
        
        super.shouldReturnUpdated(creditPool);
    }
    
    @Test
    public void shouldReturnBadRequestWhenUpdateWithInvalidNameSize() throws Exception {
        CreditPool creditPool = getNewUpdateEntity();
        creditPool.setName(RandomString.make(65));
        super.shouldReturnBadRequestWhenUpdate(creditPool,containsString("Size.creditPool.name"));
    }

    @Test
    public void shouldReturnBadRequestWhenUpdateWithNullGrantorFirm() throws Exception {
        CreditPool creditPool = getNewUpdateEntity();
        creditPool.setGrantorFirm(null);
        super.shouldReturnBadRequestWhenUpdate(creditPool,containsString("NotNull.creditPool.grantorFirm"));
    }

    @Test
    public void shouldReturnBadRequestWhenUpdateWithInvalidGrantorFirmSize() throws Exception {
        CreditPool creditPool = getNewUpdateEntity();
        creditPool.setGrantorFirm(RandomString.make(13));
        super.shouldReturnBadRequestWhenUpdate(creditPool,containsString("Size.creditPool.grantorFirm"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenUpdateWithNullCreditMethodType() throws Exception {
        CreditPool creditPool = getNewUpdateEntity();
        creditPool.setCreditMethod(null);
        super.shouldReturnBadRequestWhenUpdate(creditPool,containsString("NotNull.creditPool.creditMethod"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenUpdateWithInvalidCreditMethodType() throws Exception {
        CreditPool creditPool = getNewUpdateEntity();
        byte[] json = super.jsonReplace(creditPool, "creditMethod", "INVALID");
        super.shouldReturnBadRequestWhenUpdate(json,creditPool,containsString("CreditMethod` from String \"INVALID\": value not one of declared Enum instance names"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenUpdateWithNullLimitNop() throws Exception {
        CreditPool creditPool = getNewUpdateEntity();
        creditPool.setLimitNop(null);
        super.shouldReturnBadRequestWhenUpdate(creditPool,containsString("NotNull.creditPool.limitNop"));
    }

    @Test
    public void shouldReturnBadRequestWhenUpdateWithInvalidLimitNopSize() throws Exception {
        CreditPool creditPool = getNewUpdateEntity();
        byte[] json = super.jsonReplace(creditPool, "limitNop", Long.toString(Integer.MAX_VALUE+1L));
        super.shouldReturnBadRequestWhenUpdate(json,creditPool,containsString("limitNop"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenUpdateWithNullLimitDaily() throws Exception {
        CreditPool creditPool = getNewUpdateEntity();
        creditPool.setLimitDaily(null);
        super.shouldReturnBadRequestWhenUpdate(creditPool,containsString("NotNull.creditPool.limitDaily"));
    }

    @Test
    public void shouldReturnBadRequestWhenUpdateWithInvalidLimitDailySize() throws Exception {
        CreditPool creditPool = getNewUpdateEntity();
        byte[] json = super.jsonReplace(creditPool, "limitDaily", Long.toString(Integer.MAX_VALUE+1L));
        super.shouldReturnBadRequestWhenUpdate(json,creditPool,containsString("limitDaily"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenUpdateWithNullWarningPct() throws Exception {
        CreditPool creditPool = getNewUpdateEntity();
        creditPool.setWarningPct(null);
        super.shouldReturnBadRequestWhenUpdate(creditPool,containsString("NotNull.creditPool.warningPct"));
    }

    @Test
    public void shouldReturnBadRequestWhenUpdateWithInvalidWarningPctSize() throws Exception {
        CreditPool creditPool = getNewUpdateEntity();
        creditPool.setWarningPct(Byte.MAX_VALUE+1);
        super.shouldReturnBadRequestWhenUpdate(creditPool,containsString("Max.creditPool.warningPct"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenUpdateWithNullStatus() throws Exception {
        CreditPool creditPool = getNewUpdateEntity();
        creditPool.setStatus(null);
        super.shouldReturnBadRequestWhenUpdate(creditPool,containsString("NotNull.creditPool.status"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenUpdateWithInvalidStatus() throws Exception {
        CreditPool creditPool = getNewUpdateEntity();
        byte[] json = super.jsonReplace(creditPool, "status", "INVALID");
        super.shouldReturnBadRequestWhenUpdate(json,creditPool,containsString("Status` from String \"INVALID\": value not one of declared Enum instance names"));
    }
}