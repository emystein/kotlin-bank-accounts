package com.emilianomenendez.veritran.bankaccount;

public class WithdrawBuilder {
    private final BankAccount debitAccount;

    public WithdrawBuilder(BankAccount debitAccount) {
        this.debitAccount = debitAccount;
    }

    public Withdraw limitedBy(WithdrawLimit withdrawLimit) {
        return new Withdraw(debitAccount, withdrawLimit);
    }
}
