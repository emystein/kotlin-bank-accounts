package com.emilianomenendez.veritran.bankaccount.withdrawal;

import com.emilianomenendez.veritran.money.Dollars;
import com.emilianomenendez.veritran.money.Number;

public class CurrentFundsLimit implements WithdrawalLimit {
    @Override
    public boolean supports(Dollars amountToWithdraw, Number availableFunds) {
        return !availableFunds.isLessThan(amountToWithdraw);
    }
}
