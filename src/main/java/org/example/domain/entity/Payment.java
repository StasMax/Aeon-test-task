package org.example.domain.entity;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Запись о платеже.
 */
@Entity
@Table(name = "payment")
@Data
public class Payment {

    @Id
    @SequenceGenerator(name = "payment_seq", sequenceName = "payment_seq", allocationSize = 1)
    @GeneratedValue(generator = "payment_seq", strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "payment_date", nullable = false, unique = true)
    private LocalDateTime paymentDate;

    @Column(name = "payment_amount", nullable = false)
    private BigDecimal paymentAmount;

    @Column(name = "before_balance", nullable = false)
    private BigDecimal beforeBalance;

    @Column(name = "after_balance", nullable = false)
    private BigDecimal afterBalance;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

}
