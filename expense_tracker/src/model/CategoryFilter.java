package model;

import java.util.List;
import java.util.stream.Collectors;

public class CategoryFilter implements TransactionFilter {

    private final String categoryToFilter;

    public CategoryFilter(String categoryToFilter) {
        this.categoryToFilter = categoryToFilter;
    }

    @Override
    public List<Transaction> filter(List<Transaction> transactions) {
        return transactions.stream()
                .filter(transaction -> transaction.getCategory().equalsIgnoreCase(categoryToFilter))
                .collect(Collectors.toList());
    }
}
