package org.nordicthings.commons.holiday;

import java.time.LocalDate;

/**
 * The Easter date is the first Sunday after the first full moon in spring. It can be calculated by the Gauss's easter algorithm.
 * This implementation uses the algorithm of Hermann Kinkelin (added Gauss's easter algorithm).
 * See https://de.wikipedia.org/wiki/Gau%C3%9Fsche_Osterformel
 */
public class EasterSunday extends AbstractHoliday {

    private EasterSunday(String key, String name, AdministrativeArea... administrativeAreas) {
        super(key, name, administrativeAreas);
    }

    /**
     * @return a new instance with national validity. Key and name are set with default values.
     */
    public static EasterSunday standard() {
        return new EasterSunday(null, null);
    }

    /**
     * @param key the unique key to be used for the instance.
     * @param name the human-readable name to be used for the instance.
     * @return a new instance with national validity.
     */
    public static EasterSunday of(String key, String name) {
        return new EasterSunday(key, name);
    }

    /**
     * @param name the human-readable name to be used for the instance.
     * @return a new instance with national validity. Key is set with its default value.
     */
    public static EasterSunday of(String name) {
        return new EasterSunday(null, name);
    }

    /**
     * @param administrativeAreas the administrative areas for which the holiday is valid.
     * @return a new instance in which key and name are set with default values.
     */
    public static EasterSunday ofSpecificAreas(AdministrativeArea... administrativeAreas) {
        return new EasterSunday(null, null, administrativeAreas);
    }

    /**
     * @param key the unique key to be used for the instance.
     * @param name the human-readable name to be used for the instance.
     * @param administrativeAreas the administrative areas for which the holiday is valid.
     * @return a new instance with the given values.
     */
    public static EasterSunday ofSpecificAreas(String key, String name, AdministrativeArea... administrativeAreas) {
        return new EasterSunday(key, name, administrativeAreas);
    }

    /**
     * @param name the human-readable name to be used for the instance.
     * @param administrativeAreas the administrative areas for which the holiday is valid.
     * @return a new instance in which the name is set with default values.
     */
    public static EasterSunday ofSpecificAreas(String name, AdministrativeArea... administrativeAreas) {
        return new EasterSunday(null, name, administrativeAreas);
    }

    /**
     * @return The implemented algorithm is based on the Gregorian calendar which starts in 1583.
     */
    @Override
    public int getMinimalYear() {
        return 1583;
    }

    /**
     * @return The maximum year for which the algorithm delivers valid values is 8202.
     */
    @Override
    public int getMaximalYear() {
        return 8202;
    }

    @Override
    public LocalDate getDate(int year) {
        if (!isValidYear(year)) {
            throw new HolidayException("EasterSunday can only be determined for the years between " + getMinimalYear() + " and " + getMaximalYear());
        }

        int k = year / 100;  // Säkularzahl
        int m = 15 + (3 * k + 3) / 4 - (8 * k + 13) / 25; // säkulare Mondschaltung
        int s = 2 - (3 * k + 3) / 4; // säkulare Sonnenschaltung
        int a = year % 19; // Mondparameter
        int d = (19 * a + m) % 30; // Keim für den ersten Vollmond im Frühling
        int r = (d + a / 11) / 29; // Kalendarische Korrekturgröße
        int og = 21 + d -r; // Ostergrenze
        int sz = 7 - (year + year / 4 + s) % 7; // erster Sonntag im März
        int oe = 7 - (og - sz) % 7; // Entfernung des Ostersonntags von der Ostergrenze
        int os = og + oe; // Ostersonntag als Märzdatum (32. März = 1. April usw.)

        int day;
        int month;

        // Umrechnung in echten Monat
        if (os > 31) {
            day = os - 31;
            month = 4;
        } else {
            day = os;
            month = 3;
        }
        return LocalDate.of(year, month, day);
    }

}
