package com.emilianomenendez.veritran.bankaccount;

import com.emilianomenendez.veritran.Customer;
import com.emilianomenendez.veritran.bankaccount.withdrawal.LowerLimit;
import com.emilianomenendez.veritran.bankaccount.withdrawal.WithdrawalLimit;
import com.emilianomenendez.veritran.money.Money;

import static com.emilianomenendez.veritran.bankaccount.money.TestObjects.*;
import static java.time.LocalDateTime.now;

public class TestObjects {
    public static LowerLimit minus100DollarsLimit = new LowerLimit(Balance.negative(dollars100));

    public static AccountMovement movement1 = new AccountMovement(now(), dollars10);
    public static AccountMovement movement2 = new AccountMovement(now(), dollars20);

    public static BankAccount createSavingsAccountFor(Customer accountOwner, Money initialBalance) {
        return SavingsAccount.ownedBy(accountOwner)
                .initialBalance(initialBalance)
                .build();
    }

    public static BankAccount createCheckingAccountFor(Customer accountOwner,
                                                       Money initialBalance,
                                                       WithdrawalLimit withdrawalLimit) {
        return SavingsAccount.ownedBy(accountOwner)
                .initialBalance(initialBalance)
                .withdrawalLimit(withdrawalLimit)
                .build();
    }
}
