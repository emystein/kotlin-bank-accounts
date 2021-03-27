package com.emilianomenendez.veritran.bankaccount;

import com.emilianomenendez.veritran.Customer;
import com.emilianomenendez.veritran.money.Dollars;

public class TestObjects {
    public static SavingsAccount createSavingsAccountFor(Customer accountOwner, Dollars initialBalance) {
        return SavingsAccount.ownedBy(accountOwner).withInitialBalance(initialBalance);
    }

    public static CheckingAccount createCheckingAccountFor(Customer accountOwner,
                                                           Dollars initialBalance, WithdrawLimit withdrawLimit) {
        return new CheckingAccount(accountOwner, initialBalance, withdrawLimit);
    }
}
