package com.cola.gl.app.event.handler;

import com.cola.gl.domain.balance.gateway.ContactBalanceGateway;
import com.cola.gl.domain.balance.model.ContactBalance;
import com.cola.gl.domain.voucher.event.VoucherSubmittedEvent;
import com.cola.gl.domain.voucher.model.Voucher;
import com.cola.gl.domain.voucher.model.VoucherLine;
import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;

@Component
@AllArgsConstructor
public class ContactBalanceHandler {

    private final ContactBalanceGateway contactBalanceGateway;

    @EventListener
    public void handleVoucherSubmitted(VoucherSubmittedEvent event) {
        Voucher voucher = event.getVoucher();
        String period = voucher.getAccountingDate().format(DateTimeFormatter.ofPattern("yyyy-MM"));

        for (VoucherLine line : voucher.getLines()) {
            if (line.getContactCode() != null && !line.getContactCode().isEmpty()) {
                ContactBalance contactBalance = contactBalanceGateway.getByAccountAndContact(
                        line.getAccountCode(), line.getContactCode(), period);

                if (contactBalance == null) {
                    contactBalance = new ContactBalance();
                    contactBalance.setAccountCode(line.getAccountCode());
                    contactBalance.setContactCode(line.getContactCode());
                    contactBalance.setPeriod(period);
                }

                if ("D".equalsIgnoreCase(line.getDirection())) {
                    contactBalance.addDebit(line.getAmount());
                } else if ("C".equalsIgnoreCase(line.getDirection())) {
                    contactBalance.addCredit(line.getAmount());
                }

                contactBalanceGateway.save(contactBalance);
            }
        }
    }
}
