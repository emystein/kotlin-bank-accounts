package com.emilianomenendez.veritran.bankaccount.withdrawal;

import com.emilianomenendez.veritran.bankaccount.BankAccount;
import com.emilianomenendez.veritran.money.Dollars;

public class WithdrawalBuilder {
    private final BankAccount debitAccount;

    public WithdrawalBuilder(BankAccount debitAccount) {
        this.debitAccount = debitAccount;
    }

    public Withdrawal amount(Dollars amountToWithdraw) {
        return new Withdrawal(this.debitAccount, amountToWithdraw);
    }
}
