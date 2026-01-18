package com.cola.gl.infrastructure.db;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "balance")
public class BalanceDO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String accountCode;
    private String period;
    private BigDecimal debitAmount;
    private BigDecimal creditAmount;
}
