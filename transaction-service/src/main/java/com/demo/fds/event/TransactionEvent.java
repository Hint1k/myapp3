package com.demo.fds.event;

import com.demo.fds.dto.TransactionDto;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Getter
public abstract class TransactionEvent {

    private final UUID eventId;
    private final LocalDateTime eventTimestamp;
    private final TransactionDto transaction;

    protected TransactionEvent(TransactionDto transaction) {
        this.transaction = transaction;
        this.eventId = UUID.randomUUID();
        this.eventTimestamp = LocalDateTime.now();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        TransactionEvent that = (TransactionEvent) o;
        return Objects.equals(eventId, that.eventId)
                && Objects.equals(eventTimestamp, that.eventTimestamp)
                && Objects.equals(transaction, that.transaction);
    }

    @Override
    public int hashCode() {
        return Objects.hash(eventId, eventTimestamp, transaction);
    }

    @Override
    public String toString() {
        return "TransactionEvent{" +
                "eventId=" + eventId +
                ", eventTimestamp=" + eventTimestamp +
                ", transaction=" + transaction +
                '}';
    }
}