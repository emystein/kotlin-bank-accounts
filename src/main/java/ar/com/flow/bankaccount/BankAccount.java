package ar.com.flow.bankaccount;

import ar.com.flow.bankaccount.withdrawal.WithdrawalLimit;
import ar.com.flow.money.Money;

public interface BankAccount {

    String getCurrency();

    Balance getInitialBalance();

    Balance getBalance();

    Balance getPreviousBalance();

    TransactionRecord deposit(Money amountToDeposit);

    WithdrawalLimit getWithdrawalLimit();

    TransactionRecord withdraw(Money amountToWithdraw);

    TransactionRecord transfer(BankAccount creditAccount, Money amountToTransfer);

    TransactionHistory getTransactionHistory();

    void addTransactionRecord(TransactionRecord transactionRecord);

    BalanceTimeline getBalanceTimeline();
}
