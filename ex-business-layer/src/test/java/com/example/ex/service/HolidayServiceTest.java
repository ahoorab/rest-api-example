package com.example.ex.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.ex.exception.EntityAlreadyExistsException;
import com.example.ex.exception.EntityNotFoundException;
import com.example.ex.model.entity.admin.Currency;
import com.example.ex.model.entity.admin.Holiday;
import com.example.ex.model.type.Status;
import com.example.ex.repository.admin.HolidayRepository;

@RunWith(SpringRunner.class)
public class HolidayServiceTest {

    @TestConfiguration
    static class HolidayServiceTestContextConfiguration extends ServiceTestContextConfiguration {
        @Bean
        public HolidayService holidayService() {
            return new HolidayService();
        }
    }

    @MockBean
    private HolidayRepository holidayRepository;
    @Autowired
    private HolidayService holidayService;
    @Autowired
    private CurrencyService currencyService;
    
    protected Holiday getNewTestEntity() {
        return new Holiday("ccy","Desc",new Date(System.currentTimeMillis()),Status.ENABLED,0,new Timestamp(System.currentTimeMillis()),"user");
    }

    @Test(expected=EntityAlreadyExistsException.class)
    public void shouldNotSaveDuplicatedHoliday() {
        Date date = new Date(System.currentTimeMillis());
        Timestamp time = new Timestamp(System.currentTimeMillis());

        Holiday existingHoliday = getNewTestEntity();
        existingHoliday.setId(1);
        existingHoliday.setDate(date);
        existingHoliday.setUpdateTime(time);
        
        Mockito.when(currencyService.findByName(existingHoliday.getCurrency())).thenReturn(new Currency());
        Mockito.when(holidayRepository.findByDateAndCurrency(existingHoliday.getDate(),existingHoliday.getCurrency())).thenReturn(existingHoliday);

        Holiday holiday = getNewTestEntity();
        holiday.setDate(date);
        holiday.setUpdateTime(time);
        holidayService.save(holiday);
    }
    
    @Test(expected=EntityNotFoundException.class)
    public void shouldNotSaveHolidayWithCurrencyNotFound() {
        Holiday holiday = getNewTestEntity();

        Mockito.when(currencyService.findByName(holiday.getCurrency())).thenReturn(null);
        
        holidayService.save(holiday);
    }
    
    @Test
    public void shouldSaveHoliday() {
        Holiday holiday = getNewTestEntity();

        Mockito.when(currencyService.findByName(holiday.getCurrency())).thenReturn(new Currency());
        Mockito.when(holidayRepository.save(holiday)).thenReturn(holiday);

        Holiday savedHoliday = holidayService.save(holiday);
        
        assertThat(savedHoliday).isEqualTo(holiday);
    }

    @Test(expected=EntityNotFoundException.class)
    public void shouldNotDeleteANonExistingHoliday() {
        Mockito.when(holidayRepository.findById(1)).thenReturn(Optional.empty());
        holidayService.deleteById(1);
    }
    
    @Test
    public void shouldDeleteHoliday() {
        Holiday holiday = getNewTestEntity();
        holiday.setId(1);

        Mockito.when(holidayRepository.findById(holiday.getId())).thenReturn(Optional.of(holiday));
        List<Holiday> list = new ArrayList<>(); 
        list.add(holiday);
        Mockito.when(holidayRepository.findAllById(Arrays.asList(holiday.getId()))).thenReturn(list);

        holidayService.deleteById(holiday.getId());
        
        Mockito.verify(holidayRepository, Mockito.times(1)).deleteById(holiday.getId());
    }
}