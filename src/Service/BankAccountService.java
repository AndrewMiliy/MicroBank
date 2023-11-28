package Service;

import Model.*;
import Repository.BankAccountRepository;
import Repository.CurrencyRepository;
import Repository.TransactionRepository;
import Repository.UserRepository;

import java.time.LocalDate;

public class BankAccountService {
    private UserRepository uR;
    private BankAccountRepository bAR;
    private CurrencyRepository cR;
    private TransactionRepository tR;

    public BankAccountService(UserRepository uR, BankAccountRepository bAR
            , CurrencyRepository cR, TransactionRepository tR) {
        this.uR = uR;
        this.bAR = bAR;
        this.cR = cR;
        this.tR = tR;
    }

    public void addBankAccount(String userId, BankAccountModel account) {
            bAR.addAccount(userId, account);
    }

    public String checkBalance(BankAccountModel account) {
        return account.toString();
    }

 /*   public boolean addBalance(BankAccountModel account, double money, String code) {

        try {
            tR.addTransaction(account.getBankAccountId(), new TransactionModel(account.getBankAccountId(), LocalDate.now(), money, cR.getCurrency(code), ));
        }
    }
*/
    public void deleteBankAccount(String userId, String accountId) {
        bAR.deleteAccount(userId, accountId);
    }
}
