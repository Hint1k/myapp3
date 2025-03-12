package com.demo.fds.controller;

import com.demo.fds.converter.Converter;
import com.demo.fds.dto.TransactionDto;
import com.demo.fds.dto.TransactionFilterDto;
import com.demo.fds.entity.Transaction;
import com.demo.fds.exception.custom.InvalidPageableException;
import com.demo.fds.exception.custom.OptimisticLockingException;
import com.demo.fds.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionService transactionService;
    private final Converter converter;

    @Autowired
    public TransactionController(TransactionService transactionService, Converter converter) {
        this.transactionService = transactionService;
        this.converter = converter;
    }

    @PostMapping
    public ResponseEntity<TransactionDto> createTransaction(@RequestBody TransactionDto transactionDto) {
        Transaction transaction = converter.convertToTransaction(transactionDto);
        Transaction savedTransaction = transactionService.createTransaction(transaction);
        return ResponseEntity.status(HttpStatus.CREATED).body(converter.convertToTransactionDto(savedTransaction));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TransactionDto> updateTransaction(@PathVariable Long id,
                                                            @RequestBody TransactionDto transactionDto) {
        Transaction transaction = converter.convertToTransaction(transactionDto);
        try {
            Optional<Transaction> updatedTransaction = transactionService.updateTransaction(id, transaction);
            return updatedTransaction.map(converter::convertToTransactionDto).map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (OptimisticLockingFailureException ex) {
            throw new OptimisticLockingException("Conflict occurred: " + ex.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable Long id) {
        boolean deleted = transactionService.deleteTransaction(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @GetMapping("/filter")
    public ResponseEntity<Page<TransactionDto>> getFilteredTransactions(TransactionFilterDto filterDto,
                                                                        Pageable pageable) {
        if (pageable.getPageNumber() < 0 || pageable.getPageSize() <= 0) {
            String message = "Page number must be non-negative and page size must be greater than zero";
            throw new InvalidPageableException(message);
        }
        Page<Transaction> transactions = transactionService.getFilteredTransactions(filterDto, pageable);
        Page<TransactionDto> transactionDtoList = transactions.map(converter::convertToTransactionDto);
        return ResponseEntity.ok(transactionDtoList);
    }
}