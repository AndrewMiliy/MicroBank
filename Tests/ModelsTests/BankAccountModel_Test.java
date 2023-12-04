package ModelsTests;

import Model.BankAccountModel;
import org.junit.Test;
import static org.junit.Assert.*;

public class BankAccountModel_Test {

    @Test
    public void testGetBankAccountId() {
        BankAccountModel account = new BankAccountModel("123456", "user123", "USD");
        assertEquals("123456", account.getBankAccountId());
    }

    @Test
    public void testGetCurrencyCode() {
        BankAccountModel account = new BankAccountModel("123456", "user123", "USD");
        assertEquals("USD", account.getCurrencyCode());
    }

    @Test
    public void testGetBalance() {
        BankAccountModel account = new BankAccountModel("123456", "user123", "USD");
        assertEquals(0.0, account.getBalance(), 0.01); // Assuming initial balance is 0.0
    }

    @Test
    public void testGetFormattedBalance() {
        BankAccountModel account = new BankAccountModel("123456", "user123", "USD");
        assertEquals("0.00", account.getFormattedBalance());
    }

    @Test
    public void testGetUserId() {
        BankAccountModel account = new BankAccountModel("123456", "user123", "USD");
        assertEquals("user123", account.getUserId());
    }

    @Test
    public void testSetBalance() {
        BankAccountModel account = new BankAccountModel("123456", "user123", "USD");
        account.setBalance(100.0);
        assertEquals(100.0, account.getBalance(), 0.01);
    }

    @Test
    public void testToString() {
        BankAccountModel account = new BankAccountModel("123456", "user123", "USD");
        String expectedToString = "BankAccountModel{CurrencyCode='USD', Balance=0.0}";
        assertEquals(expectedToString, account.toString());
    }
}