package Model;

public class BankAccountModel {
    private String accountId; // Уникальный идентификатор счета
    private String userId;    // Идентификатор владельца счета
    private String currencyCode;    // Код валюты счета
    private double balance;      // Баланс счета

    // Конструкторы, геттеры и сеттеры
    public BankAccountModel(String accountId, String userId, String currencyCode, double balance) {
        this.accountId = accountId;
        this.userId = userId;
        this.currencyCode = currencyCode;
        this.balance = balance;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "BankAccountModel{" +
                "accountId='" + accountId + '\'' +
                ", userId='" + userId + '\'' +
                ", currencyCode='" + currencyCode + '\'' +
                ", balance=" + balance +
                '}';
    }
}


