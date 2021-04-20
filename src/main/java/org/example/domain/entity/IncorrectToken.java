package org.example.domain.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * Невалидный токен.
 */
@Entity
@Table(name = "incorrect_token")
@Data
public class IncorrectToken {

    @Id
    @SequenceGenerator(name = "incorrect_token_seq", sequenceName = "incorrect_token_seq", allocationSize = 1)
    @GeneratedValue(generator = "incorrect_token_seq", strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "token", nullable = false, unique = true)
    private String token;

}
