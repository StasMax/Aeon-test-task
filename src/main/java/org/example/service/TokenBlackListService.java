package org.example.service;

import org.example.domain.entity.IncorrectToken;

public interface TokenBlackListService {

    /**
     * Очистка токенов с истекшим сроком
     */
    void clearList();

    /**
     * Сохранить некорректный токен
     *
     * @param token токен
     * @return запись о токене
     */
    IncorrectToken saveIncorrectToken(String token);

    /**
     * Проверка существования токена в черном списке
     *
     * @param token токен
     * @return
     */
    boolean existsIncorrectToken(String token);
}
