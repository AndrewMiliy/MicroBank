package Repository;

import Model.UserModel;

import java.util.HashMap;
import java.util.Map;

public class UserRepository {
    private Map<String, UserModel> users = new HashMap<>();

    public void addUser(UserModel user) {
        users.put(user.getId(), user);
    }

    public UserModel getUser(String id) {
        return users.get(id);
    }

    public void updateUser(String id, UserModel user) {
        users.put(id, user);
    }

    public void deleteUser(String id) {
        users.remove(id);
    }
}
