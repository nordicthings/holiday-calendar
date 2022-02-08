package org.nordicthings.commons.holiday;

import java.time.LocalDate;

/**
 * Whit monday is 49 days after Easter sunday.
 */
public class WhitSunday extends AbstractHoliday {

    private final Holiday easterSunday = EasterSunday.ofSpecificAreas();

    private WhitSunday(String key, String name, AdministrativeArea... administrativeAreas) {
        super(key, name, administrativeAreas);
    }

    /**
     * @return a new instance with national validity. Key and name are set with default values.
     */
    public static WhitSunday standard() {
        return new WhitSunday(null, null);
    }

    /**
     * @param name the human-readable name to be used for the instance.
     * @return a new instance with national validity. Key is set with its default value.
     */
    public static WhitSunday of(String name) {
        return new WhitSunday(null, name);
    }

    /**
     * @param key the unique key to be used for the instance.
     * @param name the human-readable name to be used for the instance.
     * @return a new instance with national validity.
     */
    public static WhitSunday of(String key, String name) {
        return new WhitSunday(key, name);
    }

    /**
     * @param administrativeAreas the administrative areas for which the holiday is valid.
     * @return a new instance in which key and name are set with default values.
     */
    public static WhitSunday ofSpecificAreas(AdministrativeArea... administrativeAreas) {
        return new WhitSunday(null, null, administrativeAreas);
    }

    /**
     * @param key the unique key to be used for the instance.
     * @param name the human-readable name to be used for the instance.
     * @param administrativeAreas the administrative areas for which the holiday is valid.
     * @return a new instance with the given values.
     */
    public static WhitSunday ofSpecificAreas(String key, String name, AdministrativeArea... administrativeAreas) {
        return new WhitSunday(key, name, administrativeAreas);
    }

    /**
     * @param name the human-readable name to be used for the instance.
     * @param administrativeAreas the administrative areas for which the holiday is valid.
     * @return a new instance in which the name is set with default values.
     */
    public static WhitSunday ofSpecificAreas(String name, AdministrativeArea... administrativeAreas) {
        return new WhitSunday(null, name, administrativeAreas);
    }

    @Override
    public LocalDate getDate(int year) {
        try {
            return easterSunday.getDate(year).plusDays(49);
        } catch (HolidayException he) {
            throw new HolidayException("The calculation of Whitsunday relies on EasterSunday and therefore can only be determined for the years between " + getMinimalYear() + " and " + getMaximalYear());
        }
    }

    /**
     * @return same maximum year as Easter sunday
     */
    @Override
    public int getMaximalYear() {
        return easterSunday.getMaximalYear();
    }

    /**
     * @return same minimum year as Easter sunday
     */
    @Override
    public int getMinimalYear() {
        return easterSunday.getMinimalYear();
    }
}
