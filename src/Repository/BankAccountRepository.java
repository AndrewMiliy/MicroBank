package Repository;

import Model.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BankAccountRepository {
    private Map<String, List<BankAccountModel>> accounts = new HashMap<>();

    public void addAccount(String userId, BankAccountModel account)  {
        accounts.computeIfAbsent(userId, k -> new ArrayList<>()).add(account);
        SaveData();
    }

    public void deleteAccount(String userId, String accountId) {
        accounts.getOrDefault(userId, new ArrayList<>()).removeIf(account -> account.getBankAccountId().equals(accountId));
        SaveData();
    }

    public BankAccountModel getAccount(String accountId) {
        return accounts.values().stream()
                .flatMap(List::stream)
                .filter(account -> account.getBankAccountId().equals(accountId))
                .findFirst()
                .orElse(null);
    }

    public void updateAccount(BankAccountModel account) {
        accounts.values().stream()
                .flatMap(List::stream)
                .filter(a -> a.getBankAccountId().equals(account.getBankAccountId()))
                .findFirst()
                .ifPresent(a -> {
                    a.setBalance(account.getBalance());
                });
        SaveData();
    }

    public List<BankAccountModel> getAllAccountsForUser(String userId) {
        return accounts.getOrDefault(userId, new ArrayList<>());
    }

    public Map<String, List<BankAccountModel>> getAccounts() {
        return accounts;
    }

    public void setAccounts(Map<String,?> accounts) {
        this.accounts = (Map<String, List<BankAccountModel>>) accounts;
        SaveData();
    }

    private void SaveData() {
        DataPersistenceManager.saveData(accounts, DataPersistenceManager.ACCOUNT_DATA_FILE);
    }

    public Map<String, BankAccountModel> getAllAccountsByCurrencyCode(String code) {
        Map<String, BankAccountModel> filteredAccounts = new HashMap<>();
        for (List<BankAccountModel> accounts : accounts.values()) {
            for (BankAccountModel account : accounts) {
                if (account.getCurrencyCode().equals(code)) {
                    filteredAccounts.put(account.getBankAccountId(), account);
                }
            }
        }
        return filteredAccounts;
    }
}
