package Service;

import Model.UserModel;
import Model.UserRole;
import Repository.BankAccountRepository;
import Repository.CurrencyRepository;
import Repository.TransactionRepository;
import Repository.UserRepository;

public class UserService {
    private UserRepository uR;
    private BankAccountRepository bAR;
    private CurrencyRepository cR;
    private TransactionRepository tR;

    public UserService(UserRepository uR, BankAccountRepository bAR) {
        this.uR = uR;
        this.bAR = bAR;
    }

    public void setUserRole(UserModel user, UserRole role, UserModel admin) {
        if (admin.getUserRole() == UserRole.ADMIN) {
            user.setUserRole(role);
        } else {
            System.out.println("Only admin can edit user roles.");
        }
    }

    public void register(String email, String firstName, String lastName, String password) {
        UserModel newUser = new UserModel(email, password, firstName, lastName);
        uR.addUser(newUser);
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
