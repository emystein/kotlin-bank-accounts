package ar.com.flow.bankaccount.transfer;

import ar.com.flow.bankaccount.BankAccount;
import ar.com.flow.bankaccount.Deposit;
import ar.com.flow.bankaccount.TransactionAlgorithm;
import ar.com.flow.bankaccount.TransactionReason;
import ar.com.flow.bankaccount.withdrawal.Withdrawal;
import ar.com.flow.money.Money;

public class TransferFunds implements TransactionAlgorithm {
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
                .reason(TransactionReason.TransferDebit)
                .limit(debitAccount.getWithdrawalLimit())
                .amount(amount)
                .execute();

        Deposit.to(creditAccount)
                .reason(TransactionReason.TransferCredit)
                .amount(amount)
                .execute();
    }
}
