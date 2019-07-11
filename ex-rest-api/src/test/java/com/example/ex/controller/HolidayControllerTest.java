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

import com.example.ex.model.entity.admin.Holiday;
import com.example.ex.model.type.Status;

import net.bytebuddy.utility.RandomString;

@WebMvcTest
public class HolidayControllerTest extends AdminControllerTest<Holiday> {

    @Override
    public void initController() {
        super.baseUri = HolidayController.BASE_URI;
        super.service = this.holidayService;
        super.clazz = Holiday.class;
    }

    @Override
    public List<Holiday> getListOfEntities() {
        List<Holiday> holidays = new ArrayList<>();

        try {
            java.util.Date date = super.dateFormat.parse("2018-10-02");
            java.util.Date update = super.timeFormat.parse("03:04:05");
            Timestamp timestamp = new Timestamp(update.getTime());

            holidays.add(new Holiday("ARS", "now", new Date(date.getTime()), Status.ENABLED, 0, timestamp, "user1"));
            holidays.add(new Holiday("USD", "now", new Date(date.getTime()), Status.ENABLED, 0, timestamp, "user2"));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return holidays;
    }

    @Override
    public Holiday getNewEntity() {
        try {
            java.util.Date date = super.dateFormat.parse("2018-10-03");
            java.util.Date update = super.timeFormat.parse("12:13:14");
            Timestamp timestamp = new Timestamp(update.getTime());

            return new Holiday("SOL", "now", new Date(date.getTime()), Status.ENABLED, 0, timestamp, "user3");
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
    
    @Test
    public void shouldReturnBadRequestWhenCreateWithNullCurrency() throws Exception {
        Holiday holiday = getNewEntity();
        holiday.setCurrency(null);
        super.shouldReturnBadRequestWhenCreate(holiday,containsString("NotNull.holiday.currency"));
    }

    @Test
    public void shouldReturnBadRequestWhenCreateWithInvalidCurrencySize() throws Exception {
        Holiday holiday = getNewEntity();
        holiday.setCurrency(RandomString.make(4));
        super.shouldReturnBadRequestWhenCreate(holiday,containsString("Size.holiday.currency"));
    }
    
    @Test
    public void shouldReturnCreatedWhenCreateWithNullDescription() throws Exception {
        Holiday holiday = getNewEntity();
        holiday.setDescription(null);
        super.shouldReturnCreated(holiday);
    }
    
    @Test
    public void shouldReturnBadRequestWhenCreateWithInvalidDescriptionSize() throws Exception {
        Holiday holiday = getNewEntity();
        holiday.setDescription(RandomString.make(51));
        super.shouldReturnBadRequestWhenCreate(holiday,containsString("Size.holiday.description"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenCreateWithNullHoliday() throws Exception {
        Holiday holiday = getNewEntity();
        holiday.setDate(null);
        super.shouldReturnBadRequestWhenCreate(holiday,containsString("NotNull.holiday.date"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenCreateWithIncompleteHolidayDay() throws Exception {
        Holiday holiday = getNewEntity();
        byte[] json = super.jsonReplace(holiday, "date", "2018-01- 0");
        super.shouldReturnBadRequestWhenCreate(json,holiday,containsString("date"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenCreateWithInvalidHolidayDay() throws Exception {
        Holiday holiday = getNewEntity();
        byte[] json = super.jsonReplace(holiday, "date", "2018-01-+.");
        super.shouldReturnBadRequestWhenCreate(json,holiday,containsString("date"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenCreateWithIncompleteHolidayMonth() throws Exception {
        Holiday holiday = getNewEntity();
        byte[] json = super.jsonReplace(holiday, "date", "2018-0 -01");
        super.shouldReturnBadRequestWhenCreate(json,holiday,containsString("date"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenCreateWithInvalidHolidayMonth() throws Exception {
        Holiday holiday = getNewEntity();
        byte[] json = super.jsonReplace(holiday, "date", "2018-!\"-01");
        super.shouldReturnBadRequestWhenCreate(json,holiday,containsString("date"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenCreateWithIncompleteHolidayYear() throws Exception {
        Holiday holiday = getNewEntity();
        byte[] json = super.jsonReplace(holiday, "date", "201 -02-01");
        super.shouldReturnBadRequestWhenCreate(json,holiday,containsString("date"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenCreateWithInvalidHolidayYear() throws Exception {
        Holiday holiday = getNewEntity();
        byte[] json = super.jsonReplace(holiday, "date", "#$%^-02-01");
        super.shouldReturnBadRequestWhenCreate(json,holiday,containsString("date"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenCreateWithNullStatus() throws Exception {
        Holiday holiday = getNewEntity();
        holiday.setStatus(null);
        super.shouldReturnBadRequestWhenCreate(holiday,containsString("NotNull.holiday.status"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenCreateWithInvalidStatus() throws Exception {
        Holiday holiday = getNewEntity();
        byte[] json = super.jsonReplace(holiday, "status", "INVALID");
        super.shouldReturnBadRequestWhenCreate(json,holiday,containsString("Status` from String \"INVALID\": value not one of declared Enum instance names"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenCreateWithNullVersion() throws Exception {
        Holiday holiday = getNewEntity();
        holiday.setVersion(null);
        super.shouldReturnBadRequestWhenCreate(holiday,containsString("NotNull.holiday.version"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenCreateWithInvalidVersionSize() throws Exception {
        Holiday holiday = getNewEntity();
        byte[] json = super.jsonReplace(holiday, "version", Long.toString(Integer.MAX_VALUE+1L));
        super.shouldReturnBadRequestWhenCreate(json,holiday,containsString("version"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenCreateWithNullUpdateTime() throws Exception {
        Holiday holiday = getNewEntity();
        holiday.setUpdateTime(null);
        super.shouldReturnBadRequestWhenCreate(holiday,containsString("NotNull.holiday.updateTime"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenCreateWithIncompleteUpdateTimeSeconds() throws Exception {
        Holiday holiday = getNewEntity();
        byte[] json = super.jsonReplace(holiday, "updateTime", "01:01: 0");
        super.shouldReturnBadRequestWhenCreate(json,holiday,containsString("updateTime"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenCreateWithInvalidUpdateTimeSeconds() throws Exception {
        Holiday holiday = getNewEntity();
        byte[] json = super.jsonReplace(holiday, "updateTime", "01:01:61");
        super.shouldReturnBadRequestWhenCreate(json,holiday,containsString("updateTime"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenCreateWithIncompleteUpdateTimeMinutes() throws Exception {
        Holiday holiday = getNewEntity();
        byte[] json = super.jsonReplace(holiday, "updateTime", "21: 9:00");
        super.shouldReturnBadRequestWhenCreate(json,holiday,containsString("updateTime"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenCreateWithInvalidUpdateTimeMinutes() throws Exception {
        Holiday holiday = getNewEntity();
        byte[] json = super.jsonReplace(holiday, "updateTime", "20:61:00");
        super.shouldReturnBadRequestWhenCreate(json,holiday,containsString("updateTime"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenCreateWithIncompleteUpdateTimeHour() throws Exception {
        Holiday holiday = getNewEntity();
        byte[] json = super.jsonReplace(holiday, "updateTime", "2 :01:10");
        super.shouldReturnBadRequestWhenCreate(json,holiday,containsString("updateTime"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenCreateWithInvalidUpdateTimeHour() throws Exception {
        Holiday holiday = getNewEntity();
        byte[] json = super.jsonReplace(holiday, "updateTime", "24:00:00");
        super.shouldReturnBadRequestWhenCreate(json,holiday,containsString("updateTime"));
    }
    
    @Test
    public void shouldReturnCreatedWhenCreateWithNullUpdateUser() throws Exception {
        Holiday holiday = getNewEntity();
        holiday.setUpdateUser(null);
        super.shouldReturnCreated(holiday);
    }
    
    @Test
    public void shouldReturnBadRequestWhenCreateWithInvalidUpdateUser() throws Exception {
        Holiday holiday = getNewEntity();
        holiday.setUpdateUser(RandomString.make(33));
        super.shouldReturnBadRequestWhenCreate(holiday,containsString("Size.holiday.updateUser"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenUpdateWithNullCurrency() throws Exception {
        Holiday holiday = getNewUpdateEntity();
        holiday.setCurrency(null);
        super.shouldReturnBadRequestWhenUpdate(holiday,containsString("NotNull.holiday.currency"));
    }

    @Test
    public void shouldReturnBadRequestWhenUpdateWithInvalidCurrencySize() throws Exception {
        Holiday holiday = getNewUpdateEntity();
        holiday.setCurrency(RandomString.make(4));
        super.shouldReturnBadRequestWhenUpdate(holiday,containsString("Size.holiday.currency"));
    }
    
    @Test
    public void shouldReturnUpdatedWhenUpdateWithNullDescription() throws Exception {
        Holiday holiday = getNewUpdateEntity();
        holiday.setDescription(null);

        Mockito.when(service.findById(holiday.getId())).thenReturn(Optional.of(holiday));

        super.shouldReturnUpdated(holiday);
    }
    
    @Test
    public void shouldReturnBadRequestWhenUpdateWithInvalidDescriptionSize() throws Exception {
        Holiday holiday = getNewUpdateEntity();
        holiday.setDescription(RandomString.make(51));
        super.shouldReturnBadRequestWhenUpdate(holiday,containsString("Size.holiday.description"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenUpdateWithNullHoliday() throws Exception {
        Holiday holiday = getNewUpdateEntity();
        holiday.setDate(null);
        super.shouldReturnBadRequestWhenUpdate(holiday,containsString("NotNull.holiday.date"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenUpdateWithIncompleteHolidayDay() throws Exception {
        Holiday holiday = getNewUpdateEntity();
        byte[] json = super.jsonReplace(holiday, "date", "2018-01- 0");
        super.shouldReturnBadRequestWhenUpdate(json,holiday,containsString("date"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenUpdateWithInvalidHolidayDay() throws Exception {
        Holiday holiday = getNewUpdateEntity();
        byte[] json = super.jsonReplace(holiday, "date", "2018-01-+.");
        super.shouldReturnBadRequestWhenUpdate(json,holiday,containsString("date"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenUpdateWithIncompleteHolidayMonth() throws Exception {
        Holiday holiday = getNewUpdateEntity();
        byte[] json = super.jsonReplace(holiday, "date", "2018-0 -01");
        super.shouldReturnBadRequestWhenUpdate(json,holiday,containsString("date"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenUpdateWithInvalidHolidayMonth() throws Exception {
        Holiday holiday = getNewUpdateEntity();
        byte[] json = super.jsonReplace(holiday, "date", "2018-!\"-01");
        super.shouldReturnBadRequestWhenUpdate(json,holiday,containsString("date"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenUpdateWithIncompleteHolidayYear() throws Exception {
        Holiday holiday = getNewUpdateEntity();
        byte[] json = super.jsonReplace(holiday, "date", "201 -02-01");
        super.shouldReturnBadRequestWhenUpdate(json,holiday,containsString("date"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenUpdateWithInvalidHolidayYear() throws Exception {
        Holiday holiday = getNewUpdateEntity();
        byte[] json = super.jsonReplace(holiday, "date", "#$%^-02-01");
        super.shouldReturnBadRequestWhenUpdate(json,holiday,containsString("date"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenUpdateWithNullStatus() throws Exception {
        Holiday holiday = getNewUpdateEntity();
        holiday.setStatus(null);
        super.shouldReturnBadRequestWhenUpdate(holiday,containsString("NotNull.holiday.status"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenUpdateWithInvalidStatus() throws Exception {
        Holiday holiday = getNewUpdateEntity();
        byte[] json = super.jsonReplace(holiday, "status", "INVALID");
        super.shouldReturnBadRequestWhenUpdate(json,holiday,containsString("Status` from String \"INVALID\": value not one of declared Enum instance names"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenUpdateWithNullVersion() throws Exception {
        Holiday holiday = getNewUpdateEntity();
        holiday.setVersion(null);
        super.shouldReturnBadRequestWhenUpdate(holiday,containsString("NotNull.holiday.version"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenUpdateWithInvalidVersionSize() throws Exception {
        Holiday holiday = getNewUpdateEntity();
        byte[] json = super.jsonReplace(holiday, "version", Long.toString(Integer.MAX_VALUE+1L));
        super.shouldReturnBadRequestWhenUpdate(json,holiday,containsString("version"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenUpdateWithNullUpdateTime() throws Exception {
        Holiday holiday = getNewUpdateEntity();
        holiday.setUpdateTime(null);
        super.shouldReturnBadRequestWhenUpdate(holiday,containsString("NotNull.holiday.updateTime"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenUpdateWithIncompleteUpdateTimeSeconds() throws Exception {
        Holiday holiday = getNewUpdateEntity();
        byte[] json = super.jsonReplace(holiday, "updateTime", "01:01: 0");
        super.shouldReturnBadRequestWhenUpdate(json,holiday,containsString("updateTime"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenUpdateWithInvalidUpdateTimeSeconds() throws Exception {
        Holiday holiday = getNewUpdateEntity();
        byte[] json = super.jsonReplace(holiday, "updateTime", "01:01:61");
        super.shouldReturnBadRequestWhenUpdate(json,holiday,containsString("updateTime"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenUpdateWithIncompleteUpdateTimeMinutes() throws Exception {
        Holiday holiday = getNewUpdateEntity();
        byte[] json = super.jsonReplace(holiday, "updateTime", "21: 9:00");
        super.shouldReturnBadRequestWhenUpdate(json,holiday,containsString("updateTime"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenUpdateWithInvalidUpdateTimeMinutes() throws Exception {
        Holiday holiday = getNewUpdateEntity();
        byte[] json = super.jsonReplace(holiday, "updateTime", "20:61:00");
        super.shouldReturnBadRequestWhenUpdate(json,holiday,containsString("updateTime"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenUpdateWithIncompleteUpdateTimeHour() throws Exception {
        Holiday holiday = getNewUpdateEntity();
        byte[] json = super.jsonReplace(holiday, "updateTime", "2 :01:10");
        super.shouldReturnBadRequestWhenUpdate(json,holiday,containsString("updateTime"));
    }
    
    @Test
    public void shouldReturnBadRequestWhenUpdateWithInvalidUpdateTimeHour() throws Exception {
        Holiday holiday = getNewUpdateEntity();
        byte[] json = super.jsonReplace(holiday, "updateTime", "24:00:00");
        super.shouldReturnBadRequestWhenUpdate(json,holiday,containsString("updateTime"));
    }
    
    @Test
    public void shouldReturnUpdatedWhenUpdateWithNullUpdateUser() throws Exception {
        Holiday holiday = getNewUpdateEntity();
        holiday.setUpdateUser(null);
        
        Mockito.when(service.findById(holiday.getId())).thenReturn(Optional.of(holiday));

        super.shouldReturnUpdated(holiday);
    }
    
    @Test
    public void shouldReturnBadRequestWhenUpdateWithInvalidUpdateUser() throws Exception {
        Holiday holiday = getNewUpdateEntity();
        holiday.setUpdateUser(RandomString.make(33));
        super.shouldReturnBadRequestWhenUpdate(holiday,containsString("Size.holiday.updateUser"));
    }
}