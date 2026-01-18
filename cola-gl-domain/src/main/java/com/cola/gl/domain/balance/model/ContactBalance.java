package com.cola.gl.domain.balance.model;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class ContactBalance {
    private String accountCode;
    private String contactCode;
    private String period;
    private BigDecimal debitAmount = BigDecimal.ZERO;
    private BigDecimal creditAmount = BigDecimal.ZERO;

    public void addDebit(BigDecimal amount) {
        this.debitAmount = this.debitAmount.add(amount);
    }

    public void addCredit(BigDecimal amount) {
        this.creditAmount = this.creditAmount.add(amount);
    }

    public BigDecimal getNetBalance() {
        return debitAmount.subtract(creditAmount);
    }
}
