package ar.com.flow.bankaccount;

import ar.com.flow.bankaccount.transaction.Step;
import ar.com.flow.bankaccount.transaction.Transaction;
import ar.com.flow.bankaccount.transaction.receipt.Action;
import ar.com.flow.bankaccount.transaction.receipt.DebitPrinter;
import ar.com.flow.bankaccount.transaction.receipt.ReceiptPrinter;
import ar.com.flow.bankaccount.withdrawal.SufficientFunds;
import ar.com.flow.money.Money;

public class Withdrawal {
    public static WithdrawalBuilder from(BankAccount debitAccount) {
        return new WithdrawalBuilder(debitAccount);
    }

    public static class WithdrawalBuilder {
        private final BankAccount debitAccount;

        public WithdrawalBuilder(BankAccount debitAccount) {
            this.debitAccount = debitAccount;
        }

        public Transaction amount(Money amountToWithdraw) {
            return Transaction.builder()
                    .amount(amountToWithdraw)
                    .precondition(new SufficientFunds(debitAccount, amountToWithdraw))
                    .step(new Step(debitAccount, Withdrawal.receipt(debitAccount)))
                    .build();
        }
    }

    public static ReceiptPrinter receipt(BankAccount account) {
        return new DebitPrinter(account, Action.Withdrawal);
    }
}
