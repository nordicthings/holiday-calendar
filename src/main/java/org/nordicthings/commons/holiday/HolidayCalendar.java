package org.nordicthings.commons.holiday;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;

/**
 * A public holiday calendar holds public holidays for a specified period of time.
 */
public class HolidayCalendar {

    private LocalDate periodStart;
    private LocalDate periodEnd;
    private final Map<LocalDate, Holiday> holidays = new HashMap<>();

    private HolidayCalendar() {}

    /**
     * @return a Builder to create a new instance HolidayCalendar
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * @return the beginning of the period of time for which the calendar was created.
     */
    public LocalDate getPeriodStart() {
        return periodStart;
    }

    /**
     * @return the end of the period of time for which the calendar was created.
     */
    public LocalDate getPeriodEnd() {
        return periodEnd;
    }

    /**
     * @return true: there are no holidays configured for this instance.
     */
    public boolean isEmpty() {
        return holidays.isEmpty();
    }

    /**
     * @param holiday the holiday to be checked.
     * @return true: The given holiday falls within the time period covered by the calendar.
     */
    public boolean isInPeriod(Holiday holiday) {
        if (periodStart.getYear() == periodEnd.getYear()) {
            return !holiday.getDate(periodStart.getYear()).isBefore(periodStart) & !holiday.getDate(periodEnd.getYear()).isAfter(periodEnd);
        } else {
            return !holiday.getDate(periodStart.getYear()).isBefore(periodStart) || !holiday.getDate(periodEnd.getYear()).isAfter(periodEnd);
        }
    }

    /**
     * @param holiday a holiday
     * @return the concrete day on which the given holiday takes place within the time period covered by the calendar.
     */
    public LocalDate getConcreteDate(Holiday holiday) {
        if (holiday.getDate(periodStart.getYear()).isAfter(periodStart)) {
            return holiday.getDate(periodStart.getYear());
        }
        return holiday.getDate(periodEnd.getYear());
    }

    /**
     * @param holiday the holiday to be added to the calendar.
     */
    public void addHoliday(Holiday holiday) {
        if (isInPeriod(holiday)) {
            holidays.put(getConcreteDate(holiday), holiday);
        }
    }

    /**
     * @param date  a date within the time period covered by the calendar.
     * @param areas optional administrative areas for which the given date shall be checked.
     * @return true: the given date is a public holiday in one or more of the given areas. If no areas are specified,
     * the method only returns true if the holiday is not a regional holiday.
     */
    public boolean isHoliday(LocalDate date, AdministrativeArea... areas) {
        if (date == null) {
            throw new IllegalArgumentException("Unable to determine holiday for a date of null");
        }
        return holidays.containsKey(date) && holidays.get(date).isHolidayIn(areas);
    }

    /**
     * @param date  a date within the time period covered by the calendar.
     * @param areas optional administrative areas for which the given date shall be checked.
     * @return true: the given date is a working day in all given areas. If no areas are specified,
     * the method only returns true if the holiday is not a regional holiday.
     */
    public boolean isWorkingDay(LocalDate date, AdministrativeArea... areas) {
        if (date == null) {
            throw new IllegalArgumentException("Unable to determine working day for a date of null");
        }
        return !isWeekend(date) && !isHoliday(date, areas);
    }

    /**
     * @param date  a date within the time period covered by the calendar.
     * @return true: the given date is a saturday or a sunday.
     */
    public static boolean isWeekend(LocalDate date) {
        if (date == null) {
            throw new IllegalArgumentException("Unable to determine weekend for a date of null");
        }
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        return dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY;
    }

    /**
     * @param date  a date within the time period covered by the calendar.
     * @param areas optional administrative areas for which the next working day shall be calculated.
     * @return the next working day on or after the given date within the time period covered by the calendar.
     */
    public Optional<LocalDate> getNextWorkingDay(LocalDate date, AdministrativeArea... areas) {
        if (date.isBefore(periodStart)) {
            return Optional.empty();
        }
        LocalDate testedDate = date;
        while (!testedDate.isAfter(periodEnd)) {
            if (isWorkingDay(testedDate, areas)) {
                return Optional.of(testedDate);
            }
            testedDate = testedDate.plusDays(1);
        }
        return Optional.empty();
    }

    /**
     * @return all concrete holidays within the time period covered by the calendar.
     */
    public Map<LocalDate, Holiday> getHolidays() {
        return holidays;
    }

    public static class Builder {

        private final HolidayCalendar holidayCalendar;
        private HolidayCalendarConfigurer configurer;

        private Builder() {
            this(new HolidayCalendar());
        }

        private Builder(HolidayCalendar holidayCalendar) {
            this.holidayCalendar = holidayCalendar;
        }

        /**
         * @param start the beginning of the period of time for which the calendar will be created.
         * @return the Builder instance
         */
        public Builder periodStart(LocalDate start) {
            holidayCalendar.periodStart = start;
            return this;
        }

        /**
         * @param end the end of the period of time for which the calendar will be created.
         * @return the Builder instance
         */
        public Builder periodEnd(LocalDate end) {
            holidayCalendar.periodEnd = end;
            return this;
        }

        /**
         * Calculates a time period of January, 1 to December, 31 of the given year for which the calendar will be created.
         * @param year the year for which the calendar will be created.
         * @return the Builder instance
         */
        public Builder forYear(int year) {
            this.periodStart(LocalDate.of(year, 1, 1));
            this.periodEnd(LocalDate.of(year, 12, 31));
            return this;
        }

        /**
         * Calculates a period of one year beginning on the given date for which the calendar will be created.
         * @param begin the beginning of the period of time for which the calendar will be created.
         * @return the Builder instance
         */
        public Builder forRelativeYear(LocalDate begin) {
            this.periodStart(begin);
            this.periodEnd(begin.plusYears(1).minusDays(1));
            return this;
        }

        /**
         * @param configurer the configurer which configures the holidays considered by the new instance of HolidayCalendar
         * @return the Builder instance
         */
        public Builder configurer(HolidayCalendarConfigurer configurer) {
            this.configurer = configurer;
            return this;
        }

        /**
         * @return the new instance of HolidayCalendar.
         */
        public HolidayCalendar build() {
            if (holidayCalendar.periodStart == null) {
                throw new IllegalStateException("The beginning of the period in a holiday calendar must not be null");
            }
            if (holidayCalendar.periodEnd == null) {
                throw new IllegalStateException("The end of the period in a holiday calendar must not be null");
            }
            if (holidayCalendar.periodEnd.isBefore(holidayCalendar.periodStart)) {
                throw new IllegalStateException("The end of the period in a holiday calendar must not be earlier than the beginning");
            }
            if (holidayCalendar.periodEnd.isAfter(holidayCalendar.periodStart.plusYears(1))) {
                throw new IllegalStateException("The length of the period in a holiday calendar has to be maximal 1 year");
            }
            if (configurer != null) {
                configurer.configure(holidayCalendar);
            }
            return holidayCalendar;
        }

    }

}
