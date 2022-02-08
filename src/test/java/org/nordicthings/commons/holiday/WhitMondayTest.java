package org.nordicthings.commons.holiday;

import org.junit.jupiter.api.Test;
import org.nordicthings.commons.holiday.AdministrativeArea;
import org.nordicthings.commons.holiday.HolidayException;
import org.nordicthings.commons.holiday.WhitMonday;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class WhitMondayTest {

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
        WhitMonday sut = WhitMonday.standard();
        assertThat(sut.getKey()).isEqualTo(WhitMonday.class.getCanonicalName());
        assertThat(sut.getName()).isEqualTo(WhitMonday.class.getSimpleName());
    }

    @Test
    public void testKeyAndNameProvided() {
        WhitMonday sut = WhitMonday.of(ANY_KEY,ANY_NAME);
        assertThat(sut.getKey()).isEqualTo(ANY_KEY);
        assertThat(sut.getName()).isEqualTo(ANY_NAME);
    }

    @Test
    public void testOnlyKeyProvided() {
        WhitMonday sut = WhitMonday.of(ANY_KEY,null);
        assertThat(sut.getKey()).isEqualTo(ANY_KEY);
        assertThat(sut.getName()).isEqualTo(WhitMonday.class.getSimpleName());
    }

    @Test
    public void testOnlyNameProvided() {
        WhitMonday sut = WhitMonday.of(null,ANY_NAME);
        assertThat(sut.getKey()).isEqualTo(WhitMonday.class.getCanonicalName());
        assertThat(sut.getName()).isEqualTo(ANY_NAME);
    }

    @Test
    public void testOnlyAreasProvided() {
        WhitMonday sut = WhitMonday.ofSpecificAreas(ANY_AREA);
        assertThat(sut.getAdministrativeAreas()).containsExactly(ANY_AREA);
        assertThat(sut.getKey()).isEqualTo(WhitMonday.class.getCanonicalName());
        assertThat(sut.getName()).isEqualTo(WhitMonday.class.getSimpleName());
    }

    @Test
    public void testAllProvided() {
        WhitMonday sut = WhitMonday.ofSpecificAreas(ANY_KEY, ANY_NAME, ANY_AREA);
        assertThat(sut.getKey()).isEqualTo(ANY_KEY);
        assertThat(sut.getName()).isEqualTo(ANY_NAME);
        assertThat(sut.getAdministrativeAreas()).containsExactly(ANY_AREA);
    }

    @Test
    public void happyPath() {
        WhitMonday sut = WhitMonday.standard();
        assertThat(sut.getDate(2021)).isEqualTo(LocalDate.of(2021, 5, 24));
    }

    @Test
    public void noValidYear() {
        WhitMonday sut = WhitMonday.standard();
        assertThrows(HolidayException.class, () -> sut.getDate(sut.getMinimalYear() - 1));
        assertThrows(HolidayException.class, () -> sut.getDate(sut.getMaximalYear() + 1));
    }

    @Test
    public void succeedsOnMinAndMaxYear() {
        WhitMonday sut = WhitMonday.standard();
        assertThat(sut.getDate(sut.getMinimalYear()).getYear()).isEqualTo(sut.getMinimalYear());
        assertThat(sut.getDate(sut.getMaximalYear()).getYear()).isEqualTo(sut.getMaximalYear());
    }

    @Test
    public void isNationWideHoliday() {
        WhitMonday sut = WhitMonday.standard();
        assertThat(sut.isNationWide()).isTrue();
        assertThat(sut.getAdministrativeAreas()).isEmpty();
    }

}
