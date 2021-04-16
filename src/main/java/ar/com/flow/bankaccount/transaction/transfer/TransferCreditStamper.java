package ar.com.flow.bankaccount.transaction.transfer;

import ar.com.flow.bankaccount.BankAccount;
import ar.com.flow.bankaccount.transaction.Action;
import ar.com.flow.bankaccount.transaction.CreditStamper;
import ar.com.flow.bankaccount.transaction.ProofStamper;

public class TransferCreditStamper extends CreditStamper {
    public TransferCreditStamper(BankAccount creditAccount) {
        super(creditAccount, Action.Transfer);
    }
}
