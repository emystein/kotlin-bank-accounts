package ar.com.flow.bankaccount.transaction.withdrawal;

import ar.com.flow.bankaccount.BankAccount;
import ar.com.flow.bankaccount.transaction.Preconditions;
import ar.com.flow.money.InsufficientFundsException;
import ar.com.flow.money.Money;

public class SufficientFunds implements Preconditions {
    private final BankAccount account;
    private final Money amount;
    private final WithdrawalLimit withdrawalLimit;

    public SufficientFunds(BankAccount account, Money amount) {
        this.account = account;
        this.amount = amount;
        this.withdrawalLimit = account.getWithdrawalLimit();
    }

    public void check() {
        if (!withdrawalLimit.accepts(account, amount)) {
            throw new InsufficientFundsException();
        }
    }
}
