package com.cola.gl.api.dto.cmd;

import com.alibaba.cola.dto.Command;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class VoucherSubmitCmd extends Command {
    private Long voucherId;
}
