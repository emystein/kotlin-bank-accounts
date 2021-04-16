package ar.com.flow.bankaccount;

import ar.com.flow.bankaccount.balance.Balance;
import ar.com.flow.money.Money;
import org.assertj.core.api.AbstractAssert;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BankAccountAssert extends AbstractAssert<BankAccountAssert, BankAccount> {
    public static BankAccountAssert assertThat(BankAccount account) {
        return new BankAccountAssert(account);
    }

    public BankAccountAssert(BankAccount account) {
        super(account, BankAccountAssert.class);
    }

    public BankAccountAssert keepsInitialBalance() {
        if (!actual.getBalance().equals(actual.getInitialBalance())) {
            failWithActualExpectedAndMessage(actual.getBalance(), actual.getInitialBalance(), "Balance should be equal to initial balance.");
        }

        return this;
    }

    public BankAccountAssert hasPositiveBalance(Money amount) {
        assertEquals(Balance.positive(amount), actual.getBalance());

        return this;
    }

    public BankAccountAssert increasedFunds(Money amount) {
        assertEquals(actual.getPreviousBalance().plus(amount), actual.getBalance());

        return this;
    }

    public BankAccountAssert decreasedFunds(Money amount) {
        assertEquals(actual.getPreviousBalance().minus(amount), actual.getBalance());

        return this;
    }

    public BankAccountAssert hasNegativeBalance(Money amount) {
        assertEquals(Balance.negative(amount), actual.getBalance());

        return this;
    }
}
