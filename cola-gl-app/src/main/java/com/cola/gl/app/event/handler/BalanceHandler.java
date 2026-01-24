package com.cola.gl.app.event.handler;

import com.cola.gl.domain.balance.gateway.BalanceGateway;
import com.cola.gl.domain.balance.model.Balance;
import com.cola.gl.domain.voucher.event.VoucherSubmittedEvent;
import com.cola.gl.domain.voucher.model.Voucher;
import com.cola.gl.domain.voucher.model.VoucherLine;
import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 科目余额处理器
 * 监听凭证提交事件，按拉链模型更新余额
 */
@Component
@AllArgsConstructor
public class BalanceHandler {

    private static final String MAX_PERIOD = "999999";
    private final BalanceGateway balanceGateway;

    @EventListener
    public void handleVoucherSubmitted(VoucherSubmittedEvent event) {
        Voucher voucher = event.getVoucher();
        String period = voucher.getAccountingDate().format(DateTimeFormatter.ofPattern("yyyyMM"));

        // 按科目汇总借贷金额
        Map<String, BigDecimal> debitByAccount = new HashMap<>();
        Map<String, BigDecimal> creditByAccount = new HashMap<>();

        for (VoucherLine line : voucher.getLines()) {
            String accountCode = line.getAccountCode();
            if ("D".equalsIgnoreCase(line.getDirection())) {
                debitByAccount.merge(accountCode, line.getAmount(), BigDecimal::add);
            } else if ("C".equalsIgnoreCase(line.getDirection())) {
                creditByAccount.merge(accountCode, line.getAmount(), BigDecimal::add);
            }
        }

        // 更新每个科目的余额
        for (String accountCode : debitByAccount.keySet()) {
            updateBalance(accountCode, period,
                    debitByAccount.getOrDefault(accountCode, BigDecimal.ZERO),
                    creditByAccount.getOrDefault(accountCode, BigDecimal.ZERO));
            creditByAccount.remove(accountCode);
        }

        for (String accountCode : creditByAccount.keySet()) {
            updateBalance(accountCode, period, BigDecimal.ZERO, creditByAccount.get(accountCode));
        }
    }

    private void updateBalance(String accountCode, String period, BigDecimal debitAmount, BigDecimal creditAmount) {
        // 查询覆盖当期的余额记录
        Balance existingBalance = balanceGateway.getByAccountCodeCoveringPeriod(accountCode, period);

        if (existingBalance == null) {
            // 不存在记录，新建
            Balance newBalance = new Balance();
            newBalance.setAccountCode(accountCode);
            newBalance.setStartPeriod(period);
            newBalance.setEndPeriod(MAX_PERIOD);
            newBalance.setBeginBalance(BigDecimal.ZERO);
            newBalance.setDebitAmount(debitAmount);
            newBalance.setCreditAmount(creditAmount);
            newBalance.calculateEndBalance();
            balanceGateway.save(newBalance);
        } else if (existingBalance.getStartPeriod().equals(period)) {
            // 开始期间相同，直接累加
            existingBalance.addDebit(debitAmount);
            existingBalance.addCredit(creditAmount);
            balanceGateway.save(existingBalance);
        } else {
            // 开始期间不同，需要拆分记录
            // 1. 原记录的结束期间改为当期
            String originalEndPeriod = existingBalance.getEndPeriod();
            existingBalance.setEndPeriod(period);
            balanceGateway.save(existingBalance);

            // 2. 新建当期记录
            Balance newBalance = new Balance();
            newBalance.setAccountCode(accountCode);
            newBalance.setStartPeriod(period);
            newBalance.setEndPeriod(originalEndPeriod);
            newBalance.setBeginBalance(existingBalance.getEndBalance());
            newBalance.setDebitAmount(debitAmount);
            newBalance.setCreditAmount(creditAmount);
            newBalance.calculateEndBalance();
            balanceGateway.save(newBalance);
        }

        // 更新后续期间的期初余额
        updateSubsequentBalances(accountCode, period);
    }

    /**
     * 更新后续期间的期初余额
     */
    private void updateSubsequentBalances(String accountCode, String period) {
        // 先获取当期的期末余额
        Balance currentBalance = balanceGateway.getByAccountCodeCoveringPeriod(accountCode, period);
        if (currentBalance == null) {
            return;
        }

        // 查询后续期间的余额记录
        List<Balance> subsequentBalances = balanceGateway.getByAccountCodeAfterPeriod(accountCode, period);

        BigDecimal previousEndBalance = currentBalance.getEndBalance();
        for (Balance balance : subsequentBalances) {
            balance.setBeginBalance(previousEndBalance);
            balance.calculateEndBalance();
            balanceGateway.save(balance);
            previousEndBalance = balance.getEndBalance();
        }
    }
}
