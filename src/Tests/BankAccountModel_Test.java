package Tests;

import Model.BankAccountModel;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class BankAccountModel_Test {

    private BankAccountModel bankAccount;

    @Before
    public void setUp() {
        // Create a BankAccountModel instance before each test
        bankAccount = new BankAccountModel("bankAccountId", "userId", "USD");
    }

    @Test
    public void testGetBankAccountId() {
        assertNull(bankAccount.getBankAccountId());
    }

    @Test
    public void testGetCurrencyCode() {
        assertEquals("USD", bankAccount.getCurrencyCode());
    }

    @Test
    public void testGetBalance() {
        assertEquals(0.0, bankAccount.getBalance(), 0.001);
    }

    @Test
    public void testGetUserId() {
        assertNull(bankAccount.getUserId());
    }

    @Test
    public void testSetBalance() {
        bankAccount.setBalance(100.0);
        assertEquals(100.0, bankAccount.getBalance(), 0.001);
    }
}

