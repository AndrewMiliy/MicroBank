package RepositoryTests;

import Model.CurrencyModel;
import Repository.CurrencyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.*;

class CurrencyRepository_Test {

    private CurrencyRepository currencyRepository;

    @BeforeEach
    void setUp() {
        currencyRepository = new CurrencyRepository();
    }

    @Test
    void testAddCurrency() {
        CurrencyModel currency = new CurrencyModel("US Dollar", "USD");
        currencyRepository.addCurrency(currency);

        CurrencyModel retrievedCurrency = currencyRepository.getCurrency("USD");
        assertEquals(currency, retrievedCurrency);
    }

    @Test
    void testGetCurrency() {
        CurrencyModel currency = new CurrencyModel("Euro", "EUR");
        currencyRepository.addCurrency(currency);

        CurrencyModel retrievedCurrency = currencyRepository.getCurrency("EUR");
        assertEquals(currency, retrievedCurrency);
    }

    @Test
    void testUpdateCurrency() {
        CurrencyModel currency = new CurrencyModel("British Pound", "GBP");
        currencyRepository.addCurrency(currency);
        CurrencyModel updatedCurrency = new CurrencyModel("Updated Pound", "GBP");
        currencyRepository.updateCurrency("GBP", updatedCurrency);

        CurrencyModel retrievedCurrency = currencyRepository.getCurrency("GBP");
        assertEquals(updatedCurrency, retrievedCurrency);
    }

    @Test
    void testDeleteCurrency() {
        CurrencyModel currency = new CurrencyModel("Japanese Yen", "JPY");
        currencyRepository.addCurrency(currency);
        currencyRepository.deleteCurrency("JPY");
        CurrencyModel retrievedCurrency = currencyRepository.getCurrency("JPY");
        assertNull(retrievedCurrency);
    }

    @Test
    void testGetAllCurrencies() {
        CurrencyModel currency1 = new CurrencyModel("US Dollar", "USD");
        CurrencyModel currency2 = new CurrencyModel("Euro", "EUR");
        currencyRepository.addCurrency(currency1);
        currencyRepository.addCurrency(currency2);

        List<CurrencyModel> currencies = currencyRepository.getAllCurrencies();
        assertEquals(2, currencies.size());
        assertTrue(currencies.contains(currency1));
        assertTrue(currencies.contains(currency2));
    }

    @Test
    void testGetCurrencies() {
        CurrencyModel currency1 = new CurrencyModel("US Dollar", "USD");
        CurrencyModel currency2 = new CurrencyModel("Euro", "EUR");
        currencyRepository.addCurrency(currency1);
        currencyRepository.addCurrency(currency2);

        Map<String, ?> allCurrencies = currencyRepository.getCurrencies();
        assertTrue(allCurrencies.containsKey("USD"));
        assertTrue(allCurrencies.containsKey("EUR"));
        assertEquals(currency1, allCurrencies.get("USD"));
        assertEquals(currency2, allCurrencies.get("EUR"));
    }

    @Test
    void testSetCurrencies() {
        CurrencyModel currency1 = new CurrencyModel("US Dollar", "USD");
        CurrencyModel currency2 = new CurrencyModel("Euro", "EUR");
        Map<String, CurrencyModel> currenciesMap = new TreeMap<>();
        currenciesMap.put("USD", currency1);
        currenciesMap.put("EUR", currency2);

        currencyRepository.setCurrencies(currenciesMap);
        List<CurrencyModel> retrievedCurrencies = currencyRepository.getAllCurrencies();
        assertEquals(2, retrievedCurrencies.size());
        assertTrue(retrievedCurrencies.contains(currency1));
        assertTrue(retrievedCurrencies.contains(currency2));
    }
}