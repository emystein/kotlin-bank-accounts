package ar.com.flow.bankaccount;

import ar.com.flow.bankaccount.balance.Balance;
import ar.com.flow.bankaccount.balance.BalanceTimeline;
import ar.com.flow.bankaccount.transaction.TransactionRecord;
import ar.com.flow.bankaccount.transaction.withdrawal.WithdrawalLimit;
import ar.com.flow.money.Money;

public interface BankAccount {

    String getCurrency();

    Balance getInitialBalance();

    Balance getBalance();

    Balance getPreviousBalance();

    void deposit(Money amountToDeposit);

    WithdrawalLimit getWithdrawalLimit();

    void withdraw(Money amountToWithdraw);

    void transfer(BankAccount creditAccount, Money amountToTransfer);

    TransactionHistory getTransactionHistory();

    void addTransactionRecord(TransactionRecord transactionRecord);

    BalanceTimeline getBalanceTimeline();
}
