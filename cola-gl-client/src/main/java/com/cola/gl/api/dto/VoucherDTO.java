package com.cola.gl.api.dto;

import com.alibaba.cola.dto.DTO;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class VoucherDTO extends DTO {
    private String voucherCode;
    private LocalDate accountingDate;
    private String description;
    private List<VoucherLineDTO> lines;
}
