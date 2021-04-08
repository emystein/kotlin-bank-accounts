package ar.com.flow.bankaccount;

import ar.com.flow.bankaccount.withdrawal.WithdrawalLimit;
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
