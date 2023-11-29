package Service;


import Model.ExchangeRateModel;
import Repository.ExchangeRateRepository;

public class ExchangeRateService {

    ExchangeRateRepository exchangeRateRepository;

    public ExchangeRateService(ExchangeRateRepository exchangeRateRepository) {
        this.exchangeRateRepository = exchangeRateRepository;
    }

    public ExchangeRateModel getCurrentExchangeRate(String currencyFrom, String currencyTo) {
        var exchangeRate = exchangeRateRepository.getCurrentExchangeRate(currencyFrom, currencyTo);

        if(exchangeRate == null) {
            exchangeRate = new ExchangeRateModel(currencyFrom, currencyTo, 1.0);
            addExchangeRate(exchangeRate);
        }
        return exchangeRate;
    }

    public void addExchangeRate(ExchangeRateModel exchangeRate) {
        exchangeRateRepository.addExchangeRate(exchangeRate);
    }
}
