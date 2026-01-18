package com.cola.gl.infrastructure.db;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "voucher_line")
public class VoucherLineDO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long voucherId;
    private String accountCode;
    private String direction;
    private BigDecimal amount;
    private String description;
}
