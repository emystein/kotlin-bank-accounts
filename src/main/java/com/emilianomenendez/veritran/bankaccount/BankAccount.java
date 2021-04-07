package com.emilianomenendez.veritran.bankaccount;

import com.emilianomenendez.veritran.bankaccount.withdrawal.Withdrawal;
import com.emilianomenendez.veritran.money.Money;

public interface BankAccount {

    String getCurrency();

    Balance getInitialBalance();

    Balance getBalance();

    Balance getPreviousBalance();

    TransactionRecord deposit(Money amountToDeposit);

    TransactionRecord withdraw(Money amountToWithdraw);

    TransactionRecord transfer(BankAccount creditAccount, Money amountToTransfer);

    TransactionHistory getTransactionHistory();

    void addTransactionRecord(TransactionRecord transactionRecord);

    BalanceTimeline getBalanceTimeline();
}
