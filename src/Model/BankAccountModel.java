package Model;

public class BankAccountModel {
    private String bankAccountId; //номер счета
    private String userId; //айдишник пользователя
    private final String currencyCode; //валюта счета
    private double balance; //баланс счёта

    public BankAccountModel(String bankAccountId, String userId, String currencyCode) {
        this.bankAccountId = bankAccountId;
        this.userId = userId;
        this.currencyCode = currencyCode;
    }

    public String getBankAccountId() {
        return bankAccountId;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public double getBalance() {
        return balance;
    }

    public String getUserId() {
        return userId;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "BankAccountModel{" +
                "CurrencyCode='" + currencyCode + '\'' +
                ", Balance=" + balance +
                '}';
    }
}
