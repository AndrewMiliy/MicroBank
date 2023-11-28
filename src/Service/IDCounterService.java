package Service;

import java.io.*;
import java.nio.file.*;
import java.util.Properties;

public class IDCounterService {
    private static final String FILE_PATH = "counters.properties";
    private static int userCounter, transactionCounter, bankAccountCounter, exchangeRateCounter;

    static {
        loadCountersFromFile();
    }

    private static void loadCountersFromFile() {
        Properties properties = new Properties();
        Path path = Paths.get(FILE_PATH);

        try {
            if (!Files.exists(path)) {
                Files.createFile(path);
            } else {
                properties.load(Files.newInputStream(path));
                userCounter = Integer.parseInt(properties.getProperty("userCounter", "1"));
                transactionCounter = Integer.parseInt(properties.getProperty("transactionCounter", "1"));
                bankAccountCounter = Integer.parseInt(properties.getProperty("bankAccountCounter", "1"));
                exchangeRateCounter = Integer.parseInt(properties.getProperty("exchangeRateCounter", "1"));
            }
        } catch (IOException e) {
            throw new RuntimeException("Ошибка при загрузке счетчиков из файла: " + FILE_PATH, e);
        }
    }

    private static void saveCountersToFile() {
        Properties properties = new Properties();
        properties.setProperty("userCounter", String.valueOf(userCounter));
        properties.setProperty("transactionCounter", String.valueOf(transactionCounter));
        properties.setProperty("bankAccountCounter", String.valueOf(bankAccountCounter));
        properties.setProperty("exchangeRateCounter", String.valueOf(exchangeRateCounter));

        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(FILE_PATH))) {
            properties.store(writer, null);
        } catch (IOException e) {
            throw new RuntimeException("Ошибка при сохранении счетчиков в файл: " + FILE_PATH, e);
        }
    }

    public static String getNextUserId() {
        userCounter++;
        saveCountersToFile();
        return String.valueOf(userCounter);
    }

    public static String getNextTransactionID() {
        transactionCounter++;
        saveCountersToFile();
        return String.valueOf(transactionCounter);
    }

    public static String getNextBankAccountID() {
        bankAccountCounter++;
        saveCountersToFile();
        return String.valueOf(bankAccountCounter);
    }

    public static String getNextExchangeRateID() {
        exchangeRateCounter++;
        saveCountersToFile();
        return String.valueOf(exchangeRateCounter);
    }
}
