package com.emilianomenendez.veritran.bankaccount.withdrawal;

import com.emilianomenendez.veritran.money.Dollars;
import com.emilianomenendez.veritran.money.Number;

public class CurrentFundsLimit implements WithdrawalLimit {
    @Override
    public boolean reached(Number availableFunds, Dollars amountToWithdraw) {
        return availableFunds.isLessThan(amountToWithdraw);
    }
}
