package com.cola.gl.api.dto;

import com.alibaba.cola.dto.DTO;
import lombok.Data;

import java.math.BigDecimal;

@Data
@lombok.EqualsAndHashCode(callSuper = false)
public class BalanceDTO extends DTO {
    private String accountCode;
    private String period;
    private BigDecimal debitAmount;
    private BigDecimal creditAmount;
}
