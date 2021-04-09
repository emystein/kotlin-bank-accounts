package ar.com.flow.bankaccount.transaction.withdrawal;

import ar.com.flow.bankaccount.BankAccount;
import ar.com.flow.bankaccount.transaction.ConcreteRecorder;
import ar.com.flow.bankaccount.transaction.Recorder;
import ar.com.flow.bankaccount.transaction.SingleAccountTransaction;
import ar.com.flow.bankaccount.transaction.Action;
import ar.com.flow.money.Money;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

public class Withdrawal {
    public static WithdrawalBuilder from(BankAccount debitAccount) {
        return new WithdrawalBuilder(debitAccount);
    }

    @RequiredArgsConstructor
    public static class WithdrawalBuilder {
        private final BankAccount debitAccount;
        private Optional<BankAccount> creditAccount = Optional.empty();
        private WithdrawalLimit withdrawalLimit = new CurrentFundsLimit();
        private Recorder recorder = new ConcreteRecorder();
        private Money amountToWithdraw;

        public WithdrawalBuilder limit(WithdrawalLimit aWithdrawalLimit) {
            withdrawalLimit = aWithdrawalLimit;

            return this;
        }

        public WithdrawalBuilder recorder(Recorder recorder) {
            this.recorder = recorder;
            return this;
        }

        public WithdrawalBuilder amount(Money amountToWithdraw) {
            this.amountToWithdraw = amountToWithdraw;
            return this;
        }

        public SingleAccountTransaction build() {
            return SingleAccountTransaction.builder()
                    .action(Action.Withdrawal)
                    .account(debitAccount)
                    .amount(amountToWithdraw)
                    .algorithm(new WithdrawalAlgorithm())
                    .preconditions(new SufficientFunds(debitAccount, amountToWithdraw, withdrawalLimit))
                    .recorder(recorder)
                    .build();
        }
    }
}
