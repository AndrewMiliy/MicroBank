package UI;

import Model.*;
import Repository.*;
import Service.*;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class ConsoleUI_V2 {
    private UserService userService;
    private BankAccountService bankAccountService;
    private TransactionService transactionService;
    private CurrencyService currencyService;
    private ExchangeRateService exchangeRateService;
    private ValidationService validationService;
    private Scanner scanner;
    private UserModel currentUser;
    private UserModel selectedUser;

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
        this.exchangeRateService = new ExchangeRateService(exchangeRateRepository);
        this.validationService = new ValidationService();

        DataPersistenceManager.loadAllData(userRepository, bankAccountRepository, transactionRepository, currencyRepository, exchangeRateRepository);

        this.scanner = new Scanner(System.in);
    }

    //region AuthFlow =====

    public void auth() {
        while (currentUser == null) {
            System.out.println("Выберите опцию: \n1. Регистрация \n2. Вход \n0. Выход");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Очистка буфера сканера
            switch (choice) {
                case 1:
                    registerUser();
                    break;
                case 2:
                    loginUser();
                    break;
                case 0:
                    System.out.println("До свидания!");
                    System.exit(0);
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

        // Проверка на существование пользователя с таким же email
        if (userService.getUserByEmail(email) != null) {
            System.out.println("Пользователь с таким email уже существует.");
            return;
        }

        // Валидация и создание пользователя
        try {
            currentUser = userService.register(firstName, lastName, password, email);
            System.out.println("Пользователь успешно зарегистрирован: " + currentUser.getId());
            if(currentUser.getUserRole().equals(UserRole.ADMIN)) {
                showAdminMenu();
            } else {
                showUserMenu();
            }
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
            if (user.getUserRole().equals(UserRole.ADMIN)) {
                showAdminMenu();
            } else {
                showUserMenu();
            }
        } else {
            System.out.println("Неверный email или пароль.");
            System.out.println("Возвращаем в окно авторизации.");
            auth();
        }
    }

    //endregion AuthFlow =====

    //region UserFlow =====

    // Методы для работы с пользователем
    private void showUserMenu() {
        while (isUserLoggedIn()) {
            System.out.println("Пользовательское меню:");
            System.out.println("1. Мои счета \n0. Выйти из аккаунта");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Очистка буфера сканера
            switch (choice) {
                case 1:
                    showBankAccountsMenu();
                    // Логика просмотра счетов
                    break;
                case 0:
                    currentUser = null;
                    break;
                default:
                    System.out.println("Неверная опция.");
            }
        }
    }

    // Методы для работы со счетами
    private void showBankAccountsMenu() {
        List<BankAccountModel> bankAccounts = null;
        while (isUserLoggedIn()) {
            System.out.println("Меню управления счетами");
            bankAccounts = BankAccountService.getAllAccountsForUser(currentUser.getId());
            for (var i : bankAccounts) {
                System.out.println(i.getCurrencyCode() + ": " + i.getFormattedBalance());
            }
            System.out.println(!bankAccounts.isEmpty() ?"Что бы выбрать счет, введите код счета":"");
            System.out.println("2. Создать счет \n0. Вернуться в предыдущее меню");
            String choice = scanner.nextLine();

            switch (choice) {
                case "2":
                    if (!createBankAccountMenu()) {
                        System.out.println("Не получилось создать счет, попробуйте ещё раз.");
                    }
                    //showBankAccountsMenu();
                    // Логика создания счета
                    break;
                case "0":
                    showUserMenu(); //Возвращение в предыдущее меню
                    break;
                default:
                    for (var i : bankAccounts) {
                        if (i.getCurrencyCode().equals(choice)) {
                            showBankAccountMenu(i);
                        }
                    }
                    System.out.println("Счета с таким кодом не найдено или неверная опция. Попробуйте ещё раз.");
                    // Логика выбора счета
                    break;
            }
        }
    }

    // Методы для работы со счетом
    private void showBankAccountMenu(BankAccountModel bankAccount) {
        System.out.println(bankAccount.getCurrencyCode() + ": " + bankAccount.getFormattedBalance());
        while (isUserLoggedIn()) {
            System.out.println("Выберите действие");
            System.out.println("1. Deposit \n2. Withdraw \n3. Transfer \n4. History \n5. Delete \n0. Вернуться в предыдущее меню");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Очистка буфера сканера
            switch (choice) {
                case 1:
                    if (!deposit(bankAccount)) {
                        System.out.println("Не получилось сделать deposit, попробуйте ещё раз.");
                    } else {
                        System.out.println("Счет успешно пополнен");
                    }
                    break;
                case 2:
                    if (!withdraw(bankAccount)) {
                        System.out.println("Не получилось сделать withdraw, попробуйте ещё раз.");
                    } else {
                        System.out.println("Деньги успешно сняты");
                    }
                    break;
                case 3:
                    if(!transfer(bankAccount)) {
                        System.out.println("Не получилось сделать transfer, попробуйте ещё раз.");
                    } else {
                        System.out.println("Валюта успешно переведена");
                    }
                    break;
                case 4:
                    historyBankAccount(bankAccount);
                    break;
                case 5:
                    if (!deleteBankAccount(bankAccount)) {
                        System.out.println("Не получилось сделать delete, попробуйте ещё раз.");
                    } else {
                        System.out.println("Счет успешно удалён");
                        return;
                    }
                    break;
                case 0:
                    showBankAccountsMenu();
                    break;
            }
        }
    }

    // Методы для пополнения счета
    private boolean deposit(BankAccountModel bankAccount) {
        System.out.println("Введите сумму для deposit кратную единице");
        String input = scanner.nextLine(); // Используем nextLine для считывания всей строки ввода

        try {
            int depositAmount = Integer.parseInt(input); // Попытка преобразовать строку в число
            return transactionService.deposit(bankAccount.getBankAccountId(), depositAmount);
        } catch (NumberFormatException e) {
            System.out.println("Ошибка при вводе суммы: Введено не число.");
            return false;
        } catch (Exception e) {
            System.out.println("Ошибка при выполнении операции: " + e.getMessage());
            return false;
        }
    }

    // Методы для снятия денег
    private boolean withdraw(BankAccountModel bankAccount) {
        System.out.println("Введите сумму для снятия кратную единице");
        String input = scanner.nextLine(); // Используем nextLine для считывания всей строки ввода

        try {
            int withdrawAmount = Integer.parseInt(input); // Попытка преобразовать строку в число
            return transactionService.withdraw(bankAccount.getBankAccountId(), withdrawAmount);
        } catch (NumberFormatException e) {
            System.out.println("Ошибка при вводе суммы: Введено не число.");
            return false;
        } catch (Exception e) {
            System.out.println("Ошибка при выполнении операции: " + e.getMessage());
            return false;
        }
    }

    // Методы для перевода валюты
    private boolean transfer(BankAccountModel bankAccount) {
        System.out.println("На какой счет переводим?");
        System.out.println("Доступные счета: ");
        for (var i : BankAccountService.getAllAccountsForUser(currentUser.getId())) {
            if (i.getCurrencyCode() != bankAccount.getCurrencyCode()) {
                System.out.println(i.getCurrencyCode() + ": " + i.getFormattedBalance());
            }
        }
        String accountTo = scanner.nextLine();
        System.out.println("Введите сумму для transfer кратную единице");
        int transfer = scanner.nextInt();
        scanner.nextLine(); // Очистка буфера сканера
        return transactionService.exchangeCurrency(bankAccount.getBankAccountId(), bankAccountService.getAccountByUserIdAndCurrencyCode(currentUser.getId(), accountTo).getBankAccountId(), transfer);
    }

    // Методы для просмотра истории счета
    private void historyBankAccount(BankAccountModel bankAccount) {
        List<TransactionModel> history = transactionService.getTransactionHistory(bankAccount.getBankAccountId());
        if (history == null || (long) history.size() == 0) {
            System.out.println("Этот счет, ещё не имеет своей истории, начните её.");
        } else {
            System.out.println("История счёта");
            for (var i : history) {
                System.out.println(i.getDate() + " - " + i.getTransactionType() + " - " + i.getCurrencyCode() + ": " + i.getFormattedAmount());
            }
        }
        System.out.println("Нажмите ввод для продолжения");
        scanner.nextLine(); // Очистка буфера сканера
        showBankAccountMenu(bankAccount);
    }

    // Методы для удаления счета
    private boolean deleteBankAccount(BankAccountModel bankAccount) {
        while (bankAccount.getBalance() > 0) {
            System.out.println("У вас на счету есть деньги, если вы продолжите вы их потеряете.");
            System.out.println("На какой счет их перевести?");
            for (var i : BankAccountService.getAllAccountsForUser(currentUser.getId())) {
                if (i.getCurrencyCode() != bankAccount.getCurrencyCode()) {
                    System.out.println(i.getCurrencyCode() + ": " + i.getFormattedBalance());
                }
            }
            System.out.println("Ждём ввод счёта:");
            String accountName = scanner.nextLine();

            BankAccountModel accountTo = bankAccountService.getAccountByUserIdAndCurrencyCode(currentUser.getId(), accountName);
            if (accountTo != null) {
                transactionService.exchangeCurrency(bankAccount.getBankAccountId(), accountTo.getBankAccountId(), bankAccount.getBalance());
                bankAccountService.deleteBankAccount(currentUser.getId(), bankAccount.getBankAccountId());
                System.out.println("Ваши деньги переведены на: " + accountTo.getCurrencyCode() + ": " + accountTo.getFormattedBalance());
                System.out.println("По курсу: " + exchangeRateService.getCurrentExchangeRate(bankAccount.getCurrencyCode(), accountTo.getCurrencyCode()).getRate());
                System.out.println("Ваш счет удален.");
            } else {
                System.out.println("Такого счета нет");
                System.out.println("1. Попробуйте ещё раз \n 0. Вернуться.");
                int choice = scanner.nextInt();
                scanner.nextLine(); // Очистка буфера сканера
                switch (choice){
                    case 1:
                        deleteBankAccount(bankAccount);
                        break;
                    case 0:
                        return false;
                }
            }
        }
        return true;
    }

    // Методы для создания счета
    private boolean createBankAccountMenu() {
        BankAccountModel bankAccount;
        System.out.println("Выберите доступную валюту для счета:");
        for (var i : currencyService.getAllCurrencies()) {
            for (var j : BankAccountService.getAllAccountsForUser(currentUser.getId())) {
                if (i.getCodeName() != j.getCurrencyCode()){
                    System.out.println(i.getCodeName());
                }
            }
        }
        String choice = scanner.nextLine();
        BankAccountModel newBankAccount = new BankAccountModel(IDCounterService.getNextBankAccountID(), currentUser.getId(), choice);
        bankAccountService.addBankAccount(currentUser.getId(), newBankAccount);
        System.out.println("Счет успешно создан: " + newBankAccount.getCurrencyCode() + ": " + newBankAccount.getFormattedBalance());
        return true;
    }

    //endregion UserFlow =====

    //region AdminFlow =====

    private void showAdminMenu() {
        while (isUserLoggedIn()) {
            System.out.println("Админское меню:");
            System.out.println("1. Управление пользователями \n2. Управление валютами \n0. Выйти из аккаунта");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Очистка буфера сканера
            switch (choice) {
                case 1:
                    usersAdminMenu();
                    break;
                case 2:
                    currencyAdminMenu();
                    break;
                case 0:
                    currentUser = null;
                    break;
                default:
                    System.out.println("Неверная опция.");
            }
        }
    }

    private void usersAdminMenu() {
        System.out.println("Меню управления пользователями:");
        System.out.println("1. Показать список пользователей \n2. Найти пользователя " +
                "\n3. Выбор пользователя (по ID) \n4. GODMODE \n5. NOOBMODE \n0. Вернуться");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Очистка буфера сканера
        switch (choice) {
            case 1:
                userList();
                break;
            case 2:
                System.out.println("Введите Email пользователя");
                UserModel user = userService.getUserByEmail(scanner.nextLine());
                if (user != null) {
                    System.out.println(user.getId() + ": " + user.getEmail() + " | " + user.getUserRole());
                } else {
                    System.out.println("Пользователь не найден");
                }
                usersAdminMenu();
                break;
            case 3:
                System.out.println("Введите ID пользователя");
                selectedUser = userService.getUserBy(scanner.nextLine());
                if (selectedUser != null) {
                    List<BankAccountModel> bankAccountModelList = bankAccountService.getAllAccountsForUser(selectedUser.getId());
                    for (int i = 0; i < bankAccountModelList.size()-1; i++) {
                        for (var t : transactionService.getTransactionHistory(bankAccountModelList.get(i).getBankAccountId())) {
                            System.out.println(t.getTransactionId() + ": " + t.getDate() + " - " + t.getTransactionType() + " - " + t.getCurrencyCode() + ": " + t.getFormattedAmount());
                        }
                    }
                } else {
                    System.out.println("Пользователь не найден");
                }
                break;
            case 4:
                System.out.println("Введите ID пользователя");
                selectedUser = userService.getUserBy(scanner.nextLine());
                if (selectedUser != null) {
                    userService.setUserRole(selectedUser, UserRole.ADMIN, currentUser);
                    System.out.println(selectedUser.getId() + " стал админом");
                } else {
                    System.out.println("Пользователь не найден");
                }
                break;
            case 5:
                System.out.println("Введите ID пользователя");
                selectedUser = userService.getUserBy(scanner.nextLine());
                if (selectedUser != null) {
                    userService.setUserRole(selectedUser, UserRole.USER, currentUser);
                    System.out.println(selectedUser.getId() + " стал челядью");
                } else {
                    System.out.println("Пользователь не найден");
                }
                break;
            case 0:
                showAdminMenu();
                break;
            default:
                System.out.println("Неверная опция.");
        }

    }

    // Методы для работы с валютами
    private void currencyAdminMenu() {
        System.out.println("Меню управления валютами");
        System.out.println("1. Показать список валют \n2. Добавить валюту " +
                "\n3. Изменить валюту (по Code) \n4. Просмотр операций по валюте " +
                "\n5. Удалить валюту (по Code) \n0. Вернуться");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Очистка буфера сканера
        switch (choice) {
            case 1:
                currencyList();
                break;
            case 2:
                addCurrency();
                break;
            case 3:

                break;
            case 4:
                showCurrencyTransactions();
                break;
            case 5:

                break;
            case 0:

                break;
            default:
                System.out.println("Неверная опция.");
        }
    }

    private void showCurrencyTransactions() {

    }

    // Методы для работы с валютами
    private void addCurrency() {
        System.out.println("Введите код валюты");
        CurrencyModel newCurrency = new CurrencyModel("", scanner.nextLine());
        currencyService.addCurrency(currentUser, newCurrency);
        System.out.println("Валюта успешно создана");
        currencyAdminMenu();
    }

    private void userList () {
        var users = userService.getUsers();
        if (users == null || users.isEmpty()) {
            System.out.println("Пользователей нет");
        } else {
            for (Map.Entry<String, ?> entry : userService.getUsers().entrySet()) {
                UserModel user = (UserModel)entry.getValue();
                System.out.println(user.getId() + ": " + user.getEmail() + " | " + user.getUserRole());
            }
        }
        usersAdminMenu();
    }

    private void currencyList () {
        var allCurrency = currencyService.getAllCurrencies();
        if (allCurrency == null || allCurrency.isEmpty()) {
            System.out.println("Валют нет");
        } else {
            System.out.println("Список валют");
            for (var i : allCurrency) {
                System.out.println(i.getCodeName());
            }
        }
        currencyAdminMenu();
    }

    //endregion AdminFlow =====

    //region HelperMethods =====

    private boolean isUserLoggedIn () {
        return currentUser != null;
    }

    //endregion HelperMethods =====

    // Дополнительные методы для других операций
    public static void main(String[] args) {
        // Инициализация и запуск UI
        ConsoleUI_V2 consoleUI = new ConsoleUI_V2();
        consoleUI.auth();
    }
}
