package com.cola.gl.app.executor;

import com.alibaba.cola.dto.Response;
import com.cola.gl.api.dto.cmd.VoucherSubmitCmd;
import com.cola.gl.domain.voucher.event.VoucherSubmittedEvent;
import com.cola.gl.domain.voucher.gateway.VoucherGateway;
import com.cola.gl.domain.voucher.model.Voucher;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class VoucherSubmitCmdExe {

    private final VoucherGateway voucherGateway;
    private final ApplicationEventPublisher eventPublisher;

    public Response execute(VoucherSubmitCmd cmd) {
        Voucher voucher = voucherGateway.getById(cmd.getVoucherId());
        if (voucher == null) {
            return Response.buildFailure("Voucher not found", "Voucher not found with id: " + cmd.getVoucherId());
        }

        // Validate voucher if needed...

        // Publish Event
        eventPublisher.publishEvent(new VoucherSubmittedEvent(voucher));

        return Response.buildSuccess();
    }
}
