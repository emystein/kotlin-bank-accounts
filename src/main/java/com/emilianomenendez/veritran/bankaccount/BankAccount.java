package com.emilianomenendez.veritran.bankaccount;

import com.emilianomenendez.veritran.Customer;
import com.emilianomenendez.veritran.bankaccount.withdrawal.Withdrawal;
import com.emilianomenendez.veritran.money.Money;

public interface BankAccount {
    boolean isOwnedBy(Customer owner);

    Balance getInitialBalance();

    Balance getBalance();

    Balance getPreviousBalance();

    TransactionRecord deposit(Money amountToDeposit);

    boolean withdrawalLimitAccepts(Withdrawal withdrawal);

    TransactionRecord withdraw(Money amountToWithdraw);

    TransactionRecord transfer(BankAccount creditAccount, Money amountToTransfer);

    TransactionHistory getTransactionHistory();
}
