package org.nordicthings.commons.holiday;

import java.time.LocalDate;

/**
 * Easter monday is the monday after Easter sunday. Surprise... ;-)
 */
public class EasterMonday extends AbstractHoliday {

    private final Holiday easterSunday = EasterSunday.ofSpecificAreas();

    private EasterMonday(String key, String name, AdministrativeArea... administrativeAreas) {
        super(key, name, administrativeAreas);
    }

    /**
     * @return a new instance with national validity. Key and name are set with default values.
     */
    public static EasterMonday standard() {
        return new EasterMonday(null, null);
    }

    /**
     * @param key the unique key to be used for the instance.
     * @param name the human-readable name to be used for the instance.
     * @return a new instance with national validity.
     */
    public static EasterMonday of(String key, String name) {
        return new EasterMonday(key, name);
    }

    /**
     * @return a new instance with national validity. Key and name are set with default values.
     */
    public static EasterMonday of(String name) {
        return new EasterMonday(null, name);
    }

    /**
     * @param administrativeAreas the administrative areas for which the holiday is valid.
     * @return a new instance in which key and name are set with default values.
     */
    public static EasterMonday ofSpecificAreas(AdministrativeArea... administrativeAreas) {
        return new EasterMonday(null, null, administrativeAreas);
    }

    /**
     * @param key the unique key to be used for the instance.
     * @param name the human-readable name to be used for the instance.
     * @param administrativeAreas the administrative areas for which the holiday is valid.
     * @return a new instance with the given values.
     */
    public static EasterMonday ofSpecificAreas(String key, String name, AdministrativeArea... administrativeAreas) {
        return new EasterMonday(key, name, administrativeAreas);
    }

    /**
     * @param name the human-readable name to be used for the instance.
     * @param administrativeAreas the administrative areas for which the holiday is valid.
     * @return a new instance in which the name is set with default values.
     */
    public static EasterMonday ofSpecificAreas(String name, AdministrativeArea... administrativeAreas) {
        return new EasterMonday(null, name, administrativeAreas);
    }

    @Override
    public LocalDate getDate(int year) {
        try {
            return easterSunday.getDate(year).plusDays(1);
        } catch (HolidayException he) {
            throw new HolidayException("The calculation of EasterMonday relies on EasterSunday and therefore can only be determined for the years between " + getMinimalYear() + " and " + getMaximalYear());
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
