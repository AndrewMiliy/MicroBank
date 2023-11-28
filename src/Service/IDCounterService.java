package Service;

import java.io.*;
import java.nio.file.*;

public class IDCounterService {
    private static final String FILE_PATH = "user_id_counter.txt";
    private static int counter;

    public IDCounterService() {
        this.counter = readCounterFromFile();
    }

    private int readCounterFromFile() {
        try {
            Path path = Paths.get(FILE_PATH);
            if (!Files.exists(path)) {
                Files.createFile(path);
                return 1; // Начальное значение счетчика
            }
            String content = new String(Files.readAllBytes(path));
            return content.isEmpty() ? 1 : Integer.parseInt(content.trim());
        } catch (IOException e) {
            throw new RuntimeException("Ошибка при чтении файла: " + FILE_PATH, e);
        }
    }

    private static void writeCounterToFile() {
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(FILE_PATH))) {
            writer.write(String.valueOf(counter));
        } catch (IOException e) {
            throw new RuntimeException("Ошибка при записи в файл: " + FILE_PATH, e);
        }
    }

    public static int getNextUserId() {
        int nextId = counter++;
        writeCounterToFile();
        return nextId;
    }

    public static String getNextTransactionID() {
        int nextId = counter++;
        writeCounterToFile();
        return String.valueOf(nextId);
    }
}
