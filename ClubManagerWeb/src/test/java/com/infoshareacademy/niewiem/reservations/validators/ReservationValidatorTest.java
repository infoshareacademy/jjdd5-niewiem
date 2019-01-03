package com.infoshareacademy.niewiem.reservations.validators;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

class ReservationValidatorTest {

    private ReservationValidator testObj = new ReservationValidator();
    private ReservationValidator spy = spy(testObj);

    @Test
    @DisplayName("validatePeriodParam: Return False when periodParam is null")
    void testValidatePeriodParam1(){
        // given
        String periodParam = null;
        List<String> errors = new ArrayList<>();

        // when
        boolean result = testObj.validatePeriodParam(periodParam, errors);

        // then
        assertFalse(result);
        assertThat(errors).isEmpty();
    }

    @Test
    @DisplayName("validatePeriodParam: Return False when periodParam is empty")
    void testValidatePeriodParam2(){
        // given
        String periodParam = "";
        List<String> errors = new ArrayList<>();

        // when
        boolean result = testObj.validatePeriodParam(periodParam, errors);

        // then
        assertFalse(result);
        assertThat(errors).isEmpty();
    }

    @Test
    @DisplayName("validatePeriodParam: Return True when validatePeriodExists returns true")
    void testValidatePeriodParam3(){
        // given
        String periodParam = "Not important";
        List<String> errors = new ArrayList<>();
        when(spy.periodExists(periodParam, errors)).thenReturn(true);

        // when
        boolean result = spy.validatePeriodParam(periodParam, errors);

        // then
        assertTrue(result);
    }

    @Test
    @DisplayName("validatePeriodExists: Return True when periodParam is in all caps")
    void testValidatePeriodExists1(){
        // given
        String periodParam = "TODAY";
        List<String> errors = new ArrayList<>();

        // when
        boolean result = testObj.validatePeriodParam(periodParam, errors);

        // then
        assertTrue(result);
        assertThat(errors).isEmpty();
    }

    @Test
    @DisplayName("validatePeriodExists: Return True when periodParam is in small letters")
    void testValidatePeriodExists2(){
        // given
        String periodParam = "today";
        List<String> errors = new ArrayList<>();

        // when
        boolean result = testObj.validatePeriodParam(periodParam, errors);

        // then
        assertTrue(result);
        assertThat(errors).isEmpty();
    }

    @Test
    @DisplayName("validatePeriodExists: Return True when periodParam exists and has dash")
    void testValidatePeriodExists3(){
        // given
        String periodParam = "THIS_MONTH";
        List<String> errors = new ArrayList<>();

        // when
        boolean result = testObj.validatePeriodParam(periodParam, errors);

        // then
        assertTrue(result);
        assertThat(errors).isEmpty();
    }

    @Test
    @DisplayName("validatePeriodExists: Return False when periodParam has whitespace characters")
    void testValidatePeriodExists6(){
        // given
        String periodParam = " TODAY   ";
        List<String> errors = new ArrayList<>();

        // when
        boolean result = testObj.validatePeriodParam(periodParam, errors);

        // then
        assertFalse(result);
        assertThat(errors.get(0)).isEqualTo("Requested period option does not exist.");
    }

    @Test
    @DisplayName("validatePeriodParam: Return False when periodParam does not exist")
    void testValidatePeriodExists7(){
        // given
        String periodParam = "IDONOTEXIST";
        List<String> errors = new ArrayList<>();

        // when
        boolean result = testObj.validatePeriodParam(periodParam, errors);

        // then
        assertFalse(result);
        assertThat(errors.get(0)).isEqualTo("Requested period option does not exist.");
    }

    @Test
    @DisplayName("validateEndIsAfterStartOrSetToMax: Return ldt.MAX value, when end is before start")
    void testValidateEndIsAfterStartOrReturnMax1(){
        // given
        LocalDateTime start = LocalDateTime.of(2018,11,17,13,37);
        LocalDateTime end = LocalDateTime.of(2017,11,17,13,37);
        List<String> errors = new ArrayList<>();

        // when
        LocalDateTime result = testObj.validateEndIsAfterStartOrReturnMax(start, end, errors);

        // then
        assertThat(result).isEqualTo(LocalDateTime.MAX);
        assertThat(errors.get(0))
                .isEqualTo("The end of requested period was before the start. Showing all future results.");
    }

    @Test
    @DisplayName("validateEndIsAfterStartOrSetToMax: Return end value, when end is after start")
    void testValidateEndIsAfterStartOrReturnMax2(){
        // given
        LocalDateTime start = LocalDateTime.of(2018,11,17,13,37);
        LocalDateTime end = LocalDateTime.of(2019,11,17,13,37);
        List<String> errors = new ArrayList<>();

        // when
        LocalDateTime result = testObj.validateEndIsAfterStartOrReturnMax(start, end, errors);

        // then
        assertThat(result).isEqualTo(end);
        assertThat(errors).isEmpty();
    }

    // todo: VALIDATE THE REST OF METHODS
}