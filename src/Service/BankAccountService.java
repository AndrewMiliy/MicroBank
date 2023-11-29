package Service;

import Model.BankAccountModel;
import Repository.BankAccountRepository;
import Repository.CurrencyRepository;
import Repository.UserRepository;

import java.util.List;

public class BankAccountService {
    private UserRepository uR;
    private static BankAccountRepository bAR;
    private CurrencyRepository cR;
    private TransactionService tS;

    public BankAccountService(UserRepository uR, BankAccountRepository bAR
            , CurrencyRepository cR) {
        this.uR = uR;
        this.bAR = bAR;
        this.cR = cR;
    }

    public void addBankAccount(String userId, BankAccountModel account) {
            bAR.addAccount(userId, account);
    }

    public String checkBalance(BankAccountModel account) {
        return account.toString();
    }



    public void deleteBankAccount(String userId, String accountId) {
        bAR.deleteAccount(userId, accountId);
    }

    public static List<BankAccountModel> getAllAccountsForUser(String userId) {
        return bAR.getAllAccountsForUser(userId);
    }

    public BankAccountModel getAccount(String accountId) {
        return bAR.getAccount(accountId);
    }

}
