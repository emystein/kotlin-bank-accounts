package ar.com.flow.bankaccount.transaction;

import ar.com.flow.bankaccount.BankAccount;
import ar.com.flow.money.Money;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;

public class Deposit {
    public static DepositBuilder to(BankAccount creditAccount) {
        return new DepositBuilder(creditAccount);
    }

    @RequiredArgsConstructor
    public static class DepositBuilder {
        private final BankAccount creditAccount;

        public Transaction amount(Money amountToDeposit) {
            var steps = new ArrayList<Step>();
            steps.add(new Step(new Credit(creditAccount, Action.Deposit), creditAccount));

            return Transaction.builder()
                    .amount(amountToDeposit)
                    .steps(steps)
                    .build();
        }
    }
}
