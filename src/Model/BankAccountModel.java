package Model;

import java.io.Serial;
import java.io.Serializable;

public class BankAccountModel implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private String bankAccountId; //номер счета
    private String userId; //айдишник пользователя

    private String currencyCode; //валюта счета
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

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public double getBalance() {
        return balance;
    }

    public String getFormattedBalance() {
        return String.format("%.2f", balance);
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
