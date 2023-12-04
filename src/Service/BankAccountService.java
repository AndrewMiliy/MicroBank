package Service;

import Model.BankAccountModel;
import Repository.BankAccountRepository;
import Repository.UserRepository;

import java.util.List;

public class BankAccountService {
    private static UserRepository uR;
    private static BankAccountRepository bAR;

    public BankAccountService(UserRepository uR, BankAccountRepository bAR) {
        BankAccountService.uR = uR;
        BankAccountService.bAR = bAR;
    }

    public static void addBankAccount(String userId, BankAccountModel account) {
        bAR.addAccount(userId, account);
        uR.addBankAccount(userId, account);
    }

    public void deleteBankAccount(String userId, String accountId) {
        bAR.deleteAccount(userId, accountId);
        uR.deleteBankAccount(userId, accountId);
    }

    public static List<BankAccountModel> getAllAccountsForUser(String userId) {
        return bAR.getAllAccountsForUser(userId);
    }

    public BankAccountModel getAccountByUserIdAndCurrencyCode(String userId, String currencyCode) {
        return bAR.getAllAccountsForUser(userId).stream()
                .filter(account -> account.getCurrencyCode().equals(currencyCode))
                .findFirst()
                .orElse(null);
    }

    public void updateCurrencyInBankAccounts(String code, String newCode) {
        bAR.getAllAccountsByCurrencyCode(code)
                .forEach((accountId, account) -> {
                    account.setCurrencyCode(newCode);
                    bAR.updateAccount(account);
                });
    }
}
