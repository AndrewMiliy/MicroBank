package Service;

import Model.BankAccountModel;
import Repository.BankAccountRepository;
import Repository.CurrencyRepository;
import Repository.UserRepository;

import java.util.List;
import java.util.Map;

public class BankAccountService {
    private static UserRepository uR;
    private static BankAccountRepository bAR;
    private CurrencyRepository cR;
    private TransactionService tS;

    public BankAccountService(UserRepository uR, BankAccountRepository bAR
            , CurrencyRepository cR) {
        this.uR = uR;
        this.bAR = bAR;
        this.cR = cR;
    }

    public static Map<String, List<BankAccountModel>> getAllAccounts() {
        return bAR.getAccounts();
    }


    public static void addBankAccount(String userId, BankAccountModel account) {
        bAR.addAccount(userId, account);
        uR.addBankAccount(userId, account);
    }

    public String checkBalance(BankAccountModel account) {
        return account.toString();
    }



    public void deleteBankAccount(String userId, String accountId) {
        bAR.deleteAccount(userId, accountId);
        uR.deleteBankAccount(userId, accountId);
    }

    public static List<BankAccountModel> getAllAccountsForUser(String userId) {
        return bAR.getAllAccountsForUser(userId);
    }

    public BankAccountModel getAccount(String accountId) {
        return bAR.getAccount(accountId);
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
