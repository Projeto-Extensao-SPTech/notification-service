package com.notification.service.domain.service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RecurrenceCalculator {

    public Set<LocalDate> buildRecurrenceDates(LocalDate eventDate, List<Integer> recurrences) {
        Set<LocalDate> dates = new HashSet<>();
        dates.add(eventDate);

        recurrences.forEach(r -> dates.add(getDateFromRecurrence(eventDate, r)));
        return dates;
    }

    private LocalDate getDateFromRecurrence(LocalDate eventDate, Integer recurrenceDay) {
        return eventDate.minusDays(recurrenceDay);
    }
}