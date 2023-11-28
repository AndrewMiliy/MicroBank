package Repository;

import Model.CurrencyModel;

import java.util.Map;
import java.util.TreeMap;

public class CurrencyRepository {
    private Map<String, CurrencyModel> currencies = new TreeMap<>();

    public void addCurrency(CurrencyModel currency) {
        currencies.put(currency.getCodeName(), currency);
    }

    public CurrencyModel getCurrency(String code) {
        return currencies.get(code);
    }

    public void updateCurrency(String code, CurrencyModel currency) {
        currencies.put(code, currency);
    }

    public void deleteCurrency(String code) {
        currencies.remove(code);
    }
}