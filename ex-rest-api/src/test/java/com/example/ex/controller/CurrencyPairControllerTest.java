package com.example.ex.controller;

import static org.hamcrest.Matchers.containsString;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import com.example.ex.model.entity.admin.CurrencyPair;
import com.example.ex.model.type.Status;

import net.bytebuddy.utility.RandomString;

@WebMvcTest
public class CurrencyPairControllerTest extends AdminControllerTest<CurrencyPair> {

    @Override
    public void initController() {
        super.baseUri = CurrencyPairController.BASE_URI;
        super.service = this.currencyPairService;
        super.clazz = CurrencyPair.class;
    }

    @Override
    public List<CurrencyPair> getListOfEntities() {
        List<CurrencyPair> pairs = new ArrayList<>();

        pairs.add(new CurrencyPair("AURUSD","AUR","USD",true,(int)Byte.MAX_VALUE,(int)Byte.MAX_VALUE,true,true,Status.ENABLED,true,Double.MAX_VALUE,0D,0,0));
        pairs.add(new CurrencyPair("USDCAD","USD","CAD",false,1,4,true,true,Status.ENABLED,true,0D,Double.MAX_VALUE,Integer.MAX_VALUE,Integer.MAX_VALUE));
        return pairs;
    }

    @Override
    public CurrencyPair getNewEntity() {
        return new CurrencyPair("AURCAD","AUR","CAD",true,127,127,true,true,Status.ENABLED,true,Double.MAX_VALUE,Double.MAX_VALUE,Integer.MAX_VALUE,Integer.MAX_VALUE);
    }
    
    @Test
    public void shouldReturnBadRequestWhenCreateWithNullName() throws Exception {
        CurrencyPair cp = getNewEntity();
        cp.setName(null);
        super.shouldReturnBadRequestWhenCreate(cp,containsString("NotNull.currencyPair.name"));
    }

    @Test
    public void shouldReturnBadRequestWhenCreateWithInvalidNameSize() throws Exception {
        CurrencyPair cp = getNewEntity();
        cp.setName(RandomString.make(7));
        super.shouldReturnBadRequestWhenCreate(cp,containsString("Size.currencyPair.name"));
    }

