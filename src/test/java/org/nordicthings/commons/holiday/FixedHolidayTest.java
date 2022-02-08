package org.nordicthings.commons.holiday;

import org.junit.jupiter.api.Test;
import org.nordicthings.commons.holiday.AdministrativeArea;
import org.nordicthings.commons.holiday.FixedHoliday;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

public class FixedHolidayTest {

    private static final int ANY_DAY = 1;
    private static final int ANY_MONTH = 1;
    private static final String ANY_KEY = "anyKey";
    private static final String ANY_NAME = "anyName";
    private static final AdministrativeArea ANY_AREA = new AdministrativeArea() {
        @Override
        public String getISO() {
            return "anyAreaKey";
        }

        @Override
        public String getName() {
            return "anyAreaName";
        }
    };

    @Test
    public void testSimpleConstructor() {
        FixedHoliday fixedHoliday = FixedHoliday.of(ANY_DAY, ANY_MONTH, ANY_NAME);
        assertThat(fixedHoliday.getDate(2021)).isEqualTo(LocalDate.of(2021, ANY_MONTH, ANY_DAY));
        assertThat(fixedHoliday.getAdministrativeAreas()).isEmpty();
        assertThat(fixedHoliday.isNationWide()).isTrue();
        assertThat(fixedHoliday.getKey()).isEqualTo(FixedHoliday.class.getCanonicalName());
        assertThat(fixedHoliday.getName()).isEqualTo(ANY_NAME);
    }

    @Test
    public void testConstructorWithKey() {
        FixedHoliday fixedHoliday = FixedHoliday.of(1, 1, ANY_KEY, ANY_NAME);
        assertThat(fixedHoliday.getDate(2021)).isEqualTo(LocalDate.of(2021, ANY_MONTH, ANY_DAY));
        assertThat(fixedHoliday.getAdministrativeAreas()).isEmpty();
        assertThat(fixedHoliday.isNationWide()).isTrue();
        assertThat(fixedHoliday.getKey()).isEqualTo(ANY_KEY);
        assertThat(fixedHoliday.getName()).isEqualTo(ANY_NAME);
    }

    @Test
    public void testConstructorWithAreas() {
        System.out.println(ANY_AREA.getISO());
        FixedHoliday fixedHoliday = FixedHoliday.ofSpecificAreas(ANY_DAY, ANY_MONTH, ANY_NAME, ANY_AREA);
        assertThat(fixedHoliday.getDate(2021)).isEqualTo(LocalDate.of(2021, ANY_MONTH, ANY_DAY));
        assertThat(fixedHoliday.getAdministrativeAreas()).isNotEmpty();
        assertThat(fixedHoliday.isNationWide()).isFalse();
        assertThat(fixedHoliday.getKey()).isEqualTo(FixedHoliday.class.getCanonicalName());
        assertThat(fixedHoliday.getName()).isEqualTo(ANY_NAME);
        assertThat(fixedHoliday.getAdministrativeAreas()).containsExactly(ANY_AREA);
    }

    @Test
    public void testConstructorWithKeyAndAreas() {
        FixedHoliday fixedHoliday = FixedHoliday.ofSpecificAreas(ANY_DAY, ANY_MONTH, ANY_KEY, ANY_NAME, ANY_AREA);
        assertThat(fixedHoliday.getDate(2021)).isEqualTo(LocalDate.of(2021, ANY_MONTH, ANY_DAY));
        assertThat(fixedHoliday.getAdministrativeAreas()).isNotEmpty();
        assertThat(fixedHoliday.isNationWide()).isFalse();
        assertThat(fixedHoliday.getKey()).isEqualTo(ANY_KEY);
        assertThat(fixedHoliday.getName()).isEqualTo(ANY_NAME);
        assertThat(fixedHoliday.getAdministrativeAreas()).containsExactly(ANY_AREA);
    }

}
