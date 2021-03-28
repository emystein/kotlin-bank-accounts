package com.emilianomenendez.veritran.bankaccount.withdrawal;

import com.emilianomenendez.veritran.bankaccount.Balance;
import com.emilianomenendez.veritran.bankaccount.BankAccount;
import com.emilianomenendez.veritran.money.Dollars;
import com.emilianomenendez.veritran.money.InsufficientFundsException;

public class Withdrawal {
    private final BankAccount debitAccount;

    public static Withdrawal from(BankAccount debitAccount) {
        return new Withdrawal(debitAccount);
    }

    private Withdrawal(BankAccount debitAccount) {
        this.debitAccount = debitAccount;
    }

    public Balance amount(Dollars amountToWithdraw) {
        if (!debitAccount.hasSufficientFundsForWithdraw(amountToWithdraw)) {
            throw new InsufficientFundsException();
        }

        return debitAccount.getBalance().minus(amountToWithdraw);
    }
}
