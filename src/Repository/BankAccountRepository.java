package Repository;

import Model.BankAccountModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BankAccountRepository {
    private Map<String, List<BankAccountModel>> userAccounts = new HashMap<>();

    public void addAccount(String userId, BankAccountModel account) {
        userAccounts.computeIfAbsent(userId, k -> new ArrayList<>()).add(account);
    }

    public List<BankAccountModel> getAccountsByUser(String userId) {
        return userAccounts.getOrDefault(userId, new ArrayList<>());
    }

    public void deleteAccount(String userId, String accountId) {
        userAccounts.getOrDefault(userId, new ArrayList<>()).removeIf(account -> account.getBankAccountId().equals(accountId));
    }

    public BankAccountModel getAccount(String accountId) {
        return userAccounts.values().stream()
                .flatMap(List::stream)
                .filter(account -> account.getBankAccountId().equals(accountId))
                .findFirst()
                .orElse(null);
    }

    public void updateAccount(BankAccountModel account) {
        userAccounts.values().stream()
                .flatMap(List::stream)
                .filter(a -> a.getBankAccountId().equals(account.getBankAccountId()))
                .findFirst()
                .ifPresent(a -> {
                    a.setBalance(account.getBalance());
                });
    }
}
