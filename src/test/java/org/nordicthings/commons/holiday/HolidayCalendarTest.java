package org.nordicthings.commons.holiday;

import org.junit.jupiter.api.Test;
import org.nordicthings.commons.holiday.*;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class HolidayCalendarTest {

    private static final LocalDate ANY_DATE = LocalDate.of(2021, 9, 20);
    private static final HolidayCalendarConfigurer ANY_CONFIGURER = holidayCalendar -> {
    };

    @Test
    public void testBuilderWithoutPeriodStart() {
        HolidayCalendar.Builder sut = HolidayCalendar.builder()
                .periodEnd(ANY_DATE)
                .configurer(ANY_CONFIGURER);
        assertThrows(IllegalStateException.class, sut::build);
    }

    @Test
    public void testBuilderWithoutPeriodEnd() {
        HolidayCalendar.Builder sut = HolidayCalendar.builder()
                .periodStart(ANY_DATE)
                .configurer(ANY_CONFIGURER);
        assertThrows(IllegalStateException.class, sut::build);
    }

    @Test
    public void testEmptyness() {
        HolidayCalendar sut = HolidayCalendar.builder()
                .forYear(2021)
                .build();
        assertThat(sut.isEmpty()).isTrue();
    }

    @Test
    public void testCreationForRelativeYear() {
        LocalDate begin = LocalDate.of(2021,11,15);
        HolidayCalendar sut = HolidayCalendar.builder()
                .forRelativeYear(begin)
                .build();
        assertThat(sut.getPeriodStart()).isEqualTo(begin);
        assertThat(sut.getPeriodEnd()).isEqualTo(LocalDate.of(2022,11,14));
    }

    @Test
    public void testCreationForRelativeYearInLeapYear() {
        LocalDate begin = LocalDate.of(2019,3,1);
        HolidayCalendar sut = HolidayCalendar.builder()
                .forRelativeYear(begin)
                .build();
        assertThat(sut.getPeriodStart()).isEqualTo(begin);
        assertThat(sut.getPeriodEnd()).isEqualTo(LocalDate.of(2020,2,29));
    }

    @Test
    public void testBuilderWithWrongInterval() {
        HolidayCalendar.Builder sut = HolidayCalendar.builder()
                .periodStart(ANY_DATE)
                .periodEnd(ANY_DATE.minusDays(1))
                .configurer(ANY_CONFIGURER);
        assertThrows(IllegalStateException.class, sut::build);
    }

    @Test
    public void testBuilderWithTooLongInterval() {
        LocalDate endDate = LocalDate.of(ANY_DATE.getYear() + 1, ANY_DATE.getMonthValue(), ANY_DATE.getDayOfMonth() + 1);
        HolidayCalendar.Builder sut = HolidayCalendar.builder()
                .periodStart(ANY_DATE)
                .periodEnd(endDate)
                .configurer(ANY_CONFIGURER);
        assertThrows(IllegalStateException.class, sut::build);
    }

    @Test
    public void testBuilderWithMaxIntervalLength() {
        LocalDate endDate = LocalDate.of(ANY_DATE.getYear() + 1, ANY_DATE.getMonthValue(), ANY_DATE.getDayOfMonth());
        getCalendar(ANY_DATE, endDate);
        // If this point is reached without exceptions the test succeeds
    }

    @Test
    public void testBuilderWithYear() {
        HolidayCalendar sut = HolidayCalendar.builder()
                .forYear(2021)
                .configurer(ANY_CONFIGURER)
                .build();
        assertThat(sut.getPeriodStart()).isEqualTo(LocalDate.of(2021,1,1));
        assertThat(sut.getPeriodEnd()).isEqualTo(LocalDate.of(2021,12,31));
    }

    @Test
    public void addHolidayInPeriod() {
        HolidayCalendar sut = getCalendar(ANY_DATE, ANY_DATE);
        Holiday anyHolidayInPeriod = FixedHoliday.of(ANY_DATE.getDayOfMonth(), ANY_DATE.getMonthValue(), "anyName");
        sut.addHoliday(anyHolidayInPeriod);
        assertThat(sut.getConcreteDate(anyHolidayInPeriod)).isEqualTo(ANY_DATE);
    }

    @Test
    public void addHolidayBeforePeriod() {
        HolidayCalendar sut = getCalendar(ANY_DATE, ANY_DATE);
        Holiday holidayBeforePeriod = FixedHoliday.of(ANY_DATE.getDayOfMonth() - 1, ANY_DATE.getMonthValue(), "anyName");
        sut.addHoliday(holidayBeforePeriod);
        assertThat(sut.getHolidays()).isEmpty();
    }

    @Test
    public void addHolidayAfterPeriod() {
        HolidayCalendar sut = getCalendar(ANY_DATE, ANY_DATE);
        Holiday holidayAfterPeriod = FixedHoliday.of(ANY_DATE.getDayOfMonth() + 1, ANY_DATE.getMonthValue(), "anyName");
        sut.addHoliday(holidayAfterPeriod);
        assertThat(sut.getHolidays()).isEmpty();
    }

    @Test
    public void hasToReturnDateAfterYearChange() {
        LocalDate begin = LocalDate.of(2021, 12, 1);
        LocalDate end = LocalDate.of(2022, 1, 31);
        HolidayCalendar sut = getCalendar(begin, end);
        Holiday holidayAfterYearChange = FixedHoliday.of(1, 1, "anyName");
        assertThat(sut.getConcreteDate(holidayAfterYearChange).getYear()).isEqualTo(end.getYear());
    }

    @Test
    public void hasToReturnDateBeforeYearChange() {
        LocalDate begin = LocalDate.of(2021, 12, 1);
        LocalDate end = LocalDate.of(2022, 1, 31);
        HolidayCalendar sut = getCalendar(begin, end);
        Holiday holidayBeforeYearChange = FixedHoliday.of(25, 12, "anyName");
        assertThat(sut.getConcreteDate(holidayBeforeYearChange).getYear()).isEqualTo(begin.getYear());
    }

    @Test
    public void testIsHoliday() {
        HolidayCalendar sut = getCalendar(LocalDate.of(2021, 12, 1), LocalDate.of(2022, 1, 31));
        Holiday holiday = FixedHoliday.of(25, 12, "anyName");
        sut.addHoliday(holiday);
        assertThat(sut.isHoliday(sut.getConcreteDate(holiday))).isTrue();
        assertThat(sut.isHoliday(sut.getConcreteDate(holiday).minusDays(1))).isFalse();
    }

    @Test
    public void testIsHolidayWithArea() {
        HolidayCalendar sut = getCalendar(LocalDate.of(2021, 12, 1), LocalDate.of(2022, 1, 31));
        AdministrativeArea anyArea = new SimpleArea("any");
        AdministrativeArea anotherArea = new SimpleArea("another");
        Holiday holiday = FixedHoliday.ofSpecificAreas(25, 12, "anyName", anyArea);
        sut.addHoliday(holiday);
        assertThat(sut.isHoliday(sut.getConcreteDate(holiday))).isFalse();
        assertThat(sut.isHoliday(sut.getConcreteDate(holiday),anyArea)).isTrue();
        assertThat(sut.isHoliday(sut.getConcreteDate(holiday),anotherArea)).isFalse();
        assertThat(sut.isHoliday(sut.getConcreteDate(holiday).minusDays(1),anyArea)).isFalse();
    }


    @Test
    public void testIsInPeriod() {
        HolidayCalendar sut = getCalendar(LocalDate.of(2021, 12, 1), LocalDate.of(2021, 12, 29));
        Holiday holidayInPeriod = FixedHoliday.of(25, 12, "holidayInPeriod");
        Holiday holidayBeforePeriod = FixedHoliday.of(30, 11, "holidayBeforePeriod");
        Holiday holidayAfterPeriod = FixedHoliday.of(30, 12, "holidayAfterPeriod");
        assertThat(sut.isInPeriod(holidayInPeriod)).isTrue();
        assertThat(sut.isInPeriod(holidayBeforePeriod)).isFalse();
        assertThat(sut.isInPeriod(holidayAfterPeriod)).isFalse();
    }

    @Test
    public void testIsInPeriodWithYearChange() {
        HolidayCalendar sut = getCalendar(LocalDate.of(2021, 12, 1), LocalDate.of(2022, 1, 31));
        Holiday holidayInPeriod = FixedHoliday.of(25, 12, "anyName");
        Holiday holidayBeforePeriod = FixedHoliday.of(30, 11, "holidayBeforePeriod");
        Holiday holidayAfterPeriod = FixedHoliday.of(1, 2, "holidayAfterPeriod");
        assertThat(sut.isInPeriod(holidayInPeriod)).isTrue();
        assertThat(sut.isInPeriod(holidayBeforePeriod)).isFalse();
        assertThat(sut.isInPeriod(holidayAfterPeriod)).isFalse();
    }

    @Test
    public void testIsWorkingDay() {
        HolidayCalendar sut = getCalendar(LocalDate.of(2021, 9, 1), LocalDate.of(2021, 9, 30));
        sut.addHoliday(FixedHoliday.of(17, 9, "anyHoliday"));
        assertThat(sut.isWorkingDay(LocalDate.of(2021, 9, 16))).isTrue(); // Thursday
        assertThat(sut.isWorkingDay(LocalDate.of(2021, 9, 17))).isFalse(); // Friday but Holiday
        assertThat(sut.isWorkingDay(LocalDate.of(2021, 9, 18))).isFalse(); // Saturday
        assertThat(sut.isWorkingDay(LocalDate.of(2021, 9, 19))).isFalse(); // Sunday
        assertThrows(IllegalArgumentException.class, () -> sut.isWorkingDay(null));
    }

    @Test
    public void testIsWorkingDayWithAreas() {
        HolidayCalendar sut = getCalendar(LocalDate.of(2021, 9, 1), LocalDate.of(2021, 9, 30));
        AdministrativeArea anyArea = new SimpleArea("any");
        AdministrativeArea anotherArea = new SimpleArea("another");
        sut.addHoliday(FixedHoliday.ofSpecificAreas(17, 9, "anyHoliday", anyArea));
        assertThat(sut.isWorkingDay(LocalDate.of(2021, 9, 16),anyArea)).isTrue(); // Thursday
        assertThat(sut.isWorkingDay(LocalDate.of(2021, 9, 17), anyArea)).isFalse(); // Friday but Holiday
        assertThat(sut.isWorkingDay(LocalDate.of(2021, 9, 17), anotherArea)).isTrue(); // Friday and only Holiday in anyArea
        assertThat(sut.isWorkingDay(LocalDate.of(2021, 9, 18), anyArea)).isFalse(); // Saturday
        assertThat(sut.isWorkingDay(LocalDate.of(2021, 9, 19), anotherArea)).isFalse(); // Sunday
        assertThrows(IllegalArgumentException.class, () -> sut.isWorkingDay(null));
    }

    @Test
    public void testIsWeekend() {
        assertThat(HolidayCalendar.isWeekend(LocalDate.of(2021, 9, 16))).isFalse(); // Thursday
        assertThat(HolidayCalendar.isWeekend(LocalDate.of(2021, 9, 18))).isTrue(); // Saturday
        assertThat(HolidayCalendar.isWeekend(LocalDate.of(2021, 9, 19))).isTrue(); // Sunday
        assertThrows(IllegalArgumentException.class, () -> HolidayCalendar.isWeekend(null));
    }

    @Test
    public void testGetNextWorkDay() {
        LocalDate begin = LocalDate.of(2021, 12, 23);
        LocalDate end = LocalDate.of(2021, 12, 31);
        HolidayCalendar sut = getCalendar(begin, end);
        sut.addHoliday(FixedHoliday.of(25, 12, "1. Christmasday"));
        sut.addHoliday(FixedHoliday.of(26, 12, "2. Christmasday"));
        sut.addHoliday(FixedHoliday.of(31, 12, "AnyHoliday"));
        assertThat(sut.getNextWorkingDay(begin.minusDays(1))).isEmpty();
        assertThat(sut.getNextWorkingDay(begin)).contains(begin);
        assertThat(sut.getNextWorkingDay(LocalDate.of(2021,12,25))).contains(LocalDate.of(2021,12,27));
        assertThat(sut.getNextWorkingDay(end)).isEmpty();
    }

    private static HolidayCalendar getCalendar(LocalDate begin, LocalDate end) {
        return HolidayCalendar.builder()
                .periodStart(begin)
                .periodEnd(end)
                .configurer(ANY_CONFIGURER)
                .build();
    }

    private static class SimpleArea implements AdministrativeArea {

        private final String key;
        private final String name;
        SimpleArea(String prefix) {
            this.key = prefix+"Key";
            this.name = prefix+"Name";
        }

        @Override
        public String getISO() {
            return key;
        }

        @Override
        public String getName() {
            return name;
        }
    }

}
