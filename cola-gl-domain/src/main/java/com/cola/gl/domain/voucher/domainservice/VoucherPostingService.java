package com.cola.gl.domain.voucher.domainservice;

import com.cola.gl.domain.balance.gateway.BalanceGateway;
import com.cola.gl.domain.balance.model.Balance;
import com.cola.gl.domain.voucher.model.Voucher;
import com.cola.gl.domain.voucher.model.VoucherLine;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;

@Service
@AllArgsConstructor
public class VoucherPostingService {

    private final BalanceGateway balanceGateway;

    // Core Domain Logic: Posting a Voucher updates Balances
    public void post(Voucher voucher) {
        if (!voucher.isBalanced()) {
            throw new RuntimeException("Voucher is not balanced");
        }

        String period = voucher.getAccountingDate().format(DateTimeFormatter.ofPattern("yyyy-MM"));

        for (VoucherLine line : voucher.getLines()) {
            Balance balance = balanceGateway.getByAccountAndPeriod(line.getAccountCode(), period);

            if ("D".equalsIgnoreCase(line.getDirection())) {
                balance.addDebit(line.getAmount());
            } else if ("C".equalsIgnoreCase(line.getDirection())) {
                balance.addCredit(line.getAmount());
            }

            balanceGateway.save(balance);
        }
    }
}
