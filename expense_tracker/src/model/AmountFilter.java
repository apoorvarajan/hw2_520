package model;

import java.util.List;
import java.util.stream.Collectors;

public class AmountFilter implements TransactionFilter {

    private final double amountToFilter;

    public AmountFilter(double amountToFilter) {
        this.amountToFilter = amountToFilter;
    }

    @Override
    public List<Transaction> filter(List<Transaction> transactions) {
        return transactions.stream()
                .filter(transaction -> transaction.getAmount() == amountToFilter)
                .collect(Collectors.toList());
    }
}
