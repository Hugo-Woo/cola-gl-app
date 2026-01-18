package com.cola.gl.domain.voucher.model;

import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class VoucherTest {

    @Test
    public void testIsBalanced() {
        Voucher voucher = new Voucher();
        List<VoucherLine> lines = new ArrayList<>();

        VoucherLine line1 = new VoucherLine();
        line1.setDirection("D");
        line1.setAmount(new BigDecimal("100"));
        lines.add(line1);

        VoucherLine line2 = new VoucherLine();
        line2.setDirection("C");
        line2.setAmount(new BigDecimal("100"));
        lines.add(line2);

        voucher.setLines(lines);

        Assert.assertTrue(voucher.isBalanced());
    }

    @Test
    public void testIsNotBalanced() {
        Voucher voucher = new Voucher();
        List<VoucherLine> lines = new ArrayList<>();

        VoucherLine line1 = new VoucherLine();
        line1.setDirection("D");
        line1.setAmount(new BigDecimal("100"));
        lines.add(line1);

        VoucherLine line2 = new VoucherLine();
        line2.setDirection("C");
        line2.setAmount(new BigDecimal("90"));
        lines.add(line2);

        voucher.setLines(lines);

        Assert.assertFalse(voucher.isBalanced());
    }
}
