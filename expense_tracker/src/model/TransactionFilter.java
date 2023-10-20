package model;

import java.util.List;

public interface TransactionFilter {
    List<Integer> filter(List<Transaction> transactions);
}