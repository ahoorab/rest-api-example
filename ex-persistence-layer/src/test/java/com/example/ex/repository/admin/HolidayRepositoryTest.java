package com.example.ex.repository.admin;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;

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
import com.example.ex.model.entity.admin.Holiday;
import com.example.ex.model.type.Status;

import net.bytebuddy.utility.RandomString;

public class HolidayRepositoryTest extends BaseJpaTest<Holiday, Integer> {

    @Autowired
    private HolidayRepository holidayRepository;
    
    @Override
    protected JpaRepository<Holiday, Integer> getImplementationRepository() {
        return holidayRepository;
    }

    @Override
    public List<BaseEntity> getDependencies() {
        List<BaseEntity> dependencies = new ArrayList<>();
        dependencies.add(new Currency("ARS", 1, true, 0, 1, 1, Status.ENABLED));
        dependencies.add(new Currency("SOL", 1, true, 0, 1, 1, Status.ENABLED));
        dependencies.add(new Currency("dup", 1, true, 0, 1, 1, Status.ENABLED));

        return dependencies;
    }
    
    @Override
    protected Holiday getNewTestEntity() {
        return new Holiday("ARS","description",new Date(System.currentTimeMillis()),Status.ENABLED,0,new Timestamp(System.currentTimeMillis()),"user");
    }

    @Override
    protected void updateEntity(Holiday entity) {
        entity.setCurrency("SOL");
        entity.setDate(new Date(System.currentTimeMillis()));
        entity.setDescription("description");
        entity.setStatus(Status.DISABLED);
        entity.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        entity.setUpdateUser("user");
        entity.setVersion(1);
    }
    
    @Test(expected=DataIntegrityViolationException.class)
    public void shouldNotSaveHolidayWithDuplicatedHolidayAndCcy() {
        Date date = new Date(System.currentTimeMillis());
        Holiday holiday = getNewTestEntity();
        holiday.setDate(date);
        holiday.setCurrency("dup");
        
        try {
            holidayRepository.save(holiday);
        } catch (Exception e) {
            fail("should save entity: " + e.getMessage());
        }

        holiday = new Holiday();
        updateEntity(holiday);
        holiday.setDate(date);
        holiday.setCurrency("dup");

        holidayRepository.save(holiday);
    }
    
    @Test(expected=ConstraintViolationException.class)
    public void shouldNotHolidayWithNullCurrency() {
        Holiday holiday = getNewTestEntity();
        holiday.setCurrency(null);
        holidayRepository.save(holiday);
    }
    
    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveHolidayWithCurrencySizeGreaterThan3() {
        Holiday h = getNewTestEntity();
        h.setCurrency(RandomString.make(4));
        holidayRepository.save(h);
    }

    @Test
    public void shouldSaveHolidayWithNullDescripton() {
        Holiday holiday = getNewTestEntity();
        holiday.setDescription(null);
        Holiday savedHoliday = holidayRepository.save(holiday);

        assertThat(savedHoliday).isEqualTo(holiday);
    }
    
    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveHolidayWithDescriptionSizeGreaterThan50() {
        Holiday h = getNewTestEntity();
        h.setCurrency(RandomString.make(51));
        holidayRepository.save(h);
    }
    
    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveHolidayWithNullStatus() {
        Holiday holiday = getNewTestEntity();
        holiday.setStatus(null);
        holidayRepository.save(holiday);
    }
    
    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveHolidayWithNullVersion() {
        Holiday holiday = getNewTestEntity();
        holiday.setVersion(null);
        holidayRepository.save(holiday);
    }
    
    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveHolidayWithNullUupdateTime() {
        Holiday holiday = getNewTestEntity();
        holiday.setUpdateTime(null);
        holidayRepository.save(holiday);
    }
    
    @Test
    public void shouldSaveHolidayWithNullUpdateUser() {
        Holiday holiday = getNewTestEntity();
        holiday.setUpdateUser(null);
        Holiday savedHoliday = holidayRepository.save(holiday);

        assertThat(savedHoliday).isEqualTo(holiday);
    }
    
    @Test(expected=ConstraintViolationException.class)
    public void shouldNotSaveHolidayWithUpdateUserSizeGreaterThan32() {
        Holiday h = getNewTestEntity();
        h.setUpdateUser(RandomString.make(33));
        holidayRepository.save(h);
    }
}