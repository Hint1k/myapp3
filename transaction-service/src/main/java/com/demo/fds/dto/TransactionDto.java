package com.demo.fds.dto;

import com.demo.fds.utils.TransactionCurrency;
import com.demo.fds.utils.TransactionRisk;
import com.demo.fds.utils.TransactionStatus;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDto {

    private Long transactionId;

    @NotNull(message = "Customer ID is required")
    private Long customerId;

    @NotNull(message = "Merchant ID is required")
    private Long merchantId;

    @NotNull(message = "Amount is required")
    @DecimalMin(value = "0.01", message = "Amount must be at least 0.01")
    private BigDecimal amount;

    @NotNull(message = "Timestamp is required")
    private LocalDateTime timestamp;

    @NotNull(message = "Currency is required")
    private TransactionCurrency currency;

    @NotBlank(message = "Location is required")
    private String location;

    @NotNull(message = "Risk is required")
    private TransactionRisk risk;

    @NotNull(message = "Status is required")
    private TransactionStatus status;

    @NotNull(message = "Version is required")
    @Positive(message = "Version must be a positive number")
    private Long version;

    // no transaction id
    public TransactionDto(Long customerId, Long merchantId, BigDecimal amount, LocalDateTime timestamp,
                          TransactionCurrency currency, String location, TransactionRisk risk,
                          TransactionStatus status, Long version) {
        this.customerId = customerId;
        this.merchantId = merchantId;
        this.amount = amount;
        this.timestamp = timestamp;
        this.currency = currency;
        this.location = location;
        this.risk = risk;
        this.status = status;
        this.version = version;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        TransactionDto that = (TransactionDto) o;
        return Objects.equals(transactionId, that.transactionId) && Objects.equals(customerId, that.customerId)
                && Objects.equals(merchantId, that.merchantId) && Objects.equals(amount, that.amount)
                && Objects.equals(timestamp, that.timestamp) && currency == that.currency
                && Objects.equals(location, that.location) && risk == that.risk && status == that.status
                && Objects.equals(version, that.version);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                transactionId, customerId, merchantId, amount, timestamp, currency, location, risk, status, version
        );
    }

    @Override
    public String toString() {
        return "TransactionDto{" +
                "transactionId=" + transactionId +
                ", customerId=" + customerId +
                ", merchantId=" + merchantId +
                ", amount=" + amount +
                ", timeStamp=" + timestamp +
                ", currency=" + currency +
                ", location='" + location + '\'' +
                ", risk=" + risk +
                ", status=" + status +
                ", version=" + version +
                '}';
    }
}