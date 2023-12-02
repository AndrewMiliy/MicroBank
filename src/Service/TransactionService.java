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
    public boolean deposit(String accountId, double amount) {
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
            System.out.println("Пополнение счета: " + amount + " " + account.getCurrencyCode());
            System.out.println("Остаток на счете: " + account.getFormattedBalance() + " " + account.getCurrencyCode());
            return true;
        } catch (Exception e) {
            System.err.println("Ошибка при пополнении счета: " + e.getMessage());
            return false;
        }
    }


    // Снятие средств со счета
    // Снятие средств со счета
    public boolean withdraw(String accountId, double amount) {
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
            System.out.println("Снятие средств: " + amount + " " + account.getCurrencyCode());
            System.out.println("Остаток на счете: " + account.getFormattedBalance() + " " + account.getCurrencyCode());
            return true;
        } catch (Exception e) {
            System.err.println("Ошибка при снятии средств: " + e.getMessage());
            return false;
        }
    }


    // Обмен валют
    // Обмен валют
    public boolean exchangeCurrency(String fromAccountId, String toAccountId, double amount) {
        try {
            BankAccountModel fromAccount = bankAccountRepository.getAccount(fromAccountId);
            BankAccountModel toAccount = bankAccountRepository.getAccount(toAccountId);

            if (fromAccount == null || toAccount == null) {
                throw new IllegalArgumentException("Один из счетов не найден.");
            }
            if (fromAccount.getBalance() < amount) {
                throw new IllegalArgumentException("Недостаточно средств для обмена.");
            }

            ExchangeRateModel rate = ExchangeRateService.getCurrentExchangeRate(fromAccount.getCurrencyCode(), toAccount.getCurrencyCode());
            double convertedAmount = amount * rate.getRate();

            withdraw(fromAccountId, amount);
            deposit(toAccountId, convertedAmount);
            System.out.println("Обмен валют: " + amount + " " + fromAccount.getCurrencyCode() + " -> " + String.format("%.2f", convertedAmount)  + " " + toAccount.getCurrencyCode());
            System.out.println("Остаток на счете: " + fromAccount.getFormattedBalance() + " " + fromAccount.getCurrencyCode());
            System.out.println("Остаток на счете: " + toAccount.getFormattedBalance() + " " + toAccount.getCurrencyCode());
            return true;
        } catch (Exception e) {
            System.err.println("Ошибка при обмене валют: " + e.getMessage());
            return false;
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

    public List<TransactionModel> getAllTransactionsByCurrencyCode(String code) {
        try {
            return transactionRepository.getAllTransactionsByCurrencyCode(code);
        } catch (Exception e) {
            System.err.println("Ошибка при получении истории транзакций: " + e.getMessage());
            return null;
        }
    }

    public void updateCurrencyInTransactions(String code, String newCode) {
        try {
            List<TransactionModel> transactions = transactionRepository.getAllTransactionsByCurrencyCode(code);
            for (TransactionModel transaction : transactions) {
                transaction.setCurrencyCode(newCode);
            }
            transactionRepository.setTransactions(transactionRepository.getTransactions());
        } catch (Exception e) {
            System.err.println("Ошибка при обновлении валюты в транзакциях: " + e.getMessage());
        }
    }
}
