package com.cola.gl.app.executor;

import com.alibaba.cola.dto.Response;
import com.cola.gl.api.dto.cmd.VoucherPostCmd;
import com.cola.gl.domain.voucher.domainservice.VoucherPostingService;
import com.cola.gl.domain.voucher.gateway.VoucherGateway;
import com.cola.gl.domain.voucher.model.Voucher;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@AllArgsConstructor
public class VoucherPostCmdExe {

    private final VoucherGateway voucherGateway;
    private final VoucherPostingService voucherPostingService;

    @Transactional
    public Response execute(VoucherPostCmd cmd) {
        Voucher voucher = voucherGateway.getById(cmd.getVoucherId());
        if (voucher == null) {
            return Response.buildFailure("404", "Voucher not found");
        }

        try {
            voucherPostingService.post(voucher);
        } catch (Exception e) {
            return Response.buildFailure("500", e.getMessage());
        }

        return Response.buildSuccess();
    }
}
