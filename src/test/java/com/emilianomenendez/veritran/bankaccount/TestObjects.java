package com.emilianomenendez.veritran.bankaccount;

import com.emilianomenendez.veritran.Customer;
import com.emilianomenendez.veritran.bankaccount.withdraw.WithdrawLimit;
import com.emilianomenendez.veritran.money.Dollars;

public class TestObjects {
    public static SavingsAccount createSavingsAccountFor(Customer accountOwner, Dollars initialBalance) {
        return SavingsAccount.ownedBy(accountOwner)
                .withInitialBalance(initialBalance)
                .build();
    }

    public static BankAccount createCheckingAccountFor(Customer accountOwner,
                                                       Dollars initialBalance,
                                                       WithdrawLimit withdrawLimit) {
        return SavingsAccount.ownedBy(accountOwner)
                .withInitialBalance(initialBalance)
                .withWithdrawLimit(withdrawLimit)
                .build();
    }
}
