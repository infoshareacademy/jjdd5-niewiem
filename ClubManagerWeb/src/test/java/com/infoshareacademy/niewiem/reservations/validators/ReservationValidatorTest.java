package com.infoshareacademy.niewiem.reservations.validators;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.LocalTime;
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
    void testValidatePeriodParam1() {
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
    void testValidatePeriodParam2() {
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
    void testValidatePeriodParam3() {
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
    @DisplayName("periodExists: Return True when periodParam is in all caps")
    void testPeriodExists1() {
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
    @DisplayName("periodExists: Return True when periodParam is in small letters")
    void testPeriodExists2() {
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
    @DisplayName("periodExists: Return True when periodParam exists and has dash")
    void testPeriodExists3() {
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
    @DisplayName("periodExists: Return False when periodParam has whitespace characters")
    void testPeriodExists4() {
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
    @DisplayName("periodExists: Return False when periodParam does not exist")
    void testPeriodExists5() {
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
    void testValidateEndIsAfterStartOrReturnMax1() {
        // given
        LocalDateTime start = LocalDateTime.of(2018, 11, 17, 13, 37);
        LocalDateTime end = LocalDateTime.of(2017, 11, 17, 13, 37);
        List<String> errors = new ArrayList<>();

        // when
        LocalDateTime result = testObj.validateEndIsAfterStartOrReturnMax(start, end, errors);

        // then
        assertThat(result).isEqualTo(LocalDateTime.MAX);
        assertThat(errors.get(0))
                .isEqualTo("The end of requested period was before the start. Showing results with no end date.");
    }

    @Test
    @DisplayName("validateEndIsAfterStartOrSetToMax: Return end value, when end is after start")
    void testValidateEndIsAfterStartOrReturnMax2() {
        // given
        LocalDateTime start = LocalDateTime.of(2018, 11, 17, 13, 37);
        LocalDateTime end = LocalDateTime.of(2019, 11, 17, 13, 37);
        List<String> errors = new ArrayList<>();

        // when
        LocalDateTime result = testObj.validateEndIsAfterStartOrReturnMax(start, end, errors);

        // then
        assertThat(result).isEqualTo(end);
        assertThat(errors).isEmpty();
    }

    @Test
    @DisplayName("validateTimeParam: Return false when null")
    void testValidateTimeParam1() {
        // given
        String timeParam = null;

        // when
        boolean result = testObj.validateTimeParam(timeParam);

        // then
        assertFalse(result);
    }

    @Test
    @DisplayName("validateTimeParam: Return false when empty")
    void testValidateTimeParam2() {
        // given
        String timeParam = "";

        // when
        boolean result = testObj.validateTimeParam(timeParam);

        // then
        assertFalse(result);
    }

    @Test
    @DisplayName("validateTimeParam: Return false when only whitespace characters")
    void testValidateTimeParam3() {
        // given
        String timeParam = "    ";

        // when
        boolean result = testObj.validateTimeParam(timeParam);

        // then
        assertFalse(result);
    }

    @Test
    @DisplayName("validateTimeParam: Return false when bad string")
    void testValidateTimeParam4() {
        // given
        String timeParam = "BAD-STRING";

        // when
        boolean result = testObj.validateTimeParam(timeParam);

        // then
        assertFalse(result);
    }

    @Test
    @DisplayName("validateTimeParam: Return false when bad character between time")
    void testValidateTimeParam5() {
        // given
        String timeParam = "13.37";

        // when
        boolean result = testObj.validateTimeParam(timeParam);

        // then
        assertFalse(result);
    }

    @Test
    @DisplayName("validateTimeParam: Return true when encoded character")
    void testValidateTimeParam6() {
        // given
        String timeParam = "13%3A37";

        // when
        boolean result = testObj.validateTimeParam(timeParam);

        // then
        assertTrue(result);
    }

    @Test
    @DisplayName("validateTimeParam: Return true when correct time")
    void testValidateTimeParam7() {
        // given
        String timeParam = "13:37";

        // when
        boolean result = testObj.validateTimeParam(timeParam);

        // then
        assertTrue(result);
    }

    @Test
    @DisplayName("returnDecodedTimeParam: Return same character when bad string")
    void testReturnDecodedTimeParam1() {
        // given
        String timeParam = "BAD-STRING";

        // when
        String result = testObj.returnDecodedTimeParam(timeParam);

        // then
        assertThat(result).isEqualTo(timeParam);
    }

    @Test
    @DisplayName("returnDecodedTimeParam: Return same character when correct time")
    void testReturnDecodedTimeParam2() {
        // given
        String timeParam = "13:37";

        // when
        String result = testObj.returnDecodedTimeParam(timeParam);

        // then
        assertThat(result).isEqualTo(timeParam);
    }

    @Test
    @DisplayName("returnDecodedTimeParam: Return decoded time string when provided encoded string")
    void testReturnDecodedTimeParam3() {
        // given
        String timeParam = "13%3A37";
        String expected = "13:37";

        // when
        String result = testObj.returnDecodedTimeParam(timeParam);

        // then
        assertThat(result).isEqualTo(expected);
    }

    @Test
    @DisplayName("validateDateParam: Return false when null")
    void testValidateDateParam1() {
        // given
        String dateParam = null;

        // when
        boolean result = testObj.validateDateParam(dateParam);

        // then
        assertFalse(result);
    }

    @Test
    @DisplayName("validateDateParam: Return false when empty")
    void testValidateDateParam2() {
        // given
        String dateParam = "";

        // when
        boolean result = testObj.validateDateParam(dateParam);

        // then
        assertFalse(result);
    }

    @Test
    @DisplayName("validateDateParam: Return false when white spaces")
    void testValidateDateParam3() {
        // given
        String dateParam = "   ";

        // when
        boolean result = testObj.validateDateParam(dateParam);

        // then
        assertFalse(result);
    }

    @Test
    @DisplayName("validateDateParam: Return false when bad string")
    void testValidateDateParam4() {
        // given
        String dateParam = "BAD-STRING";

        // when
        boolean result = testObj.validateDateParam(dateParam);

        // then
        assertFalse(result);
    }

    @Test
    @DisplayName("validateDateParam: Return false when letters instead of numbert")
    void testValidateDateParam5() {
        // given
        String dateParam = "yyyy-MM-dd";

        // when
        boolean result = testObj.validateDateParam(dateParam);

        // then
        assertFalse(result);
    }

    @Test
    @DisplayName("validateDateParam: Return true when correct date")
    void testValidateDateParam6() {
        // given
        String dateParam = "2018-11-17";

        // when
        boolean result = testObj.validateDateParam(dateParam);

        // then
        assertTrue(result);
    }

    @Test
    @DisplayName("returnValidatedTimeOrDefault: return correct LocalTime with MIN default")
    void testReturnValidatedTimeOrDefault1() {
        // given
        String timeParam = "13:37";
        List<String> warnings = new ArrayList<>();
        LocalTime defaultTime = LocalTime.MIN;

        when(spy.validateDateParam(timeParam)).thenReturn(true);
        when(spy.returnDecodedTimeParam(timeParam)).thenReturn(timeParam);

        // when
        LocalTime result = spy.returnValidatedTimeOrDefault(timeParam, warnings, defaultTime);
        LocalTime expected = LocalTime.of(13, 37);

        // then
        assertThat(result).isEqualTo(expected);
        assertThat(warnings).isEmpty();
    }

    @Test
    @DisplayName("returnValidatedTimeOrDefault: return correct LocalTime with MAX default")
    void testReturnValidatedTimeOrDefault2() {
        // given
        String timeParam = "13:37";
        List<String> warnings = new ArrayList<>();
        LocalTime defaultTime = LocalTime.MAX;

        when(spy.validateDateParam(timeParam)).thenReturn(true);
        when(spy.returnDecodedTimeParam(timeParam)).thenReturn(timeParam);

        // when
        LocalTime result = spy.returnValidatedTimeOrDefault(timeParam, warnings, defaultTime);
        LocalTime expected = LocalTime.of(13, 37, 59, 999999999);

        // then
        assertThat(result).isEqualTo(expected);
        assertThat(warnings).isEmpty();
    }

    @Test
    @DisplayName("returnValidatedTimeOrDefault: return default LocalTime")
    void testReturnValidatedTimeOrDefault3() {
        // given
        String timeParam = "13:37";
        List<String> warnings = new ArrayList<>();
        LocalTime defaultTime = LocalTime.MAX;

        when(spy.validateTimeParam(timeParam)).thenReturn(false);

        // when
        LocalTime result = spy.returnValidatedTimeOrDefault(timeParam, warnings, defaultTime);

        // then
        assertThat(result).isEqualTo(defaultTime);
        assertThat(warnings.get(0)).isEqualTo("Invalid time parameter, showing results with default value.");
    }

    @Test
    @DisplayName("returnValidatedDateTimeOrDefault: return correct LocalDateTime with MAX default")
    void testReturnValidatedDateTimeOrDefault1() {
        // given
        String dateParam = "2018-11-17";
        String timeParam = "13:37";
        List<String> warnings = new ArrayList<>();
        LocalDateTime defaultDateTime = LocalDateTime.MAX;

        LocalTime expectedLocalTime = LocalTime.of(13, 37);
        LocalDateTime expected = LocalDateTime.of(2018,11,17,13,37);

        when(spy.validateDateParam(dateParam)).thenReturn(true);
        when(spy.returnValidatedTimeOrDefault(timeParam, warnings, defaultDateTime.toLocalTime())).thenReturn(expectedLocalTime);

        // when
        LocalDateTime result = spy.returnValidatedDateTimeOrDefault(dateParam, timeParam, warnings, defaultDateTime);

        // then
        assertThat(result).isEqualTo(expected);
        assertThat(warnings).isEmpty();
    }

    @Test
    @DisplayName("returnValidatedDateTimeOrDefault: return correct LocalDateTime with MIN default")
    void testReturnValidatedDateTimeOrDefault2() {
        // given
        String dateParam = "2018-11-17";
        String timeParam = "13:37";
        List<String> warnings = new ArrayList<>();
        LocalDateTime defaultDateTime = LocalDateTime.MIN;

        LocalTime expectedLocalTime = LocalTime.of(13, 37);
        LocalDateTime expected = LocalDateTime.of(2018,11,17,13,37);

        when(spy.validateDateParam(dateParam)).thenReturn(true);
        when(spy.returnValidatedTimeOrDefault(timeParam, warnings, defaultDateTime.toLocalTime())).thenReturn(expectedLocalTime);

        // when
        LocalDateTime result = spy.returnValidatedDateTimeOrDefault(dateParam, timeParam, warnings, defaultDateTime);

        // then
        assertThat(result).isEqualTo(expected);
        assertThat(warnings).isEmpty();
    }

    @Test
    @DisplayName("returnValidatedDateTimeOrDefault: return default LocalDateTime")
    void testReturnValidatedDateTimeOrDefault3() {
        // given
        String dateParam = "2018-11-17";
        String timeParam = "13:37";
        List<String> warnings = new ArrayList<>();
        LocalDateTime defaultDateTime = LocalDateTime.MAX;

        when(spy.validateDateParam(dateParam)).thenReturn(false);

        // when
        LocalDateTime result = spy.returnValidatedDateTimeOrDefault(dateParam, timeParam, warnings, defaultDateTime);

        // then
        assertThat(result).isEqualTo(defaultDateTime);
        assertThat(warnings.get(0)).isEqualTo("Invalid date parameter, showing results with default value.");
    }
}