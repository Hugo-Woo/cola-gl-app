package com.cola.gl.infrastructure.db;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@Table(name = "voucher")
public class VoucherDO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String voucherCode;
    private LocalDate accountingDate;
    private String description;

    @OneToMany(mappedBy = "voucherId", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<VoucherLineDO> lines;
}
