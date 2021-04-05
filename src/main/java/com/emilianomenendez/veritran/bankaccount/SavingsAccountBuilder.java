package com.emilianomenendez.veritran.bankaccount;

import com.emilianomenendez.veritran.Customer;
import com.emilianomenendez.veritran.bankaccount.withdrawal.CurrentFundsLimit;
import com.emilianomenendez.veritran.bankaccount.withdrawal.WithdrawalLimit;
import com.emilianomenendez.veritran.money.Money;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SavingsAccountBuilder {
    private final Customer accountOwner;
    private String currency = "USD";
    private Money initialBalance;
    private WithdrawalLimit withdrawalLimit = new CurrentFundsLimit();

    public SavingsAccountBuilder currency(String currency) {
        this.currency = currency;

        return this;
    }

    public SavingsAccountBuilder initialBalance(Money initialBalance) {
        this.initialBalance = initialBalance;

        return this;
    }

    public SavingsAccountBuilder withdrawalLimit(WithdrawalLimit withdrawalLimit) {
        this.withdrawalLimit = withdrawalLimit;

        return this;
    }

    public SavingsAccount build() {
        var accountHistory = new InMemoryTransactionHistory();

        var account = new SavingsAccount(accountOwner, currency, withdrawalLimit, accountHistory);

        if (initialBalance != null) {
            account.deposit(initialBalance);
        }

        return account;
    }
}
