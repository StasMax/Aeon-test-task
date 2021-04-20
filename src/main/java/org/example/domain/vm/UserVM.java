package org.example.domain.vm;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Data
public class UserVM {

    @Length(min = 3, message = "Имя должно быть не менее 3 символов")
    @NotBlank(message = "Имя не может быть пустым")
    private String login;

    @Length(min = 6, message = "Пароль должен быть не менее 6 симвотов")
    @NotBlank(message = "Пароль не может быть пустым")
    private String password;
}
