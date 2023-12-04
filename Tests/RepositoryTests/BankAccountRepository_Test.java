package RepositoryTests;

import Repository.BankAccountRepository;
import Model.BankAccountModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class BankAccountRepository_Test {

    private BankAccountRepository bankAccountRepository;

    @BeforeEach
    void setUp() {
        bankAccountRepository = new BankAccountRepository();
    }

    @Test
    void testAddAccount() {
        String userId = "user1";
        BankAccountModel account = new BankAccountModel("acc1", userId, "100.0");
        bankAccountRepository.addAccount(userId, account);

        List<BankAccountModel> accounts = bankAccountRepository.getAccountsByUser(userId);
        assertEquals(1, accounts.size());
        assertEquals(account, accounts.get(0));
    }

    @Test
    void testGetAccountsByUser() {
        // Arrange
        String userId = "user1";
        BankAccountModel account1 = new BankAccountModel("acc1", userId, "100.0");
        BankAccountModel account2 = new BankAccountModel("acc2", userId, "200.0");
        bankAccountRepository.addAccount(userId, account1);
        bankAccountRepository.addAccount(userId, account2);
        List<BankAccountModel> accounts = bankAccountRepository.getAccountsByUser(userId);

        assertEquals(2, accounts.size());
        assertTrue(accounts.contains(account1));
        assertTrue(accounts.contains(account2));
    }

    @Test
    void testDeleteAccount() {

        String userId = "user1";
        String accountId = "acc1";
        BankAccountModel account = new BankAccountModel(accountId, userId, "100.0");
        bankAccountRepository.addAccount(userId, account);
        bankAccountRepository.deleteAccount(userId, accountId);

        List<BankAccountModel> accounts = bankAccountRepository.getAccountsByUser(userId);
        assertEquals(0, accounts.size());
    }

    @Test
    void testGetAccount() {
        String accountId = "acc1";
        BankAccountModel account = new BankAccountModel(accountId, "user1", "100.0");
        bankAccountRepository.addAccount("user1", account);

        BankAccountModel retrievedAccount = bankAccountRepository.getAccount(accountId);
        assertEquals(account, retrievedAccount);
    }

    @Test
    void testUpdateAccount() {
        String userId = "user1";
        String accountId = "acc1";
        BankAccountModel account = new BankAccountModel(accountId, userId, "100.0");
        bankAccountRepository.addAccount(userId, account);

        account.setBalance(150.0);
        bankAccountRepository.updateAccount(account);

        BankAccountModel updatedAccount = bankAccountRepository.getAccount(accountId);
        assertEquals(150.0, updatedAccount.getBalance());
    }

    @Test
    void testGetAllAccountsForUser() {

        String userId = "user1";
        BankAccountModel account1 = new BankAccountModel("acc1", userId, "100.0");
        BankAccountModel account2 = new BankAccountModel("acc2", userId, "200.0");
        bankAccountRepository.addAccount(userId, account1);
        bankAccountRepository.addAccount(userId, account2);

        List<BankAccountModel> accounts = bankAccountRepository.getAllAccountsForUser(userId);

        assertEquals(2, accounts.size());
        assertTrue(accounts.contains(account1));
        assertTrue(accounts.contains(account2));
    }

    @Test
    void testGetAccounts() {
        String userId = "user1";
        BankAccountModel account = new BankAccountModel("acc1", userId, "100.0");
        bankAccountRepository.addAccount(userId, account);

        Map<String, ?> allAccounts = bankAccountRepository.getAccounts();

        assertTrue(allAccounts.containsKey(userId));
        List<BankAccountModel> userAccounts = (List<BankAccountModel>) allAccounts.get(userId);
        assertEquals(1, userAccounts.size());
        assertEquals(account, userAccounts.get(0));
    }

    @Test
    void testSetAccounts() {

        String userId = "user1";
        BankAccountModel account = new BankAccountModel("acc1", userId, "100.0");
        Map<String, List<BankAccountModel>> accountsMap = Map.of(userId, List.of(account));

        bankAccountRepository.setAccounts(accountsMap);

        List<BankAccountModel> userAccounts = bankAccountRepository.getAccountsByUser(userId);
        assertEquals(1, userAccounts.size());
        assertEquals(account, userAccounts.get(0));
    }

}