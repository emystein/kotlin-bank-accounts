package com.emilianomenendez.veritran.bankaccount.withdraw;

import com.emilianomenendez.veritran.bankaccount.Balance;
import com.emilianomenendez.veritran.money.Dollars;
import com.emilianomenendez.veritran.money.Number;

public class NumberLowerLimit implements WithdrawLimit {
    private final Number limit;

    public NumberLowerLimit(Number limit) {
        this.limit = limit;
    }

    @Override
    public boolean reached(Balance balance, Dollars amountToWithdraw) {
        return balance.minus(amountToWithdraw).isLessThan(limit);
    }
}
