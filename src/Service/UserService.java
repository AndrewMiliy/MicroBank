package Service;

import Model.UserModel;
import Model.UserRole;
import Repository.UserRepository;

public class UserService {
    private UserRepository uR;

    public UserService(UserRepository uR) {
        this.uR = uR;
    }

    public void setUserRole(UserModel user, UserRole role, UserModel admin) {
        if (admin.getUserRole() == UserRole.ADMIN) {
            user.setUserRole(role);
        } else {
            System.out.println("Only admin can edit user roles.");
        }
    }

    public UserModel register(String firstName, String lastName, String password, String email) {
        UserModel newUser = new UserModel(firstName, lastName, password, email);
        uR.addUser(newUser);
        return newUser;
    }

    // Авторизация
    public UserModel authorize(String email, String password) {
        UserModel user = uR.getUserByEmail(email); // TODO требуется проверка.
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }
}
