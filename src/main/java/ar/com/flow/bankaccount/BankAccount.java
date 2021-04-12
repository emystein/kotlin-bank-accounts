package ar.com.flow.bankaccount;

import ar.com.flow.bankaccount.balance.Balance;
import ar.com.flow.bankaccount.transaction.TransactionRecord;
import ar.com.flow.bankaccount.transaction.withdrawal.WithdrawalLimit;
import ar.com.flow.money.Money;

public interface BankAccount {
    String getCurrency();

    Balance getInitialBalance();

    Balance getBalance();

    Balance getPreviousBalance();

    void deposit(Money amountToDeposit);

    void withdraw(Money amountToWithdraw);

    void transfer(BankAccount creditAccount, Money amountToTransfer);

    void addTransactionRecord(TransactionRecord transactionRecord);

    Statement getStatement();

    boolean withdrawalLimitSupports(Money amount);
}
