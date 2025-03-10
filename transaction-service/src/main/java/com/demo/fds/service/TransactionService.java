package com.demo.fds.service;

import com.demo.fds.dto.TransactionFilterDto;
import com.demo.fds.entity.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface TransactionService {

    Transaction createTransaction(Transaction transaction);

    Optional<Transaction> updateTransaction(Long id, Transaction transaction);

    boolean deleteTransaction(Long id);

    Page<Transaction> getFilteredTransactions(TransactionFilterDto transactionFilterDto, Pageable pageable);
}