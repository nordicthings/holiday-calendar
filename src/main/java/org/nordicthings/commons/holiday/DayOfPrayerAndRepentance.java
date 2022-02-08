package org.nordicthings.commons.holiday;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.MonthDay;

/**
 * The Day of Prayer and Receptance is a german holiday and is at Wednesday before November, 23.
 */
public class DayOfPrayerAndRepentance extends AbstractHoliday {

    private DayOfPrayerAndRepentance(String key, String name, AdministrativeArea... administrativeAreas) {
        super(key, name, administrativeAreas);
    }

    /**
     * @return a new instance with national validity. Key and name are set with default values.
     */
    public static DayOfPrayerAndRepentance standard() {
        return new DayOfPrayerAndRepentance(null, null);
    }

    /**
     * @param name the human-readable name to be used for the instance.
     * @return a new instance with national validity. Key is set with its default value.
     */
    public static DayOfPrayerAndRepentance of(String name) {
        return new DayOfPrayerAndRepentance(null, name);
    }

    /**
     * @param key the unique key to be used for the instance.
     * @param name the human-readable name to be used for the instance.
     * @return a new instance with national validity.
     */
    public static DayOfPrayerAndRepentance of(String key, String name) {
        return new DayOfPrayerAndRepentance(key, name);
    }

    /**
     * @param administrativeAreas the administrative areas for which the holiday is valid.
     * @return a new instance in which key and name are set with default values.
     */
    public static DayOfPrayerAndRepentance ofSpecificAreas(AdministrativeArea... administrativeAreas) {
        return new DayOfPrayerAndRepentance(null, null, administrativeAreas);
    }

    /**
     * @param key the unique key to be used for the instance.
     * @param name the human-readable name to be used for the instance.
     * @param administrativeAreas the administrative areas for which the holiday is valid.
     * @return a new instance with the given values.
     */
    public static DayOfPrayerAndRepentance ofSpecificAreas(String key, String name, AdministrativeArea... administrativeAreas) {
        return new DayOfPrayerAndRepentance(key, name, administrativeAreas);
    }

    /**
     * @param name the human-readable name to be used for the instance.
     * @param administrativeAreas the administrative areas for which the holiday is valid.
     * @return a new instance in which the name is set with default values.
     */
    public static DayOfPrayerAndRepentance ofSpecificAreas(String name, AdministrativeArea... administrativeAreas) {
        return new DayOfPrayerAndRepentance(null, name, administrativeAreas);
    }

    @Override
    public LocalDate getDate(int year) {
        if (year < getMinimalYear() || year > getMaximalYear()) {
            throw new HolidayException("The calculation of DayOfPrayerAndRepentance can only be determined for the years between " + getMinimalYear() + " and " + getMaximalYear());
        }
        LocalDate base = MonthDay.of(11, 22).atYear(year);
        LocalDate day = base.with(DayOfWeek.WEDNESDAY);
        return day.isAfter(base) ? day.minusDays(7) : day;
    }

    @Override
    public int getMaximalYear() {
        return LocalDate.MAX.getYear();
    }

    /**
     * @return the first year of the current legal regulation in germany, which is 1995.
     */
    @Override
    public int getMinimalYear() {
        return 1995;
    }
}
