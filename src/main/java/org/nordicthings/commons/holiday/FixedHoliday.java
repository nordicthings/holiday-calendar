package org.nordicthings.commons.holiday;

import java.time.LocalDate;

/**
 * Generic holiday which can be used for holidays at fixed dates like Christmas.
 */
public class FixedHoliday extends AbstractHoliday {

    private final int day;
    private final int month;

    private FixedHoliday(int day, int month, String key, String name, AdministrativeArea... administrativeAreas) {
        super(key, name, administrativeAreas);
        this.day = day;
        this.month = month;
    }

    /**
     * @param day the fixed day of month
     * @param month the fixed month
     * @param name the human-readable name to be used for the instance.
     * @return a new instance with national validity. Key is set with its default value.
     */
    public static FixedHoliday of(int day, int month, String name) {
        return of(day, month, null, name);
    }

    /**
     * @param day the fixed day of month
     * @param month the fixed month
     * @param key the unique key to be used for the instance.
     * @param name the human-readable name to be used for the instance.
     * @return a new instance with national validity.
     */
    public static FixedHoliday of(int day, int month, String key, String name) {
        if (name == null) {
            throw new IllegalStateException("The name of a FixedHoliday must not be null");
        }
        return new FixedHoliday(day, month, key, name);
    }

    /**
     *
     * @param day the fixed day of month
     * @param month the fixed month
     * @param name the human-readable name to be used for the instance.
     * @param administrativeAreas the administrative areas for which the holiday is valid.
     * @return a new instance in which the key is set to default value.
     */
    public static FixedHoliday ofSpecificAreas(int day, int month, String name, AdministrativeArea... administrativeAreas) {
        return ofSpecificAreas(day, month, null, name, administrativeAreas);
    }

    /**
     *
     * @param day the fixed day of month
     * @param month the fixed month
     * @param key the unique key to be used for the instance.
     * @param name the human-readable name to be used for the instance.
     * @param administrativeAreas the administrative areas for which the holiday is valid.
     * @return a new instance with the given values.
     */
    public static FixedHoliday ofSpecificAreas(int day, int month, String key, String name, AdministrativeArea... administrativeAreas) {
        if (name == null) {
            throw new IllegalStateException("The name of a FixedHoliday must not be null");
        }
        return new FixedHoliday(day, month, key, name, administrativeAreas);
    }

    @Override
    public LocalDate getDate(int year) {
        return LocalDate.of(year, month, day);
    }

}
