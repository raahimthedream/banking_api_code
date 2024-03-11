package com.banking.api.bankin_project.model;

public final class TransactionType {

    public static final String DEBIT = "DEBIT";
    public static final String CREDIT = "CREDIT";

    private TransactionType() {
        throw new IllegalStateException("Constant class");
    }
}
