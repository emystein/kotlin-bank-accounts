package ar.com.flow.bankaccount.transaction.withdrawal;

import ar.com.flow.bankaccount.BankAccount;
import ar.com.flow.bankaccount.transaction.Action;
import ar.com.flow.bankaccount.transaction.Debit;
import ar.com.flow.bankaccount.transaction.Step;
import ar.com.flow.bankaccount.transaction.Transaction;
import ar.com.flow.money.Money;

public class Withdrawal {
    public static WithdrawalBuilder from(BankAccount debitAccount) {
        return new WithdrawalBuilder(debitAccount);
    }

    public static class WithdrawalBuilder {
        private final BankAccount debitAccount;
        private Money amountToWithdraw;

        public WithdrawalBuilder(BankAccount debitAccount) {
            this.debitAccount = debitAccount;
        }

        public WithdrawalBuilder amount(Money amountToWithdraw) {
            this.amountToWithdraw = amountToWithdraw;
            return this;
        }

        public Transaction build() {
            return Transaction.builder()
                    .amount(amountToWithdraw)
                    .precondition(new SufficientFunds(debitAccount, amountToWithdraw))
                    .step(new Step(new Debit(debitAccount, Action.Withdrawal), debitAccount))
                    .build();
        }
    }
}
