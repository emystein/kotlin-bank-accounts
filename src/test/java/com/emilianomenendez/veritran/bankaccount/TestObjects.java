package com.emilianomenendez.veritran.bankaccount;

import com.emilianomenendez.veritran.Customer;
import com.emilianomenendez.veritran.bankaccount.withdrawal.LowerLimit;
import com.emilianomenendez.veritran.bankaccount.withdrawal.WithdrawalLimit;
import com.emilianomenendez.veritran.money.Dollars;

public class TestObjects {
    public static LowerLimit minus100DollarsLimit = new LowerLimit(Balance.negative(Dollars.amount(100)));

    public static SavingsAccount createSavingsAccountFor(Customer accountOwner, Dollars initialBalance) {
        return SavingsAccount.ownedBy(accountOwner)
                .withInitialBalance(initialBalance)
                .build();
    }

    public static BankAccount createCheckingAccountFor(Customer accountOwner,
                                                       Dollars initialBalance,
                                                       WithdrawalLimit withdrawalLimit) {
        return SavingsAccount.ownedBy(accountOwner)
                .withInitialBalance(initialBalance)
                .withWithdrawLimit(withdrawalLimit)
                .build();
    }
}
