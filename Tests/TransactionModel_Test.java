import Model.TransactionModel;
import Model.TransactionType;
import org.junit.Before;
import org.junit.Test;

import java.time.Instant;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class TransactionModel_Test {

    private TransactionModel transaction;

    @Before
    public void setUp() {

        Date currentDate = Date.from(Instant.now());
        transaction = new TransactionModel("123456", "789012", currentDate, 100.0, "USD", TransactionType.DEPOSIT);
    }

    @Test
    public void testGetTransactionId() {
        assertEquals("123456", transaction.getTransactionId());
    }

    @Test
    public void testSetTransactionId() {
        transaction.setTransactionId("789012");
        assertEquals("789012", transaction.getTransactionId());
    }

    @Test
    public void testGetAccountId() {
        assertEquals("789012", transaction.getAccountId());
    }

    @Test
    public void testSetAccountId() {
        transaction.setAccountId("345678");
        assertEquals("345678", transaction.getAccountId());
    }

    @Test
    public void testGetDate() {
        assertNotNull(transaction.getDate());
    }

    @Test
    public void testSetDate() {
        Date newDate = new Date();
        transaction.setDate(newDate);
        assertEquals(newDate, transaction.getDate());
    }

    @Test
    public void testGetAmount() {
        assertEquals(100.0, transaction.getAmount(), 0.001);
    }

    @Test
    public void testSetAmount() {
        transaction.setAmount(200.0);
        assertEquals(200.0, transaction.getAmount(), 0.001);
    }

    @Test
    public void testGetCurrencyCode() {
        assertEquals("USD", transaction.getCurrencyCode());
    }

    @Test
    public void testSetCurrencyCode() {
        transaction.setCurrencyCode("EUR");
        assertEquals("EUR", transaction.getCurrencyCode());
    }

    @Test
    public void testGetType() {
        assertEquals(TransactionType.DEPOSIT, transaction.getType());
    }

    @Test
    public void testSetType() {
        transaction.setType(TransactionType.DEPOSIT);
        assertEquals(TransactionType.DEPOSIT, transaction.getType());
    }
}
