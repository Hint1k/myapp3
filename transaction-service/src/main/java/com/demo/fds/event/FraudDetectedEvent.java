package com.demo.fds.event;

import lombok.Getter;

import java.util.Objects;

@Getter
public class FraudDetectedEvent extends TransactionEvent {

    private final String fraudReason;
    private final double fraudScore;

    public FraudDetectedEvent(Long transactionId, String fraudReason, double fraudScore) {
        super(transactionId);
        this.fraudReason = fraudReason;
        this.fraudScore = fraudScore;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        FraudDetectedEvent that = (FraudDetectedEvent) o;
        return Double.compare(fraudScore, that.fraudScore) == 0 && Objects.equals(fraudReason, that.fraudReason);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fraudReason, fraudScore);
    }

    @Override
    public String toString() {
        return "FraudDetectedEvent{" +
                "fraudReason='" + fraudReason + '\'' +
                ", fraudScore=" + fraudScore +
                '}';
    }
}