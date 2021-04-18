package ar.com.flow.bankaccount;

import ar.com.flow.bankaccount.transaction.Step;
import ar.com.flow.bankaccount.transaction.Transaction;
import ar.com.flow.bankaccount.transaction.receipt.Action;
import ar.com.flow.bankaccount.transaction.receipt.CreditPrinter;
import ar.com.flow.money.Money;

class Deposit {
    static DepositBuilder to(BankAccount creditAccount) {
        return new DepositBuilder(creditAccount);
    }

    static class DepositBuilder {
        private final BankAccount creditAccount;

        DepositBuilder(BankAccount creditAccount) {
            this.creditAccount = creditAccount;
        }

        Transaction amount(Money amountToDeposit) {
            return Transaction.builder()
                    .amount(amountToDeposit)
                    .step(new Step(creditAccount, Deposit.receipt(creditAccount)))
                    .build();
        }
    }

    static CreditPrinter receipt(BankAccount account) {
        return new CreditPrinter(account, Action.Deposit);
    }
}
