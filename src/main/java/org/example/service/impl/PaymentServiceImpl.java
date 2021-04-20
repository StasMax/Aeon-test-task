package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.domain.dto.PaymentDto;
import org.example.domain.entity.Payment;
import org.example.domain.entity.User;
import org.example.domain.mapper.PaymentMapper;
import org.example.exception.AuthenticationException;
import org.example.exception.ServiceException;
import org.example.repository.PaymentRepository;
import org.example.service.PaymentService;
import org.example.service.TokenBlackListService;
import org.example.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static java.math.BigDecimal.ROUND_FLOOR;

@Service
@Slf4j
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository repository;
    private final UserService userService;
    private final PaymentMapper mapper;
    private final TokenBlackListService tokenBlackListService;

    @Value("${app.default-payment}")
    private String defaultPayment;

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public PaymentDto pay(String login, String token) {

        if (token != null && tokenBlackListService.existsIncorrectToken(token)) {
            throw new AuthenticationException("Session is terminated. You must login again.");
        }

        User user = userService.findByLogin(login);

        BigDecimal paymentAmount = new BigDecimal(defaultPayment);
        BigDecimal newBalance = user.getBalance().subtract(paymentAmount).setScale(1, ROUND_FLOOR);
        if (newBalance.doubleValue() < 0) {
            throw new ServiceException("Not enough money on balance for this operation", HttpStatus.FORBIDDEN);
        }

        Payment payment = new Payment();
        payment.setBeforeBalance(user.getBalance());
        payment.setAfterBalance(newBalance);
        payment.setUser(user);
        payment.setPaymentAmount(paymentAmount);
        payment.setPaymentDate(LocalDateTime.now());
        repository.save(payment);

        user.setBalance(newBalance);
        userService.updateBalance(user.getId(), newBalance);

        return mapper.toDto(payment);
    }
}
