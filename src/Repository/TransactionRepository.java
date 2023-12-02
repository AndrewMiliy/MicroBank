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
        SaveData();
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
        SaveData();
    }

    // Удаление транзакции
    public void deleteTransaction(String accountId, String transactionId) {
        List<TransactionModel> transactions = transactionsByAccount.get(accountId);
        if (transactions != null) {
            transactions.removeIf(transaction -> transaction.getTransactionId().equals(transactionId));
        }
        SaveData();
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

    public Map<String,?> getTransactions() {
        return transactionsByAccount;
    }

    public void setTransactions(Map<String,?> transactions) {
        this.transactionsByAccount = (Map<String, List<TransactionModel>>) transactions;
        SaveData();
    }

    private void SaveData() {
        DataPersistenceManager.saveData(transactionsByAccount, DataPersistenceManager.TRANSACTION_DATA_FILE);
    }

    public List<TransactionModel> getAllTransactionsByCurrencyCode(String code) {
        List<TransactionModel> filteredTransactions = new ArrayList<>();
        for (List<TransactionModel> transactions : transactionsByAccount.values()) {
            for (TransactionModel transaction : transactions) {
                if (transaction.getCurrencyCode().equals(code)) {
                    filteredTransactions.add(transaction);
                }
            }
        }
        return filteredTransactions;
    }
}
