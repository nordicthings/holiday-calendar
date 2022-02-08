package org.nordicthings.commons.holiday;

import org.junit.jupiter.api.Test;
import org.nordicthings.commons.holiday.AdministrativeArea;
import org.nordicthings.commons.holiday.DayOfPrayerAndRepentance;
import org.nordicthings.commons.holiday.HolidayException;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DayOfPrayerAndRepentanceTest {

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
        DayOfPrayerAndRepentance sut = DayOfPrayerAndRepentance.standard();
        assertThat(sut.getKey()).isEqualTo(DayOfPrayerAndRepentance.class.getCanonicalName());
        assertThat(sut.getName()).isEqualTo(DayOfPrayerAndRepentance.class.getSimpleName());
    }

    @Test
    public void testKeyAndNameProvided() {
        DayOfPrayerAndRepentance sut = DayOfPrayerAndRepentance.of(ANY_KEY,ANY_NAME);
        assertThat(sut.getKey()).isEqualTo(ANY_KEY);
        assertThat(sut.getName()).isEqualTo(ANY_NAME);
    }

    @Test
    public void testOnlyKeyProvided() {
        DayOfPrayerAndRepentance sut = DayOfPrayerAndRepentance.of(ANY_KEY,null);
        assertThat(sut.getKey()).isEqualTo(ANY_KEY);
        assertThat(sut.getName()).isEqualTo(DayOfPrayerAndRepentance.class.getSimpleName());
    }

    @Test
    public void testOnlyNameProvided() {
        DayOfPrayerAndRepentance sut = DayOfPrayerAndRepentance.of(null,ANY_NAME);
        assertThat(sut.getKey()).isEqualTo(DayOfPrayerAndRepentance.class.getCanonicalName());
        assertThat(sut.getName()).isEqualTo(ANY_NAME);
    }

    @Test
    public void testOnlyAreasProvided() {
        DayOfPrayerAndRepentance sut = DayOfPrayerAndRepentance.ofSpecificAreas(ANY_AREA);
        assertThat(sut.getAdministrativeAreas()).containsExactly(ANY_AREA);
        assertThat(sut.getKey()).isEqualTo(DayOfPrayerAndRepentance.class.getCanonicalName());
        assertThat(sut.getName()).isEqualTo(DayOfPrayerAndRepentance.class.getSimpleName());
    }

    @Test
    public void testAllProvided() {
        DayOfPrayerAndRepentance sut = DayOfPrayerAndRepentance.ofSpecificAreas(ANY_KEY, ANY_NAME, ANY_AREA);
        assertThat(sut.getKey()).isEqualTo(ANY_KEY);
        assertThat(sut.getName()).isEqualTo(ANY_NAME);
        assertThat(sut.getAdministrativeAreas()).containsExactly(ANY_AREA);
    }

    @Test
    public void happyPath() {
        DayOfPrayerAndRepentance sut = DayOfPrayerAndRepentance.standard();
        assertThat(sut.getDate(2021)).isEqualTo(LocalDate.of(2021, 11, 17));
        assertThat(sut.getDate(2023)).isEqualTo(LocalDate.of(2023, 11, 22));
    }

    @Test
    public void noValidYear() {
        DayOfPrayerAndRepentance sut = DayOfPrayerAndRepentance.standard();
        assertThrows(HolidayException.class, () -> sut.getDate(sut.getMinimalYear() - 1));
        assertThrows(HolidayException.class, () -> sut.getDate(sut.getMaximalYear() + 1));
    }

    @Test
    public void succeedsOnMinAndMaxYear() {
        DayOfPrayerAndRepentance sut = DayOfPrayerAndRepentance.standard();
        assertThat(sut.getDate(sut.getMinimalYear()).getYear()).isEqualTo(sut.getMinimalYear());
        assertThat(sut.getDate(sut.getMaximalYear()).getYear()).isEqualTo(sut.getMaximalYear());
    }

    @Test
    public void isNationWideHoliday() {
        DayOfPrayerAndRepentance sut = DayOfPrayerAndRepentance.standard();
        assertThat(sut.isNationWide()).isTrue();
        assertThat(sut.getAdministrativeAreas()).isEmpty();
    }

}
