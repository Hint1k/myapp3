package com.demo.fds.converter;

import com.demo.fds.dto.TransactionDto;
import com.demo.fds.dto.TransactionFilterDto;
import com.demo.fds.entity.Transaction;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.function.Supplier;

@Component
public class ConverterImpl implements Converter {

    @Override
    public TransactionDto convertToTransactionDto(Transaction transaction) {
        return convert(transaction, TransactionDto::new);
    }

    @Override
    public Transaction convertToTransaction(TransactionDto dto) {
        return convert(dto, Transaction::new);
    }

    @Override
    public Transaction convertToTransactionForFilter(TransactionFilterDto filterDto) {
        if (filterDto == null) {
            return null;
        }
        Transaction transaction = new Transaction();
        if (filterDto.getTransactionId() != null) {
            transaction.setTransactionId(filterDto.getTransactionId());
        }
        if (filterDto.getCustomerId() != null) {
            transaction.setCustomerId(filterDto.getCustomerId());
        }
        if (filterDto.getMerchantId() != null) {
            transaction.setMerchantId(filterDto.getMerchantId());
        }
        if (filterDto.getStatus() != null) {
            transaction.setStatus(filterDto.getStatus());
        }
        if (filterDto.getRisk() != null) {
            transaction.setRisk(filterDto.getRisk());
        }
        if (filterDto.getCurrency() != null) {
            transaction.setCurrency(filterDto.getCurrency());
        }
        return transaction;
    }

    private <S, T> T convert(S source, Supplier<T> targetSupplier) {
        if (source == null) {
            return null;
        }
        T target = targetSupplier.get();
        BeanUtils.copyProperties(source, target);
        return target;
    }
}