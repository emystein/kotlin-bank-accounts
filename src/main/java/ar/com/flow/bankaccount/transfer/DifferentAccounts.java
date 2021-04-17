package ar.com.flow.bankaccount.transfer;

import ar.com.flow.bankaccount.BankAccount;
import ar.com.flow.bankaccount.transaction.Precondition;

public class DifferentAccounts implements Precondition {
    private final BankAccount debitAccount;
    private final BankAccount creditAccount;

    public DifferentAccounts(BankAccount debitAccount, BankAccount creditAccount) {
        this.debitAccount = debitAccount;
        this.creditAccount = creditAccount;
    }

    public void check() {
        if (debitAccount.equals(creditAccount)) {
            throw new SameAccountException();
        }
    }
}
