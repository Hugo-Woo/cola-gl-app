package com.cola.gl.domain.voucher.model;

import com.alibaba.cola.domain.Entity;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Entity
public class VoucherLine {
    private String accountCode;
    private String direction;
    private BigDecimal amount;
    private String description;
    private String contactCode;
}
