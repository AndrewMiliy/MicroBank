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


}
