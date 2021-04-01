package com.emilianomenendez.veritran.bankaccount.withdrawal;

public class LowerLimit implements WithdrawalLimit {
    private final int limit;

    public LowerLimit(int limit) {
        this.limit = limit;
    }

    @Override
    public boolean accepts(Withdrawal withdrawal) {
        return withdrawal.previewBalanceAfter().getAmount() >= limit;
    }
}
