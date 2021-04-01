package com.emilianomenendez.veritran.bankaccount;

import com.emilianomenendez.veritran.Customer;
import com.emilianomenendez.veritran.bankaccount.withdrawal.Withdrawal;
import com.emilianomenendez.veritran.money.Dollars;
import com.emilianomenendez.veritran.money.Money;

public interface BankAccount {
    boolean isOwnedBy(Customer owner);

    Money getInitialBalance();

    Money getBalance();

    AccountMovement deposit(Dollars amountToDeposit);

    boolean withdrawalLimitAccepts(Withdrawal withdrawal);

    AccountMovement withdraw(Dollars amountToWithdraw);

    void transfer(BankAccount creditAccount, Dollars amountToTransfer);
}
