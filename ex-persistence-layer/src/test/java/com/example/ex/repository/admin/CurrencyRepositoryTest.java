package com.example.ex.repository.admin;

import static org.junit.Assert.fail;

import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ex.model.entity.admin.Currency;
import com.example.ex.model.type.Status;

import net.bytebuddy.utility.RandomString;

public class CurrencyRepositoryTest extends BaseJpaTest<Currency, Integer> {

    @Autowired
    private CurrencyRepository currencyRepository;

    @Override
    protected JpaRepository<Currency, Integer> getImplementationRepository() {
        return currencyRepository;
    }

    @Override
    protected Currency getNewTestEntity() {
        return new Currency("ARS", 127, true, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Status.ENABLED);
    }

    @Override
    protected void updateEntity(Currency entity) {
        entity.setName("ARG");
        entity.setAmountDp(0);
        entity.setCls(false);
        entity.setMinQuantity(1);
        entity.setMinIncrement(2);
        entity.setLotSize(2);
        entity.setStatus(Status.DISABLED);
    }
    
    @Test(expected=DataIntegrityViolationException.class)
    public void shouldNotSaveAppTypeWithDuplicatedName() {
        Currency currency = getNewTestEntity();
        currency.setName("dup");
        
        try {
            currencyRepository.save(currency);
        } catch (Exception e) {
            fail("should save entity: " + e.getMessage());
        }

        currency = new Currency();
        updateEntity(currency);
        currency.setName("dup");
        
        currencyRepository.save(currency);
    }
    
    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveCurrencyWithNullName() {
        Currency currency = getNewTestEntity();
        currency.setName(null);
        currencyRepository.save(currency);
    }
    
    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveCurrencyWithNameSizeGreaterThan4() {
        Currency currency = getNewTestEntity();
        currency.setName(RandomString.make(5));
        currencyRepository.save(currency);
    }
    
    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveCurrencyWithNullAmountDp() {
        Currency currency = getNewTestEntity();
        currency.setAmountDp(null);
        currencyRepository.save(currency);
    }
    
    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveCurrencyWithNullIsCls() {
        Currency currency = getNewTestEntity();
        currency.setCls(null);
        currencyRepository.save(currency);
    }
    
    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveCurrencyWithNullMinQuantity() {
        Currency currency = getNewTestEntity();
        currency.setMinQuantity(null);
        currencyRepository.save(currency);
    }
    
    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveCurrencyWithNullMinIncrement() {
        Currency currency = getNewTestEntity();
        currency.setMinIncrement(null);
        currencyRepository.save(currency);
    }
    
    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveCurrencyWithNullLotSize() {
        Currency currency = getNewTestEntity();
        currency.setLotSize(null);
        currencyRepository.save(currency);
    }
    
    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveCurrencyWithNullStatus() {
        Currency currency = getNewTestEntity();
        currency.setStatus(null);
        currencyRepository.save(currency);
    }
    
}