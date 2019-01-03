package com.infoshareacademy.niewiem.reservations.publishers;

import com.infoshareacademy.niewiem.reservations.enums.Period;

import javax.ejb.Stateless;
import java.util.EnumSet;
import java.util.Map;

@Stateless
public class PeriodListPublisher {
    public void publishPeriods(Map<String, Object> model) {
        EnumSet<Period> periods = EnumSet.allOf(Period.class);
        model.put("periods", periods);
    }
}