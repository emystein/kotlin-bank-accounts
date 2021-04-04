package com.emilianomenendez.veritran.bankaccount;

import com.emilianomenendez.veritran.money.Money;

public class DepositBuilder {
    private final BankAccount targetAccount;

    public DepositBuilder(BankAccount targetAccount) {
        this.targetAccount = targetAccount;
    }

    public Deposit amount(Money amountToDeposit) {
        return new Deposit(targetAccount, amountToDeposit);
    }
}
