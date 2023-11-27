package Repository;

import Model.TransactionModel;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class TransactionRepository {
    private Map<String, List<TransactionModel>> transactionsByAccount = new LinkedHashMap<>();

    // Добавление транзакции
    public void addTransaction(String accountId, TransactionModel transaction) {
        transactionsByAccount.computeIfAbsent(accountId, k -> new ArrayList<>()).add(transaction);
    }

    // Получение всех транзакций по идентификатору счета
    public List<TransactionModel> getTransactionsByAccount(String accountId) {
        return transactionsByAccount.getOrDefault(accountId, new ArrayList<>());
    }

    // Получение транзакции по идентификатору транзакции и счета
    public TransactionModel getTransactionById(String accountId, String transactionId) {
        List<TransactionModel> transactions = transactionsByAccount.get(accountId);
        if (transactions != null) {
            for (TransactionModel transaction : transactions) {
                if (transaction.getTransactionId().equals(transactionId)) {
                    return transaction;
                }
            }
        }
        return null;
    }

    // Обновление транзакции
    public void updateTransaction(String accountId, String transactionId, TransactionModel updatedTransaction) {
        List<TransactionModel> transactions = transactionsByAccount.get(accountId);
        if (transactions != null) {
            for (int i = 0; i < transactions.size(); i++) {
                if (transactions.get(i).getTransactionId().equals(transactionId)) {
                    transactions.set(i, updatedTransaction);
                    return;
                }
            }
        }
    }

    // Удаление транзакции
    public void deleteTransaction(String accountId, String transactionId) {
        List<TransactionModel> transactions = transactionsByAccount.get(accountId);
        if (transactions != null) {
            transactions.removeIf(transaction -> transaction.getTransactionId().equals(transactionId));
        }
    }

    // Получение всех транзакций
    public List<TransactionModel> getAllTransactions() {
        List<TransactionModel> allTransactions = new ArrayList<>();
        for (List<TransactionModel> transactions : transactionsByAccount.values()) {
            allTransactions.addAll(transactions);
        }
        return allTransactions;
    }

    // Фильтрация транзакций по определенному критерию (например, типу)
    public List<TransactionModel> getTransactionsByType(String accountId, String type) {
        List<TransactionModel> filteredTransactions = new ArrayList<>();
        List<TransactionModel> transactions = transactionsByAccount.get(accountId);
        if (transactions != null) {
            for (TransactionModel transaction : transactions) {
                if (transaction.getType().equals(type)) {
                    filteredTransactions.add(transaction);
                }
            }
        }
        return filteredTransactions;
    }
}
