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
    private TransactionService tS;

    public BankAccountService(UserRepository uR, BankAccountRepository bAR
            , CurrencyRepository cR, TransactionService tR) {
        this.uR = uR;
        this.bAR = bAR;
        this.cR = cR;
        this.tS = tS;
    }

    public void addBankAccount(String userId, BankAccountModel account) {
            bAR.addAccount(userId, account);
    }

    public String checkBalance(BankAccountModel account) {
        return account.toString();
    }

   public void addBalance(BankAccountModel account, double money) {
       tS.deposit(account.getBankAccountId(),money);
    }

    public void deleteBankAccount(String userId, String accountId) {
        bAR.deleteAccount(userId, accountId);
    }

    public void transferBalance(String bankAccountIdFrom, String bankAccountIdTo, double money) {
        tS.exchangeCurrency(bankAccountIdFrom, bankAccountIdTo, money);
    }
}
