package com.cola.gl.app.event.handler;

import com.cola.gl.domain.balance.gateway.ContactBalanceGateway;
import com.cola.gl.domain.balance.model.ContactBalance;
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
 * 往来余额处理器
 * 监听凭证提交事件，按拉链模型更新往来余额
 */
@Component
@AllArgsConstructor
public class ContactBalanceHandler {

    private static final String MAX_PERIOD = "999999";
    private final ContactBalanceGateway contactBalanceGateway;

    @EventListener
    public void handleVoucherSubmitted(VoucherSubmittedEvent event) {
        Voucher voucher = event.getVoucher();
        String period = voucher.getAccountingDate().format(DateTimeFormatter.ofPattern("yyyyMM"));

        // 按科目+往来单位汇总借贷金额
        Map<String, BigDecimal> debitByKey = new HashMap<>();
        Map<String, BigDecimal> creditByKey = new HashMap<>();

        for (VoucherLine line : voucher.getLines()) {
            if (line.getContactCode() != null && !line.getContactCode().isEmpty()) {
                String key = line.getAccountCode() + "|" + line.getContactCode();
                if ("D".equalsIgnoreCase(line.getDirection())) {
                    debitByKey.merge(key, line.getAmount(), BigDecimal::add);
                } else if ("C".equalsIgnoreCase(line.getDirection())) {
                    creditByKey.merge(key, line.getAmount(), BigDecimal::add);
                }
            }
        }

        // 更新每个科目+往来单位的余额
        for (String key : debitByKey.keySet()) {
            String[] parts = key.split("\\|");
            updateContactBalance(parts[0], parts[1], period,
                    debitByKey.getOrDefault(key, BigDecimal.ZERO),
                    creditByKey.getOrDefault(key, BigDecimal.ZERO));
            creditByKey.remove(key);
        }

        for (String key : creditByKey.keySet()) {
            String[] parts = key.split("\\|");
            updateContactBalance(parts[0], parts[1], period, BigDecimal.ZERO, creditByKey.get(key));
        }
    }

    private void updateContactBalance(String accountCode, String contactCode, String period,
            BigDecimal debitAmount, BigDecimal creditAmount) {
        // 查询覆盖当期的往来余额记录
        ContactBalance existingBalance = contactBalanceGateway.getByAccountAndContactCoveringPeriod(
                accountCode, contactCode, period);

        if (existingBalance == null) {
            // 不存在记录，新建
            ContactBalance newBalance = new ContactBalance();
            newBalance.setAccountCode(accountCode);
            newBalance.setContactCode(contactCode);
            newBalance.setStartPeriod(period);
            newBalance.setEndPeriod(MAX_PERIOD);
            newBalance.setBeginBalance(BigDecimal.ZERO);
            newBalance.setDebitAmount(debitAmount);
            newBalance.setCreditAmount(creditAmount);
            newBalance.calculateEndBalance();
            contactBalanceGateway.save(newBalance);
        } else if (existingBalance.getStartPeriod().equals(period)) {
            // 开始期间相同，直接累加
            existingBalance.addDebit(debitAmount);
            existingBalance.addCredit(creditAmount);
            contactBalanceGateway.save(existingBalance);
        } else {
            // 开始期间不同，需要拆分记录
            // 1. 原记录的结束期间改为当期
            String originalEndPeriod = existingBalance.getEndPeriod();
            existingBalance.setEndPeriod(period);
            contactBalanceGateway.save(existingBalance);

            // 2. 新建当期记录
            ContactBalance newBalance = new ContactBalance();
            newBalance.setAccountCode(accountCode);
            newBalance.setContactCode(contactCode);
            newBalance.setStartPeriod(period);
            newBalance.setEndPeriod(originalEndPeriod);
            newBalance.setBeginBalance(existingBalance.getEndBalance());
            newBalance.setDebitAmount(debitAmount);
            newBalance.setCreditAmount(creditAmount);
            newBalance.calculateEndBalance();
            contactBalanceGateway.save(newBalance);
        }

        // 更新后续期间的期初余额
        updateSubsequentBalances(accountCode, contactCode, period);
    }

    /**
     * 更新后续期间的期初余额
     */
    private void updateSubsequentBalances(String accountCode, String contactCode, String period) {
        // 先获取当期的期末余额
        ContactBalance currentBalance = contactBalanceGateway.getByAccountAndContactCoveringPeriod(
                accountCode, contactCode, period);
        if (currentBalance == null) {
            return;
        }

        // 查询后续期间的往来余额记录
        List<ContactBalance> subsequentBalances = contactBalanceGateway.getByAccountAndContactAfterPeriod(
                accountCode, contactCode, period);

        BigDecimal previousEndBalance = currentBalance.getEndBalance();
        for (ContactBalance balance : subsequentBalances) {
            balance.setBeginBalance(previousEndBalance);
            balance.calculateEndBalance();
            contactBalanceGateway.save(balance);
            previousEndBalance = balance.getEndBalance();
        }
    }
}
