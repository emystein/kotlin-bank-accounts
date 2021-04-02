package com.emilianomenendez.veritran.bankaccount.withdrawal;

import com.emilianomenendez.veritran.bankaccount.BankAccount;
import com.emilianomenendez.veritran.money.PositiveAmount;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class WithdrawalBuilder {
    private final BankAccount debitAccount;

    public Withdrawal amount(PositiveAmount amountToWithdraw) {
        return new Withdrawal(this.debitAccount, amountToWithdraw);
    }
}
