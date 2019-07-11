package com.example.ex.repository.admin;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import javax.validation.ConstraintViolationException;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ex.model.entity.admin.BaseEntity;
import com.example.ex.model.entity.admin.Currency;
import com.example.ex.model.entity.admin.CurrencyPair;
import com.example.ex.model.type.Status;
import com.example.ex.repository.admin.CurrencyPairRepository;

import net.bytebuddy.utility.RandomString;

public class CurrencyPairRepositoryTest extends BaseJpaTest<CurrencyPair, Integer> {

    @Autowired
    private CurrencyPairRepository currencyPairRepository;

    @Override
    protected JpaRepository<CurrencyPair, Integer> getImplementationRepository() {
        return currencyPairRepository;
    }
    
    @Override
    public List<BaseEntity> getDependencies() {
        List<BaseEntity> dependencies = new ArrayList<>();
        dependencies.add(new Currency("AUR", 1, true, 0, 1, 1, Status.ENABLED));
        dependencies.add(new Currency("USD", 1, true, 0, 1, 1, Status.ENABLED));
        dependencies.add(new Currency("CAD", 1, true, 0, 1, 1, Status.ENABLED));

        return dependencies;
    }
    
    @Override
    protected CurrencyPair getNewTestEntity() {
        return new CurrencyPair("AURUSD","AUR","USD",true,(int)Byte.MAX_VALUE,(int)Byte.MAX_VALUE,true,true,Status.ENABLED,true,Double.MAX_VALUE,Double.MAX_VALUE,Integer.MAX_VALUE,Integer.MAX_VALUE);
    }

    @Override
    protected void updateEntity(CurrencyPair entity) {
        entity.setName("USDCAD");
        entity.setCcy1("USD");
        entity.setCcy2("CAD");
        entity.setIsTradeable(false);
        entity.setDaysToSpot(2);
        entity.setRatePrecision(4);
        entity.setIsDecimalized(true);
        entity.setIsCls(true);
        entity.setIsEnableCollars(true);
        entity.setPrevClose(0D);
        entity.setAvgSpread(0D);
        entity.setAvgOrderSize(0);
        entity.setAvgTradeSize(0);
        entity.setStatus(Status.ENABLED);
    }

    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveCurrencyPairWithDuplicatedName() {
        CurrencyPair cp = getNewTestEntity();
        cp.setName("duplicated");
        currencyPairRepository.save(cp);

        cp = new CurrencyPair();
        updateEntity(cp);
        cp.setName("duplicated");
        currencyPairRepository.save(cp);
    }
    
    @Test
    public void shouldSave2CurrencyPairWithDifferentName() {
        CurrencyPair cp = getNewTestEntity();
        cp.setName("dup");
        currencyPairRepository.save(cp);

        cp = new CurrencyPair();
        updateEntity(cp);
        cp.setName("notDup");
        currencyPairRepository.save(cp);
    }
    
    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveCurrencyPairWithNullName() {
        CurrencyPair cp = getNewTestEntity();
        cp.setName(null);
        currencyPairRepository.save(cp);
    }

    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveCurrencyPairWithNameSizeGreaterThan6() {
        CurrencyPair cp = getNewTestEntity();
        cp.setName(RandomString.make(7));
        currencyPairRepository.save(cp);
    }

    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveCurrencyPairWithNullCcy1() {
        CurrencyPair cp = getNewTestEntity();
        cp.setCcy1(null);
        currencyPairRepository.save(cp);
    }

    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveCurrencyPairWithCcy1SizeGreaterThan3() {
        CurrencyPair cp = getNewTestEntity();
        cp.setCcy1(RandomString.make(4));
        currencyPairRepository.save(cp);
    }
    
    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveCurrencyPairWithNullCcy2() {
        CurrencyPair cp = getNewTestEntity();
        cp.setCcy2(null);
        currencyPairRepository.save(cp);
    }

    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveCurrencyPairWithCcy2SizeGreaterThan3() {
        CurrencyPair cp = getNewTestEntity();
        cp.setCcy2(RandomString.make(4));
        currencyPairRepository.save(cp);
    }
    
    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveCurrencyPairWithNullIsTradeable() {
        CurrencyPair cp = getNewTestEntity();
        cp.setIsTradeable(null);
        currencyPairRepository.save(cp);
    }
    
    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveCurrencyPairWithNullDaysToSpot() {
        CurrencyPair cp = getNewTestEntity();
        cp.setDaysToSpot(null);
        currencyPairRepository.save(cp);
    }

    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveCurrencyPairWithNullRatePresicion() {
        CurrencyPair cp = getNewTestEntity();
        cp.setRatePrecision(null);
        currencyPairRepository.save(cp);
    }

    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveCurrencyPairWithNullIsDecimalized() {
        CurrencyPair cp = getNewTestEntity();
        cp.setIsDecimalized(null);
        currencyPairRepository.save(cp);
    }
    
    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveCurrencyPairWithNullIsCls() {
        CurrencyPair cp = getNewTestEntity();
        cp.setIsCls(null);
        currencyPairRepository.save(cp);
    }
    
    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveCurrencyPairWithNullStatus() {
        CurrencyPair cp = getNewTestEntity();
        cp.setStatus(null);
        currencyPairRepository.save(cp);
    }
    
    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveCurrencyPairWithNullIsEnableCollars() {
        CurrencyPair cp = getNewTestEntity();
        cp.setIsEnableCollars(null);
        currencyPairRepository.save(cp);
    } 
    
    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveCurrencyPairWithNullPrevClose() {
        CurrencyPair cp = getNewTestEntity();
        cp.setPrevClose(null);
        currencyPairRepository.save(cp);
    }

    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveCurrencyPairWithNullAvgSpread() {
        CurrencyPair cp = getNewTestEntity();
        cp.setAvgSpread(null);
        currencyPairRepository.save(cp);
    }
    
    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveCurrencyPairWithNullAvgOrderSize() {
        CurrencyPair cp = getNewTestEntity();
        cp.setAvgOrderSize(null);
        currencyPairRepository.save(cp);
    }

    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveCurrencyPairWithNullAvgTradeSize() {
        CurrencyPair cp = getNewTestEntity();
        cp.setAvgTradeSize(null);
        currencyPairRepository.save(cp);
    }

}