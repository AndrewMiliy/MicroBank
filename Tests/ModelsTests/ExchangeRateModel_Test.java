package ModelsTests;
import Model.ExchangeRateModel;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class ExchangeRateModel_Test {

    private ExchangeRateModel exchangeRate;

    @Before
    public void setUp() {
        exchangeRate = new ExchangeRateModel("USD", "EUR", 0.85);
    }

    @Test
    public void testGetCurrencyFrom() {
        assertEquals("USD", exchangeRate.getCurrencyFrom());
    }

    @Test
    public void testSetCurrencyFrom() {
        exchangeRate.setCurrencyFrom("GBP");
        assertEquals("GBP", exchangeRate.getCurrencyFrom());
    }

    @Test
    public void testGetCurrencyTo() {
        assertEquals("EUR", exchangeRate.getCurrencyTo());
    }

    @Test
    public void testSetCurrencyTo() {
        exchangeRate.setCurrencyTo("JPY");
        assertEquals("JPY", exchangeRate.getCurrencyTo());
    }

    @Test
    public void testGetRate() {
        assertEquals(0.85, exchangeRate.getRate(), 0.001);
    }

    @Test
    public void testSetRate() {
        exchangeRate.setRate(1.0);
        assertEquals(1.0, exchangeRate.getRate(), 0.001);
    }

    @Test
    public void testToString() {
        String expectedString = "ExchangeRateModel{currencyFrom='USD', currencyTo='EUR', rate=0.85}";
        assertEquals(expectedString, exchangeRate.toString());
    }

}