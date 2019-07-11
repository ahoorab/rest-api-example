package com.example.ex.controller;

import static org.hamcrest.Matchers.containsString;

import java.sql.Date;
import java.sql.Time;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import com.example.ex.model.entity.admin.SystemState;
import com.example.ex.model.type.SystemStatus;

import net.bytebuddy.utility.RandomString;

@WebMvcTest
public class SystemStateControllerTest extends AdminControllerTest<SystemState> {

    @Override
    public void initController() {
        super.baseUri = SystemStateController.BASE_URI;
        super.service = super.systemStateService;
        super.clazz = SystemState.class;
    }

    @Override
    public List<SystemState> getListOfEntities() {
        List<SystemState> states = new ArrayList<>();
        
        try {
            java.util.Date trade = super.dateFormat.parse("2018-01-01");
            java.util.Date prev = super.dateFormat.parse("2018-12-31");
            java.util.Date open = super.timeFormat.parse("09:00:00");
            java.util.Date close = super.timeFormat.parse("18:00:00");

            states.add(new SystemState("USD",new Date(trade.getTime()),new Date(prev.getTime()),SystemStatus.NONE,new Time(open.getTime()),new Time(close.getTime())));
            states.add(new SystemState("USD",null,null,null,new Time(open.getTime()),new Time(close.getTime())));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        return states;
    }

    @Override
    public SystemState getNewEntity() {
        try {
            java.util.Date trade = super.dateFormat.parse("2018-04-20");
            java.util.Date prev = super.dateFormat.parse("2018-04-08");
            java.util.Date open = super.timeFormat.parse("20:02:59");
            java.util.Date close = super.timeFormat.parse("23:59:59");

            return new SystemState("USD",new Date(trade.getTime()),new Date(prev.getTime()),SystemStatus.NONE,new Time(open.getTime()),new Time(close.getTime()));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
    
    @Test
    public void shouldReturnBadRequestWhenCreateWithNullBaseCcy() throws Exception {
        SystemState systemState = getNewEntity();
        systemState.setBaseCcy(null);
        super.shouldReturnBadRequestWhenCreate(systemState,containsString("NotNull.systemState.baseCcy"));
    }

    @Test
    public void shouldReturnBadRequestWhenCreateWithInvalidBaseCcyNameSize() throws Exception {
        SystemState systemState = getNewEntity();
        systemState.setBaseCcy(RandomString.make(4));
        super.shouldReturnBadRequestWhenCreate(systemState,containsString("Size.systemState.baseCcy"));
    }

    @Test
    public void shouldReturnCreatedWhenCreateWithNullTradeDate() throws Exception {
        SystemState systemState = getNewEntity();
        systemState.setTradeDate(null);
        super.shouldReturnCreated(systemState);
    }
    
    @Test
    public void shouldReturnBadRequestWhenCreateWithIncompleteTradeDay() throws Exception {
        SystemState systemState = getNewEntity();
        byte[] json = super.jsonReplace(systemState, "tradeDate", "2018-01-");
        super.shouldReturnBadRequestWhenCreate(json,systemState,containsString("tradeDate"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenCreateWithInvalidTradeDay() throws Exception {
        SystemState systemState = getNewEntity();
        byte[] json = super.jsonReplace(systemState, "tradeDate", "2018-01-99");
        super.shouldReturnBadRequestWhenCreate(json,systemState,containsString("tradeDate"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenCreateWithInvalidTradeMonth() throws Exception {
        SystemState systemState = getNewEntity();
        byte[] json = super.jsonReplace(systemState, "tradeDate", "2018-99-01");
        super.shouldReturnBadRequestWhenCreate(json,systemState,containsString("tradeDate"));
    }

    @Test
    public void shouldReturnBadRequestWhenCreateWithInvalidTradeYear() throws Exception {
        SystemState systemState = getNewEntity();
        byte[] json = super.jsonReplace(systemState, "tradeDate", "-01-01");
        super.shouldReturnBadRequestWhenCreate(json,systemState,containsString("tradeDate"));
    }

    @Test
    public void shouldReturnBadRequestWhenCreateWithTradeDateOutOfRange() throws Exception {
        SystemState systemState = getNewEntity();
        byte[] json = super.jsonReplace(systemState, "tradeDate", "2018-03-33");
        super.shouldReturnBadRequestWhenCreate(json,systemState,containsString("tradeDate"));
    }

    @Test
    public void shouldReturnCreatedWhenCreateWithNullPrevTradeDate() throws Exception {
        SystemState systemState = getNewEntity();
        systemState.setPrevTradeDate(null);
        super.shouldReturnCreated(systemState);
    }

    @Test
    public void shouldReturnBadRequestWhenCreateWithIncompletePrevTradeDay() throws Exception {
        SystemState systemState = getNewEntity();
        byte[] json = super.jsonReplace(systemState, "prevTradeDate", "2018-01-00");
        super.shouldReturnBadRequestWhenCreate(json,systemState,containsString("prevTradeDate"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenCreateWithInvalidPrevTradeDay() throws Exception {
        SystemState systemState = getNewEntity();
        byte[] json = super.jsonReplace(systemState, "prevTradeDate", "2018-01-AB");
        super.shouldReturnBadRequestWhenCreate(json,systemState,containsString("prevTradeDate"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenCreateWithInvalidPrevTradeMonth() throws Exception {
        SystemState systemState = getNewEntity();
        byte[] json = super.jsonReplace(systemState, "prevTradeDate", "2018-AB-01");
        super.shouldReturnBadRequestWhenCreate(json,systemState,containsString("prevTradeDate"));
    }

    @Test
    public void shouldReturnBadRequestWhenCreateWithInvalidPrevTradeYear() throws Exception {
        SystemState systemState = getNewEntity();
        byte[] json = super.jsonReplace(systemState, "prevTradeDate", "0-01-01");
        super.shouldReturnBadRequestWhenCreate(json,systemState,containsString("prevTradeDate"));
    }

    @Test
    public void shouldReturnBadRequestWhenCreateWithPrevTradeDateOutOfRange() throws Exception {
        SystemState systemState = getNewEntity();
        byte[] json = super.jsonReplace(systemState, "prevTradeDate", "2018-13-33");
        super.shouldReturnBadRequestWhenCreate(json,systemState,containsString("prevTradeDate"));
    }
    
    @Test
    public void shouldReturnCreatedWhenCreateWithNullSystemState() throws Exception {
        SystemState systemState = getNewEntity();
        systemState.setSystemState(null);
        super.shouldReturnCreated(systemState);
    }

    @Test
    public void shouldReturnBadRequestWhenCreateWithInvalidSystemState() throws Exception {
        SystemState systemState = getNewEntity();
        byte[] json = super.jsonReplace(systemState, "systemState", "INVALID");
        super.shouldReturnBadRequestWhenCreate(json,systemState,containsString("SystemStatus` from String \"INVALID\": value not one of declared Enum instance names"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenCreateWithNullMarketOpenTime() throws Exception {
        SystemState systemState = getNewEntity();
        systemState.setMarketOpenTime(null);
        super.shouldReturnBadRequestWhenCreate(systemState,containsString("NotNull.systemState.marketOpen"));
    }

    @Test
    public void shouldReturnBadRequestWhenCreateWithIncompleteMarketOpenSeconds() throws Exception {
        SystemState systemState = getNewEntity();
        byte[] json = super.jsonReplace(systemState, "marketOpenTime", "13:01:");
        super.shouldReturnBadRequestWhenCreate(json,systemState,containsString("marketOpenTime"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenCreateWithInvalidMarketOpenSeconds() throws Exception {
        SystemState systemState = getNewEntity();
        byte[] json = super.jsonReplace(systemState, "marketOpenTime", "13:01:99");
        super.shouldReturnBadRequestWhenCreate(json,systemState,containsString("marketOpenTime"));
    }

    @Test
    public void shouldReturnBadRequestWhenCreateWithIncompleteMarketOpenMinutes() throws Exception {
        SystemState systemState = getNewEntity();
        byte[] json = super.jsonReplace(systemState, "marketOpenTime", "13::01");
        super.shouldReturnBadRequestWhenCreate(json,systemState,containsString("marketOpenTime"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenCreateWithInvalidMarketOpenMinutes() throws Exception {
        SystemState systemState = getNewEntity();
        byte[] json = super.jsonReplace(systemState, "marketOpenTime", "13:61:01");
        super.shouldReturnBadRequestWhenCreate(json,systemState,containsString("marketOpenTime"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenCreateWithIncompleteMarketOpenHours() throws Exception {
        SystemState systemState = getNewEntity();
        byte[] json = super.jsonReplace(systemState, "marketOpenTime", ":21:01");
        super.shouldReturnBadRequestWhenCreate(json,systemState,containsString("marketOpenTime"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenCreateWithInvalidMarketOpenHours() throws Exception {
        SystemState systemState = getNewEntity();
        byte[] json = super.jsonReplace(systemState, "marketOpenTime", "25:14:01");
        super.shouldReturnBadRequestWhenCreate(json,systemState,containsString("marketOpenTime"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenCreateWithNullMarketCloseTime() throws Exception {
        SystemState systemState = getNewEntity();
        systemState.setMarketCloseTime(null);
        super.shouldReturnBadRequestWhenCreate(systemState,containsString("NotNull.systemState.marketCloseTime"));
    }

    @Test
    public void shouldReturnBadRequestWhenCreateWithIncompleteMarketCloseSeconds() throws Exception {
        SystemState systemState = getNewEntity();
        byte[] json = super.jsonReplace(systemState, "marketCloseTime", "13:01:");
        super.shouldReturnBadRequestWhenCreate(json,systemState,containsString("marketCloseTime"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenCreateWithInvalidMarketCloseSeconds() throws Exception {
        SystemState systemState = getNewEntity();
        byte[] json = super.jsonReplace(systemState, "marketCloseTime", "13:01:AB");
        super.shouldReturnBadRequestWhenCreate(json,systemState,containsString("marketCloseTime"));
    }

    @Test
    public void shouldReturnBadRequestWhenCreateWithIncompleteMarketCloseMinutes() throws Exception {
        SystemState systemState = getNewEntity();
        byte[] json = super.jsonReplace(systemState, "marketCloseTime", "13::01");
        super.shouldReturnBadRequestWhenCreate(json,systemState,containsString("marketCloseTime"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenCreateWithInvalidMarketCloseMinutes() throws Exception {
        SystemState systemState = getNewEntity();
        byte[] json = super.jsonReplace(systemState, "marketCloseTime", "13:AB:01");
        super.shouldReturnBadRequestWhenCreate(json,systemState,containsString("marketCloseTime"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenCreateWithIncompleteMarketCloseHours() throws Exception {
        SystemState systemState = getNewEntity();
        byte[] json = super.jsonReplace(systemState, "marketCloseTime", ":21:01");
        super.shouldReturnBadRequestWhenCreate(json,systemState,containsString("marketCloseTime"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenCreateWithInvalidMarketCloseHours() throws Exception {
        SystemState systemState = getNewEntity();
        byte[] json = super.jsonReplace(systemState, "marketCloseTime", "AB:14:01");
        super.shouldReturnBadRequestWhenCreate(json,systemState,containsString("marketCloseTime"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenUpdateWithNullBaseCcy() throws Exception {
        SystemState systemState = getNewUpdateEntity();
        systemState.setBaseCcy(null);
        super.shouldReturnBadRequestWhenUpdate(systemState,containsString("NotNull.systemState.baseCcy"));
    }

    @Test
    public void shouldReturnBadRequestWhenUpdateWithInvalidBaseCcyNameSize() throws Exception {
        SystemState systemState = getNewUpdateEntity();
        systemState.setBaseCcy(RandomString.make(4));
        super.shouldReturnBadRequestWhenUpdate(systemState,containsString("Size.systemState.baseCcy"));
    }

    @Test
    public void shouldReturnUpdatedWhenUpdateWithNullTradeDate() throws Exception {
        SystemState systemState = getNewUpdateEntity();
        systemState.setTradeDate(null);
        
        Mockito.when(service.findById(systemState.getId())).thenReturn(Optional.of(systemState));

        super.shouldReturnUpdated(systemState);
    }
    
    @Test
    public void shouldReturnBadRequestWhenUpdateWithIncompleteTradeDay() throws Exception {
        SystemState systemState = getNewUpdateEntity();
        byte[] json = super.jsonReplace(systemState, "tradeDate", "2018-01-    ");
        super.shouldReturnBadRequestWhenUpdate(json,systemState,containsString("tradeDate"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenUpdateWithInvalidTradeDay() throws Exception {
        SystemState systemState = getNewUpdateEntity();
        byte[] json = super.jsonReplace(systemState, "tradeDate", "2018-01-.=");
        super.shouldReturnBadRequestWhenUpdate(json,systemState,containsString("tradeDate"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenUpdateWithInvalidTradeMonth() throws Exception {
        SystemState systemState = getNewUpdateEntity();
        byte[] json = super.jsonReplace(systemState, "tradeDate", "2018-**-01");
        super.shouldReturnBadRequestWhenUpdate(json,systemState,containsString("tradeDate"));
    }

    @Test
    public void shouldReturnBadRequestWhenUpdateWithInvalidTradeYear() throws Exception {
        SystemState systemState = getNewUpdateEntity();
        byte[] json = super.jsonReplace(systemState, "tradeDate", "%^&*-01-01");
        super.shouldReturnBadRequestWhenUpdate(json,systemState,containsString("tradeDate"));
    }

    @Test
    public void shouldReturnBadRequestWhenUpdateWithTradeDateOutOfRange() throws Exception {
        SystemState systemState = getNewUpdateEntity();
        byte[] json = super.jsonReplace(systemState, "tradeDate", "2018-101-20");
        super.shouldReturnBadRequestWhenUpdate(json,systemState,containsString("tradeDate"));
    }

    @Test
    public void shouldReturnUpdatedWhenUpdateWithNullPrevTradeDate() throws Exception {
        SystemState systemState = getNewUpdateEntity();
        systemState.setPrevTradeDate(null);

        Mockito.when(service.findById(systemState.getId())).thenReturn(Optional.of(systemState));

        super.shouldReturnUpdated(systemState);
    }

    @Test
    public void shouldReturnBadRequestWhenUpdateWithIncompletePrevTradeDay() throws Exception {
        SystemState systemState = getNewUpdateEntity();
        byte[] json = super.jsonReplace(systemState, "prevTradeDate", "2018-01- 0");
        super.shouldReturnBadRequestWhenUpdate(json,systemState,containsString("prevTradeDate"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenUpdateWithInvalidPrevTradeDay() throws Exception {
        SystemState systemState = getNewUpdateEntity();
        byte[] json = super.jsonReplace(systemState, "prevTradeDate", "2018-01-$%");
        super.shouldReturnBadRequestWhenUpdate(json,systemState,containsString("prevTradeDate"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenUpdateWithInvalidPrevTradeMonth() throws Exception {
        SystemState systemState = getNewUpdateEntity();
        byte[] json = super.jsonReplace(systemState, "prevTradeDate", "2018-#!-01");
        super.shouldReturnBadRequestWhenUpdate(json,systemState,containsString("prevTradeDate"));
    }

    @Test
    public void shouldReturnBadRequestWhenUpdateWithInvalidPrevTradeYear() throws Exception {
        SystemState systemState = getNewUpdateEntity();
        byte[] json = super.jsonReplace(systemState, "prevTradeDate", "0a-01-01");
        super.shouldReturnBadRequestWhenUpdate(json,systemState,containsString("prevTradeDate"));
    }

    @Test
    public void shouldReturnBadRequestWhenUpdateWithPrevTradeDateOutOfRange() throws Exception {
        SystemState systemState = getNewUpdateEntity();
        byte[] json = super.jsonReplace(systemState, "prevTradeDate", "2018-10-101");
        super.shouldReturnBadRequestWhenUpdate(json,systemState,containsString("prevTradeDate"));
    }
    
    @Test
    public void shouldReturnUpdatedWhenUpdateWithNullSystemState() throws Exception {
        SystemState systemState = getNewUpdateEntity();
        systemState.setSystemState(null);

        Mockito.when(service.findById(systemState.getId())).thenReturn(Optional.of(systemState));

        super.shouldReturnUpdated(systemState);
    }

    @Test
    public void shouldReturnBadRequestWhenUpdateWithInvalidSystemState() throws Exception {
        SystemState systemState = getNewUpdateEntity();
        byte[] json = super.jsonReplace(systemState, "systemState", "INVALID");
        super.shouldReturnBadRequestWhenUpdate(json,systemState,containsString("SystemStatus` from String \"INVALID\": value not one of declared Enum instance names"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenUpdateWithNullMarketOpenTime() throws Exception {
        SystemState systemState = getNewUpdateEntity();
        systemState.setMarketOpenTime(null);
        super.shouldReturnBadRequestWhenUpdate(systemState,containsString("NotNull.systemState.marketOpenTime"));
    }

    @Test
    public void shouldReturnBadRequestWhenUpdateWithIncompleteMarketOpenSeconds() throws Exception {
        SystemState systemState = getNewUpdateEntity();
        byte[] json = super.jsonReplace(systemState, "marketOpenTime", "13:01: ");
        super.shouldReturnBadRequestWhenUpdate(json,systemState,containsString("marketOpenTime"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenUpdateWithInvalidMarketOpenSeconds() throws Exception {
        SystemState systemState = getNewUpdateEntity();
        byte[] json = super.jsonReplace(systemState, "marketOpenTime", "13:01://");
        super.shouldReturnBadRequestWhenUpdate(json,systemState,containsString("marketOpenTime"));
    }

    @Test
    public void shouldReturnBadRequestWhenUpdateWithIncompleteMarketOpenMinutes() throws Exception {
        SystemState systemState = getNewUpdateEntity();
        byte[] json = super.jsonReplace(systemState, "marketOpenTime", "13:  :01");
        super.shouldReturnBadRequestWhenUpdate(json,systemState,containsString("marketOpenTime"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenUpdateWithInvalidMarketOpenMinutes() throws Exception {
        SystemState systemState = getNewUpdateEntity();
        byte[] json = super.jsonReplace(systemState, "marketOpenTime", "13:-=:01");
        super.shouldReturnBadRequestWhenUpdate(json,systemState,containsString("marketOpenTime"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenUpdateWithIncompleteMarketOpenHours() throws Exception {
        SystemState systemState = getNewUpdateEntity();
        byte[] json = super.jsonReplace(systemState, "marketOpenTime", " :21:01");
        super.shouldReturnBadRequestWhenUpdate(json,systemState,containsString("marketOpenTime"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenUpdateWithInvalidMarketOpenHours() throws Exception {
        SystemState systemState = getNewUpdateEntity();
        byte[] json = super.jsonReplace(systemState, "marketOpenTime", "25:14:01");
        super.shouldReturnBadRequestWhenUpdate(json,systemState,containsString("marketOpenTime"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenUpdateWithNullMarketCloseTime() throws Exception {
        SystemState systemState = getNewUpdateEntity();
        systemState.setMarketCloseTime(null);
        super.shouldReturnBadRequestWhenUpdate(systemState,containsString("NotNull.systemState.marketCloseTime"));
    }

    @Test
    public void shouldReturnBadRequestWhenUpdateWithIncompleteMarketCloseSeconds() throws Exception {
        SystemState systemState = getNewUpdateEntity();
        byte[] json = super.jsonReplace(systemState, "marketCloseTime", "13:01: ");
        super.shouldReturnBadRequestWhenUpdate(json,systemState,containsString("marketCloseTime"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenUpdateWithInvalidMarketCloseSeconds() throws Exception {
        SystemState systemState = getNewUpdateEntity();
        byte[] json = super.jsonReplace(systemState, "marketCloseTime", "13:01:60");
        super.shouldReturnBadRequestWhenUpdate(json,systemState,containsString("marketCloseTime"));
    }

    @Test
    public void shouldReturnBadRequestWhenUpdateWithIncompleteMarketCloseMinutes() throws Exception {
        SystemState systemState = getNewUpdateEntity();
        byte[] json = super.jsonReplace(systemState, "marketCloseTime", "13: :01");
        super.shouldReturnBadRequestWhenUpdate(json,systemState,containsString("marketCloseTime"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenUpdateWithInvalidMarketCloseMinutes() throws Exception {
        SystemState systemState = getNewUpdateEntity();
        byte[] json = super.jsonReplace(systemState, "marketCloseTime", "13:60:01");
        super.shouldReturnBadRequestWhenUpdate(json,systemState,containsString("marketCloseTime"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenUpdateWithIncompleteMarketCloseHours() throws Exception {
        SystemState systemState = getNewUpdateEntity();
        byte[] json = super.jsonReplace(systemState, "marketCloseTime", " 0:21:01");
        super.shouldReturnBadRequestWhenUpdate(json,systemState,containsString("marketCloseTime"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenUpdateWithInvalidMarketCloseHours() throws Exception {
        SystemState systemState = getNewUpdateEntity();
        byte[] json = super.jsonReplace(systemState, "marketCloseTime", "25:14:01");
        super.shouldReturnBadRequestWhenUpdate(json,systemState,containsString("marketCloseTime"));
    }

}