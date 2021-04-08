package ar.com.flow.bankaccount.withdrawal;

import ar.com.flow.bankaccount.Balance;
import ar.com.flow.bankaccount.BankAccount;
import ar.com.flow.money.Money;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class LowerLimit implements WithdrawalLimit {
    private final Balance limit;

    public boolean accepts(BankAccount account, Money amount) {
        var previewBalanceAfter = account.getBalance().minus(amount);

        return previewBalanceAfter.isGreaterThanOrEqual(limit);
    }
}
