package UI;

import Model.*;
import Repository.*;
import Service.*;

import java.util.Scanner;

public class ConsoleUI_V2 {
    private UserService userService;
    private BankAccountService bankAccountService;
    private TransactionService transactionService;
    private CurrencyService currencyService;
    private ValidationService validationService;
    private Scanner scanner;
    private UserModel currentUser;

    public ConsoleUI_V2() {
        UserRepository userRepository = new UserRepository();
        BankAccountRepository bankAccountRepository = new BankAccountRepository();
        TransactionRepository transactionRepository = new TransactionRepository();
        ExchangeRateRepository exchangeRateRepository = new ExchangeRateRepository();
        CurrencyRepository currencyRepository = new CurrencyRepository();

        this.userService = new UserService(userRepository);
        this.bankAccountService = new BankAccountService(userRepository, bankAccountRepository, currencyRepository);
        this.transactionService = new TransactionService(bankAccountRepository, transactionRepository, exchangeRateRepository);
        this.currencyService = new CurrencyService(currencyRepository);
        this.validationService = new ValidationService();

        this.scanner = new Scanner(System.in);
    }

    public void auth() {
        boolean running = true;
        while (running) {
            System.out.println("Выберите опцию: \n1. Регистрация \n2. Вход \n3. Выход");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Очистка буфера сканера
            switch (choice) {
                case 1:
                    registerUser();
                    break;
                case 2:
                    loginUser();
                    break;
                case 3:
                    running = false;
                    break;
                default:
                    System.out.println("Неверная опция.");
            }
        }
    }

    private void registerUser() {
        System.out.println("Регистрация нового пользователя");
        System.out.print("Введите имя: ");
        String firstName = scanner.nextLine();
        System.out.print("Введите фамилию: ");
        String lastName = scanner.nextLine();
        System.out.print("Введите email: ");
        String email = scanner.nextLine();
        System.out.print("Введите пароль: ");
        String password = scanner.nextLine();

        // Валидация и создание пользователя
        try {
            currentUser = userService.register(email, firstName,lastName, password);
            System.out.println("Пользователь успешно зарегистрирован: " + currentUser.getId());
        } catch (Exception e) {
            System.out.println("Ошибка регистрации: " + e.getMessage());
        }
    }

    private void loginUser() {
        System.out.println("Вход в систему");
        System.out.print("Введите email: ");
        String email = scanner.nextLine();
        System.out.print("Введите пароль: ");
        String password = scanner.nextLine();

        // Проверка учетных данных пользователя
        UserModel user = userService.authorize(email, password);
        if (user != null) {
            currentUser = user;
            System.out.println("Вход выполнен успешно. Добро пожаловать, " + user.getEmail());
            showUserMenu();
        } else {
            System.out.println("Неверный email или пароль.");
        }
    }

    private void showUserMenu() {
        boolean isUserLoggedIn = true;
        while (isUserLoggedIn) {
            System.out.println("Пользовательское меню:");
            System.out.println("1. Просмотр баланса \n2. Пополнение счета \n3. Снятие средств \n4. Выход");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Очистка буфера сканера
            switch (choice) {
                case 1:
                    // Логика просмотра баланса
                    break;
                case 2:
                    // Логика пополнения счета
                    break;
                case 3:
                    // Логика снятия средств
                    break;
                case 4:
                    isUserLoggedIn = false;
                    currentUser = null;
                    break;
                default:
                    System.out.println("Неверная опция.");
            }
        }
    }

    // Дополнительные методы для других операций
    public static void main(String[] args) {
        // Инициализация и запуск UI
        ConsoleUI_V2 consoleUI = new ConsoleUI_V2();
        consoleUI.auth();
    }
}
