package ar.com.flow.bankaccount;

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

    public BankAccountAssert balanceIncreasedBy(Money amount) {
        assertEquals(actual.getPreviousBalance().plus(amount), actual.getBalance());

        return this;
    }

    public BankAccountAssert balanceDecreasedBy(Money amount) {
        assertEquals(actual.getPreviousBalance().minus(amount), actual.getBalance());

        return this;
    }
}
