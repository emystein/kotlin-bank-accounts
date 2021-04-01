package com.emilianomenendez.veritran.bankaccount;

import com.emilianomenendez.veritran.Customer;
import com.emilianomenendez.veritran.bankaccount.withdrawal.CurrentFundsLimit;
import com.emilianomenendez.veritran.bankaccount.withdrawal.WithdrawalLimit;
import com.emilianomenendez.veritran.money.Dollars;

public class SavingsAccountBuilder {
    private final Customer accountOwner;
    private Dollars initialBalance;
    private WithdrawalLimit withdrawalLimit = new CurrentFundsLimit();

    public SavingsAccountBuilder(Customer accountOwner) {
        this.accountOwner = accountOwner;
    }

    public SavingsAccountBuilder withInitialBalance(Dollars initialBalance) {
        this.initialBalance = initialBalance;

        return this;
    }

    public SavingsAccountBuilder withWithdrawLimit(WithdrawalLimit withdrawalLimit) {
        this.withdrawalLimit = withdrawalLimit;

        return this;
    }

    public SavingsAccount build() {
        return new SavingsAccount(accountOwner, withdrawalLimit, initialBalance, new InMemoryAccountHistory());
    }
}
