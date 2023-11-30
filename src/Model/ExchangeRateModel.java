package Model;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

public class ExchangeRateModel implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private String currencyFrom; // Исходная валюта
    private String currencyTo;   // Целевая валюта
    private Date timestamp;           // Дата курса
    private double rate = 1;         // Курс обмена

    // Конструкторы, геттеры и сеттеры
    public ExchangeRateModel(String currencyFrom, String currencyTo, double rate) {
        this(currencyFrom, currencyTo, rate, null);
    }

    // Конструкторы, геттеры и сеттеры
    public ExchangeRateModel(String currencyFrom, String currencyTo, double rate, Date timestamp) {
        this.currencyFrom = currencyFrom;
        this.currencyTo = currencyTo;
        this.timestamp = timestamp;
        this.rate = rate;
    }

    public String getCurrencyFrom() {
        return currencyFrom;
    }

    public void setCurrencyFrom(String currencyFrom) {
        this.currencyFrom = currencyFrom;
    }

    public String getCurrencyTo() {
        return currencyTo;
    }

    public void setCurrencyTo(String currencyTo) {
        this.currencyTo = currencyTo;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }


    @Override
    public String toString() {
        return "ExchangeRateModel{" +
                "currencyFrom='" + currencyFrom + '\'' +
                ", currencyTo='" + currencyTo + '\'' +
                ", rate=" + rate +
                '}';
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}