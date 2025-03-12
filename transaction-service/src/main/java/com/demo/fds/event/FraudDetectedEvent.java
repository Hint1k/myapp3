package com.demo.fds.event;

import lombok.Getter;

@Getter
public class FraudDetectedEvent extends TransactionEvent {

    private final String fraudReason;
    private final double fraudScore;

    public FraudDetectedEvent(Long transactionId, String fraudReason, double fraudScore) {
        super(transactionId);
        this.fraudReason = fraudReason;
        this.fraudScore = fraudScore;
    }}
