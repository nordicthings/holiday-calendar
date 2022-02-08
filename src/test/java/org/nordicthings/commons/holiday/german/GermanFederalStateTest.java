package org.nordicthings.commons.holiday.german;

import org.junit.jupiter.api.Test;
import org.nordicthings.commons.holiday.german.GermanFederalState;

import static org.assertj.core.api.Assertions.assertThat;

public class GermanFederalStateTest {

    @Test
    public void shouldFindAppropriateState() {
        assertThat(GermanFederalState.getByISO(GermanFederalState.HAMBURG.getISO())).isSameAs(GermanFederalState.HAMBURG);
    }

    @Test
    public void shouldReturnNullForUnknownKey() {
        assertThat(GermanFederalState.getByISO("unknown")).isNull();
    }

    @Test
    public void shouldReturnNullForNullKey() {
        assertThat(GermanFederalState.getByISO(null)).isNull();
    }

}
