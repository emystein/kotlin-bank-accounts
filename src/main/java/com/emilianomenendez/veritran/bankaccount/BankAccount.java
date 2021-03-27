package com.emilianomenendez.veritran.bankaccount;

import com.emilianomenendez.veritran.Customer;
import com.emilianomenendez.veritran.bankaccount.withdraw.WithdrawLimit;
import com.emilianomenendez.veritran.money.Dollars;

public interface BankAccount {
    boolean isOwnedBy(Customer owner);

    WithdrawLimit getWithdrawLimit();

    Balance getInitialBalance();

    Balance getBalance();

    void deposit(Dollars amountToDeposit);

    void withdraw(Dollars amountToWithdraw);

    void transfer(BankAccount creditAccount, Dollars amountToTransfer);
}
