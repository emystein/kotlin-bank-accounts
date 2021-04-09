package ar.com.flow.bankaccount.transaction.transfer;

import ar.com.flow.bankaccount.BankAccount;
import ar.com.flow.bankaccount.Deposit;
import ar.com.flow.bankaccount.transaction.Algorithm;
import ar.com.flow.bankaccount.transaction.Action;
import ar.com.flow.bankaccount.transaction.withdrawal.Withdrawal;
import ar.com.flow.money.Money;

public class TransferFunds implements Algorithm {
    private final BankAccount debitAccount;
    private final BankAccount creditAccount;
    private final Money amount;

    public TransferFunds(BankAccount debitAccount, BankAccount creditAccount, Money amount) {
        this.debitAccount = debitAccount;
        this.creditAccount = creditAccount;
        this.amount = amount;
    }

    @Override
    public void execute() {
        Withdrawal.from(debitAccount)
                .reason(Action.Transfer)
                .limit(debitAccount.getWithdrawalLimit())
                .amount(amount)
                .execute();

        Deposit.to(creditAccount)
                .reason(Action.Transfer)
                .amount(amount)
                .execute();
    }
}
