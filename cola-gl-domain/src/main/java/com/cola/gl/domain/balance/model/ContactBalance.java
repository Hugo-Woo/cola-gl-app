package com.cola.gl.domain.balance.model;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class ContactBalance {
    private Long id;
    private String accountCode;
    private String contactCode;
    private String startPeriod; // 开始期间，余额发生期间
    private String endPeriod; // 结束期间，下一次发生期间（999999表示最新）
    private BigDecimal beginBalance = BigDecimal.ZERO; // 期初余额
    private BigDecimal endBalance = BigDecimal.ZERO; // 期末余额
    private BigDecimal debitAmount = BigDecimal.ZERO; // 借方发生额
    private BigDecimal creditAmount = BigDecimal.ZERO; // 贷方发生额

    public void addDebit(BigDecimal amount) {
        this.debitAmount = this.debitAmount.add(amount);
        this.endBalance = this.beginBalance.add(this.debitAmount).subtract(this.creditAmount);
    }

    public void addCredit(BigDecimal amount) {
        this.creditAmount = this.creditAmount.add(amount);
        this.endBalance = this.beginBalance.add(this.debitAmount).subtract(this.creditAmount);
    }

    public BigDecimal getNetBalance() {
        return debitAmount.subtract(creditAmount);
    }

    /**
     * 计算期末余额
     */
    public void calculateEndBalance() {
        this.endBalance = this.beginBalance.add(this.debitAmount).subtract(this.creditAmount);
    }
}
