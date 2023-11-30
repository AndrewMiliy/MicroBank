package Model;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

public class TransactionModel  implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private String transactionId; // Уникальный идентификатор транзакции
    private String accountId;     // Идентификатор счета
    private Date date;            // Дата транзакции
    private double amount;        // Сумма транзакции
    private String currencyCode;  // Код валюты
    private TransactionType transactionType;          // Тип транзакции (например, пополнение, снятие)

    // Конструкторы, геттеры и сеттеры
    public TransactionModel(String transactionId, String accountId, Date date, double amount, String currencyCode, TransactionType transactionType) {
        this.transactionId = transactionId;
        this.accountId = accountId;
        this.date = date;
        this.amount = amount;
        this.currencyCode = currencyCode;
        this.transactionType = transactionType;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public TransactionType getType() {
        return transactionType;
    }

    public void setType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    @Override
    public String toString() {
        return "TransactionModel{" +
                "transactionId='" + transactionId + '\'' +
                ", accountId='" + accountId + '\'' +
                ", date=" + date +
                ", amount=" + amount +
                ", currencyCode='" + currencyCode + '\'' +
                ", transactionType='" + transactionType + '\'' +
                '}';
    }
}
