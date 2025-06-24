package com.demo.fds.event;

import com.demo.fds.dto.TransactionDto;
import lombok.Getter;

@Getter
public class TransactionReceivedEvent extends TransactionEvent {

    public TransactionReceivedEvent(TransactionDto transaction) {
        super(transaction);
    }
}