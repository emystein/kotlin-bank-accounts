package com.emilianomenendez.veritran.bankaccount.withdrawal;

import com.emilianomenendez.veritran.money.Number;

public class LowerLimit implements WithdrawalLimit {
    private final Number limit;

    public LowerLimit(Number limit) {
        this.limit = limit;
    }

    @Override
    public boolean accepts(Withdrawal withdrawal) {
        return withdrawal.previewBalanceAfter().isGreaterThanOrEqual(limit);
    }
}
