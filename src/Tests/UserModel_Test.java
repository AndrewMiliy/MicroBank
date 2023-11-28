package Tests;

import Model.UserModel;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class UserModel_Test {

    private UserModel user;

    @Before
    public void setUp() {

        user = new UserModel("John", "Doe", "strongPassword", "john.doe@example.com");
    }

    @Test
    public void testGetFirstName() {
        assertEquals("John", user.getFirstName());
    }

    @Test
    public void testSetFirstName() {
        user.setFirstName("Jane");
        assertEquals("Jane", user.getFirstName());
    }

    @Test
    public void testGetLastName() {
        assertEquals("Doe", user.getLastName());
    }

    @Test
    public void testSetLastName() {
        user.setLastName("Smith");
        assertEquals("Smith", user.getLastName());
    }

    @Test
    public void testGetPassword() {
        assertEquals("strongPassword", user.getPassword());
    }

    @Test
    public void testSetPassword() {
        user.setPassword("newPassword");
        assertEquals("newPassword", user.getPassword());
    }

    @Test
    public void testGetEmail() {
        assertEquals("john.doe@example.com", user.getEmail());
    }

    @Test
    public void testSetEmail() {
        user.setEmail("jane.smith@example.com");
        assertEquals("jane.smith@example.com", user.getEmail());
    }

    @Test
    public void testGetBankAccountId() {
        assertNull(user.getBankAccountId());
    }

    @Test
    public void testSetBankAccountId() {
        List<String> bankAccountIds = new ArrayList<>();
        bankAccountIds.add("123456");
        bankAccountIds.add("789012");
        user.setBankAccountId(bankAccountIds);
        assertEquals(bankAccountIds, user.getBankAccountId());
    }
}