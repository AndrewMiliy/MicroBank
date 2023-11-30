package Service;

import Model.UserModel;
import Model.UserRole;
import Repository.UserRepository;

import java.util.List;
import java.util.Map;

public class UserService {
    private UserRepository uR;

    public UserService(UserRepository uR) {
        this.uR = uR;
    }

    public void setUserRole(UserModel user, UserRole role, UserModel admin) {
        if (admin.getUserRole() == UserRole.ADMIN) {
            user.setUserRole(role);
            uR.SaveData();
        } else {
            System.out.println("Only admin can edit user roles.");
        }
    }

    public UserModel register(String firstName, String lastName, String password, String email) {
        UserModel newUser = new UserModel(IDCounterService.getNextUserId(), firstName, lastName, password, email, uR.getUsers().size()>0?UserRole.USER:UserRole.ADMIN);
        uR.addUser(newUser);
        return newUser;
    }

    // Авторизация
    public UserModel authorize(String email, String password) {
        UserModel user = uR.getUserByEmail(email);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

    public UserModel getUserByEmail(String email) {
        return uR.getUserByEmail(email);
    }

    public List<UserModel> getAllUsers() {
        return uR.getAllUsers();
    }

    public Map<String, UserModel> getUsers() {
        return (Map<String, UserModel>) uR.getUsers();
    }

    public UserModel getUserBy(String userId) {
        return uR.getUser(userId);
    }


}
