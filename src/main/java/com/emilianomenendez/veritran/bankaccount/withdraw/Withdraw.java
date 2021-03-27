package com.emilianomenendez.veritran.bankaccount.withdraw;

import com.emilianomenendez.veritran.bankaccount.Balance;
import com.emilianomenendez.veritran.bankaccount.BankAccount;
import com.emilianomenendez.veritran.money.Dollars;
import com.emilianomenendez.veritran.money.InsufficientFundsException;

public class Withdraw {
    private final BankAccount debitAccount;

    public static Withdraw from(BankAccount debitAccount) {
        return new Withdraw(debitAccount);
    }

    public Withdraw(BankAccount debitAccount) {
        this.debitAccount = debitAccount;
    }

    public Balance amount(Dollars amountToWithdraw) {
        WithdrawLimit limit = debitAccount.getWithdrawLimit();

        if (limit.reached(debitAccount.getBalance(), amountToWithdraw)) {
            throw new InsufficientFundsException();
        }

        return this.debitAccount.getBalance().minus(amountToWithdraw);
    }
}
