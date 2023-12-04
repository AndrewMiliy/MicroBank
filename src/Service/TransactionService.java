package Service;

import Model.BankAccountModel;
import Model.ExchangeRateModel;
import Model.TransactionModel;
import Model.TransactionType;
import Repository.BankAccountRepository;
import Repository.ExchangeRateRepository;
import Repository.TransactionRepository;
import UI.Styles;

import java.time.Instant;
import java.util.Date;
import java.util.List;

public class TransactionService {
    private BankAccountRepository bankAccountRepository;
    private TransactionRepository transactionRepository;
    private ExchangeRateRepository exchangeRateRepository;

    //region ANSI Styles =====

    private static final String dimRed = Styles.dimRed;
    private static final String dimGreen = Styles.dimGreen;
    private static final String Olive = Styles.Olive;
    private static final String dimBlue = Styles.dimBlue;
    private static final String dimPurple = Styles.dimPurple;
    private static final String dimCyan = Styles.dimCyan;
    private static final String dimWhite = Styles.dimWhite;


    private static final String brightRed = Styles.brightRed;
    private static final String brightGreen = Styles.brightGreen;
    private static final String brightYellow = Styles.brightYellow;
    private static final String brightBlue = Styles.brightBlue;
    private static final String brightPurple = Styles.brightPurple;
    private static final String brightCyan = Styles.brightCyan;
    private static final String brightWhite = Styles.brightWhite;

    public static final String bold = Styles.bold;
    public static final String underline = Styles.underline;

    private static final String reset = Styles.reset;

    //endregion ANSI Styles =====

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
            System.out.println("Пополнение счета: " +brightGreen+ amount +reset+ " " + account.getCurrencyCode());
            System.out.println("Остаток на счете: " +brightGreen+  account.getFormattedBalance()+reset + " " + account.getCurrencyCode());
            return true;
        } catch (Exception e) {
            System.err.println(brightRed+"Ошибка при пополнении счета: "+reset + e.getMessage());
            return false;
        }
    }


    // Снятие средств со счета
    // Снятие средств со счета
    public boolean withdraw(String accountId, double amount) {
        try {
            BankAccountModel account = bankAccountRepository.getAccount(accountId);
            if (account == null) {
                throw new IllegalArgumentException(brightRed+"Счет не найден: " + accountId+reset);
            }
            if (account.getBalance() < amount) {
                throw new IllegalArgumentException(brightRed+"Недостаточно средств на счете."+reset);
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
            System.out.println("Снятие средств: " + dimRed+amount + reset+" " + account.getCurrencyCode());
            System.out.println("Остаток на счете: " +dimGreen+ account.getFormattedBalance() +reset+ " " + account.getCurrencyCode());
            return true;
        } catch (Exception e) {
            System.err.println(brightRed+"Ошибка при снятии средств: " +reset+ e.getMessage());
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
                throw new IllegalArgumentException(brightRed+"Один из счетов не найден.");
            }
            if (fromAccount.getBalance() < amount) {
                throw new IllegalArgumentException(brightRed+"Недостаточно средств для обмена.");
            }

            ExchangeRateModel rate = ExchangeRateService.getCurrentExchangeRate(fromAccount.getCurrencyCode(), toAccount.getCurrencyCode());
            double convertedAmount = amount * rate.getRate();

            withdraw(fromAccountId, amount);
            deposit(toAccountId, convertedAmount);
            System.out.println("Обмен валют: " + amount + " " + fromAccount.getCurrencyCode() + " -> " + String.format("%.2f", convertedAmount)  + " " + toAccount.getCurrencyCode());
            System.out.println("Остаток на счете: " +dimGreen+ fromAccount.getFormattedBalance() +reset+ " " + fromAccount.getCurrencyCode());
            System.out.println("Остаток на счете: " +dimGreen+ toAccount.getFormattedBalance() +reset+ " " + toAccount.getCurrencyCode());
            return true;
        } catch (Exception e) {
            System.err.println(brightRed+"Ошибка при обмене валют: " +reset+ e.getMessage());
            return false;
        }
    }


    // Получение истории транзакций по счету
    public List<TransactionModel> getTransactionHistory(String accountId) {
        try {
            return transactionRepository.getTransactionsByAccount(accountId);
        } catch (Exception e) {
            System.err.println( brightRed+"Ошибка при получении истории транзакций: "+reset + e.getMessage());
            return null;
        }
    }

    public List<TransactionModel> getAllTransactionsByCurrencyCode(String code) {
        try {
            return transactionRepository.getAllTransactionsByCurrencyCode(code);
        } catch (Exception e) {
            System.err.println(brightRed+"Ошибка при получении истории транзакций: " +reset+ e.getMessage());
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
            System.err.println(brightRed+"Ошибка при обновлении валюты в транзакциях: "+reset + e.getMessage());
        }
    }
}