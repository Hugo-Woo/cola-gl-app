package com.cola.gl.api.service;

import com.alibaba.cola.dto.Response;
import com.cola.gl.api.dto.cmd.VoucherAddCmd;
import com.cola.gl.api.dto.cmd.VoucherPostCmd;

public interface VoucherServiceI {
    Response add(VoucherAddCmd cmd);

    Response post(VoucherPostCmd cmd);
}
