# hw1- Manual Review

The homework will be based on this project named "Expense Tracker",where users will be able to add/remove daily transaction. 

## Compile

To compile the code from terminal, use the following command:
```
cd src
javac ExpenseTrackerApp.java
java ExpenseTracker
```

You should be able to view the GUI of the project upon successful compilation. 

## Java Version
This code is compiled with ```openjdk 17.0.7 2023-04-18```. Please update your JDK accordingly if you face any incompatibility issue.

Modularity [Open-CLosed Principle]:

1. The transactions field in ExpenseTrackerModel is made private to encapsulate it.
2. The getTransactions method in ExpenseTrackerModel returns an unmodifiable list to ensure immutability.
3. In the Transaction class, the fields are made private and final to achieve immutability.
4. Setter methods in the Transaction class are removed to prevent external modification.