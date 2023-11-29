package Repository;

import Model.ExchangeRateModel;

import java.util.*;

public class ExchangeRateRepository {
    private Map<String, List<ExchangeRateModel>> exchangeRates = new HashMap<>();

    // Добавление нового курса валют
    public void addExchangeRate(ExchangeRateModel exchangeRate) {
        String key = createKey(exchangeRate.getCurrencyFrom(), exchangeRate.getCurrencyTo());
        exchangeRates.computeIfAbsent(key, k -> new ArrayList<>()).add(exchangeRate);
    }

    // Получение текущего курса обмена
    public ExchangeRateModel getCurrentExchangeRate(String currencyFrom, String currencyTo) {
        String key = createKey(currencyFrom, currencyTo);
        return exchangeRates.getOrDefault(key, new ArrayList<>()).stream()
                .max(Comparator.comparing(ExchangeRateModel::getTimestamp))
                .orElse(null);
    }

    // Получение истории курсов обмена
    public List<ExchangeRateModel> getExchangeRateHistory(String currencyFrom, String currencyTo) {
        String key = createKey(currencyFrom, currencyTo);
        return new ArrayList<>(exchangeRates.getOrDefault(key, new ArrayList<>()));
    }

    // Создание ключа для Map
    private String createKey(String currencyFrom, String currencyTo) {
        return currencyFrom + "_" + currencyTo;
    }

    private void updateExchangeRate(ExchangeRateModel exchangeRate) {
        String key = createKey(exchangeRate.getCurrencyFrom(), exchangeRate.getCurrencyTo());
        exchangeRates.computeIfAbsent(key, k -> new ArrayList<>()).add(exchangeRate);
    }

}
