package com.emilianomenendez.veritran.bankaccount.withdrawal;

import com.emilianomenendez.veritran.bankaccount.Balance;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class LowerLimit implements WithdrawalLimit {
    private final Balance limit;

    public boolean accepts(Withdrawal withdrawal) {
        return withdrawal.previewBalanceAfter().isGreaterThanOrEqual(limit);
    }
}
