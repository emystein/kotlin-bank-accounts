package com.emilianomenendez.veritran.bankaccount;

import com.emilianomenendez.veritran.Customer;
import com.emilianomenendez.veritran.bankaccount.withdrawal.LowerLimit;
import com.emilianomenendez.veritran.bankaccount.withdrawal.WithdrawalLimit;
import com.emilianomenendez.veritran.money.Money;

import static com.emilianomenendez.veritran.bankaccount.money.TestObjects.*;
import static java.time.LocalDateTime.now;

public class TestObjects {
    public static LowerLimit minusDollars100Limit = new LowerLimit(Balance.negative(dollars100));

    public static TransactionRecord dollars10Record = new TransactionRecord(now(), dollars10);
    public static TransactionRecord dollars20Record = new TransactionRecord(now(), dollars20);

    public static TransactionRecord minusDollars20Record = new TransactionRecord(now(), Balance.negative(dollars20));

    public static BankAccount createSavingsAccountFor(Customer accountOwner, Money initialBalance) {
        var account = SavingsAccount.ownedBy(accountOwner).build();

        account.deposit(initialBalance);

        return account;
    }

    public static BankAccount createCheckingAccountFor(Customer accountOwner,
                                                       Money initialBalance,
                                                       WithdrawalLimit withdrawalLimit) {
        var account = SavingsAccount.ownedBy(accountOwner)
                .withdrawalLimit(withdrawalLimit)
                .build();

        account.deposit(initialBalance);

        return account;
    }
}
