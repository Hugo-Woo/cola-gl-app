package com.cola.gl.domain.voucher.event;

import com.cola.gl.domain.voucher.model.Voucher;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class VoucherSubmittedEvent {
    private Voucher voucher;
}
