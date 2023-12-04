package Repository;

import Model.BankAccountModel;
import Model.UserModel;

import java.util.HashMap;
import java.util.Map;

public class UserRepository {
    private Map<String, UserModel> users = new HashMap<>();

    public void addUser(UserModel user) {
        users.put(user.getId(), user);
        SaveData();
    }

    public UserModel getUser(String userId) {
        return users.get(userId);
    }
    public UserModel getUserByEmail (String email) {
        return users.values().stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst()
                .orElse(null);
    }

    public Map<String,?> getUsers() {
        return users;
    }

    public void setUsers(Map<String,?> users) {
        this.users = (Map<String, UserModel>) users;
        SaveData();
    }

    public void SaveData() {
        DataPersistenceManager.saveData(users, DataPersistenceManager.USER_DATA_FILE);
    }

    public void addBankAccount(String userId, BankAccountModel account) {
        users.get(userId).getBankAccountId().add(account.getBankAccountId());
        SaveData();
    }

    public void deleteBankAccount(String userId, String accountId) {
        users.get(userId).getBankAccountId().removeIf(account -> account.equals(accountId));
        SaveData();
    }
}
