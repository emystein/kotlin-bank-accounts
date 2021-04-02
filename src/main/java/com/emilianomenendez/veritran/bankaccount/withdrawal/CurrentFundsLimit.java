package com.emilianomenendez.veritran.bankaccount.withdrawal;

public class CurrentFundsLimit implements WithdrawalLimit {
    public boolean accepts(Withdrawal withdrawal) {
        return withdrawal.previewBalanceAfter().isGreaterThanOrEqual(withdrawal.getAmount());
    }
}
