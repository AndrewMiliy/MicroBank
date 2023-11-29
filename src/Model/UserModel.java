package Model;

import Service.IDCounterService;

import java.util.List;

public class UserModel {
    private String firstName;
    private String lastName;
    private String password;
    private String email;
    private List<String> bankAccountId;
    private String id;
    private UserRole userRole;
    public UserModel(String firstName, String lastName, String password, String email) {
        this.id = IDCounterService.getNextUserId();
        this.userRole = UserRole.USER;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<String> getBankAccountId() {
        return bankAccountId;
    }

    public void setBankAccountId(List<String> bankAccountId) {
        this.bankAccountId = bankAccountId;
    }

    public String getId() {
        return id;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }
}
