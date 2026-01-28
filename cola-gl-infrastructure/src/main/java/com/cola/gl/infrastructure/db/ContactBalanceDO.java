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
    private String startPeriod; // 开始期间
    private String endPeriod; // 结束期间
    private BigDecimal beginBalance; // 期初余额
    private BigDecimal endBalance; // 期末余额
    private BigDecimal debitAmount; // 借方发生额
    private BigDecimal creditAmount; // 贷方发生额
}
