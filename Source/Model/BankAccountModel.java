package Model;

public class BankAccountModel {
    private String bankAccountId; //номер счета
    private final String currencyCode; //валюта счета
    private double balance; //баланс счёта
    private String userId; //айдишник пользователя

    public BankAccountModel(String currencyCode) {
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
}
