package org.nordicthings.commons.holiday;

import java.time.LocalDate;
import java.util.Set;

/**
 * An annually recurring date according to a given rhythm. A holiday may be public nationwide or only in certain
 * administrative areas and a holiday may be valid only in a specific interval of years.
 */
public interface Holiday {

    /**
     * @param year the year of interest
     * @return the concrete date of the holiday in the given year
     */
    LocalDate getDate(int year);

    /**
     * @return a unique key for the specific holiday.
     */
    String getKey();

    /**
     * @return a human-readable name for the holiday.
     */
    String getName();

    /**
     * @return true: the holiday is public across the nation.
     */
    boolean isNationWide();

    /**
     * @param areas some administrative areas
     * @return true: the holiday is public in <strong>all</strong> given areas.
     */
    boolean isHolidayIn(AdministrativeArea... areas);

    /**
     * @return the holiday is public in these administrative areas.
     */
    Set<AdministrativeArea> getAdministrativeAreas();

    /**
     *
     * @param year the year of interest
     * @return true: the holiday can be calculated for this year.
     */
    boolean isValidYear(int year);

    /**
     * @return the first year for which the holiday can be calculated.
     */
    int getMinimalYear();

    /**
     * @return the last year for which the holiday can be calculated.
     */
    int getMaximalYear();

}
