package com.demo.fds.event;

import com.demo.fds.dto.TransactionDto;
import lombok.Getter;

import java.util.Objects;

@Getter
public class TransactionFlaggedEvent extends TransactionEvent {

    private final String fraudReason;
    private final double fraudScore;

    public TransactionFlaggedEvent(TransactionDto transaction, String fraudReason, double fraudScore) {
        super(transaction);
        this.fraudReason = fraudReason;
        this.fraudScore = fraudScore;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        TransactionFlaggedEvent that = (TransactionFlaggedEvent) o;
        return Double.compare(fraudScore, that.fraudScore) == 0 && Objects.equals(fraudReason, that.fraudReason);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), fraudReason, fraudScore);
    }

    @Override
    public String toString() {
        return "TransactionFlaggedEvent{" +
                "fraudReason='" + fraudReason + '\'' +
                ", fraudScore=" + fraudScore +
                '}';
    }
}