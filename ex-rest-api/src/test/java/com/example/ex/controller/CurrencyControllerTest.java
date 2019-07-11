package com.example.ex.controller;

import static org.hamcrest.Matchers.containsString;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import com.example.ex.model.entity.admin.Currency;
import com.example.ex.model.type.Status;

import net.bytebuddy.utility.RandomString;

@WebMvcTest
public class CurrencyControllerTest extends AdminControllerTest<Currency>{
    
    @Override
    public void initController() {
        super.baseUri = CurrencyController.BASE_URI;
        super.service = this.currencyService;
        super.clazz = Currency.class;
    }
    
    @Override
    public List<Currency> getListOfEntities() {
        List<Currency> currencies = new ArrayList<>();
        currencies.add(new Currency("ARS",1,true,Integer.MAX_VALUE,Integer.MAX_VALUE,Integer.MAX_VALUE,Status.ENABLED));
        currencies.add(new Currency("SOL",2,false,2,2,2,Status.DISABLED));
        return currencies;
    }

    @Override
    public Currency getNewEntity() {
        return new Currency("ARS",1,true,1,1,1,Status.ENABLED);
    }

    @Test
    public void shouldReturnBadRequestWhenCreateWithNullName() throws Exception {
        Currency currency = getNewEntity();
        currency.setName(null);
        super.shouldReturnBadRequestWhenCreate(currency,containsString("NotNull.currency.name"));
    }

