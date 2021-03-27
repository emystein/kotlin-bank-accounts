package com.emilianomenendez.veritran.bankaccount;

import com.emilianomenendez.veritran.Customer;
import com.emilianomenendez.veritran.bankaccount.withdraw.WithdrawLimit;
import com.emilianomenendez.veritran.bankaccount.withdraw.BalanceLimit;
import com.emilianomenendez.veritran.money.Dollars;

public class SavingsAccountBuilder {
    private final Customer accountOwner;
    private Dollars initialBalance;
    private WithdrawLimit withdrawLimit = new BalanceLimit();

    public SavingsAccountBuilder(Customer accountOwner) {
        this.accountOwner = accountOwner;
    }

    public SavingsAccountBuilder withInitialBalance(Dollars initialBalance) {
        this.initialBalance = initialBalance;

        return this;
    }

    public SavingsAccountBuilder withWithdrawLimit(WithdrawLimit withdrawLimit) {
        this.withdrawLimit = withdrawLimit;

        return this;
    }

    public SavingsAccount build() {
        return new SavingsAccount(accountOwner, withdrawLimit, initialBalance);
    }
}
