package com.emilianomenendez.veritran.bankaccount;

import com.emilianomenendez.veritran.Customer;
import com.emilianomenendez.veritran.money.Dollars;

public interface BankAccount {
    boolean isOwnedBy(Customer owner);

    Balance getInitialBalance();

    Balance getBalance();

    void deposit(Dollars amountToDeposit);

    boolean acceptsWithdrawAmount(Dollars amountToWithdraw);

    void withdraw(Dollars amountToWithdraw);

    void transfer(BankAccount creditAccount, Dollars amountToTransfer);
}
