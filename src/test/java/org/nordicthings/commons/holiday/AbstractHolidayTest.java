package org.nordicthings.commons.holiday;

import org.junit.jupiter.api.Test;
import org.nordicthings.commons.holiday.AbstractHoliday;
import org.nordicthings.commons.holiday.AdministrativeArea;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

public class AbstractHolidayTest {

    private static final String ANY_NAME = "anyName";
    private static final int ANY_YEAR = 2021;

    @Test
    public void testDefaults() {
        AbstractHoliday anyHoliday = new DummyHoliday();
        assertThat(anyHoliday.getAdministrativeAreas()).isEmpty();
        assertThat(anyHoliday.isNationWide()).isTrue();
        assertThat(anyHoliday.isValidYear(ANY_YEAR)).isTrue();
        assertThat(anyHoliday.getMinimalYear()).isEqualTo(LocalDate.MIN.getYear());
        assertThat(anyHoliday.getMaximalYear()).isEqualTo(LocalDate.MAX.getYear());
    }

    @Test
    public void testNameProvided() {
        assertThat(new DummyHoliday().getKey()).isEqualTo(DummyHoliday.class.getCanonicalName());
        assertThat(new DummyHoliday(ANY_NAME).getName()).isEqualTo(ANY_NAME);
        assertThat(new DummyHoliday().getName()).isEqualTo(DummyHoliday.class.getSimpleName());
    }

    @Test
    public void testDefaultNameProviding() {
        assertThat(new DummyHoliday().getName()).isEqualTo(DummyHoliday.class.getSimpleName());
    }

    @Test
    public void testIsHolidayIn() {
        AdministrativeArea anyArea = new DummyArea();
        AdministrativeArea anotherArea = new DummyArea();
        assertThat(new DummyHoliday(anyArea).isHolidayIn(anyArea)).isTrue();
        assertThat(new DummyHoliday(anyArea).isHolidayIn(anotherArea)).isFalse();
        assertThat(new DummyHoliday(anyArea).isHolidayIn()).isFalse();
        assertThat(new DummyHoliday().isHolidayIn(anyArea)).isTrue();
    }

    private static class DummyHoliday extends AbstractHoliday {

        private DummyHoliday() {
            super(null, null, (AdministrativeArea[]) null);
        }

        private DummyHoliday(String name) {
            super(null, name, (AdministrativeArea[]) null);
        }

        private DummyHoliday(AdministrativeArea... areas) {
            super(null, null, areas);
        }

        @Override
        public LocalDate getDate(int year) {
            return null;
        }
    }

    private static class DummyArea implements AdministrativeArea {
        @Override
        public String getISO() {
            return "anyAreaKey";
        }

        @Override
        public String getName() {
            return "anyAreaName";
        }
    }

}
