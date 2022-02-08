package org.nordicthings.commons.holiday;

import java.time.LocalDate;

/**
 * Ascension of Christ is 39 days after Easter Sunday.
 */
public class AscensionOfChrist extends AbstractHoliday {

    private final Holiday easterSunday = EasterSunday.ofSpecificAreas();

    private AscensionOfChrist(String key, String name, AdministrativeArea... administrativeAreas) {
        super(key, name, administrativeAreas);
    }

    /**
     * @return a new instance with national validity. Key and name are set with default values.
     */
    public static AscensionOfChrist standard() {
        return new AscensionOfChrist(null, null);
    }

    /**
     * @param name the human-readable name to be used for the instance.
     * @return a new instance with national validity. Key is set with its default value.
     */
    public static AscensionOfChrist of(String name) {
        return new AscensionOfChrist(null, name);
    }

    /**
     * @param key the unique key to be used for the instance.
     * @param name the human-readable name to be used for the instance.
     * @return a new instance with national validity.
     */
    public static AscensionOfChrist of(String key, String name) {
        return new AscensionOfChrist(key, name);
    }

    /**
     * @param administrativeAreas the administrative areas for which the holiday is valid.
     * @return a new instance in which key and name are set with default values.
     */
    public static AscensionOfChrist ofSpecificAreas(AdministrativeArea... administrativeAreas) {
        return new AscensionOfChrist(null, null, administrativeAreas);
    }

    /**
     * @param key the unique key to be used for the instance.
     * @param name the human-readable name to be used for the instance.
     * @param administrativeAreas the administrative areas for which the holiday is valid.
     * @return a new instance with the given values.
     */
    public static AscensionOfChrist ofSpecificAreas(String key, String name, AdministrativeArea... administrativeAreas) {
        return new AscensionOfChrist(key, name, administrativeAreas);
    }

    /**
     * @param name the human-readable name to be used for the instance.
     * @param administrativeAreas the administrative areas for which the holiday is valid.
     * @return a new instance in which the name is set with default values.
     */
    public static AscensionOfChrist ofSpecificAreas(String name, AdministrativeArea... administrativeAreas) {
        return new AscensionOfChrist(null, name, administrativeAreas);
    }

    @Override
    public LocalDate getDate(int year) {
        try {
            return easterSunday.getDate(year).plusDays(39);
        } catch (HolidayException he) {
            throw new HolidayException("The calculation of AscensionOfChrist relies on EasterSunday and therefore can only be determined for the years between " + getMinimalYear() + " and " + getMaximalYear());
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
