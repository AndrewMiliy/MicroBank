package Repository;

import Model.UserModel;

import java.util.HashMap;
import java.util.Map;

public class UserRepository {
    private Map<String, UserModel> users = new HashMap<>();

    public void addUser(String userEmail, UserModel user) {
        users.put(user.getId(), user);
    }

    public UserModel getUserById(String userId) {
        return users.get(userId);
    }

    public void deleteAccount(String userId, UserModel user) {
        users.put(userId, user);
    }

    public void updateUser(String userId, UserModel user) {
        users.put(userId, user);
    }
}