    @Test
    public void shouldReturnBadRequestWhenCreateWithInvalidNameSize() throws Exception {
        Currency currency = getNewEntity();
        currency.setName(RandomString.make(5));
        super.shouldReturnBadRequestWhenCreate(currency,containsString("Size.currency.name"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenCreateWithNullAmountDp() throws Exception {
        Currency currency = getNewEntity();
        currency.setAmountDp(null);
        super.shouldReturnBadRequestWhenCreate(currency,containsString("NotNull.currency.amountDp"));
    }

    @Test
    public void shouldReturnBadRequestWhenCreateWithInvalidAmountDpSize() throws Exception {
        Currency currency = getNewEntity();
        currency.setAmountDp(Byte.MAX_VALUE+1);
        super.shouldReturnBadRequestWhenCreate(currency,containsString("Max.currency.amountDp"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenCreateWithNullIsCls() throws Exception {
        Currency currency = getNewEntity();
        currency.setCls(null);
        super.shouldReturnBadRequestWhenCreate(currency,containsString("NotNull.currency.cls"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenCreateWithInvalidIsCls() throws Exception {
        Currency currency = getNewEntity();
        byte[] json = super.jsonReplace(currency, "cls", "INVALID");
        super.shouldReturnBadRequestWhenCreate(json,currency,containsString("cls"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenCreateWithNullMinQuantity() throws Exception {
        Currency currency = getNewEntity();
        currency.setMinQuantity(null);
        super.shouldReturnBadRequestWhenCreate(currency,containsString("NotNull.currency.minQuantity"));
    }

    @Test
    public void shouldReturnBadRequestWhenCreateWithInvalidMinQuantitySize() throws Exception {
        Currency currency = getNewEntity();
        byte[] json = super.jsonReplace(currency, "minQuantity", Long.toString(Integer.MAX_VALUE+1L));
        super.shouldReturnBadRequestWhenCreate(json,currency,containsString("minQuantity"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenCreateWithNullMinIncrement() throws Exception {
        Currency currency = getNewEntity();
        currency.setMinIncrement(null);
        super.shouldReturnBadRequestWhenCreate(currency,containsString("NotNull.currency.minIncrement"));
    }

    @Test
    public void shouldReturnBadRequestWhenCreateWithInvalidMinIncrementSize() throws Exception {
        Currency currency = getNewEntity();
        byte[] json = super.jsonReplace(currency, "minIncrement", Long.toString(Integer.MAX_VALUE+1L));
        super.shouldReturnBadRequestWhenCreate(json,currency,containsString("minIncrement"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenCreateWithNullLotSize() throws Exception {
        Currency currency = getNewEntity();
        currency.setLotSize(null);
        super.shouldReturnBadRequestWhenCreate(currency,containsString("NotNull.currency.lotSize"));
    }

    @Test
    public void shouldReturnBadRequestWhenCreateWithInvalidLotSize() throws Exception {
        Currency currency = getNewEntity();
        byte[] json = super.jsonReplace(currency, "lotSize", Long.toString(Integer.MAX_VALUE+1L));
        super.shouldReturnBadRequestWhenCreate(json,currency,containsString("lotSize"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenCreateWithNullStatus() throws Exception {
        Currency currency = getNewEntity();
        currency.setStatus(null);
        super.shouldReturnBadRequestWhenCreate(currency,containsString("NotNull.currency.status"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenCreateWithInvalidStatus() throws Exception {
        Currency currency = getNewEntity();
        byte[] json = super.jsonReplace(currency, "status", "INVALID");
        super.shouldReturnBadRequestWhenCreate(json,currency,containsString("Status` from String \"INVALID\": value not one of declared Enum instance names"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenUpdateWithNullName() throws Exception {
        Currency currency = getNewUpdateEntity();
        currency.setName(null);
        super.shouldReturnBadRequestWhenUpdate(currency,containsString("NotNull.currency.name"));
    }

    @Test
    public void shouldReturnBadRequestWhenUpdateWithInvalidNameSize() throws Exception {
        Currency currency = getNewUpdateEntity();
        currency.setName(RandomString.make(5));
        super.shouldReturnBadRequestWhenUpdate(currency,containsString("Size.currency.name"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenUpdateWithNullAmountDp() throws Exception {
        Currency currency = getNewUpdateEntity();
        currency.setAmountDp(null);
        super.shouldReturnBadRequestWhenUpdate(currency,containsString("NotNull.currency.amountDp"));
    }

    @Test
    public void shouldReturnBadRequestWhenUpdateWithInvalidAmountDpSize() throws Exception {
        Currency currency = getNewUpdateEntity();
        currency.setAmountDp(Byte.MAX_VALUE+1);
        super.shouldReturnBadRequestWhenUpdate(currency,containsString("Max.currency.amountDp"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenUpdateWithNullIsCls() throws Exception {
        Currency currency = getNewUpdateEntity();
        currency.setCls(null);
        super.shouldReturnBadRequestWhenUpdate(currency,containsString("NotNull.currency.cls"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenUpdateWithInvalidIsCls() throws Exception {
        Currency currency = getNewUpdateEntity();
        byte[] json = super.jsonReplace(currency, "cls", "INVALID");
        super.shouldReturnBadRequestWhenUpdate(json,currency,containsString("cls"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenUpdateWithNullMinQuantity() throws Exception {
        Currency currency = getNewUpdateEntity();
        currency.setMinQuantity(null);
        super.shouldReturnBadRequestWhenUpdate(currency,containsString("NotNull.currency.minQuantity"));
    }

    @Test
    public void shouldReturnBadRequestWhenUpdateWithInvalidMinQuantitySize() throws Exception {
        Currency currency = getNewUpdateEntity();
        byte[] json = super.jsonReplace(currency, "minQuantity", Long.toString(Integer.MAX_VALUE+1L));
        super.shouldReturnBadRequestWhenUpdate(json,currency,containsString("minQuantity"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenUpdateWithNullMinIncrement() throws Exception {
        Currency currency = getNewUpdateEntity();
        currency.setMinIncrement(null);
        super.shouldReturnBadRequestWhenUpdate(currency,containsString("NotNull.currency.minIncrement"));
    }

    @Test
    public void shouldReturnBadRequestWhenUpdateWithInvalidMinIncrementSize() throws Exception {
        Currency currency = getNewUpdateEntity();
        byte[] json = super.jsonReplace(currency, "minIncrement", Long.toString(Integer.MAX_VALUE+1L));
        super.shouldReturnBadRequestWhenUpdate(json,currency,containsString("minIncrement"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenUpdateWithNullLotSize() throws Exception {
        Currency currency = getNewUpdateEntity();
        currency.setLotSize(null);
        super.shouldReturnBadRequestWhenUpdate(currency,containsString("NotNull.currency.lotSize"));
    }

    @Test
    public void shouldReturnBadRequestWhenUpdateWithInvalidLotSize() throws Exception {
        Currency currency = getNewUpdateEntity();
        byte[] json = super.jsonReplace(currency, "lotSize", Long.toString(Integer.MAX_VALUE+1L));
        super.shouldReturnBadRequestWhenUpdate(json,currency,containsString("lotSize"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenUpdateWithNullStatus() throws Exception {
        Currency currency = getNewUpdateEntity();
        currency.setStatus(null);
        super.shouldReturnBadRequestWhenUpdate(currency,containsString("NotNull.currency.status"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenUpdateWithInvalidStatus() throws Exception {
        Currency currency = getNewUpdateEntity();
        byte[] json = super.jsonReplace(currency, "status", "INVALID");
        super.shouldReturnBadRequestWhenUpdate(json,currency,containsString("Status` from String \"INVALID\": value not one of declared Enum instance names"));
    }
}
