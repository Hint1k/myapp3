package com.demo.fds.event;

public class TransactionDeletedEvent extends TransactionEvent {

    public TransactionDeletedEvent(Long transactionId) {
        super(transactionId);
    }
}