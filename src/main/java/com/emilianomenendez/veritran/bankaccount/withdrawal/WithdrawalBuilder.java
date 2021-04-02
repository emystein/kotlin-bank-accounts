package com.emilianomenendez.veritran.bankaccount.withdrawal;

import com.emilianomenendez.veritran.bankaccount.BankAccount;
import com.emilianomenendez.veritran.money.PositiveAmount;

public class WithdrawalBuilder {
    private final BankAccount debitAccount;

    public WithdrawalBuilder(BankAccount debitAccount) {
        this.debitAccount = debitAccount;
    }

    public Withdrawal amount(PositiveAmount amountToWithdraw) {
        return new Withdrawal(this.debitAccount, amountToWithdraw);
    }
}
