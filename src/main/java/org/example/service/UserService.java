package org.example.service;

import org.example.domain.entity.User;
import org.example.domain.vm.UserVM;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface UserService {

    /**
     * Добавить нового пользователя
     *
     * @param vm логин/пароль
     * @return сообщение со статусом
     */
    String addUser(UserVM vm);

    /**
     * Поиск пользователя по логину
     *
     * @param login логин
     * @return Запись о пользователе
     */
    User findByLogin(String login);

    /**
     * Обновление баланса пользователя
     *
     * @param userId  id пользователя
     * @param balance новый баланс
     * @return Запись о пользователе
     */
    User updateBalance(long userId, BigDecimal balance);

    /**
     * Обновление данных для блокировки пользователя
     *
     * @param userId              id пользователя
     * @param failedLoginAttempts кол-во неудачных попыток входа
     * @param lockTime            время блокировки пользователя
     * @return
     */
    User updateBrutForceFields(long userId, int failedLoginAttempts, LocalDateTime lockTime);

}
