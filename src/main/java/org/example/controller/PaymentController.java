package org.example.controller;

import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.common.Urls;
import org.example.domain.dto.PaymentDto;
import org.example.service.PaymentService;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

import static org.example.common.Constants.TOKEN_HEADER;

@Api(tags = "Payment Controller")
@RestController
@RequiredArgsConstructor
@Slf4j
public class PaymentController {

    private final PaymentService paymentService;

    @Operation(
            summary = "Проведение нового платежа",
            method = "POST",
            description = "Позволяет провести новый дефолтный платеж"
    )
    @PostMapping(value = Urls.Payments.ALL, produces = MediaType.APPLICATION_JSON_VALUE)
    public PaymentDto createPayment(HttpServletRequest request) {
        return paymentService.pay(
                SecurityContextHolder.getContext().getAuthentication().getName(),
                request.getHeader(TOKEN_HEADER)
        );
    }
}
