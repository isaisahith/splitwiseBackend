package com.example.splitwise.strategies;

import com.example.splitwise.models.Expense;
import com.example.splitwise.models.Transaction;
import com.example.splitwise.models.User;
import com.example.splitwise.models.UserExpense;
import org.springframework.data.util.Pair;

import java.util.*;

public class SimpleSettleUpStrategy implements SettleUpStrategy {

    @Override
    public List<Transaction> settleUp(List<Expense> expenseList) {
        Map<User, Integer> netBalances = calculateNetBalances(expenseList);

        // Min-heap for users who owe money (debtors)
        PriorityQueue<Pair<User, Integer>> minHeap = new PriorityQueue<>(Comparator.comparingInt(Pair::getSecond));

        // Max-heap for users who are owed money (creditors)
        PriorityQueue<Pair<User, Integer>> maxHeap = new PriorityQueue<>((a, b) -> b.getSecond() - a.getSecond());

        for (Map.Entry<User, Integer> entry : netBalances.entrySet()) {
            User user = entry.getKey();
            int balance = entry.getValue();

            if (balance < 0) {
                minHeap.add(Pair.of(user, balance)); // debtor
            } else if (balance > 0) {
                maxHeap.add(Pair.of(user, balance)); // creditor
            }
        }

        List<Transaction> transactions = new ArrayList<>();

        while (!minHeap.isEmpty() && !maxHeap.isEmpty()) {
            Pair<User, Integer> debtorPair = minHeap.poll();
            Pair<User, Integer> creditorPair = maxHeap.poll();

            User debtor = debtorPair.getFirst();
            int debtorBalance = debtorPair.getSecond(); // negative value

            User creditor = creditorPair.getFirst();
            int creditorBalance = creditorPair.getSecond(); // positive value

            int settledAmount = Math.min(-debtorBalance, creditorBalance);

            // Create transaction
            Transaction transaction = new Transaction();
            transaction.setFromUser(debtor.getEmail());
            transaction.setToUser(creditor.getEmail());
            transaction.setAmount(settledAmount);
            transactions.add(transaction);

            // Update remaining balances
            int newDebtorBalance = debtorBalance + settledAmount;
            int newCreditorBalance = creditorBalance - settledAmount;

            if (newDebtorBalance != 0) {
                minHeap.add(Pair.of(debtor, newDebtorBalance));
            }

            if (newCreditorBalance != 0) {
                maxHeap.add(Pair.of(creditor, newCreditorBalance));
            }
        }

        return transactions;
    }

    private Map<User, Integer> calculateNetBalances(List<Expense> expenseList) {
        Map<User, Integer> balanceMap = new HashMap<>();

        for (Expense expense : expenseList) {
            // Add paid amounts
            for (UserExpense ue : expense.getPaidBy()) {
                balanceMap.put(
                        ue.getUser(),
                        balanceMap.getOrDefault(ue.getUser(), 0) + ue.getAmount()
                );
            }

            // Subtract owed amounts
            for (UserExpense ue : expense.getPaidFor()) {
                balanceMap.put(
                        ue.getUser(),
                        balanceMap.getOrDefault(ue.getUser(), 0) - ue.getAmount()
                );
            }
        }

        return balanceMap;
    }
}