    @Test
    public void shouldReturnBadRequestWhenCreateWithNullCcy1() throws Exception {
        CurrencyPair cp = getNewEntity();
        cp.setCcy1(null);
        super.shouldReturnBadRequestWhenCreate(cp,containsString("NotNull.currencyPair.ccy1"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenCreateWithInvalidCcy1Size() throws Exception {
        CurrencyPair cp = getNewEntity();
        cp.setCcy1(RandomString.make(4));
        super.shouldReturnBadRequestWhenCreate(cp,containsString("Size.currencyPair.ccy1"));
    }

    @Test
    public void shouldReturnBadRequestWhenCreateWithNullCcy2() throws Exception {
        CurrencyPair cp = getNewEntity();
        cp.setCcy2(null);
        super.shouldReturnBadRequestWhenCreate(cp,containsString("NotNull.currencyPair.ccy2"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenCreateWithInvalidCcy2Size() throws Exception {
        CurrencyPair cp = getNewEntity();
        cp.setCcy2(RandomString.make(4));
        super.shouldReturnBadRequestWhenCreate(cp,containsString("Size.currencyPair.ccy2"));
    }

    @Test
    public void shouldReturnBadRequestWhenCreateWithNullIsTradeable() throws Exception {
        CurrencyPair cp = getNewEntity();
        cp.setIsTradeable(null);
        super.shouldReturnBadRequestWhenCreate(cp,containsString("NotNull.currencyPair.isTradeable"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenCreateWithInvalidIsTradeable() throws Exception {
        CurrencyPair cp = getNewEntity();
        cp.setIsTradeable(null);
        byte[] json = super.jsonReplace(cp, "isTradeable", "INVALID");
        super.shouldReturnBadRequestWhenCreate(json,cp,containsString("isTradeable"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenCreateWithNullDaysToSpot() throws Exception {
        CurrencyPair cp = getNewEntity();
        cp.setDaysToSpot(null);
        super.shouldReturnBadRequestWhenCreate(cp,containsString("NotNull.currencyPair.daysToSpot"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenCreateWithInvalidDaysToSpotSize() throws Exception {
        CurrencyPair cp = getNewEntity();
        cp.setDaysToSpot(Byte.MAX_VALUE+1);
        super.shouldReturnBadRequestWhenCreate(cp,containsString("Max.currencyPair.daysToSpot"));
    }

    @Test
    public void shouldReturnBadRequestWhenCreateWithNullRatePrecision() throws Exception {
        CurrencyPair cp = getNewEntity();
        cp.setRatePrecision(null);
        super.shouldReturnBadRequestWhenCreate(cp,containsString("NotNull.currencyPair.ratePrecision"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenCreateWithInvalidRatePrecisionSize() throws Exception {
        CurrencyPair cp = getNewEntity();
        cp.setRatePrecision(Byte.MAX_VALUE+1);
        super.shouldReturnBadRequestWhenCreate(cp,containsString("Max.currencyPair.ratePrecision"));
    }

    @Test
    public void shouldReturnBadRequestWhenCreateWithNullIsDecimalized() throws Exception {
        CurrencyPair cp = getNewEntity();
        cp.setIsDecimalized(null);
        super.shouldReturnBadRequestWhenCreate(cp,containsString("NotNull.currencyPair.isDecimalized"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenCreateWithInvalidIsDecimalized() throws Exception {
        CurrencyPair cp = getNewEntity();
        byte[] json = super.jsonReplace(cp, "isDecimalized", "INVALID");
        super.shouldReturnBadRequestWhenCreate(json,cp,containsString("isDecimalized"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenCreateWithNullIsCls() throws Exception {
        CurrencyPair cp = getNewEntity();
        cp.setIsCls(null);
        super.shouldReturnBadRequestWhenCreate(cp,containsString("NotNull.currencyPair.isCls"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenCreateWithInvalidIsCls() throws Exception {
        CurrencyPair cp = getNewEntity();
        byte[] json = super.jsonReplace(cp, "isCls", "INVALID");
        super.shouldReturnBadRequestWhenCreate(json,cp,containsString("isCls"));
    }

    @Test
    public void shouldReturnBadRequestWhenCreateWithNullStatus() throws Exception {
        CurrencyPair cp = getNewEntity();
        cp.setStatus(null);
        super.shouldReturnBadRequestWhenCreate(cp,containsString("NotNull.currencyPair.status"));
    }

    @Test
    public void shouldReturnBadRequestWhenCreateWithNullIsEnableCollars() throws Exception {
        CurrencyPair cp = getNewEntity();
        cp.setIsEnableCollars(null);
        super.shouldReturnBadRequestWhenCreate(cp,containsString("NotNull.currencyPair.isEnableCollars"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenCreateWithInvalidIsEnableCollars() throws Exception {
        CurrencyPair cp = getNewEntity();
        byte[] json = super.jsonReplace(cp, "isEnableCollars", "INVALID");
        super.shouldReturnBadRequestWhenCreate(json,cp,containsString("isEnableCollars"));
    }

    @Test
    public void shouldReturnBadRequestWhenCreateWithNullPrevClose() throws Exception {
        CurrencyPair cp = getNewEntity();
        cp.setPrevClose(null);
        super.shouldReturnBadRequestWhenCreate(cp,containsString("NotNull.currencyPair.prevClose"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenCreateWithInvalidPrevClose() throws Exception {
        CurrencyPair cp = getNewEntity();
        byte[] json = super.jsonReplace(cp, "prevClose", "INVALID");
        super.shouldReturnBadRequestWhenCreate(json,cp,containsString("prevClose"));
    }

    @Test
    public void shouldReturnBadRequestWhenCreateWithNullAvgSpread() throws Exception {
        CurrencyPair cp = getNewEntity();
        cp.setAvgSpread(null);
        super.shouldReturnBadRequestWhenCreate(cp,containsString("NotNull.currencyPair.avgSpread"));
    }

    @Test
    public void shouldReturnBadRequestWhenCreateWithNullAvgOrderSize() throws Exception {
        CurrencyPair cp = getNewEntity();
        cp.setAvgOrderSize(null);
        super.shouldReturnBadRequestWhenCreate(cp,containsString("NotNull.currencyPair.avgOrderSize"));
    }

    @Test
    public void shouldReturnBadRequestWhenCreateWithInvalidAvgOrderSize() throws Exception {
        CurrencyPair cp = getNewEntity();
        byte[] json = super.jsonReplace(cp, "avgOrderSize", Long.toString(Integer.MAX_VALUE+1L));
        super.shouldReturnBadRequestWhenCreate(json,cp,containsString("avgOrderSize"));
    }

    @Test
    public void shouldReturnBadRequestWhenCreateWithNullAvgTradeSize() throws Exception {
        CurrencyPair cp = getNewEntity();
        cp.setAvgTradeSize(null);
        super.shouldReturnBadRequestWhenCreate(cp,containsString("NotNull.currencyPair.avgTradeSize"));
    }

    @Test
    public void shouldReturnBadRequestWhenCreateWithInvalidAvgTradeSize() throws Exception {
        CurrencyPair cp = getNewEntity();
        byte[] json = super.jsonReplace(cp, "avgTradeSize", Long.toString(Integer.MAX_VALUE+1L));
        super.shouldReturnBadRequestWhenCreate(json,cp,containsString("avgTradeSize"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenUpdateWithNullName() throws Exception {
        CurrencyPair cp = getNewUpdateEntity();
        cp.setName(null);
        super.shouldReturnBadRequestWhenUpdate(cp,containsString("NotNull.currencyPair.name"));
    }

    @Test
    public void shouldReturnBadRequestWhenUpdateWithInvalidNameSize() throws Exception {
        CurrencyPair cp = getNewUpdateEntity();
        cp.setName(RandomString.make(7));
        super.shouldReturnBadRequestWhenUpdate(cp,containsString("Size.currencyPair.name"));
    }

    @Test
    public void shouldReturnBadRequestWhenUpdateWithNullCcy1() throws Exception {
        CurrencyPair cp = getNewUpdateEntity();
        cp.setCcy1(null);
        super.shouldReturnBadRequestWhenUpdate(cp,containsString("NotNull.currencyPair.ccy1"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenUpdateWithInvalidCcy1Size() throws Exception {
        CurrencyPair cp = getNewUpdateEntity();
        cp.setCcy1(RandomString.make(4));
        super.shouldReturnBadRequestWhenUpdate(cp,containsString("Size.currencyPair.ccy1"));
    }

    @Test
    public void shouldReturnBadRequestWhenUpdateWithNullCcy2() throws Exception {
        CurrencyPair cp = getNewUpdateEntity();
        cp.setCcy2(null);
        super.shouldReturnBadRequestWhenUpdate(cp,containsString("NotNull.currencyPair.ccy2"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenUpdateWithInvalidCcy2Size() throws Exception {
        CurrencyPair cp = getNewUpdateEntity();
        cp.setCcy2(RandomString.make(4));
        super.shouldReturnBadRequestWhenUpdate(cp,containsString("Size.currencyPair.ccy2"));
    }

    @Test
    public void shouldReturnBadRequestWhenUpdateWithNullIsTradeable() throws Exception {
        CurrencyPair cp = getNewUpdateEntity();
        cp.setIsTradeable(null);
        super.shouldReturnBadRequestWhenUpdate(cp,containsString("NotNull.currencyPair.isTradeable"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenUpdateWithInvalidIsTradeable() throws Exception {
        CurrencyPair cp = getNewUpdateEntity();
        byte[] json = super.jsonReplace(cp, "isTradeable", "INVALID");
        super.shouldReturnBadRequestWhenUpdate(json,cp,containsString("isTradeable"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenUpdateWithNullDaysToSpot() throws Exception {
        CurrencyPair cp = getNewUpdateEntity();
        cp.setDaysToSpot(null);
        super.shouldReturnBadRequestWhenUpdate(cp,containsString("NotNull.currencyPair.daysToSpot"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenUpdateWithInvalidDaysToSpotSize() throws Exception {
        CurrencyPair cp = getNewUpdateEntity();
        cp.setDaysToSpot(Byte.MAX_VALUE+1);
        super.shouldReturnBadRequestWhenUpdate(cp,containsString("Max.currencyPair.daysToSpot"));
    }

    @Test
    public void shouldReturnBadRequestWhenUpdateWithNullRatePrecision() throws Exception {
        CurrencyPair cp = getNewUpdateEntity();
        cp.setRatePrecision(null);
        super.shouldReturnBadRequestWhenUpdate(cp,containsString("NotNull.currencyPair.ratePrecision"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenUpdateWithInvalidRatePrecisionSize() throws Exception {
        CurrencyPair cp = getNewUpdateEntity();
        cp.setRatePrecision(Byte.MAX_VALUE+1);
        super.shouldReturnBadRequestWhenUpdate(cp,containsString("Max.currencyPair.ratePrecision"));
    }

    @Test
    public void shouldReturnBadRequestWhenUpdateWithNullIsDecimalized() throws Exception {
        CurrencyPair cp = getNewUpdateEntity();
        cp.setIsDecimalized(null);
        super.shouldReturnBadRequestWhenUpdate(cp,containsString("NotNull.currencyPair.isDecimalized"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenUpdateWithInvalidIsDecimalized() throws Exception {
        CurrencyPair cp = getNewUpdateEntity();
        byte[] json = super.jsonReplace(cp, "isDecimalized", "INVALID");
        super.shouldReturnBadRequestWhenUpdate(json,cp,containsString("isDecimalized"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenUpdateWithNullIsCls() throws Exception {
        CurrencyPair cp = getNewUpdateEntity();
        cp.setIsCls(null);
        super.shouldReturnBadRequestWhenUpdate(cp,containsString("NotNull.currencyPair.isCls"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenUpdateWithInvalidIsCls() throws Exception {
        CurrencyPair cp = getNewUpdateEntity();
        byte[] json = super.jsonReplace(cp, "isCls", "INVALID");
        super.shouldReturnBadRequestWhenUpdate(json,cp,containsString("isCls"));
    }

    @Test
    public void shouldReturnBadRequestWhenUpdateWithNullStatus() throws Exception {
        CurrencyPair cp = getNewUpdateEntity();
        cp.setStatus(null);
        super.shouldReturnBadRequestWhenUpdate(cp,containsString("NotNull.currencyPair.status"));
    }

    @Test
    public void shouldReturnBadRequestWhenUpdateWithNullIsEnableCollars() throws Exception {
        CurrencyPair cp = getNewUpdateEntity();
        cp.setIsEnableCollars(null);
        super.shouldReturnBadRequestWhenUpdate(cp,containsString("NotNull.currencyPair.isEnableCollars"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenUpdateWithInvalidIsEnableCollars() throws Exception {
        CurrencyPair cp = getNewUpdateEntity();
        byte[] json = super.jsonReplace(cp, "isEnableCollars", "INVALID");
        super.shouldReturnBadRequestWhenUpdate(json,cp,containsString("isEnableCollars"));
    }

    @Test
    public void shouldReturnBadRequestWhenUpdateWithNullPrevClose() throws Exception {
        CurrencyPair cp = getNewUpdateEntity();
        cp.setPrevClose(null);
        super.shouldReturnBadRequestWhenUpdate(cp,containsString("NotNull.currencyPair.prevClose"));
    }

    @Test
    public void shouldReturnBadRequestWhenUpdateWithNullAvgSpread() throws Exception {
        CurrencyPair cp = getNewUpdateEntity();
        cp.setAvgSpread(null);
        super.shouldReturnBadRequestWhenUpdate(cp,containsString("NotNull.currencyPair.avgSpread"));
    }

    @Test
    public void shouldReturnBadRequestWhenUpdateWithNullAvgOrderSize() throws Exception {
        CurrencyPair cp = getNewUpdateEntity();
        cp.setAvgOrderSize(null);
        super.shouldReturnBadRequestWhenUpdate(cp,containsString("NotNull.currencyPair.avgOrderSize"));
    }

    @Test
    public void shouldReturnBadRequestWhenUpdateWithInvalidAvgOrderSize() throws Exception {
        CurrencyPair cp = getNewUpdateEntity();
        byte[] json = super.jsonReplace(cp, "avgOrderSize", Long.toString(Integer.MAX_VALUE+1L));
        super.shouldReturnBadRequestWhenUpdate(json,cp,containsString("avgOrderSize"));
    }

    @Test
    public void shouldReturnBadRequestWhenUpdateWithNullAvgTradeSize() throws Exception {
        CurrencyPair cp = getNewUpdateEntity();
        cp.setAvgTradeSize(null);
        super.shouldReturnBadRequestWhenUpdate(cp,containsString("NotNull.currencyPair.avgTradeSize"));
    }

    @Test
    public void shouldReturnBadRequestWhenUpdateWithInvalidAvgTradeSize() throws Exception {
        CurrencyPair cp = getNewUpdateEntity();
        byte[] json = super.jsonReplace(cp, "avgTradeSize", Long.toString(Integer.MAX_VALUE+1L));
        super.shouldReturnBadRequestWhenUpdate(json,cp,containsString("avgTradeSize"));
    }
    
}