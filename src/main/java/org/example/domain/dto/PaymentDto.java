package org.example.domain.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PaymentDto {

    private Long userId;

    private String paymentDate;

    private BigDecimal paymentAmount;

    private BigDecimal beforeBalance;

    private BigDecimal afterBalance;
}
