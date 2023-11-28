package Service;

import Model.BankAccountModel;
import Model.ExchangeRateModel;
import Model.TransactionModel;
import Model.TransactionType;
import Repository.BankAccountRepository;
import Repository.ExchangeRateRepository;
import Repository.TransactionRepository;

import java.time.Instant;
import java.util.Date;
import java.util.List;

public class TransactionService {
    private BankAccountRepository bankAccountRepository;
    private TransactionRepository transactionRepository;
    private ExchangeRateRepository exchangeRateRepository;

    public TransactionService(BankAccountRepository bankAccountRepository, TransactionRepository transactionRepository, ExchangeRateRepository exchangeRateRepository) {
        this.bankAccountRepository = bankAccountRepository;
        this.transactionRepository = transactionRepository;
        this.exchangeRateRepository = exchangeRateRepository;
    }

    // Пополнение счета
    public void deposit(String accountId, double amount) {
        try {
            BankAccountModel account = bankAccountRepository.getAccount(accountId);
            if (account == null) {
                throw new IllegalArgumentException("Счет не найден: " + accountId);
            }
            account.setBalance(account.getBalance() + amount);
            bankAccountRepository.updateAccount(account);
            transactionRepository.addTransaction(accountId,
                    new TransactionModel(IDCounterService.getNextTransactionID(),
                    accountId,
                    Date.from(Instant.now()),
                    amount,
                    account.getCurrencyCode(),
                    TransactionType.DEPOSIT));
        } catch (Exception e) {
            System.err.println("Ошибка при пополнении счета: " + e.getMessage());
        }
    }

    // Снятие средств со счета
    public void withdraw(String accountId, double amount) {
        try {
            BankAccountModel account = bankAccountRepository.getAccount(accountId);
            if (account == null) {
                throw new IllegalArgumentException("Счет не найден: " + accountId);
            }
            if (account.getBalance() < amount) {
                throw new IllegalArgumentException("Недостаточно средств на счете.");
            }
            account.setBalance(account.getBalance() - amount);
            bankAccountRepository.updateAccount(account);
            transactionRepository.addTransaction(accountId,
                    new TransactionModel(IDCounterService.getNextTransactionID(),
                    accountId,
                    Date.from(Instant.now()),
                    amount,
                    account.getCurrencyCode(),
                    TransactionType.WITHDRAW));
        } catch (Exception e) {
            System.err.println("Ошибка при снятии средств: " + e.getMessage());
        }
    }

    // Обмен валют
    public void exchangeCurrency(String fromAccountId, String toAccountId, double amount) {
        try {
            BankAccountModel fromAccount = bankAccountRepository.getAccount(fromAccountId);
            BankAccountModel toAccount = bankAccountRepository.getAccount(toAccountId);

            if (fromAccount == null || toAccount == null) {
                throw new IllegalArgumentException("Один из счетов не найден.");
            }
            if (fromAccount.getBalance() < amount) {
                throw new IllegalArgumentException("Недостаточно средств для обмена.");
            }

            ExchangeRateModel rate = exchangeRateRepository.getCurrentExchangeRate(fromAccount.getCurrencyCode(), toAccount.getCurrencyCode());
            double convertedAmount = amount * rate.getRate();

            withdraw(fromAccountId, amount);
            deposit(toAccountId, convertedAmount);
        } catch (Exception e) {
            System.err.println("Ошибка при обмене валют: " + e.getMessage());
        }
    }

    // Получение истории транзакций по счету
    public List<TransactionModel> getTransactionHistory(String accountId) {
        try {
            return transactionRepository.getTransactionsByAccount(accountId);
        } catch (Exception e) {
            System.err.println("Ошибка при получении истории транзакций: " + e.getMessage());
            return null;
        }
    }
}
