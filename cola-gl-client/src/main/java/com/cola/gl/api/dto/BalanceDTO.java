package com.cola.gl.api.dto;

import com.alibaba.cola.dto.DTO;
import lombok.Data;

import java.math.BigDecimal;

@Data
@lombok.EqualsAndHashCode(callSuper = false)
public class BalanceDTO extends DTO {
    private String accountCode;
    private String startPeriod; // 开始期间
    private String endPeriod; // 结束期间
    private BigDecimal beginBalance; // 期初余额
    private BigDecimal endBalance; // 期末余额
    private BigDecimal debitAmount; // 借方发生额
    private BigDecimal creditAmount; // 贷方发生额
}
