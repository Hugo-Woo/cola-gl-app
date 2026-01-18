package com.cola.gl.domain.voucher.model;

import com.alibaba.cola.domain.Entity;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@Entity
public class Voucher {

    private Long id;
    private String voucherCode;
    private LocalDate accountingDate;
    private String description;
    private List<VoucherLine> lines;

    public boolean isBalanced() {
        if (lines == null || lines.isEmpty()) {
            return false;
        }

        BigDecimal debitSum = BigDecimal.ZERO;
        BigDecimal creditSum = BigDecimal.ZERO;

        for (VoucherLine line : lines) {
            if ("D".equalsIgnoreCase(line.getDirection())) {
                debitSum = debitSum.add(line.getAmount());
            } else if ("C".equalsIgnoreCase(line.getDirection())) {
                creditSum = creditSum.add(line.getAmount());
            }
        }

        return debitSum.compareTo(creditSum) == 0;
    }
}
