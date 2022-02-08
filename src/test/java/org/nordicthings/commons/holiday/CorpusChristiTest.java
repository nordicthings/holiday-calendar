package org.nordicthings.commons.holiday;

import org.junit.jupiter.api.Test;
import org.nordicthings.commons.holiday.AdministrativeArea;
import org.nordicthings.commons.holiday.CorpusChristi;
import org.nordicthings.commons.holiday.HolidayException;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CorpusChristiTest {

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
        CorpusChristi sut = CorpusChristi.standard();
        assertThat(sut.getKey()).isEqualTo(CorpusChristi.class.getCanonicalName());
        assertThat(sut.getName()).isEqualTo(CorpusChristi.class.getSimpleName());
    }

    @Test
    public void testKeyAndNameProvided() {
        CorpusChristi sut = CorpusChristi.of(ANY_KEY,ANY_NAME);
        assertThat(sut.getKey()).isEqualTo(ANY_KEY);
        assertThat(sut.getName()).isEqualTo(ANY_NAME);
    }

    @Test
    public void testOnlyKeyProvided() {
        CorpusChristi sut = CorpusChristi.of(ANY_KEY,null);
        assertThat(sut.getKey()).isEqualTo(ANY_KEY);
        assertThat(sut.getName()).isEqualTo(CorpusChristi.class.getSimpleName());
    }

    @Test
    public void testOnlyNameProvided() {
        CorpusChristi sut = CorpusChristi.of(null,ANY_NAME);
        assertThat(sut.getKey()).isEqualTo(CorpusChristi.class.getCanonicalName());
        assertThat(sut.getName()).isEqualTo(ANY_NAME);
    }

    @Test
    public void testOnlyAreasProvided() {
        CorpusChristi sut = CorpusChristi.ofSpecificAreas(ANY_AREA);
        assertThat(sut.getAdministrativeAreas()).containsExactly(ANY_AREA);
        assertThat(sut.getKey()).isEqualTo(CorpusChristi.class.getCanonicalName());
        assertThat(sut.getName()).isEqualTo(CorpusChristi.class.getSimpleName());
    }

    @Test
    public void testAllProvided() {
        CorpusChristi sut = CorpusChristi.ofSpecificAreas(ANY_KEY, ANY_NAME, ANY_AREA);
        assertThat(sut.getKey()).isEqualTo(ANY_KEY);
        assertThat(sut.getName()).isEqualTo(ANY_NAME);
        assertThat(sut.getAdministrativeAreas()).containsExactly(ANY_AREA);
    }

    @Test
    public void happyPath() {
        CorpusChristi sut = CorpusChristi.standard();
        assertThat(sut.getDate(2021)).isEqualTo(LocalDate.of(2021, 6, 3));
    }

    @Test
    public void noValidYear() {
        CorpusChristi sut = CorpusChristi.standard();
        assertThrows(HolidayException.class, () -> sut.getDate(sut.getMinimalYear() - 1));
        assertThrows(HolidayException.class, () -> sut.getDate(sut.getMaximalYear() + 1));
    }

    @Test
    public void succeedsOnMinAndMaxYear() {
        CorpusChristi sut = CorpusChristi.standard();
        assertThat(sut.getDate(sut.getMinimalYear()).getYear()).isEqualTo(sut.getMinimalYear());
        assertThat(sut.getDate(sut.getMaximalYear()).getYear()).isEqualTo(sut.getMaximalYear());
    }

}
