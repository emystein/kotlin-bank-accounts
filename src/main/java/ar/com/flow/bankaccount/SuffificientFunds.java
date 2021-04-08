package ar.com.flow.bankaccount;

import ar.com.flow.bankaccount.withdrawal.WithdrawalLimit;
import ar.com.flow.money.InsufficientFundsException;
import ar.com.flow.money.Money;

public class SuffificientFunds implements Preconditions {
    private final BankAccount debitAccount;
    private final Money amount;
    private final WithdrawalLimit withdrawalLimit;

    public SuffificientFunds(BankAccount debitAccount, Money amount, WithdrawalLimit withdrawalLimit) {
        this.debitAccount = debitAccount;
        this.amount = amount;
        this.withdrawalLimit = withdrawalLimit;
    }

    public void check() {
        if (!withdrawalLimit.accepts(debitAccount, amount)) {
            throw new InsufficientFundsException();
        }
    }
}
