package com.demo.fds.event;

import com.demo.fds.dto.TransactionDto;
import lombok.Getter;

import java.util.Objects;

@Getter
public class TransactionUpdatedEvent extends TransactionEvent {

    private final TransactionDto oldTransaction;
    private final TransactionDto newTransaction;

    public TransactionUpdatedEvent(TransactionDto oldTransaction, TransactionDto newTransaction) {
        super(newTransaction.getTransactionId());
        this.oldTransaction = oldTransaction;
        this.newTransaction = newTransaction;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        TransactionUpdatedEvent that = (TransactionUpdatedEvent) o;
        return Objects.equals(oldTransaction, that.oldTransaction)
                && Objects.equals(newTransaction, that.newTransaction);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), oldTransaction, newTransaction);
    }

    @Override
    public String toString() {
        return "TransactionUpdatedEvent{" +
                "oldTransaction=" + oldTransaction +
                ", newTransaction=" + newTransaction +
                '}';
    }
}