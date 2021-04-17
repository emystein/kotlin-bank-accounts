package ar.com.flow.bankaccount.withdrawal;

import ar.com.flow.bankaccount.BankAccount;
import ar.com.flow.bankaccount.transaction.Precondition;
import ar.com.flow.money.InsufficientFundsException;
import ar.com.flow.money.Money;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SufficientFunds implements Precondition {
    private final BankAccount account;
    private final Money amount;

    public void check() {
        if (!account.withdrawalLimitSupports(amount)) {
            throw new InsufficientFundsException();
        }
    }
}
