import Model.*;
import Repository.*;
import Service.*;

import java.util.Random;

public class CreateTestData {

    private static UserService userService;
    private static BankAccountService bankAccountService;
    private static TransactionService transactionService;
    private static CurrencyService currencyService;
    private static ExchangeRateService exchangeRateService;
    private static ValidationService validationService;

    private static UserModel admin;

    public static void main(String[] args) {
        init();

        // Добавляем администратора в репозиторий
        admin = userService.register("Admin", "Admin", "Qwerty1!", "admin@admin.com");

        // Создаем пользователей
        for (int i = 1; i <= 10; i++) {
            // Добавляем пользователей в репозиторий
            userService.register("User" + i, "User" + i, "Qwerty1!", "User" + i + "@gmail.com");
        }

        currencyService.addCurrency(admin, new CurrencyModel("Доллар США", "USD"));
        currencyService.addCurrency(admin, new CurrencyModel("Евро", "EUR"));
        exchangeRateService.addExchangeRate(new ExchangeRateModel("USD", "EUR", 0.85));
        currencyService.addCurrency(admin, new CurrencyModel("Фунт стерлингов", "GBP"));
        exchangeRateService.addExchangeRate(new ExchangeRateModel("USD", "GBP", 0.72));
        currencyService.addCurrency(admin, new CurrencyModel("Швейцарский франк", "CHF"));
        exchangeRateService.addExchangeRate(new ExchangeRateModel("USD", "CHF", 0.91));
        currencyService.addCurrency(admin, new CurrencyModel("Японская йена", "JPY"));
        exchangeRateService.addExchangeRate(new ExchangeRateModel("USD", "JPY", 109.84));
        currencyService.addCurrency(admin, new CurrencyModel("Китайский юань", "CNY"));
        exchangeRateService.addExchangeRate(new ExchangeRateModel("USD", "CNY", 6.46));
        currencyService.addCurrency(admin, new CurrencyModel("Индийская рупия", "INR"));
        exchangeRateService.addExchangeRate(new ExchangeRateModel("USD", "INR", 74.47));
        currencyService.addCurrency(admin, new CurrencyModel("Канадский доллар", "CAD"));
        exchangeRateService.addExchangeRate(new ExchangeRateModel("USD", "CAD", 1.26));
        currencyService.addCurrency(admin, new CurrencyModel("Австралийский доллар", "AUD"));
        exchangeRateService.addExchangeRate(new ExchangeRateModel("USD", "AUD", 1.29));
        currencyService.addCurrency(admin, new CurrencyModel("Новозеландский доллар", "NZD"));
        exchangeRateService.addExchangeRate(new ExchangeRateModel("USD", "NZD", 1.39));

        // Создаем банковские счета для пользователей
        for (int i = 1; i <= 10; i++) {
            // Получаем пользователя из репозитория
            UserModel user = userService.getUserByEmail("User" + i + "@gmail.com");
            // Создаем банковский счет
            BankAccountModel account1 = new BankAccountModel(IDCounterService.getNextBankAccountID(), user.getId(), getRandomCurrencyCode());
            BankAccountModel account2 = new BankAccountModel(IDCounterService.getNextBankAccountID(), user.getId(), getRandomCurrencyCode());
            // Добавляем банковский счет пользователям
            BankAccountService.addBankAccount(user.getId(), account1);
            BankAccountService.addBankAccount(user.getId(), account2);
        }

        // Создаем транзакции
        for (int i = 1; i <= 100; i++) {
            // Получаем пользователя из репозитория
            UserModel user = userService.getUserByEmail("User" + getRandomNumberInRange(1, 10) + "@gmail.com");
            // Получаем банковский счет пользователя
            BankAccountModel account = bankAccountService.getAccountByUserIdAndCurrencyCode(user.getId(), getRandomCurrencyCode());
            if(account == null) {
                continue;
            }
            // Создаем транзакцию ввода
            transactionService.deposit(account.getBankAccountId(), Double.parseDouble(getRandomNumberInRange(1, 1000)));
            // Создаем транзакцию вывода
            transactionService.withdraw(account.getBankAccountId(), account.getBalance()/2);
            transactionService.exchangeCurrency(account.getBankAccountId(), getRandomCurrencyCode(), account.getBalance()/2);
        }
    }

    private static String getRandomNumberInRange(int i, int i1) {
        Random random = new Random();
        return String.valueOf(random.nextInt(i1 - i) + i);
    }

    public static String getRandomCurrencyCode() {
        Random random = new Random();
        return currencyService.getAllCurrencies().get(random.nextInt(10)).getCodeName();
    }

    public static void init()
    {
        DataPersistenceManager.clearAllData();
        UserRepository userRepository = new UserRepository();
        BankAccountRepository bankAccountRepository = new BankAccountRepository();
        TransactionRepository transactionRepository = new TransactionRepository();
        ExchangeRateRepository exchangeRateRepository = new ExchangeRateRepository();
        CurrencyRepository currencyRepository = new CurrencyRepository();

        userService = new UserService(userRepository);
        bankAccountService = new BankAccountService(userRepository, bankAccountRepository);
        transactionService = new TransactionService(bankAccountRepository, transactionRepository);
        currencyService = new CurrencyService(currencyRepository);
        exchangeRateService = new ExchangeRateService(exchangeRateRepository);
        validationService = new ValidationService();
    }
}

