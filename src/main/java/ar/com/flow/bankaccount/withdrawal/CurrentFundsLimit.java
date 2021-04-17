package ar.com.flow.bankaccount.withdrawal;

import ar.com.flow.bankaccount.BankAccount;
import ar.com.flow.money.Money;

public class CurrentFundsLimit implements WithdrawalLimit {
    public boolean accepts(BankAccount account, Money amount) {
        var previewBalanceAfter = account.getBalance().minus(amount);

        return previewBalanceAfter.isGreaterThanOrEqual(amount);
    }
}
