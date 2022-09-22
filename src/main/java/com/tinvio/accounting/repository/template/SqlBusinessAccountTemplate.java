package com.tinvio.accounting.repository.template;

public interface SqlBusinessAccountTemplate {
    String FIND_BY_ID = "SELECT fba.* FROM finance_business_accounts fba WHERE id = ?";
}
