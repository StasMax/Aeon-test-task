package org.example.security.bruteforce;

public interface BruteForceProtectionService {

    /**
     * Метод регистрирует ошибку авторизации
     * Вносит изменения в поля для блокировки пользователя
     *
     * @param username
     */
    void registerLoginFailure(final String username);

    /**
     * Метод сбрасывает значения полей для блокировки пользователя
     *
     * @param username
     */
    void resetBruteForceCounter(final String username);

}
