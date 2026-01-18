package com.cola.gl.api.dto;

import com.alibaba.cola.dto.DTO;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class VoucherLineDTO extends DTO {
    private String accountCode;
    private String direction;
    private BigDecimal amount;
    private String description;
    private String contactCode;
}
