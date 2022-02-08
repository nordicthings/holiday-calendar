package org.nordicthings.commons.holiday;

import java.time.LocalDate;

/**
 * Corpus Christi is 60 days after Easter Sunday.
 */
public class CorpusChristi extends AbstractHoliday {

    private final Holiday easterSunday = EasterSunday.ofSpecificAreas();

    private CorpusChristi(String key, String name, AdministrativeArea... administrativeAreas) {
        super(key, name, administrativeAreas);
    }

    /**
     * @return a new instance with national validity. Key and name are set with default values.
     */
    public static CorpusChristi standard() {
        return new CorpusChristi(null, null);
    }

    /**
     * @param key the unique key to be used for the instance.
     * @param name the human-readable name to be used for the instance.
     * @return a new instance with national validity.
     */
    public static CorpusChristi of(String key, String name) {
        return new CorpusChristi(key, name);
    }

    /**
     * @param name the human-readable name to be used for the instance.
     * @return a new instance with national validity. Key is set with its default value.
     */
    public static CorpusChristi of(String name) {
        return new CorpusChristi(null, name);
    }

    /**
     * @param administrativeAreas the administrative areas for which the holiday is valid.
     * @return a new instance in which key and name are set with default values.
     */
    public static CorpusChristi ofSpecificAreas(AdministrativeArea... administrativeAreas) {
        return new CorpusChristi(null, null, administrativeAreas);
    }

    /**
     * @param key the unique key to be used for the instance.
     * @param name the human-readable name to be used for the instance.
     * @param administrativeAreas the administrative areas for which the holiday is valid.
     * @return a new instance with the given values.
     */
    public static CorpusChristi ofSpecificAreas(String key, String name, AdministrativeArea... administrativeAreas) {
        return new CorpusChristi(key, name, administrativeAreas);
    }

    /**
     * @param name the human-readable name to be used for the instance.
     * @param administrativeAreas the administrative areas for which the holiday is valid.
     * @return a new instance in which the name is set with default values.
     */
    public static CorpusChristi ofSpecificAreas(String name, AdministrativeArea... administrativeAreas) {
        return new CorpusChristi(null, name, administrativeAreas);
    }

    @Override
    public LocalDate getDate(int year) {
        try {
            return easterSunday.getDate(year).plusDays(60);
        } catch (HolidayException he) {
            throw new HolidayException("Corpus Christi cannot be determined for the years between " + getMinimalYear() + " and " + getMaximalYear());
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
