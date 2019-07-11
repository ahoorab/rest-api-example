package com.example.ex.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.example.ex.exception.EntityNotFoundException;
import com.example.ex.model.entity.admin.Currency;
import com.example.ex.model.entity.admin.Holiday;
import com.example.ex.repository.admin.HolidayRepository;

/**
 * Business layer object for the Holiday entity
 * 
 * It covers basic operations inherited from JpaRepository and applies business
 * validations
 * 
 * @author Sergio Pintos <spintos@gmail.com>
 */

@Service
public class HolidayService extends BaseService<Holiday> {

    private static final Logger LOGGER = LoggerFactory.getLogger(HolidayService.class);

    @Autowired
    private HolidayRepository holidayRepository;
    @Autowired
    private CurrencyService currencyService;

    @Override
    public Holiday save(Holiday holiday) {
        super.validateUnique(holidayRepository::findByDateAndCurrency,holiday,holiday.getDate(),holiday.getCurrency());

        super.validateDependency(currencyService::findByName,Currency.class,holiday.getCurrency());

        return holidayRepository.save(holiday);
    }

    @Override
    public void deleteById(Integer id) {
        Optional<Holiday> holiday = this.findById(id);
        if (!holiday.isPresent()) {
            throw new EntityNotFoundException("Holiday with id <" + id + "> was not found");
        }
        holidayRepository.deleteById(id);
    }

    public List<Holiday> findByCurrencyName(String name) {
        return holidayRepository.findByCurrency(name);
    }

    @Override
    protected JpaRepository<Holiday, Integer> getRepository() {
        return holidayRepository;
    }

}