package com.cola.gl.domain.voucher.gateway;

import com.cola.gl.domain.voucher.model.Voucher;

public interface VoucherGateway {
    void save(Voucher voucher);

    Voucher getById(Long id);
}
