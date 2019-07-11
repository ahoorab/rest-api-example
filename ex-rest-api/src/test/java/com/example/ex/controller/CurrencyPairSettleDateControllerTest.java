package com.example.ex.controller;

import static org.hamcrest.Matchers.containsString;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import com.example.ex.model.entity.admin.CurrencyPairSettleDate;
import com.example.ex.model.type.Status;

import net.bytebuddy.utility.RandomString;

@WebMvcTest
public class CurrencyPairSettleDateControllerTest extends AdminControllerTest<CurrencyPairSettleDate> {

    @Override
    public void initController() {
        super.baseUri = CurrencyPairSettleDateController.BASE_URI;
        super.service = this.currencyPairSettleDateService;
        super.clazz = CurrencyPairSettleDate.class;
    }

    @Override
    public List<CurrencyPairSettleDate> getListOfEntities() {
        List<CurrencyPairSettleDate> settleDates = new ArrayList<>();
       
        try {
            java.util.Date trade = super.dateFormat.parse("2018-10-03");
            java.util.Date settle = super.dateFormat.parse("2018-11-15");
            java.util.Date update = super.timestampFormat.parse("2018-10-03 12:13:14");

            settleDates.add(new CurrencyPairSettleDate("USDCAD", new Date(trade.getTime()), new Date(settle.getTime()), 1, "notes", Status.ENABLED, new Timestamp(update.getTime())));

            trade = super.dateFormat.parse("2018-10-04");
            update = super.timestampFormat.parse("2018-10-28 01:02:03");
            settleDates.add(new CurrencyPairSettleDate("USDCAD", new Date(trade.getTime()), null, 2, "", Status.DISABLED, new Timestamp(update.getTime())));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return settleDates;
    }

    @Override
    public CurrencyPairSettleDate getNewEntity() {
        try {
            java.util.Date trade = super.dateFormat.parse("2017-09-10");
            java.util.Date settle = super.dateFormat.parse("2016-08-09");
            java.util.Date update = super.timestampFormat.parse("2015-07-08 21:22:23");
            return new CurrencyPairSettleDate("USDCAD", new Date(trade.getTime()), new Date(settle.getTime()), 1, "notes", Status.ENABLED, new Timestamp(update.getTime()));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
    
    @Test
    public void shouldReturnBadRequestWhenCreateWithNullCurrencyPair() throws Exception {
        CurrencyPairSettleDate settleDate = getNewEntity();
        settleDate.setCurrencyPair(null);
        super.shouldReturnBadRequestWhenCreate(settleDate,containsString("NotNull.currencyPairSettleDate.currencyPair"));
    }

    @Test
    public void shouldReturnBadRequestWhenCreateWithInvalidCurrencyPairSize() throws Exception {
        CurrencyPairSettleDate settleDate = getNewEntity();
        settleDate.setCurrencyPair(RandomString.make(7));
        super.shouldReturnBadRequestWhenCreate(settleDate,containsString("Size.currencyPairSettleDate.currencyPair"));
    }

    @Test
    public void shouldReturnBadRequesttWhenCreateWithNullTradeDate() throws Exception {
        CurrencyPairSettleDate settleDate = getNewEntity();
        settleDate.setTradeDate(null);
        super.shouldReturnBadRequestWhenCreate(settleDate,containsString("NotNull.currencyPairSettleDate.tradeDate"));
    }
 
    @Test
    public void shouldReturnBadRequestWhenCreateWithIncompleteTradeDay() throws Exception {
        CurrencyPairSettleDate settleDate = getNewEntity();
        byte[] json = super.jsonReplace(settleDate, "tradeDate", "2018-01-0");
        super.shouldReturnBadRequestWhenCreate(json,settleDate,containsString("tradeDate"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenCreateWithInvalidTradeDay() throws Exception {
        CurrencyPairSettleDate settleDate = getNewEntity();
        byte[] json = super.jsonReplace(settleDate, "tradeDate", "2018-01-32");
        super.shouldReturnBadRequestWhenCreate(json,settleDate,containsString("tradeDate"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenCreateWithInvalidTradeMonth() throws Exception {
        CurrencyPairSettleDate settleDate = getNewEntity();
        byte[] json = super.jsonReplace(settleDate, "tradeDate", "2018-13-01");
        super.shouldReturnBadRequestWhenCreate(json,settleDate,containsString("tradeDate"));
    }

    @Test
    public void shouldReturnBadRequestWhenCreateWithInvalidTradeYear() throws Exception {
        CurrencyPairSettleDate settleDate = getNewEntity();
        byte[] json = super.jsonReplace(settleDate, "tradeDate", "0000-01-01");
        super.shouldReturnBadRequestWhenCreate(json,settleDate,containsString("tradeDate"));
    }

    @Test
    public void shouldReturnBadRequestWhenCreateWithTradeDateOutOfRange() throws Exception {
        CurrencyPairSettleDate settleDate = getNewEntity();
        byte[] json = super.jsonReplace(settleDate, "tradeDate", "2018-02-30");
        super.shouldReturnBadRequestWhenCreate(json,settleDate,containsString("tradeDate"));
    }
    
    @Test
    public void shouldReturnCreatedtWhenCreateWithNullSettleDate() throws Exception {
        CurrencyPairSettleDate settleDate = getNewEntity();
        settleDate.setSettleDate(null);
        super.shouldReturnCreated(settleDate);
    }
 
    @Test
    public void shouldReturnBadRequestWhenCreateWithIncompleteSettleDay() throws Exception {
        CurrencyPairSettleDate settleDate = getNewEntity();
        byte[] json = super.jsonReplace(settleDate, "settleDate", "2018-01");
        super.shouldReturnBadRequestWhenCreate(json,settleDate,containsString("settleDate"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenCreateWithInvalidSettleDay() throws Exception {
        CurrencyPairSettleDate settleDate = getNewEntity();
        byte[] json = super.jsonReplace(settleDate, "settleDate", "2018-09-00");
        super.shouldReturnBadRequestWhenCreate(json,settleDate,containsString("settleDate"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenCreateWithInvalidSettleMonth() throws Exception {
        CurrencyPairSettleDate settleDate = getNewEntity();
        byte[] json = super.jsonReplace(settleDate, "settleDate", "2018-00-01");
        super.shouldReturnBadRequestWhenCreate(json,settleDate,containsString("settleDate"));
    }

    @Test
    public void shouldReturnBadRequestWhenCreateWithInvalidSettleYear() throws Exception {
        CurrencyPairSettleDate settleDate = getNewEntity();
        byte[] json = super.jsonReplace(settleDate, "settleDate", "-1-01-01");
        super.shouldReturnBadRequestWhenCreate(json,settleDate,containsString("settleDate"));
    }

    @Test
    public void shouldReturnBadRequestWhenCreateWithSettleDateOutOfRange() throws Exception {
        CurrencyPairSettleDate settleDate = getNewEntity();
        byte[] json = super.jsonReplace(settleDate, "settleDate", "2018-09-31");
        super.shouldReturnBadRequestWhenCreate(json,settleDate,containsString("settleDate"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenCreateWithNullHolidayCount() throws Exception {
        CurrencyPairSettleDate settleDate = getNewEntity();
        settleDate.setHolidayCount(null);
        super.shouldReturnBadRequestWhenCreate(settleDate,containsString("NotNull.currencyPairSettleDate.holidayCount"));
    }

    @Test
    public void shouldReturnBadRequestWhenCreateWithInvalidHolidayCountSize() throws Exception {
        CurrencyPairSettleDate settleDate = getNewEntity();
        settleDate.setHolidayCount(Byte.MAX_VALUE+1);
        super.shouldReturnBadRequestWhenCreate(settleDate,containsString("Max.currencyPairSettleDate.holidayCount"));
    }
    
    @Test
    public void shouldReturnCreatedtWhenCreateWithNullNotes() throws Exception {
        CurrencyPairSettleDate settleDate = getNewEntity();
        settleDate.setNotes(null);
        super.shouldReturnCreated(settleDate);
    }

    @Test
    public void shouldReturnBadRequestWhenCreateWithInvalidNotesSize() throws Exception {
        CurrencyPairSettleDate settleDate = getNewEntity();
        settleDate.setNotes(RandomString.make(65));
        super.shouldReturnBadRequestWhenCreate(settleDate,containsString("Size.currencyPairSettleDate.notes"));
    }

    @Test
    public void shouldReturnCreatedtWhenCreateWithNullStatus() throws Exception {
        CurrencyPairSettleDate settleDate = getNewEntity();
        settleDate.setStatus(null);
        super.shouldReturnCreated(settleDate);
    }
    
    @Test
    public void shouldReturnBadRequestWhenCreateWithInvalidStatus() throws Exception {
        CurrencyPairSettleDate settleDate = getNewEntity();
        byte[] json = super.jsonReplace(settleDate, "status", "INVALID");
        super.shouldReturnBadRequestWhenCreate(json,settleDate,containsString("Status` from String \"INVALID\": value not one of declared Enum instance names"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenCreateWithNullUpdateTime() throws Exception {
        CurrencyPairSettleDate settleDate = getNewEntity();
        settleDate.setUpdateTime(null);
        super.shouldReturnBadRequestWhenCreate(settleDate,containsString("NotNull.currencyPairSettleDate.updateTime"));
    }
 
    @Test
    public void shouldReturnBadRequestWhenCreateWithIncompleteUpdateTime() throws Exception {
        CurrencyPairSettleDate settleDate = getNewEntity();
        byte[] json = super.jsonReplace(settleDate, "updateTime", "2018-10-10");
        super.shouldReturnBadRequestWhenCreate(json,settleDate,containsString("updateTime"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenCreateWithIncompleteUpdateSeconds() throws Exception {
        CurrencyPairSettleDate settleDate = getNewEntity();
        byte[] json = super.jsonReplace(settleDate, "updateTime", "2018-10-10 01:02:61");
        super.shouldReturnBadRequestWhenCreate(json,settleDate,containsString("updateTime"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenCreateWithIncompleteUpdateMinutes() throws Exception {
        CurrencyPairSettleDate settleDate = getNewEntity();
        byte[] json = super.jsonReplace(settleDate, "updateTime", "2018-10-10 01:61:09");
        super.shouldReturnBadRequestWhenCreate(json,settleDate,containsString("updateTime"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenCreateWithIncompleteUpdateHours() throws Exception {
        CurrencyPairSettleDate settleDate = getNewEntity();
        byte[] json = super.jsonReplace(settleDate, "updateTime", "2018-10-10 25:10:09");
        super.shouldReturnBadRequestWhenCreate(json,settleDate,containsString("updateTime"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenCreateWithInvalidUpdateDay() throws Exception {
        CurrencyPairSettleDate settleDate = getNewEntity();
        byte[] json = super.jsonReplace(settleDate, "updateTime", "2018-01-99");
        super.shouldReturnBadRequestWhenCreate(json,settleDate,containsString("updateTime"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenCreateWithInvalidUpdateMonth() throws Exception {
        CurrencyPairSettleDate settleDate = getNewEntity();
        byte[] json = super.jsonReplace(settleDate, "updateTime", "2018-20-01");
        super.shouldReturnBadRequestWhenCreate(json,settleDate,containsString("updateTime"));
    }

    @Test
    public void shouldReturnBadRequestWhenCreateWithInvalidUpdateYear() throws Exception {
        CurrencyPairSettleDate settleDate = getNewEntity();
        byte[] json = super.jsonReplace(settleDate, "updateTime", "9999999999999999999999999999999999999-01-01");
        super.shouldReturnBadRequestWhenCreate(json,settleDate,containsString("updateTime"));
    }

    @Test
    public void shouldReturnBadRequestWhenCreateWithUpdateTimeOutOfRange() throws Exception {
        CurrencyPairSettleDate settleDate = getNewEntity();
        byte[] json = super.jsonReplace(settleDate, "updateTime", "0000-00-00");
        super.shouldReturnBadRequestWhenCreate(json,settleDate,containsString("updateTime"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenUpdateWithNullCurrencyPair() throws Exception {
        CurrencyPairSettleDate settleDate = getNewUpdateEntity();
        settleDate.setCurrencyPair(null);
        super.shouldReturnBadRequestWhenUpdate(settleDate,containsString("NotNull.currencyPairSettleDate.currencyPair"));
    }

    @Test
    public void shouldReturnBadRequestWhenUpdateWithInvalidCurrencyPairSize() throws Exception {
        CurrencyPairSettleDate settleDate = getNewUpdateEntity();
        settleDate.setCurrencyPair(RandomString.make(7));
        super.shouldReturnBadRequestWhenUpdate(settleDate,containsString("Size.currencyPairSettleDate.currencyPair"));
    }

    @Test
    public void shouldReturnBadRequesttWhenUpdateWithNullTradeDate() throws Exception {
        CurrencyPairSettleDate settleDate = getNewUpdateEntity();
        settleDate.setTradeDate(null);
        super.shouldReturnBadRequestWhenUpdate(settleDate,containsString("NotNull.currencyPairSettleDate.tradeDate"));
    }
 
    @Test
    public void shouldReturnBadRequestWhenUpdateWithIncompleteTradeDay() throws Exception {
        CurrencyPairSettleDate settleDate = getNewUpdateEntity();
        byte[] json = super.jsonReplace(settleDate, "tradeDate", "2018-01-0");
        super.shouldReturnBadRequestWhenUpdate(json,settleDate,containsString("tradeDate"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenUpdateWithInvalidTradeDay() throws Exception {
        CurrencyPairSettleDate settleDate = getNewUpdateEntity();
        byte[] json = super.jsonReplace(settleDate, "tradeDate", "2018-01-ab");
        super.shouldReturnBadRequestWhenUpdate(json,settleDate,containsString("tradeDate"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenUpdateWithInvalidTradeMonth() throws Exception {
        CurrencyPairSettleDate settleDate = getNewUpdateEntity();
        byte[] json = super.jsonReplace(settleDate, "tradeDate", "2018-ab-01");
        super.shouldReturnBadRequestWhenUpdate(json,settleDate,containsString("tradeDate"));
    }

    @Test
    public void shouldReturnBadRequestWhenUpdateWithInvalidTradeYear() throws Exception {
        CurrencyPairSettleDate settleDate = getNewUpdateEntity();
        byte[] json = super.jsonReplace(settleDate, "tradeDate", "abcd-01-01");
        super.shouldReturnBadRequestWhenUpdate(json,settleDate,containsString("tradeDate"));
    }

    @Test
    public void shouldReturnBadRequestWhenUpdateWithTradeDateOutOfRange() throws Exception {
        CurrencyPairSettleDate settleDate = getNewUpdateEntity();
        byte[] json = super.jsonReplace(settleDate, "tradeDate", "2018-02-29");
        super.shouldReturnBadRequestWhenUpdate(json,settleDate,containsString("tradeDate"));
    }
    
    @Test
    public void shouldReturnUpdatedtWhenUpdateWithNullSettleDate() throws Exception {
        CurrencyPairSettleDate settleDate = getNewUpdateEntity();
        settleDate.setSettleDate(null);

        Mockito.when(service.findById(settleDate.getId())).thenReturn(Optional.of(settleDate));

        super.shouldReturnUpdated(settleDate);
    }
 
    @Test
    public void shouldReturnBadRequestWhenUpdateWithIncompleteSettleDay() throws Exception {
        CurrencyPairSettleDate settleDate = getNewUpdateEntity();
        byte[] json = super.jsonReplace(settleDate, "settleDate", "2018");
        super.shouldReturnBadRequestWhenUpdate(json,settleDate,containsString("settleDate"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenUpdateWithInvalidSettleDay() throws Exception {
        CurrencyPairSettleDate settleDate = getNewUpdateEntity();
        byte[] json = super.jsonReplace(settleDate, "settleDate", "2018-09-");
        super.shouldReturnBadRequestWhenUpdate(json,settleDate,containsString("settleDate"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenUpdateWithInvalidSettleMonth() throws Exception {
        CurrencyPairSettleDate settleDate = getNewUpdateEntity();
        byte[] json = super.jsonReplace(settleDate, "settleDate", "2018--01");
        super.shouldReturnBadRequestWhenUpdate(json,settleDate,containsString("settleDate"));
    }

    @Test
    public void shouldReturnBadRequestWhenUpdateWithInvalidSettleYear() throws Exception {
        CurrencyPairSettleDate settleDate = getNewUpdateEntity();
        byte[] json = super.jsonReplace(settleDate, "settleDate", "-01-01");
        super.shouldReturnBadRequestWhenUpdate(json,settleDate,containsString("settleDate"));
    }

    @Test
    public void shouldReturnBadRequestWhenUpdateWithSettleDateOutOfRange() throws Exception {
        CurrencyPairSettleDate settleDate = getNewUpdateEntity();
        byte[] json = super.jsonReplace(settleDate, "settleDate", "2018-11-31");
        super.shouldReturnBadRequestWhenUpdate(json,settleDate,containsString("settleDate"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenUpdateWithNullHolidayCount() throws Exception {
        CurrencyPairSettleDate settleDate = getNewUpdateEntity();
        settleDate.setHolidayCount(null);
        super.shouldReturnBadRequestWhenUpdate(settleDate,containsString("NotNull.currencyPairSettleDate.holidayCount"));
    }

    @Test
    public void shouldReturnBadRequestWhenUpdateWithInvalidHolidayCountSize() throws Exception {
        CurrencyPairSettleDate settleDate = getNewUpdateEntity();
        settleDate.setHolidayCount(Byte.MAX_VALUE+1);
        super.shouldReturnBadRequestWhenUpdate(settleDate,containsString("Max.currencyPairSettleDate.holidayCount"));
    }
    
    @Test
    public void shouldReturnUpdatedtWhenUpdateWithNullNotes() throws Exception {
        CurrencyPairSettleDate settleDate = getNewUpdateEntity();
        settleDate.setNotes(null);

        Mockito.when(service.findById(settleDate.getId())).thenReturn(Optional.of(settleDate));

        super.shouldReturnUpdated(settleDate);
    }

    @Test
    public void shouldReturnBadRequestWhenUpdateWithInvalidNotesSize() throws Exception {
        CurrencyPairSettleDate settleDate = getNewUpdateEntity();
        settleDate.setNotes(RandomString.make(65));
        super.shouldReturnBadRequestWhenUpdate(settleDate,containsString("Size.currencyPairSettleDate.notes"));
    }

    @Test
    public void shouldReturnUpdatedtWhenUpdateWithNullStatus() throws Exception {
        CurrencyPairSettleDate settleDate = getNewUpdateEntity();
        settleDate.setStatus(null);
        
        Mockito.when(service.findById(settleDate.getId())).thenReturn(Optional.of(settleDate));

        super.shouldReturnUpdated(settleDate);
    }
    
    @Test
    public void shouldReturnBadRequestWhenUpdateWithInvalidStatus() throws Exception {
        CurrencyPairSettleDate settleDate = getNewUpdateEntity();
        byte[] json = super.jsonReplace(settleDate, "status", "INVALID");
        super.shouldReturnBadRequestWhenUpdate(json,settleDate,containsString("Status` from String \"INVALID\": value not one of declared Enum instance names"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenUpdateWithNullUpdateTime() throws Exception {
        CurrencyPairSettleDate settleDate = getNewUpdateEntity();
        settleDate.setUpdateTime(null);
        super.shouldReturnBadRequestWhenUpdate(settleDate,containsString("NotNull.currencyPairSettleDate.updateTime"));
    }
 
    @Test
    public void shouldReturnBadRequestWhenUpdateWithIncompleteUpdateTime() throws Exception {
        CurrencyPairSettleDate settleDate = getNewUpdateEntity();
        byte[] json = super.jsonReplace(settleDate, "updateTime", "2018-10-10");
        super.shouldReturnBadRequestWhenUpdate(json,settleDate,containsString("updateTime"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenUpdateWithIncompleteUpdateSeconds() throws Exception {
        CurrencyPairSettleDate settleDate = getNewUpdateEntity();
        byte[] json = super.jsonReplace(settleDate, "updateTime", "2018-10-10 01:02:");
        super.shouldReturnBadRequestWhenUpdate(json,settleDate,containsString("updateTime"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenUpdateWithIncompleteUpdateMinutes() throws Exception {
        CurrencyPairSettleDate settleDate = getNewUpdateEntity();
        byte[] json = super.jsonReplace(settleDate, "updateTime", "2018-10-10 01::09");
        super.shouldReturnBadRequestWhenUpdate(json,settleDate,containsString("updateTime"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenUpdateWithIncompleteUpdateHours() throws Exception {
        CurrencyPairSettleDate settleDate = getNewUpdateEntity();
        byte[] json = super.jsonReplace(settleDate, "updateTime", "2018-10-10 :10:09");
        super.shouldReturnBadRequestWhenUpdate(json,settleDate,containsString("updateTime"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenUpdateWithInvalidUpdateDay() throws Exception {
        CurrencyPairSettleDate settleDate = getNewUpdateEntity();
        byte[] json = super.jsonReplace(settleDate, "updateTime", "2018-01-000000000000000000000000000000000000000000000000000000001");
        super.shouldReturnBadRequestWhenUpdate(json,settleDate,containsString("updateTime"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenUpdateWithInvalidUpdateMonth() throws Exception {
        CurrencyPairSettleDate settleDate = getNewUpdateEntity();
        byte[] json = super.jsonReplace(settleDate, "updateTime", "2018-0000000000000000000000000000000000000000000000000000001-01");
        super.shouldReturnBadRequestWhenUpdate(json,settleDate,containsString("updateTime"));
    }

    @Test
    public void shouldReturnBadRequestWhenUpdateWithInvalidUpdateYear() throws Exception {
        CurrencyPairSettleDate settleDate = getNewUpdateEntity();
        byte[] json = super.jsonReplace(settleDate, "updateTime", "00000000000000000000000000000000000000000000000000001999-01-01");
        super.shouldReturnBadRequestWhenUpdate(json,settleDate,containsString("updateTime"));
    }

    @Test
    public void shouldReturnBadRequestWhenUpdateWithUpdateTimeOutOfRange() throws Exception {
        CurrencyPairSettleDate settleDate = getNewUpdateEntity();
        byte[] json = super.jsonReplace(settleDate, "updateTime", "000-44-44");
        super.shouldReturnBadRequestWhenUpdate(json,settleDate,containsString("updateTime"));
    }
    
}