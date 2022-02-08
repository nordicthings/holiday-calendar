package org.nordicthings.commons.holiday;

import org.junit.jupiter.api.Test;
import org.nordicthings.commons.holiday.AdministrativeArea;
import org.nordicthings.commons.holiday.EasterSunday;
import org.nordicthings.commons.holiday.HolidayException;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class EasterSundayTest {

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
    public void testStandard() {
        EasterSunday sut = EasterSunday.standard();
        assertThat(sut.getKey()).isEqualTo(EasterSunday.class.getCanonicalName());
        assertThat(sut.getName()).isEqualTo(EasterSunday.class.getSimpleName());
    }

    @Test
    public void testKeyAndNameProvided() {
        EasterSunday sut = EasterSunday.of(ANY_KEY,ANY_NAME);
        assertThat(sut.getKey()).isEqualTo(ANY_KEY);
        assertThat(sut.getName()).isEqualTo(ANY_NAME);
    }

    @Test
    public void testOnlyKeyProvided() {
        EasterSunday sut = EasterSunday.of(ANY_KEY,null);
        assertThat(sut.getKey()).isEqualTo(ANY_KEY);
        assertThat(sut.getName()).isEqualTo(EasterSunday.class.getSimpleName());
    }

    @Test
    public void testOnlyNameProvided() {
        EasterSunday sut = EasterSunday.of(null,ANY_NAME);
        assertThat(sut.getKey()).isEqualTo(EasterSunday.class.getCanonicalName());
        assertThat(sut.getName()).isEqualTo(ANY_NAME);
    }

    @Test
    public void testOnlyAreasProvided() {
        EasterSunday sut = EasterSunday.ofSpecificAreas(ANY_AREA);
        assertThat(sut.getAdministrativeAreas()).containsExactly(ANY_AREA);
        assertThat(sut.getKey()).isEqualTo(EasterSunday.class.getCanonicalName());
        assertThat(sut.getName()).isEqualTo(EasterSunday.class.getSimpleName());
    }

    @Test
    public void testAllProvided() {
        EasterSunday sut = EasterSunday.ofSpecificAreas(ANY_KEY, ANY_NAME, ANY_AREA);
        assertThat(sut.getKey()).isEqualTo(ANY_KEY);
        assertThat(sut.getName()).isEqualTo(ANY_NAME);
        assertThat(sut.getAdministrativeAreas()).containsExactly(ANY_AREA);
    }

    @Test
    public void dayInMarch() {
        EasterSunday sut = EasterSunday.standard();
        assertThat(sut.getDate(2024)).isEqualTo(LocalDate.of(2024, 3, 31));
    }

    @Test
    public void dayInApril() {
        EasterSunday sut = EasterSunday.standard();
        assertThat(sut.getDate(2021)).isEqualTo(LocalDate.of(2021, 4, 4));
    }

    @Test
    public void specialCasesOfGauss() {
        EasterSunday sut = EasterSunday.standard();
        assertThat(sut.getDate(1908)).isEqualTo(LocalDate.of(1908, 4, 19));
        assertThat(sut.getDate(1954)).isEqualTo(LocalDate.of(1954, 4, 18));
    }

    @Test
    public void noValidYear() {
        EasterSunday sut = EasterSunday.standard();
        assertThrows(HolidayException.class, () -> sut.getDate(sut.getMinimalYear() - 1));
        assertThrows(HolidayException.class, () -> sut.getDate(sut.getMaximalYear() + 1));
    }

    @Test
    public void succeedsOnMinAndMaxYear() {
        EasterSunday sut = EasterSunday.standard();
        assertThat(sut.getDate(sut.getMinimalYear()).getYear()).isEqualTo(sut.getMinimalYear());
        assertThat(sut.getDate(sut.getMaximalYear()).getYear()).isEqualTo(sut.getMaximalYear());
    }

    @Test
    public void isNationWideHoliday() {
        EasterSunday sut = EasterSunday.standard();
        assertThat(sut.isNationWide()).isTrue();
        assertThat(sut.getAdministrativeAreas()).isEmpty();
    }

}
