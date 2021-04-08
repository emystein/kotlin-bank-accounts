package ar.com.flow.bankaccount;

import ar.com.flow.Customer;
import ar.com.flow.bankaccount.transfer.SameAccountTransferException;
import ar.com.flow.money.InsufficientFundsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static ar.com.flow.bankaccount.SavingsAccountAssertions.*;
import static ar.com.flow.money.TestObjects.*;
import static org.junit.jupiter.api.Assertions.*;

public class SavingsAccountTest {
    private Customer francisco;
    private BankAccount franciscosAccount;
    private Customer mabel;
    private BankAccount mabelsAccount;

    @BeforeEach
    void setUp() {
        francisco = Customer.named("francisco");
        franciscosAccount = TestObjects.createSavingsAccountFor(francisco, dollars100);
        mabel = Customer.named("mabel");
        mabelsAccount = TestObjects.createSavingsAccountFor(mabel, dollars100);
    }

    @Test
    void givenAnInitialStateWhenCreateABankAccountThenItShouldHaveBasicInformationSet() {
        var account = SavingsAccount.ownedBy(francisco).currency("ARS").build();

        assertEquals(francisco, account.getOwner());
        assertEquals("ARS", account.getCurrency());
        assertEquals(Balance.zero("ARS"), account.getBalance());
    }

    @Test
    void givenACustomerAndAnInitialAmountWhenCreateAnAccountThenTheAccountShouldHaveBalance() {
        assertAccountKeepsInitialBalance(franciscosAccount);
    }

    @Test
    void givenAnAccountWith100USDBalanceWhenDeposit10USDThenPreviousBalanceShouldBeInitialBalance() {
        franciscosAccount.deposit(dollars10);

        assertEquals(Balance.create(dollars100), franciscosAccount.getPreviousBalance());
    }

    @Test
    void givenAnAccountWith100USDBalanceWhenDeposit10USDThenBalanceShouldBe110USD() {
        franciscosAccount.deposit(dollars10);

        assertBalanceIncreasedBy(franciscosAccount, dollars10);
    }

    @Test
    void givenAnAccountWith100USDBalanceWhenWithdraw10USDThenBalanceShouldBe90USD() {
        franciscosAccount.withdraw(dollars10);

        assertBalanceDecreasedBy(franciscosAccount, dollars10);
    }

    @Test
    void givenAnAccountWith100USDBalanceWhenWithdraw200USDThenWithdrawalShouldBeRejected() {
        assertThrows(InsufficientFundsException.class, () -> franciscosAccount.withdraw(dollars200));

        assertAccountKeepsInitialBalance(franciscosAccount);
    }

    @Test
    void givenAnAccountWhenWithdrawThenTransactionHistoryShouldContainTheTransactionRecord() {
        franciscosAccount.withdraw(dollars10);

        var maybeDebitRecordFound = franciscosAccount.getTransactionHistory().stream()
                .filter(r -> r.getReason().equals(TransactionReason.Withdrawal))
                .findFirst();

        assertTrue(maybeDebitRecordFound.isPresent());
    }

    @Test
    void givenADebitAndCreditAccountsWhenTransfer10USDThenTheMoneyShouldBeTransferred() {
        franciscosAccount.transfer(mabelsAccount, dollars10);

        assertAmountMovedFromTo(franciscosAccount, mabelsAccount, dollars10);

        var maybeDebitRecordFound = franciscosAccount.getTransactionHistory().stream()
                .filter(r -> r.getReason().equals(TransactionReason.TransferDebit))
                .findFirst();
        assertTrue(maybeDebitRecordFound.isPresent());

        var maybeCreditRecordFound = mabelsAccount.getTransactionHistory().stream()
                .filter(r -> r.getReason().equals(TransactionReason.TransferCredit))
                .findFirst();
        assertTrue(maybeCreditRecordFound.isPresent());
    }

    @Test
    void givenADebitAndCreditAccountWhenTransferInsufficientFundsThenTheMoneyShouldNotBeTransferred() {
        assertThrows(InsufficientFundsException.class, () ->
                franciscosAccount.transfer(mabelsAccount, dollars200));

        assertAccountKeepsInitialBalance(franciscosAccount);
        assertAccountKeepsInitialBalance(mabelsAccount);
    }

    @Test
    void givenTheSameDebitAndCreditAccountWhenTransferThenTheTransferShouldBeRejected() {
        assertThrows(SameAccountTransferException.class, () ->
                franciscosAccount.transfer(franciscosAccount, dollars10));

        assertAccountKeepsInitialBalance(franciscosAccount);
    }
}
