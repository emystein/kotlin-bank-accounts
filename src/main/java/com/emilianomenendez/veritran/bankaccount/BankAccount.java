package com.emilianomenendez.veritran.bankaccount;

import com.emilianomenendez.veritran.Customer;
import com.emilianomenendez.veritran.bankaccount.withdrawal.Withdrawal;
import com.emilianomenendez.veritran.money.Dollars;

public interface BankAccount {
    boolean isOwnedBy(Customer owner);

    Balance getInitialBalance();

    Balance getBalance();

    AccountMovement deposit(Dollars amountToDeposit);

    boolean withdrawalLimitAccepts(Withdrawal withdrawal);

    AccountMovement withdraw(Dollars amountToWithdraw);

    void transfer(BankAccount creditAccount, Dollars amountToTransfer);
}
