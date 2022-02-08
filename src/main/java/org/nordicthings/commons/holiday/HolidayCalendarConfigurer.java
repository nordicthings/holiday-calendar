package org.nordicthings.commons.holiday;

/**
 * Configures a calendar, typically with regard to the relevant holidays.
 */
public interface HolidayCalendarConfigurer {

    /**
     * @param holidayCalendar the calendar to be configured
     */
    void configure(HolidayCalendar holidayCalendar);
}
