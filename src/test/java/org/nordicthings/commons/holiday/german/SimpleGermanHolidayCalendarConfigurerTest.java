package org.nordicthings.commons.holiday.german;

import org.nordicthings.commons.holiday.HolidayCalendar;
import org.junit.jupiter.api.Test;
import org.nordicthings.commons.holiday.german.GermanFederalState;
import org.nordicthings.commons.holiday.german.SimpleGermanHolidayCalendarConfigurer;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

public class SimpleGermanHolidayCalendarConfigurerTest {

    @Test
    public void testConfigure() {
        HolidayCalendar calendar = HolidayCalendar.builder()
                .forYear(2021)
                .configurer(new SimpleGermanHolidayCalendarConfigurer())
                .build();
        assertThat(calendar.isHoliday(LocalDate.of(2021,11,17), GermanFederalState.SACHSEN)).isTrue();
        assertThat(calendar.isHoliday(LocalDate.of(2021,10,31),GermanFederalState.BAYERN)).isFalse();
    }

}
