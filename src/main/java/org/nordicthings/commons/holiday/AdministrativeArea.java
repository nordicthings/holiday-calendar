package org.nordicthings.commons.holiday;

/**
 * An administrative Area in a country.
 */
public interface AdministrativeArea {

    /**
     * @return the ISO-3166-code of the area.
     */
    String getISO();

    /**
     * @return a human readable name of the area.
     */
    String getName();

    /**
     * @param holiday holiday to be tested
     * @return true: the given holiday is a public holiday in this area
     */
    default boolean hasHoliday(Holiday holiday) {
        return holiday.isNationWide() || holiday.getAdministrativeAreas().contains(this);
    }
}
