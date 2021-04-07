package ar.com.flow.bankaccount;

public class PositiveBalance extends Balance {
    public PositiveBalance(String currency, int amount) {
        super(currency, amount);

        if (amount < 0) {
            throw new IllegalArgumentException();
        }
    }
}
