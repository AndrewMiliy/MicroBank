package Repository;

import java.io.*;
import java.util.Map;

public class DataPersistenceManager {
    private static final String USER_DATA_FILE = "users.dat";
    private static final String ACCOUNT_DATA_FILE = "accounts.dat";
    private static final String TRANSACTION_DATA_FILE = "transactions.dat";
    private static final String CURRENCY_DATA_FILE = "currencies.dat";
    private static final String EXCHANGE_RATE_DATA_FILE = "exchangeRates.dat";

    // Сохранение данных в файл
    public static void saveData(Map<String, ?> data, String fileName) throws IOException {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName))) {
            out.writeObject(data);
        }
    }

    // Чтение данных из файла
    public static Map<String, ?> loadData(String fileName) throws IOException, ClassNotFoundException {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName))) {
            return (Map<String, ?>) in.readObject();
        } catch (FileNotFoundException e) {
            return null; // или инициализировать пустую коллекцию
        }
    }

    // Сохранение всех данных
    /*public static void saveAllData(UserRepository userRepository, BankAccountRepository bankAccountRepository,
                                   TransactionRepository transactionRepository, CurrencyRepository currencyRepository,
                                   ExchangeRateRepository exchangeRateRepository) throws IOException {
        saveData(userRepository.getAllUsers(), USER_DATA_FILE);
        saveData(bankAccountRepository.getAllAccounts(), ACCOUNT_DATA_FILE);
        saveData(transactionRepository.getAllTransactions(), TRANSACTION_DATA_FILE);
        saveData(currencyRepository.getAllCurrencies(), CURRENCY_DATA_FILE);
        saveData(exchangeRateRepository.getAllExchangeRates(), EXCHANGE_RATE_DATA_FILE);
    }

    // Загрузка всех данных
    public static void loadAllData(UserRepository userRepository, BankAccountRepository bankAccountRepository,
                                   TransactionRepository transactionRepository, CurrencyRepository currencyRepository,
                                   ExchangeRateRepository exchangeRateRepository) throws IOException, ClassNotFoundException {
        userRepository.setUsers(loadData(USER_DATA_FILE));
        bankAccountRepository.setAccounts(loadData(ACCOUNT_DATA_FILE));
        transactionRepository.setTransactions(loadData(TRANSACTION_DATA_FILE));
        currencyRepository.setCurrencies(loadData(CURRENCY_DATA_FILE));
        exchangeRateRepository.setExchangeRates(loadData(EXCHANGE_RATE_DATA_FILE));
    }*/
}
