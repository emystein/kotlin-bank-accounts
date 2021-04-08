package ar.com.flow.bankaccount.balance;

public class NegativeBalance extends Balance {
    public NegativeBalance(String currency, int amount) {
        super(currency, Math.min(-amount, amount));
    }
}
