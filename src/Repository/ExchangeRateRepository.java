package Repository;

import Model.ExchangeRateModel;

import java.util.*;

public class ExchangeRateRepository {
    private Map<String, List<ExchangeRateModel>> exchangeRates = new HashMap<>();

    // Добавление нового курса валют
    public void addExchangeRate(ExchangeRateModel exchangeRate) {
        String key = createKey(exchangeRate.getCurrencyFrom(), exchangeRate.getCurrencyTo());
        exchangeRates.computeIfAbsent(key, k -> new ArrayList<>()).add(exchangeRate);
        SaveData();
    }

    // Получение текущего курса обмена
    public ExchangeRateModel getCurrentExchangeRate(String currencyFrom, String currencyTo) {
        String key = createKey(currencyFrom, currencyTo);
        var rate = exchangeRates.getOrDefault(key, new ArrayList<>()).stream()
                .max(Comparator.comparing(ExchangeRateModel::getTimestamp))
                .orElse(null);
        return rate;
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
        SaveData();
    }

    public Map<String,?> getExchangeRates() {
        return exchangeRates;
    }

    public void setExchangeRates(Map<String,?> exchangeRates) {
        this.exchangeRates = (Map<String, List<ExchangeRateModel>>) exchangeRates;
        SaveData();
    }

    private void SaveData() {
        DataPersistenceManager.saveData(exchangeRates, DataPersistenceManager.EXCHANGE_RATE_DATA_FILE);
    }
}
