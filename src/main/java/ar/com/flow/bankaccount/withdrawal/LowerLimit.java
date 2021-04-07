package ar.com.flow.bankaccount.withdrawal;

import ar.com.flow.bankaccount.Balance;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class LowerLimit implements WithdrawalLimit {
    private final Balance limit;

    public boolean accepts(Withdrawal withdrawal) {
        return withdrawal.previewBalanceAfter().isGreaterThanOrEqual(limit);
    }
}
