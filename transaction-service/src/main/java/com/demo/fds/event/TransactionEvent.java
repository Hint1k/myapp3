package com.demo.fds.event;

import lombok.Getter;

import java.time.LocalDateTime;
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
}