package net.groster.moex.forts.drunkypenguin.core.fast.domain;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;

public class Event {

    private int eventType;
    private LocalDate eventDate;
    private DateTime eventTime;

    public void setEventType(final int eventType) {
        this.eventType = eventType;
    }

    public void setEventDate(final LocalDate eventDate) {
        this.eventDate = eventDate;
    }

    public void setEventTime(final DateTime eventTime) {
        this.eventTime = eventTime;
    }

}
