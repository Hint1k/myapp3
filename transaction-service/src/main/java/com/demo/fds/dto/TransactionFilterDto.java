package com.demo.fds.dto;

import com.demo.fds.utils.TransactionCurrency;
import com.demo.fds.utils.TransactionRisk;
import com.demo.fds.utils.TransactionStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionFilterDto {

    private Long transactionId;
    private Long customerId;
    private Long merchantId;
    private TransactionStatus status;
    private TransactionRisk risk;
    private TransactionCurrency currency;
    private BigDecimal minAmount;
    private BigDecimal maxAmount;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime startDate;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime endDate;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        TransactionFilterDto that = (TransactionFilterDto) o;
        return Objects.equals(transactionId, that.transactionId) && Objects.equals(customerId, that.customerId)
                && Objects.equals(merchantId, that.merchantId) && Objects.equals(status, that.status)
                && Objects.equals(risk, that.risk) && Objects.equals(currency, that.currency)
                && Objects.equals(minAmount, that.minAmount) && Objects.equals(maxAmount, that.maxAmount)
                && Objects.equals(startDate, that.startDate) && Objects.equals(endDate, that.endDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                transactionId, customerId, merchantId, status, risk, currency, minAmount, maxAmount, startDate, endDate
        );
    }

    @Override
    public String toString() {
        return "TransactionFilterDto{" +
                "transactionId=" + transactionId +
                ", customerId=" + customerId +
                ", merchantId=" + merchantId +
                ", status='" + status + '\'' +
                ", risk='" + risk + '\'' +
                ", currency='" + currency + '\'' +
                ", minAmount=" + minAmount +
                ", maxAmount=" + maxAmount +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}