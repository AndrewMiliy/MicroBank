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
