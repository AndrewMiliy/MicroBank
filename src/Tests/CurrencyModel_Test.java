package Tests;

import Model.CurrencyModel;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

 public class CurrencyModel_Test {

    private CurrencyModel currency;

    @Before
    public void setUp() {
        currency = new CurrencyModel("US Dollar", "USD");
    }

    @Test
    public void testGetName() {
        assertEquals("US Dollar", currency.getName());
    }

    @Test
    public void testSetName() {
        currency.setName("Euro");
        assertEquals("Euro", currency.getName());
    }

    @Test
    public void testGetCodeName() {
        assertEquals("USD", currency.getCodeName());
    }

    @Test
    public void testSetCodeName() {
        currency.setCodeName("EUR");
        assertEquals("EUR", currency.getCodeName());
    }
}
