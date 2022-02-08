package org.nordicthings.commons.holiday;

import java.time.LocalDate;

/**
 * Good Friday is the friday before Easter sunday.
 */
public class GoodFriday extends AbstractHoliday {

    private final Holiday easterSunday = EasterSunday.standard();

    private GoodFriday(String key, String name, AdministrativeArea... administrativeAreas) {
        super(key, name, administrativeAreas);
    }

    /**
     * @return a new instance with national validity. Key and name are set with default values.
     */
    public static GoodFriday standard() {
        return new GoodFriday(null, null);
    }

    /**
     * @param key the unique key to be used for the instance.
     * @param name the human-readable name to be used for the instance.
     * @return a new instance with national validity.
     */
    public static GoodFriday of(String key, String name) {
        return new GoodFriday(key, name);
    }

    /**
     * @param name the human-readable name to be used for the instance.
     * @return a new instance with national validity. Key is set with its default value.
     */
    public static GoodFriday of(String name) {
        return new GoodFriday(null, name);
    }

    /**
     * @param administrativeAreas the administrative areas for which the holiday is valid.
     * @return a new instance in which key and name are set with default values.
     */
    public static GoodFriday ofSpecificAreas(AdministrativeArea... administrativeAreas) {
        return new GoodFriday(null, null, administrativeAreas);
    }

    /**
     * @param key the unique key to be used for the instance.
     * @param name the human-readable name to be used for the instance.
     * @param administrativeAreas the administrative areas for which the holiday is valid.
     * @return a new instance with the given values.
     */
    public static GoodFriday ofSpecificAreas(String key, String name, AdministrativeArea... administrativeAreas) {
        return new GoodFriday(key, name, administrativeAreas);
    }

    /**
     * @param name the human-readable name to be used for the instance.
     * @param administrativeAreas the administrative areas for which the holiday is valid.
     * @return a new instance in which the name is set with default values.
     */
    public static GoodFriday ofSpecificAreas(String name, AdministrativeArea... administrativeAreas) {
        return new GoodFriday(null, name, administrativeAreas);
    }

    @Override
    public LocalDate getDate(int year) {
        try {
            return easterSunday.getDate(year).minusDays(2);
        } catch (HolidayException he) {
            throw new HolidayException("The calculation of GoodFriday relies on EasterSunday and therefore can only be determined for the years between " + getMinimalYear() + " and " + getMaximalYear());
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
