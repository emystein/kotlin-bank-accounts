package com.emilianomenendez.veritran.bankaccount;

import com.emilianomenendez.veritran.Customer;
import com.emilianomenendez.veritran.bankaccount.withdrawal.CurrentFundsLimit;
import com.emilianomenendez.veritran.bankaccount.withdrawal.WithdrawalLimit;
import com.emilianomenendez.veritran.money.Money;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SavingsAccountBuilder {
    private final Customer accountOwner;
    private Money initialBalance;
    private WithdrawalLimit withdrawalLimit = new CurrentFundsLimit();
    private InMemoryTransactionHistory accountHistory = new InMemoryTransactionHistory();

    public SavingsAccountBuilder initialBalance(Money initialBalance) {
        this.initialBalance = initialBalance;

        return this;
    }

    public SavingsAccountBuilder withdrawalLimit(WithdrawalLimit withdrawalLimit) {
        this.withdrawalLimit = withdrawalLimit;

        return this;
    }

    public SavingsAccount build() {
        var account = new SavingsAccount(accountOwner, withdrawalLimit, accountHistory);
        account.deposit(initialBalance);
        return account;
    }
}
