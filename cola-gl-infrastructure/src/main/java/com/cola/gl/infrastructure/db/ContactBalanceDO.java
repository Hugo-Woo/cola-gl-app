package com.cola.gl.infrastructure.db;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "contact_balance")
public class ContactBalanceDO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String accountCode;
    private String contactCode;
    private String period;
    private BigDecimal debitAmount;
    private BigDecimal creditAmount;
}
