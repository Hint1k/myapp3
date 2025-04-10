package com.demo.fds.event;

import com.demo.fds.dto.TransactionDto;
import lombok.Getter;

import java.util.Objects;

@Getter
public class TransactionCreatedEvent extends TransactionEvent {

    private final TransactionDto transaction;

    public TransactionCreatedEvent(TransactionDto transaction) {
        super(transaction.getTransactionId());
        this.transaction = transaction;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        TransactionCreatedEvent that = (TransactionCreatedEvent) o;
        return Objects.equals(transaction, that.transaction);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(transaction);
    }

    @Override
    public String toString() {
        return "TransactionCreatedEvent{" +
                "transaction=" + transaction +
                '}';
    }
}