package com.demo.fds.converter;

import com.demo.fds.dto.TransactionDto;
import com.demo.fds.dto.TransactionFilterDto;
import com.demo.fds.entity.Transaction;

public interface Converter {

    Transaction convertToTransaction(TransactionDto dto);

    TransactionDto convertToTransactionDto(Transaction transaction);

    Transaction convertToTransactionForFilter(TransactionFilterDto filterDto);
}