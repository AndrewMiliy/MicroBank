package Repository;

import Model.UserModel;

import java.util.HashMap;
import java.util.Map;

public class UserRepository {
    private Map<String, UserModel> users = new HashMap<>();

    public void addUser(UserModel user) {
        users.put(user.getId(), user);
    }

    public UserModel getUser(String userId) {
        return users.get(userId);
    }
    public UserModel getUserByEmail (String email) { return users.get(email);}

    public void deleteUser(String userId, UserModel user) {
        users.put(userId, user);
    }

    public void updateUser(String userId, UserModel user) {
        users.put(userId, user);
    }
}
