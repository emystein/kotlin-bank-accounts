package com.emilianomenendez.veritran.bankaccount.withdrawal;

public class CurrentFundsLimit implements WithdrawalLimit {
    @Override
    public boolean accepts(Withdrawal withdrawal) {
        return withdrawal.previewBalanceAfter().isGreaterThanOrEqual(withdrawal.getAmount());
    }
}
