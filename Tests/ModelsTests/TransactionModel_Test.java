package ModelsTests;
import Model.TransactionModel;
import Model.TransactionType;
import org.junit.Test;
import java.util.Date;
import static org.junit.Assert.*;

public class TransactionModel_Test {

    @Test
    public void testGetTransactionId() {
        TransactionModel transaction = new TransactionModel("123456", "account123", new Date(), 100.0, "USD", TransactionType.DEPOSIT);
        assertEquals("123456", transaction.getTransactionId());
    }

    @Test
    public void testSetTransactionId() {
        TransactionModel transaction = new TransactionModel("123456", "account123", new Date(), 100.0, "USD", TransactionType.DEPOSIT);
        transaction.setTransactionId("789012");
        assertEquals("789012", transaction.getTransactionId());
    }

    @Test
    public void testGetAccountId() {
        TransactionModel transaction = new TransactionModel("123456", "account123", new Date(), 100.0, "USD", TransactionType.DEPOSIT);
        assertEquals("account123", transaction.getAccountId());
    }

    @Test
    public void testSetAccountId() {
        TransactionModel transaction = new TransactionModel("123456", "account123", new Date(), 100.0, "USD", TransactionType.DEPOSIT);
        transaction.setAccountId("newAccount");
        assertEquals("newAccount", transaction.getAccountId());
    }

    @Test
    public void testGetDate() {
        Date date = new Date();
        TransactionModel transaction = new TransactionModel("123456", "account123", date, 100.0, "USD", TransactionType.DEPOSIT);
        assertEquals(date, transaction.getDate());
    }

    @Test
    public void testSetDate() {
        Date originalDate = new Date();
        TransactionModel transaction = new TransactionModel("123456", "account123", originalDate, 100.0, "USD", TransactionType.DEPOSIT);
        Date newDate = new Date();
        transaction.setDate(newDate);
        assertEquals(newDate, transaction.getDate());
    }

    @Test
    public void testGetAmount() {
        TransactionModel transaction = new TransactionModel("123456", "account123", new Date(), 100.0, "USD", TransactionType.DEPOSIT);
        assertEquals(100.0, transaction.getAmount(), 0.01);
    }

    @Test
    public void testSetAmount() {
        TransactionModel transaction = new TransactionModel("123456", "account123", new Date(), 100.0, "USD", TransactionType.DEPOSIT);
        transaction.setAmount(200.0);
        assertEquals(200.0, transaction.getAmount(), 0.01);
    }

    @Test
    public void testGetCurrencyCode() {
        TransactionModel transaction = new TransactionModel("123456", "account123", new Date(), 100.0, "USD", TransactionType.DEPOSIT);
        assertEquals("USD", transaction.getCurrencyCode());
    }

    @Test
    public void testSetCurrencyCode() {
        TransactionModel transaction = new TransactionModel("123456", "account123", new Date(), 100.0, "USD", TransactionType.DEPOSIT);
        transaction.setCurrencyCode("EUR");
        assertEquals("EUR", transaction.getCurrencyCode());
    }

    @Test
    public void testGetType() {
        TransactionModel transaction = new TransactionModel("123456", "account123", new Date(), 100.0, "USD", TransactionType.DEPOSIT);
        assertEquals(TransactionType.DEPOSIT, transaction.getType());
    }

    @Test
    public void testSetType() {
        TransactionModel transaction = new TransactionModel("123456", "account123", new Date(), 100.0, "USD", TransactionType.DEPOSIT);
        transaction.setType(TransactionType.TRANSFER);
        assertEquals(TransactionType.TRANSFER, transaction.getType());
    }

    @Test
    public void testToString() {
        TransactionModel transaction = new TransactionModel("123456", "account123", new Date(), 100.0, "USD", TransactionType.DEPOSIT);
        String expectedToString = "TransactionModel{transactionId='123456', accountId='account123', date=" + transaction.getDate() +
                ", amount=100.0, currencyCode='USD', transactionType='DEPOSIT'}";
        assertEquals(expectedToString, transaction.toString());
    }

    @Test
    public void testGetTransactionType() {
        TransactionModel transaction = new TransactionModel("123456", "account123", new Date(), 100.0, "USD", TransactionType.DEPOSIT);
        assertEquals(TransactionType.DEPOSIT, transaction.getTransactionType());
    }

    @Test
    public void testGetFormattedAmount() {
        TransactionModel transaction = new TransactionModel("123456", "account123", new Date(), 100.0, "USD", TransactionType.DEPOSIT);
        assertEquals("100.00", transaction.getFormattedAmount());
    }
}