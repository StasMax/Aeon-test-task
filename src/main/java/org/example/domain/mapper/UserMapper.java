package org.example.domain.mapper;

import org.example.domain.entity.User;
import org.example.domain.vm.UserVM;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class UserMapper {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${app.default-balance}")
    private String defaultBalance;

    public User toEntity(UserVM vm) {
        User user = new User();
        user.setLogin(vm.getLogin());
        user.setPassword(passwordEncoder.encode(vm.getPassword()));
        user.setBalance(new BigDecimal(defaultBalance));
        return user;
    }
}
