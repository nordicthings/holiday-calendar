package org.nordicthings.commons.holiday;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Abstract implementation of the {@link Holiday}-interface.
 */
public abstract class AbstractHoliday implements Holiday {

    private String key = this.getClass().getCanonicalName();
    private String name = this.getClass().getSimpleName();
    private final Set<AdministrativeArea> administrativeAreas = new HashSet<>();

    /**
     * @param key the unique key of the holiday. If {@code null} is passed, the canonical classname is used as key.
     * @param name the human-readable name of the holiday. If {@code null} is passed, the simple classname is used as key.
     * @param administrativeAreas the administrative areas for which the holiday is public (if any)
     */
    protected AbstractHoliday(String key, String name, AdministrativeArea... administrativeAreas) {
        if (key != null) {
            this.key = key;
        }
        if (name != null) {
            this.name = name;
        }
        if (administrativeAreas != null) {
            this.administrativeAreas.addAll(Arrays.asList(administrativeAreas));
        }
    }

    @Override
    public abstract LocalDate getDate(int year);

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public String getName() {
        return this.name;
    }

    /**
     * {@inheritDoc}
     * The default-implementation returns {@code true} if no administrative area was provided for the holiday.
     */
    @Override
    public boolean isNationWide() {
        return getAdministrativeAreas().isEmpty();
    }

    /**
     * {@inheritDoc}
     * The default-implementation returns {@code true} if at least one of the given areas matches the administrative
     * areas provided for the holiday.
     */
    @Override
    public boolean isHolidayIn(AdministrativeArea... areas) {
        return isNationWide() || containsAny(administrativeAreas, areas);
    }

    @Override
    public Set<AdministrativeArea> getAdministrativeAreas() {
        return administrativeAreas;
    }

    /**
     * {@inheritDoc}
     * The default-implementation returns {@code true} if the given year lies on or between the maximal and the
     * minimal year.
     */
    @Override
    public boolean isValidYear(int year) {
        return year >= getMinimalYear() && year <= getMaximalYear();
    }

    /**
     * {@inheritDoc}
     * The default-implementation returns {@link LocalDate#MAX}.
     */
    @Override
    public int getMaximalYear() {
        return LocalDate.MAX.getYear();
    }

    /**
     * {@inheritDoc}
     * The default-implementation returns {@link LocalDate#MIN}.
     */
    @Override
    public int getMinimalYear() {
        return LocalDate.MIN.getYear();
    }

    private boolean containsAny(Set<AdministrativeArea> set, AdministrativeArea... areas) {
        Set<AdministrativeArea> checkedSet = new HashSet<>(set);
        checkedSet.retainAll(Arrays.asList(areas));
        return !checkedSet.isEmpty();
    }

}
