package com.cola.gl.api.dto.cmd;

import com.cola.gl.api.dto.VoucherDTO;
import com.alibaba.cola.dto.Command;
import lombok.Data;

@Data
public class VoucherAddCmd extends Command {

    private VoucherDTO voucherDTO;
}
