package com.emilianomenendez.veritran.bankaccount.withdrawal;

import com.emilianomenendez.veritran.money.Dollars;
import com.emilianomenendez.veritran.money.Number;

public class LowerLimit implements WithdrawalLimit {
    private final Number limit;

    public LowerLimit(Number limit) {
        this.limit = limit;
    }

    @Override
    public boolean reached(Number availableFunds, Dollars amountToWithdraw) {
        return availableFunds.minus(amountToWithdraw).isLessThan(limit);
    }
}
