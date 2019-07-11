package com.example.ex.repository.admin;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ex.model.entity.admin.BaseEntity;
import com.example.ex.model.entity.admin.Currency;
import com.example.ex.model.entity.admin.CurrencyPair;
import com.example.ex.model.entity.admin.CurrencyPairSettleDate;
import com.example.ex.model.type.Status;

import net.bytebuddy.utility.RandomString;

public class CurrencyPairSettleDateRepositoryTest extends BaseJpaTest<CurrencyPairSettleDate, Integer> {

    @Autowired
    private CurrencyPairSettleDateRepository currencyPairSettleDateRepository;

    @Override
    protected JpaRepository<CurrencyPairSettleDate, Integer> getImplementationRepository() {
        return currencyPairSettleDateRepository;
    }
    
    @Override
    public List<BaseEntity> getDependencies() {
        List<BaseEntity> dependencies = new ArrayList<>();
        dependencies.add(new Currency("CAD", 1, true, 0, 1, 1, Status.ENABLED));
        dependencies.add(new Currency("USD", 1, true, 0, 1, 1, Status.ENABLED));
        dependencies.add(new CurrencyPair("CADUSD","CAD","USD",true,2,2,true,true,Status.ENABLED,true,0D,0D,0,0));
        dependencies.add(new CurrencyPair("USDCAD","USD","CAD",true,2,2,true,true,Status.ENABLED,true,0D,0D,0,0));
        dependencies.add(new CurrencyPair("USDUSD","USD","USD",true,2,2,true,true,Status.ENABLED,true,0D,0D,0,0));

        return dependencies;
    }
    
    @Override
    protected CurrencyPairSettleDate getNewTestEntity() {
        return new CurrencyPairSettleDate("USDCAD", new Date(System.currentTimeMillis()),
                new Date(System.currentTimeMillis()), (int)Byte.MAX_VALUE, "notes", Status.ENABLED,
                new Timestamp(System.currentTimeMillis()));
    }

    @Override
    protected void updateEntity(CurrencyPairSettleDate entity) {
        entity.setCurrencyPair("CADUSD");
        entity.setTradeDate(new Date(System.currentTimeMillis()));
        entity.setSettleDate(new Date(System.currentTimeMillis()));
        entity.setHolidayCount(127);
        entity.setNotes(null);
        entity.setStatus(Status.DISABLED);
        entity.setUpdateTime(new Timestamp(System.currentTimeMillis()));
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void shouldNotSaveCurrencyPairSettleDateWithDuplicatedCurrencyPairTradeDate() {
        Date duplicatedDate = new Date(System.currentTimeMillis());
        CurrencyPairSettleDate cp = getNewTestEntity();
        cp.setCurrencyPair("USDUSD");
        cp.setTradeDate(duplicatedDate);
        currencyPairSettleDateRepository.save(cp);

        cp = new CurrencyPairSettleDate();
        updateEntity(cp);
        cp.setCurrencyPair("USDUSD");
        cp.setTradeDate(duplicatedDate);
        currencyPairSettleDateRepository.save(cp);
    }
    
    @Test
    public void shouldSave2CurrencyPairSettleDateWithSameNameButDifferentTradeDate() {
        CurrencyPairSettleDate cp = getNewTestEntity();
        cp.setCurrencyPair("USDUSD");
        currencyPairSettleDateRepository.save(cp);

        cp = new CurrencyPairSettleDate();
        updateEntity(cp);
        cp.setCurrencyPair("USDUSD");
        cp.setTradeDate(new Date(2323223232L));
        currencyPairSettleDateRepository.save(cp);
    }
    
    @Test
    public void shouldSave2CurrencyPairSettleDateWithSameTradeDateButDifferentCP() {
        CurrencyPairSettleDate cp = getNewTestEntity();
        cp.setCurrencyPair("USDUSD");
        currencyPairSettleDateRepository.save(cp);

        cp = new CurrencyPairSettleDate();
        updateEntity(cp);
        cp.setCurrencyPair("USDCAD");
        currencyPairSettleDateRepository.save(cp);
    }

    @Test(expected = ConstraintViolationException.class)
    public void shouldNotSaveCurrencyPairSettleDateWithNullCurrencyPair() {
        CurrencyPairSettleDate cp = getNewTestEntity();
        cp.setCurrencyPair(null);
        currencyPairSettleDateRepository.save(cp);
    }

    @Test(expected = ConstraintViolationException.class)
    public void shouldNotSaveCurrencyPairSettleDateWithCurrencyPairSizeGreaterThan6() {
        CurrencyPairSettleDate cp = getNewTestEntity();
        cp.setCurrencyPair(RandomString.make(7));
        currencyPairSettleDateRepository.save(cp);
    }

    @Test(expected = ConstraintViolationException.class)
    public void shouldNotSaveCurrencyPairSettleDateWithNullTradeDate() {
        CurrencyPairSettleDate cp = getNewTestEntity();
        cp.setTradeDate(null);
        currencyPairSettleDateRepository.save(cp);
    }

    @Test
    public void shouldSaveCurrencyPairSettleDateWithNullSettleDate() {
        CurrencyPairSettleDate cp = getNewTestEntity();
        cp.setSettleDate(null);
        CurrencyPairSettleDate savedCp = currencyPairSettleDateRepository.save(cp);

        assertThat(savedCp).isEqualTo(cp);
    }

    @Test(expected = ConstraintViolationException.class)
    public void shouldNotSaveCurrencyPairSettleDateWithNullHolidayCount() {
        CurrencyPairSettleDate cp = getNewTestEntity();
        cp.setHolidayCount(null);
        currencyPairSettleDateRepository.save(cp);
    }

    @Test
    public void shouldSaveCurrencyPairSettleDateWithNullNotes() {
        CurrencyPairSettleDate cp = getNewTestEntity();
        cp.setNotes(null);
        CurrencyPairSettleDate savedCp = currencyPairSettleDateRepository.save(cp);

        assertThat(savedCp).isEqualTo(cp);
    }

    @Test(expected = ConstraintViolationException.class)
    public void shouldNotSaveCurrencyPairSettleDateWithNotesSizeGreaterThan64() {
        CurrencyPairSettleDate cp = getNewTestEntity();
        cp.setNotes(RandomString.make(65));
        currencyPairSettleDateRepository.save(cp);
    }

    @Test(expected = ConstraintViolationException.class)
    public void shouldNotSaveCurrencyPairSettleDateWithNullUpdateTime() {
        CurrencyPairSettleDate cp = getNewTestEntity();
        cp.setUpdateTime(null);
        currencyPairSettleDateRepository.save(cp);
    }
}