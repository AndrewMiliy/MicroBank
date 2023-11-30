package Repository;

import Model.*;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

public class DataPersistenceManager {
    public static final String USER_DATA_FILE = "users.json";
    public static final String ACCOUNT_DATA_FILE = "accounts.json";
    public static final String TRANSACTION_DATA_FILE = "transactions.json";
    public static final String CURRENCY_DATA_FILE = "currencies.json";
    public static final String EXCHANGE_RATE_DATA_FILE = "exchangeRates.json";

    private static final Gson gson = new Gson();

    // Сохранение данных в файл
    public static void saveData(Map<String, ?> data, String fileName) {
        try (Writer writer = new FileWriter(fileName)) {
            gson.toJson(data, writer);
        } catch (IOException e) {
            System.err.println("Ошибка при сохранении данных в файл: " + fileName);
            // Дополнительная обработка ошибок
        }
    }

    // Чтение данных из файла с учетом типа
    public static Map<String, ?> loadData(String fileName) {
        Type type;

        switch (fileName) {
            case USER_DATA_FILE:
                type = new TypeToken<Map<String, UserModel>>() {}.getType();
                break;
            case ACCOUNT_DATA_FILE:
                type = new TypeToken<Map<String, List<BankAccountModel>>>(){}.getType();
                break;
            case TRANSACTION_DATA_FILE:
                type = new TypeToken<Map<String, List<TransactionModel>>>() {}.getType();
                break;
            case CURRENCY_DATA_FILE:
                type = new TypeToken<Map<String, CurrencyModel>>() {}.getType();
                break;
            case EXCHANGE_RATE_DATA_FILE:
                type = new TypeToken<Map<String, List<ExchangeRateModel>>>() {}.getType();
                break;
            default:
                throw new IllegalArgumentException("Неизвестный тип файла: " + fileName);
        }

        try (Reader reader = new FileReader(fileName)) {
            return gson.fromJson(reader, type);
        } catch (FileNotFoundException e) {
            return null; // или инициализировать пустую коллекцию
        } catch (IOException e) {
            System.err.println("Ошибка при загрузке данных из файла: " + fileName);
            return null;
        }
    }

   /* // Сохранение данных в файл
    public static void saveData(Map<String, ?> data, String fileName) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName))) {
            out.writeObject(data);
        } catch (IOException e) {
            System.err.println("Ошибка при сохранении данных в файл: " + fileName);
            e.printStackTrace(); // Для более подробной трассировки ошибки
        }
    }

    // Чтение данных из файла
    public static Map<String, ?> loadData(String fileName) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName))) {
            return (Map<String, ?>) in.readObject();
        } catch (FileNotFoundException e) {
            return null; // или инициализировать пустую коллекцию
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Ошибка при загрузке данных из файла: " + fileName);
            return null;
        }
    }*/

    // Сохранение всех данных
    public static void saveAllData(UserRepository userRepository, BankAccountRepository bankAccountRepository,
                                   TransactionRepository transactionRepository, CurrencyRepository currencyRepository,
                                   ExchangeRateRepository exchangeRateRepository) {
        saveData(userRepository.getUsers(), USER_DATA_FILE);
        saveData(bankAccountRepository.getAccounts(), ACCOUNT_DATA_FILE);
        saveData(transactionRepository.getTransactions(), TRANSACTION_DATA_FILE);
        saveData(currencyRepository.getCurrencies(), CURRENCY_DATA_FILE);
        saveData(exchangeRateRepository.getExchangeRates(), EXCHANGE_RATE_DATA_FILE);
    }

    // Загрузка всех данных
    public static void loadAllData(UserRepository userRepository, BankAccountRepository bankAccountRepository,
                                   TransactionRepository transactionRepository, CurrencyRepository currencyRepository,
                                   ExchangeRateRepository exchangeRateRepository)  {
        Map<String, ?> users = loadData(USER_DATA_FILE);
        if (users != null) {
            userRepository.setUsers(users);
        }
        Map<String, ?> accounts = loadData(ACCOUNT_DATA_FILE);
        if (accounts != null) {
            bankAccountRepository.setAccounts(accounts);
        }
        Map<String, ?> transactions = loadData(TRANSACTION_DATA_FILE);
        if (transactions != null) {
            transactionRepository.setTransactions(transactions);
        }
        Map<String, ?> currencies = loadData(CURRENCY_DATA_FILE);
        if (currencies != null) {
            currencyRepository.setCurrencies(currencies);
        }
        Map<String, ?> exchangeRates = loadData(EXCHANGE_RATE_DATA_FILE);
        if (exchangeRates != null) {
            exchangeRateRepository.setExchangeRates(exchangeRates);
        }
    }
}
