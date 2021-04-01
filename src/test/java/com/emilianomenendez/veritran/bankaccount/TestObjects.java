package com.emilianomenendez.veritran.bankaccount;

import com.emilianomenendez.veritran.Customer;
import com.emilianomenendez.veritran.bankaccount.withdrawal.LowerLimit;
import com.emilianomenendez.veritran.bankaccount.withdrawal.WithdrawalLimit;
import com.emilianomenendez.veritran.money.Dollars;

import static com.emilianomenendez.veritran.bankaccount.money.TestObjects.dollars10;
import static com.emilianomenendez.veritran.bankaccount.money.TestObjects.dollars20;
import static java.time.LocalDateTime.now;

public class TestObjects {
    public static LowerLimit minus100DollarsLimit = new LowerLimit(Balance.negative(Dollars.amount(100)));

    public static AccountMovement movement1 = new AccountMovement(now(), dollars10);
    public static AccountMovement movement2 = new AccountMovement(now(), dollars20);

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
