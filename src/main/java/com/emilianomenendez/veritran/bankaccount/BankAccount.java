package com.emilianomenendez.veritran.bankaccount;

import com.emilianomenendez.veritran.Customer;
import com.emilianomenendez.veritran.bankaccount.withdrawal.Withdrawal;
import com.emilianomenendez.veritran.money.PositiveAmount;

public interface BankAccount {
    boolean isOwnedBy(Customer owner);

    Balance getInitialBalance();

    Balance getBalance();

    AccountMovement deposit(PositiveAmount amountToDeposit);

    boolean withdrawalLimitAccepts(Withdrawal withdrawal);

    AccountMovement withdraw(PositiveAmount amountToWithdraw);

    void transfer(BankAccount creditAccount, PositiveAmount amountToTransfer);
}
