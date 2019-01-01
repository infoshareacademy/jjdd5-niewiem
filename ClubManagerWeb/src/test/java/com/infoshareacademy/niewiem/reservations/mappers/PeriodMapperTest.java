package com.infoshareacademy.niewiem.reservations.mappers;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static com.infoshareacademy.niewiem.reservations.enums.Period.*;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

class PeriodMapperTest {

    private PeriodMapper testObj = new PeriodMapper();
    private PeriodMapper spy = spy(testObj);

    @Test
    @DisplayName("Check if getNow returns now")
    void test0(){
        // given
        LocalDateTime expected = LocalDateTime.now();

        // when
        LocalDateTime result = testObj.getNow();

        // then
        Assertions.assertThat(result).isCloseTo(expected, Assertions.within(100, ChronoUnit.MILLIS));
    }

    @Test
    @DisplayName("Return start of YESTERDAY.")
    void test1(){
        // given
        LocalDateTime ldtReturn = LocalDateTime.of(2018,11,17,13,37);
        when(spy.getNow()).thenReturn(ldtReturn);

        // when
        LocalDateTime result = spy.getStartTime(YESTERDAY);
        LocalDateTime expected = LocalDateTime.of(2018,11,16,0,0);

        // then
        Assertions.assertThat(result).isEqualTo(expected);
    }

    @Test
    @DisplayName("Return end of YESTERDAY.")
    void test1b(){
        // given
        LocalDateTime ldtReturn = LocalDateTime.of(2018,11,17,13,37);
        when(spy.getNow()).thenReturn(ldtReturn);

        // when
        LocalDateTime result = spy.getEndTime(YESTERDAY);
        LocalDateTime expected = LocalDateTime
                .of(2018,11,16,23,59,59,999999999);

        // then
        Assertions.assertThat(result).isEqualTo(expected);
    }

    @Test
    @DisplayName("Return start of TODAY.")
    void test2(){
        // given
        LocalDateTime ldtReturn = LocalDateTime.of(2018,11,17,13,37);
        when(spy.getNow()).thenReturn(ldtReturn);

        // when
        LocalDateTime result = spy.getStartTime(TODAY);
        LocalDateTime expected = LocalDateTime.of(2018,11,17,0,0);

        // then
        Assertions.assertThat(result).isEqualTo(expected);
    }

    @Test
    @DisplayName("Return end of TODAY.")
    void test2b(){
        // given
        LocalDateTime ldtReturn = LocalDateTime.of(2018,11,17,13,37);
        when(spy.getNow()).thenReturn(ldtReturn);

        // when
        LocalDateTime result = spy.getEndTime(TODAY);
        LocalDateTime expected = LocalDateTime
                .of(2018,11,17,23,59,59,999999999);

        // then
        Assertions.assertThat(result).isEqualTo(expected);
    }

    @Test
    @DisplayName("Return start of TOMORROW.")
    void test3(){
        // given
        LocalDateTime ldtReturn = LocalDateTime.of(2018,11,17,13,37);
        when(spy.getNow()).thenReturn(ldtReturn);

        // when
        LocalDateTime result = spy.getStartTime(TOMORROW);
        LocalDateTime expected = LocalDateTime.of(2018,11,18,0,0);

        // then
        Assertions.assertThat(result).isEqualTo(expected);
    }

    @Test
    @DisplayName("Return end of TOMORROW.")
    void test3b(){
        // given
        LocalDateTime ldtReturn = LocalDateTime.of(2018,11,17,13,37);
        when(spy.getNow()).thenReturn(ldtReturn);

        // when
        LocalDateTime result = spy.getEndTime(TOMORROW);
        LocalDateTime expected = LocalDateTime
                .of(2018,11,18,23,59,59,999999999);

        // then
        Assertions.assertThat(result).isEqualTo(expected);
    }

    @Test
    @DisplayName("Return start of last 24 hours.")
    void test4(){
        // given
        LocalDateTime ldtReturn = LocalDateTime.of(2018,11,17,13,37);
        when(spy.getNow()).thenReturn(ldtReturn);

        // when
        LocalDateTime result = spy.getStartTime(LAST24H);
        LocalDateTime expected = LocalDateTime.of(2018,11,16,13,37);

        // then
        Assertions.assertThat(result).isEqualTo(expected);
    }

    @Test
    @DisplayName("Return end of last 24 hours (which is now).")
    void test4b(){
        // given
        LocalDateTime ldtReturn = LocalDateTime.of(2018,11,17,13,37);
        when(spy.getNow()).thenReturn(ldtReturn);

        // when
        LocalDateTime result = spy.getEndTime(LAST24H);
        LocalDateTime expected = ldtReturn;

        // then
        Assertions.assertThat(result).isEqualTo(expected);
    }

    @Test
    @DisplayName("Return start of this month.")
    void test5(){
        // given
        LocalDateTime ldtReturn = LocalDateTime.of(2018,11,17,13,37);
        when(spy.getNow()).thenReturn(ldtReturn);

        // when
        LocalDateTime result = spy.getStartTime(THIS_MONTH);
        LocalDateTime expected = LocalDateTime.of(2018,11,1,0,0);

        // then
        Assertions.assertThat(result).isEqualTo(expected);
    }

    @Test
    @DisplayName("Return end of this month.")
    void test5b(){
        // given
        LocalDateTime ldtReturn = LocalDateTime.of(2018,11,17,13,37);
        when(spy.getNow()).thenReturn(ldtReturn);

        // when
        LocalDateTime result = spy.getEndTime(THIS_MONTH);
        LocalDateTime expected = LocalDateTime
                .of(2018,11,30,23,59,59,999999999);

        // then
        Assertions.assertThat(result).isEqualTo(expected);
    }

    @Test
    @DisplayName("Return beginning of history (ldt MIN value).")
    void test6(){
        // given
        LocalDateTime ldtReturn = LocalDateTime.of(2018,11,17,13,37);
        when(spy.getNow()).thenReturn(ldtReturn);

        // when
        LocalDateTime result = spy.getStartTime(HISTORY);
        LocalDateTime expected = LocalDateTime.MIN;

        // then
        Assertions.assertThat(result).isEqualTo(expected);
    }

    @Test
    @DisplayName("Return end of history (which is now).")
    void test6b(){
        // given
        LocalDateTime ldtReturn = LocalDateTime.of(2018,11,17,13,37);
        when(spy.getNow()).thenReturn(ldtReturn);

        // when
        LocalDateTime result = spy.getEndTime(HISTORY);
        LocalDateTime expected = ldtReturn;

        // then
        Assertions.assertThat(result).isEqualTo(expected);
    }

    @Test
    @DisplayName("Return start of UPCOMING (which is now).")
    void test7(){
        // given
        LocalDateTime ldtReturn = LocalDateTime.of(2018,11,17,13,37);
        when(spy.getNow()).thenReturn(ldtReturn);

        // when
        LocalDateTime result = spy.getStartTime(UPCOMING);
        LocalDateTime expected = ldtReturn;

        // then
        Assertions.assertThat(result).isEqualTo(expected);
    }

    @Test
    @DisplayName("Return end of UPCOMING (ldt MAX value).")
    void test7b(){
        // given
        LocalDateTime ldtReturn = LocalDateTime.of(2018,11,17,13,37);
        when(spy.getNow()).thenReturn(ldtReturn);

        // when
        LocalDateTime result = spy.getEndTime(UPCOMING);
        LocalDateTime expected = LocalDateTime.MAX;

        // then
        Assertions.assertThat(result).isEqualTo(expected);
    }
}