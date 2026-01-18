package com.cola.gl.app.executor;

import com.alibaba.cola.dto.SingleResponse;
import com.cola.gl.api.dto.VoucherDTO;
import com.cola.gl.api.dto.VoucherLineDTO;
import com.cola.gl.api.dto.cmd.VoucherAddCmd;
import com.cola.gl.domain.voucher.gateway.VoucherGateway;
import com.cola.gl.domain.voucher.model.Voucher;
import com.cola.gl.domain.voucher.model.VoucherLine;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class VoucherAddCmdExe {

    private final VoucherGateway voucherGateway;

    public SingleResponse<Long> execute(VoucherAddCmd cmd) {
        VoucherDTO dto = cmd.getVoucherDTO();
        Voucher voucher = new Voucher();
        voucher.setVoucherCode(dto.getVoucherCode());
        voucher.setAccountingDate(dto.getAccountingDate());
        voucher.setDescription(dto.getDescription());

        if (dto.getLines() != null) {
            voucher.setLines(dto.getLines().stream().map(this::convertLine).collect(Collectors.toList()));
        }

        voucherGateway.save(voucher);
        return SingleResponse.of(voucher.getId());
    }

    private VoucherLine convertLine(VoucherLineDTO l) {
        VoucherLine line = new VoucherLine();
        line.setAccountCode(l.getAccountCode());
        line.setDirection(l.getDirection());
        line.setAmount(l.getAmount());
        line.setDescription(l.getDescription());
        line.setContactCode(l.getContactCode());
        return line;
    }
}
