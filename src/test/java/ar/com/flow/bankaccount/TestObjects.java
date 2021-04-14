package ar.com.flow.bankaccount;

import ar.com.flow.Customer;
import ar.com.flow.bankaccount.balance.Balance;
import ar.com.flow.bankaccount.transaction.Action;
import ar.com.flow.bankaccount.transaction.TransactionRecord;
import ar.com.flow.bankaccount.transaction.withdrawal.LowerLimit;
import ar.com.flow.bankaccount.transaction.withdrawal.WithdrawalLimit;
import ar.com.flow.money.Money;

import static ar.com.flow.money.TestObjects.*;
import static java.time.LocalDateTime.now;

public class TestObjects {
    public static Customer francisco = Customer.named("francisco");
    public static Customer mabel = Customer.named("mabel");

    public static LowerLimit minusDollars100Limit = new LowerLimit(Balance.negative(dollars100));

    public static TransactionRecord dollars10Record = new TransactionRecord(now(), Action.Deposit, dollars10);
    public static TransactionRecord dollars20Record = new TransactionRecord(now(), Action.Deposit, dollars20);

    public static TransactionRecord minusDollars20Record = new TransactionRecord(now(), Action.Withdrawal, Balance.negative(dollars20));

    public static BankAccount createSavingsAccountFor(Customer accountOwner, Money initialBalance) {
        var account = SavingsAccount.builder().owner(accountOwner).currency("USD").build();

        account.deposit(initialBalance);

        return account;
    }

    public static BankAccount createCheckingAccountFor(Customer accountOwner,
                                                       Money initialBalance,
                                                       WithdrawalLimit withdrawalLimit) {
        var account = SavingsAccount.builder()
                .owner(accountOwner)
                .currency("USD")
                .withdrawalLimit(withdrawalLimit)
                .build();

        account.deposit(initialBalance);

        return account;
    }
}
