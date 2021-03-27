package com.emilianomenendez.veritran.bankaccount;

import com.emilianomenendez.veritran.money.Dollars;
import com.emilianomenendez.veritran.money.InsufficientFundsException;

public class Withdraw {
    private final BankAccount bankAccount;
    private final WithdrawLimit limit;

    public static WithdrawBuilder from(BankAccount debitAccount) {
        return new WithdrawBuilder(debitAccount);
    }

    public Withdraw(BankAccount debitAccount, WithdrawLimit limit) {
        this.bankAccount = debitAccount;
        this.limit = limit;
    }

    public Balance amount(Dollars amountToWithdraw) {
        if (limit.reached(bankAccount.getBalance(), amountToWithdraw)) {
            throw new InsufficientFundsException();
        }

        return this.bankAccount.getBalance().minus(amountToWithdraw);
    }
}
