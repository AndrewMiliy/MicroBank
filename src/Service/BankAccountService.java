package Service;

import Model.BankAccountModel;
import Repository.BankAccountRepository;
import Repository.CurrencyRepository;
import Repository.UserRepository;

public class BankAccountService {
    private UserRepository uR;
    private BankAccountRepository bAR;
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


}
