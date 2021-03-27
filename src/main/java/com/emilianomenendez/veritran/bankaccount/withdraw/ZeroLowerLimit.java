package com.emilianomenendez.veritran.bankaccount.withdraw;

import com.emilianomenendez.veritran.bankaccount.Balance;
import com.emilianomenendez.veritran.money.Dollars;

public class ZeroLowerLimit implements WithdrawLimit {
    @Override
    public boolean reached(Balance balance, Dollars amountToWithdraw) {
        return balance.isLessThan(amountToWithdraw);
    }
}
