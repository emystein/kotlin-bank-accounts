package ar.com.flow.bankaccount;

import ar.com.flow.money.Money;

public class DepositBuilder {
    private final BankAccount targetAccount;

    public DepositBuilder(BankAccount targetAccount) {
        this.targetAccount = targetAccount;
    }

    public Deposit amount(Money amountToDeposit) {
        return new Deposit(targetAccount, amountToDeposit);
    }
}
