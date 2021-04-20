package org.example.service;

import org.example.domain.dto.PaymentDto;

public interface PaymentService {

    /**
     * Проведение платежа со стандартным значением
     *
     * @param login логин пользователя
     * @param token токен
     * @return информация о платеже
     */
    PaymentDto pay(String login, String token);
}
