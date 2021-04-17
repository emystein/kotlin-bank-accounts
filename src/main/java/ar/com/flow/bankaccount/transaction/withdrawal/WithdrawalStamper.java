package ar.com.flow.bankaccount.transaction.withdrawal;

import ar.com.flow.bankaccount.BankAccount;
import ar.com.flow.bankaccount.transaction.Action;
import ar.com.flow.bankaccount.transaction.DebitStamper;

public class WithdrawalStamper extends DebitStamper {
    public WithdrawalStamper(BankAccount debitAccount) {
        super(debitAccount, Action.Withdrawal);
    }
}