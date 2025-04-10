package com.demo.fds.event;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Getter
public abstract class TransactionEvent {

    private final UUID eventId;
    private final LocalDateTime eventTimestamp;
    private final Long transactionId;

    protected TransactionEvent(Long transactionId) {
        this.eventId = UUID.randomUUID();
        this.eventTimestamp = LocalDateTime.now();
        this.transactionId = transactionId;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        TransactionEvent that = (TransactionEvent) o;
        return Objects.equals(eventId, that.eventId) && Objects.equals(eventTimestamp, that.eventTimestamp)
                && Objects.equals(transactionId, that.transactionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(eventId, eventTimestamp, transactionId);
    }

    @Override
    public String toString() {
        return "TransactionEvent{" +
                "eventId=" + eventId +
                ", eventTimestamp=" + eventTimestamp +
                ", transactionId=" + transactionId +
                '}';
    }
}