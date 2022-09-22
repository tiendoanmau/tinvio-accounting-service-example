package com.tinvio.accounting.model.dto;

import com.tinvio.accounting.model.enums.AccountStatus;
import com.tinvio.accounting.model.enums.AccountType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BusinessAccount {
    private Integer id;
    private AccountType accountType;
    private String permanentAccNumber;
    private String businessAccountName;
    private AccountStatus status;
    private String currency;
    private Flags flags;
    private BigDecimal balance;
    private BigDecimal holdBalance;
    private Integer supplierId;
    private Integer outletId;
    private String referenceId;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BusinessAccount that = (BusinessAccount) o;
        return id.equals(that.id) &&
                permanentAccNumber.equals(that.permanentAccNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, permanentAccNumber);
    }
}
