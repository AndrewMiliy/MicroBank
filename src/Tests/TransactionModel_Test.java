package Tests;

import Model.TransactionModel;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Date;

public class TransactionModel_Test {

    private TransactionModel transaction;

    @Before
    public void setUp() {

        Date currentDate = new Date();
        transaction = new TransactionModel("123456", "789012", currentDate, 100.0, "USD", "Deposit");
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
        assertEquals("Deposit", transaction.getType());
    }

    @Test
    public void testSetType() {
        transaction.setType("Withdrawal");
        assertEquals("Withdrawal", transaction.getType());
    }

    @Test
    public void testToString() {
        String expectedString = "TransactionModel{transactionId='123456', accountId='789012', date=" + transaction.getDate() + ", amount=100.0, currencyCode='USD', type='Deposit'}";
        assertEquals(expectedString, transaction.toString());
    }
}
