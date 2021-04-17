package ar.com.flow.bankaccount;

import ar.com.flow.bankaccount.balance.Balance;
import ar.com.flow.bankaccount.transaction.receipt.Receipt;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class InMemoryStatement implements Statement {
    private List<Receipt> history = new ArrayList<>();

    public int total() {
        return history.size();
    }

    public Optional<Receipt> first() {
        return history.stream().findFirst();
    }

    public Optional<Receipt> last() {
        return history.stream().reduce((first, second) -> second);
    }

    public void add(Receipt receipt) {
        history.add(receipt);
    }

    public boolean contains(Receipt receipt) {
        return history.contains(receipt);
    }

    public boolean containsInOrder(Receipt... records) {
        List<Receipt> indexedMovements = Arrays.asList(records);

        return history.stream()
                .filter(indexedMovements::contains)
                .collect(toList())
                .equals(indexedMovements);
    }

    public Optional<Balance> getCurrentBalance() {
        return sum(total());
    }

    public Optional<Balance> getPreviousBalance() {
        return sum(total() - 1);
    }

    public Optional<Balance> getInitialBalance() {
        return first().map(Receipt::getAmount);
    }

    private Optional<Balance> sum(int numberOfTransactions) {
        return entries(numberOfTransactions)
                .map(Receipt::getAmount)
                .reduce(Balance::plus);
    }

    private Stream<Receipt> entries(int numberOfEntries) {
        if (!history.isEmpty()) {
            return history.stream().limit(numberOfEntries);
        } else {
            return Stream.empty();
        }
    }
}
