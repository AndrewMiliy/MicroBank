package Repository;

import Model.CurrencyModel;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class CurrencyRepository {
    private Map<String, CurrencyModel> currencies = new TreeMap<>();

    public void addCurrency(CurrencyModel currency) {
        currencies.put(currency.getCodeName(), currency);
        SaveData();
    }

    public CurrencyModel getCurrency(String code) {
        return currencies.get(code);
    }

    public void updateCurrency(String code, CurrencyModel currency) {
        //удаление старой валюты
        deleteCurrency(code);
        //добавление новой валюты
        addCurrency(currency);
        SaveData();
    }

    public void deleteCurrency(String code) {
        currencies.remove(code);
        SaveData();
    }

    public List<CurrencyModel> getAllCurrencies() {
        return List.copyOf(currencies.values());
    }

    public Map<String,?> getCurrencies() {
        return currencies;
    }

    public void setCurrencies(Map<String,?> currencies) {
        this.currencies = (Map<String, CurrencyModel>) currencies;
        SaveData();
    }

    private void SaveData() {
        DataPersistenceManager.saveData(currencies, DataPersistenceManager.CURRENCY_DATA_FILE);
    }


}