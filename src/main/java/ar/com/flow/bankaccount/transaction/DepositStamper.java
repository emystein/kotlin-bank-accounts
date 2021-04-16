package ar.com.flow.bankaccount.transaction;

import ar.com.flow.bankaccount.BankAccount;

public class DepositStamper extends CreditStamper {
    public DepositStamper(BankAccount account) {
        super(account, Action.Deposit);
    }
}
