package org.nordicthings.commons.holiday;

import org.junit.jupiter.api.Test;
import org.nordicthings.commons.holiday.AdministrativeArea;
import org.nordicthings.commons.holiday.Holiday;
import org.nordicthings.commons.holiday.HolidayException;
import org.nordicthings.commons.holiday.WhitSunday;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class WhitSundayTest {

    private final Holiday sut = WhitSunday.ofSpecificAreas();

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
        WhitSunday sut = WhitSunday.standard();
        assertThat(sut.getKey()).isEqualTo(WhitSunday.class.getCanonicalName());
        assertThat(sut.getName()).isEqualTo(WhitSunday.class.getSimpleName());
    }

    @Test
    public void testKeyAndNameProvided() {
        WhitSunday sut = WhitSunday.of(ANY_KEY,ANY_NAME);
        assertThat(sut.getKey()).isEqualTo(ANY_KEY);
        assertThat(sut.getName()).isEqualTo(ANY_NAME);
    }

    @Test
    public void testOnlyKeyProvided() {
        WhitSunday sut = WhitSunday.of(ANY_KEY,null);
        assertThat(sut.getKey()).isEqualTo(ANY_KEY);
        assertThat(sut.getName()).isEqualTo(WhitSunday.class.getSimpleName());
    }

    @Test
    public void testOnlyNameProvided() {
        WhitSunday sut = WhitSunday.of(null,ANY_NAME);
        assertThat(sut.getKey()).isEqualTo(WhitSunday.class.getCanonicalName());
        assertThat(sut.getName()).isEqualTo(ANY_NAME);
    }

    @Test
    public void testOnlyAreasProvided() {
        WhitSunday sut = WhitSunday.ofSpecificAreas(ANY_AREA);
        assertThat(sut.getAdministrativeAreas()).containsExactly(ANY_AREA);
        assertThat(sut.getKey()).isEqualTo(WhitSunday.class.getCanonicalName());
        assertThat(sut.getName()).isEqualTo(WhitSunday.class.getSimpleName());
    }

    @Test
    public void testAllProvided() {
        WhitSunday sut = WhitSunday.ofSpecificAreas(ANY_KEY, ANY_NAME, ANY_AREA);
        assertThat(sut.getKey()).isEqualTo(ANY_KEY);
        assertThat(sut.getName()).isEqualTo(ANY_NAME);
        assertThat(sut.getAdministrativeAreas()).containsExactly(ANY_AREA);
    }

    @Test
    public void happyPath() {
        assertThat(sut.getDate(2021)).isEqualTo(LocalDate.of(2021, 5, 23));
    }

    @Test
    public void noValidYear() {
        assertThrows(HolidayException.class, () -> sut.getDate(sut.getMinimalYear() - 1));
        assertThrows(HolidayException.class, () -> sut.getDate(sut.getMaximalYear() + 1));
    }

    @Test
    public void succeedsOnMinAndMaxYear() {
        assertThat(sut.getDate(sut.getMinimalYear()).getYear()).isEqualTo(sut.getMinimalYear());
        assertThat(sut.getDate(sut.getMaximalYear()).getYear()).isEqualTo(sut.getMaximalYear());
    }

    @Test
    public void isNationWideHoliday() {
        assertThat(sut.isNationWide()).isTrue();
        assertThat(sut.getAdministrativeAreas()).isEmpty();
    }

}
