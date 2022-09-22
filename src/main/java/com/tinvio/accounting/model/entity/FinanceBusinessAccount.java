package com.tinvio.accounting.model.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Map;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class FinanceBusinessAccount extends BaseEntity {

    @Id
    @Column(name = "id")
    private int id;

    @Column(name="pan")
    private String permanentAccNumber;

    @Column(name="account_type")
    private String accountType;

    @Column(name="name")
    private String businessAccountName;

    @Column(name="parent_id")
    private int parentId;

    @Column(name="status")
    private String status;

    @Column(name="currency")
    private String currency;

    @Column(name="balance")
    private BigDecimal balance;

    @Column(name="hold_balance")
    private BigDecimal holdBalance;

    @Column(name="remarks")
    private String remarks;

    @Column(name="reference_id")
    private String referenceId;

    @Type(type = "jsonb")
    @Column(name = "flags", columnDefinition = "jsonb")
    private Map<String, Boolean> flags;

    @Column(name = "fees_id")
    private Integer feesId;
}
