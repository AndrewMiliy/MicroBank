package UI;

import Model.*;
import Repository.*;
import Service.*;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class ConsoleUI_V2 {
    private final UserService userService;
    private final BankAccountService bankAccountService;
    private final TransactionService transactionService;
    private final CurrencyService currencyService;
    private final ExchangeRateService exchangeRateService;
    private final ValidationService validationService;
    private final Scanner scanner;
    private UserModel currentUser;
    private UserModel selectedUser;

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
            System.out.println(brightWhite+underline + "Добро пожаловать в микро банк" + reset);
            System.out.println(brightYellow + "Выберите опцию:" + reset + "\n1. Регистрация \n2. Вход \n0. Выход");
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
                    System.out.println(brightGreen + "До свидания!" + reset);
                    System.exit(0);
                    break;
                default:
                    System.out.println(brightRed + "Неверная опция." + reset);
            }
        }
    }

    private void registerUser() {
        System.out.println(brightWhite + underline + "Регистрация нового пользователя" + reset);
        System.out.print("Введите имя: ");
        String firstName = scanner.nextLine();
        System.out.print("Введите фамилию: ");
        String lastName = scanner.nextLine();
        System.out.print("Введите email: ");
        String email = scanner.nextLine();
        System.out.print("Введите пароль: ");
        String password = scanner.nextLine();

        // Валидация email
        if (!ValidationService.validateEmail(email)) {
            System.out.println(brightRed + "Ошибка валидации: некорректный email." + reset);
            return;
        }

        // Валидация пароля
        if (!ValidationService.validatePassword(password)) {
            System.out.println(brightRed + "Ошибка валидации: пароль должен быть не менее 8 символов и содержать буквы и цифры." + reset);
            return;
        }

        // Валидация имени и фамилии
        if (!ValidationService.validateFullName(firstName + " " + lastName)) {
            System.out.println(brightRed + "Ошибка валидации: имя и фамилия должны содержать только буквы." + reset);
            return;
        }

        // Проверка на существование пользователя с таким же email
        if (userService.getUserByEmail(email) != null) {
            System.out.println(brightRed + "Пользователь с таким email уже существует." + reset);
            return;
        }

        // Создание пользователя
        try {
            currentUser = userService.register(firstName, lastName, password, email);
            System.out.println(dimGreen + "Пользователь успешно зарегистрирован: " + reset + currentUser.getId());
            if (currentUser.getUserRole().equals(UserRole.ADMIN)) {
                showAdminMenu();
            } else {
                showUserMenu();
            }
        } catch (Exception e) {
            System.out.println(brightRed + "Ошибка регистрации: " + dimRed + e.getMessage() + reset);
        }
    }

    private void loginUser() {
        System.out.println(brightWhite +underline+ "Вход в систему" + reset);
        System.out.print("Введите email: ");
        String email = scanner.nextLine();
        System.out.print("Введите пароль: ");
        String password = scanner.nextLine();

        // Проверка учетных данных пользователя
        UserModel user = userService.authorize(email, password);
        if (user != null) {
            currentUser = user;
            System.out.println(brightGreen + "Вход выполнен успешно. Добро пожаловать, " + reset + user.getEmail());
            if (user.getUserRole().equals(UserRole.ADMIN)) {
                showAdminMenu();
            } else {
                showUserMenu();
            }
        } else {
            System.out.println(brightRed + "Неверный email или пароль.");
            System.out.println(brightYellow + "Возвращаем в окно авторизации.");
            auth();
        }
    }

    //endregion AuthFlow =====

    //region UserFlow =====

    // Методы для работы с пользователем
    private void showUserMenu() {
        while (isUserLoggedIn()) {
            System.out.println(brightWhite + underline + "Пользовательское меню:" + reset);
            System.out.println(brightYellow + "Выберите опцию:" + reset + "\n1. Мои счета \n0. Выйти из аккаунта");
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
            System.out.println(brightWhite +underline+"Меню управления счетами"+reset);
            bankAccounts = BankAccountService.getAllAccountsForUser(currentUser.getId());
            for (var i : bankAccounts) {
                System.out.println(brightWhite + i.getCurrencyCode() + ": " + brightGreen+ i.getFormattedBalance()+reset);
            }
            System.out.println(!bankAccounts.isEmpty() ?
                    brightYellow + "Что бы выбрать счет, введите код счета" + reset:
                    brightYellow + "Счетов еще нет. Вы можете его создать" + reset);
            System.out.println("2. Создать счет \n0. Вернуться в предыдущее меню");
            String choice = scanner.nextLine();

            switch (choice) {
                case "2":
                    if (!createBankAccountMenu()) {
                        System.out.println(brightRed + "Не получилось создать счет, попробуйте ещё раз." + reset);
                    }
                    //showBankAccountsMenu();
                    // Логика создания счета
                    break;
                case "0":
                    showUserMenu(); //Возвращение в предыдущее меню
                    break;
                default:
                    if(!bankAccounts.isEmpty())
                    {
                        try
                        {
                            for (var i : bankAccounts) {
                                if (i.getCurrencyCode().equals(choice)) {
                                    showBankAccountMenu(i);
                                }
                            }
                            System.out.println(brightRed + "Счета с таким кодом не найдено или неверная опция. Попробуйте ещё раз." + reset);
                        }
                        catch (Exception ignored) // Игнорирование ошибки изменения списка во время итерации при возврате в это меню
                        {}
                    }
                    break;
            }
        }
    }

    // Методы для работы со счетом
    private void showBankAccountMenu(BankAccountModel bankAccount) {
        System.out.println(bankAccount.getCurrencyCode() + ": " + bankAccount.getFormattedBalance());
        while (isUserLoggedIn()) {
            System.out.println(brightWhite +underline+"Меню управления счетом"+reset);
            System.out.println(brightYellow +"Выберите действие"+reset);
            System.out.println("""
                    1. Deposit\s
                    2. Withdraw\s
                    3. Transfer\s
                    4. History\s
                    5. Delete\s
                    0. Вернуться в предыдущее меню""");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Очистка буфера сканера
            switch (choice) {
                case 1:
                    if (!deposit(bankAccount)) {
                        System.out.println(brightRed + "Не получилось сделать deposit, попробуйте ещё раз." + reset);
                    } else {
                        System.out.println(brightGreen + "Счет успешно пополнен" + reset);
                    }
                    break;
                case 2:
                    if (!withdraw(bankAccount)) {
                        System.out.println(brightRed + "Не получилось сделать withdraw, попробуйте ещё раз." + reset);
                    } else {
                        System.out.println(brightGreen + "Деньги успешно сняты" + reset);
                    }
                    break;
                case 3:
                    if(!transfer(bankAccount)) {
                        System.out.println(brightRed + "Не получилось сделать transfer, попробуйте ещё раз." + reset);
                    } else {
                        System.out.println(brightGreen + "Валюта успешно переведена" + reset);
                    }
                    break;
                case 4:
                    historyBankAccount(bankAccount);
                    break;
                case 5:
                    if (!deleteBankAccount(bankAccount)) {
                        System.out.println(brightRed + "Не получилось сделать delete, попробуйте ещё раз." + reset);
                    } else {
                        System.out.println(brightGreen + "Счет успешно удалён" + reset);
                        showBankAccountsMenu();
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
        System.out.println(brightWhite +underline+"Меню пополнения счета"+reset);
        System.out.println(brightYellow +"Введите сумму для пополнения кратную единице"+reset);
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
        System.out.println(brightWhite +underline+"Меню снятия со счета"+reset);
        System.out.println(brightYellow +"Введите сумму для снятия кратную единице"+reset);
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
        System.out.println(brightWhite +underline+"Меню перевода средств"+reset);
        System.out.println(brightYellow +"Введите счет для перевода и \nвведите сумму для снятия кратную единице"+reset);
        System.out.println("Доступные счета: ");
        for (var i : BankAccountService.getAllAccountsForUser(currentUser.getId())) {
            if (i.getCurrencyCode() != bankAccount.getCurrencyCode()) {
                System.out.println(i.getCurrencyCode() + ": " + i.getFormattedBalance());
            }
        }
        String accountTo = scanner.nextLine();
        System.out.println("Введите сумму для перевода кратную единице");
        int transfer = scanner.nextInt();
        scanner.nextLine(); // Очистка буфера сканера
        return transactionService.exchangeCurrency(bankAccount.getBankAccountId(), bankAccountService.getAccountByUserIdAndCurrencyCode(currentUser.getId(), accountTo).getBankAccountId(), transfer);
    }

    // Методы для просмотра истории счета
    private void historyBankAccount(BankAccountModel bankAccount) {
        System.out.println(brightWhite +underline+"История транзакций по счету"+reset);
        List<TransactionModel> history = transactionService.getTransactionHistory(bankAccount.getBankAccountId());
        if (history == null || (long) history.size() == 0) {
            System.out.println("Этот счет, ещё не имеет своей истории, начните её.");
        } else {
            System.out.println("История счёта");
            for (var i : history) {
                System.out.println(brightWhite + i.getDate() + reset + " - " +
                        (i.getTransactionType() == TransactionType.DEPOSIT?
                                dimGreen+ i.getTransactionType()+reset  + " - " +
                                        i.getCurrencyCode() + ": " + brightGreen + i.getFormattedAmount() + reset:
                                dimRed + i.getTransactionType()+reset + " - " +
                                        i.getCurrencyCode() + ": " + brightRed + i.getFormattedAmount() + reset));
            }
        }
        System.out.println(brightYellow + "Нажмите ввод для продолжения" + reset);
        scanner.nextLine(); // Очистка буфера сканера
        showBankAccountMenu(bankAccount);
    }

    // Методы для удаления счета
    private boolean deleteBankAccount(BankAccountModel bankAccount) {

        while (bankAccountService.getAccountByUserIdAndCurrencyCode(currentUser.getId(), bankAccount.getCurrencyCode()) != null) {
            System.out.println(brightWhite +underline+"Меню удаления счета"+reset);
            if(bankAccount.getBalance() > 0)
            {
                System.out.println(brightRed+ "У вас на счету есть деньги. Что бы продолжить удаление переведите их на другой счет."+reset);
                System.out.println("""
                1. Перевести\s
                0. Вернуться.""");
                int choice = scanner.nextInt();
                scanner.nextLine(); // Очистка буфера сканера
                switch (choice){
                    case 1:
                        break;
                    case 0:
                        return false;
                }
            }
            else {
                bankAccountService.deleteBankAccount(currentUser.getId(), bankAccount.getBankAccountId());
                return true;
            }
            System.out.println("На какой счет их перевести?");
            for (var i : BankAccountService.getAllAccountsForUser(currentUser.getId())) {
                if (i.getCurrencyCode() != bankAccount.getCurrencyCode()) {
                    System.out.println(i.getCurrencyCode() + ": " + dimGreen + i.getFormattedBalance() + reset);
                }
            }
            System.out.println("Ждём ввод счёта:");
            String accountName = scanner.nextLine();

            BankAccountModel accountTo = bankAccountService.getAccountByUserIdAndCurrencyCode(currentUser.getId(), accountName);
            if (accountTo != null) {
                transactionService.exchangeCurrency(bankAccount.getBankAccountId(), accountTo.getBankAccountId(), bankAccount.getBalance());

                System.out.println("Ваши деньги переведены на: " +
                        accountTo.getCurrencyCode() + ": " + dimGreen + accountTo.getFormattedBalance() + reset);
                System.out.println("По курсу: " +
                        brightWhite + ExchangeRateService.getCurrentExchangeRate(bankAccount.getCurrencyCode(), accountTo.getCurrencyCode()).getRate() + reset);

                bankAccountService.deleteBankAccount(currentUser.getId(), bankAccount.getBankAccountId());
                return true;

            } else {
                System.out.println(brightRed + "Такого счета нет" + reset);
                System.out.println("1. Повтор \n 0. Вернуться.");
                int choice = scanner.nextInt();
                scanner.nextLine(); // Очистка буфера сканера
                switch (choice){
                    case 1:
                        return deleteBankAccount(bankAccount);
                    case 0:
                        return false;
                }
            }
        }
        return true;
    }

    // Методы для создания счета
    private boolean createBankAccountMenu() {
        System.out.println(brightWhite +underline+"Меню создания счета"+reset);
        System.out.println(brightYellow  + "Доступные валюты:" + reset);
        for (var i : CurrencyService.getAllCurrencies()) {
            if(!BankAccountService.getAllAccountsForUser(currentUser.getId()).contains(i.getCodeName()))
            {
                System.out.println(i.getCodeName());
            }
        }
        System.out.println(brightWhite + "Введите код валюты" + reset);
        String choice = scanner.nextLine();
        for (var i : CurrencyService.getAllCurrencies()) {
            if (i.getCodeName().equals(choice)) {
                boolean isUserHaveAccount = false;

                if(!BankAccountService.getAllAccountsForUser(currentUser.getId()).isEmpty())
                {
                    for (var t : BankAccountService.getAllAccountsForUser(currentUser.getId())) {
                        if (t.getCurrencyCode().equals(choice)) {
                            isUserHaveAccount = true;
                            break;
                        }
                    }
                }
                if(isUserHaveAccount)
                {
                    System.out.println("У вас уже есть счет с такой валютой");
                    return false;
                }
                else
                {
                    BankAccountModel newBankAccount = new BankAccountModel(IDCounterService.getNextBankAccountID(), currentUser.getId(), choice);
                    bankAccountService.addBankAccount(currentUser.getId(), newBankAccount);

                    System.out.println("Счет успешно создан: " + newBankAccount.getCurrencyCode() + ": " + newBankAccount.getFormattedBalance());
                    return true;
                }
            }
        }
        System.out.println("Такой валюты нет");
        return false;
    }

    //endregion UserFlow =====

    //region AdminFlow =====

    private void showAdminMenu() {
        while (isUserLoggedIn()) {
            System.out.println(brightWhite + underline + "Меню администратора:" + reset);
            System.out.println(brightYellow  + "Выберите действие:" + reset);
            System.out.println("""
                    1. Управление пользователями\s
                    2. Управление валютами\s
                    0. Выйти из аккаунта""");
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
        System.out.println(brightWhite + underline + "Меню управления пользователями:" + reset);
        System.out.println(brightYellow  + "Выберите действие:" + reset);
        System.out.println("""
                1. Показать список пользователей\s
                2. Найти пользователя\s
                3. Выбор пользователя (по ID)\s
                4. Назначить администратора\s
                5. Удалить администратора\s
                0. Вернуться""");
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
                    List<BankAccountModel> bankAccountModelList = BankAccountService.getAllAccountsForUser(selectedUser.getId());
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
        System.out.println(brightWhite + underline + "Меню управления валютами:" + reset);
        System.out.println(brightYellow  + "Выберите действие:" + reset);
        System.out.println("""
                1. Показать список валют\s
                2. Добавить валюту\s
                3. Изменить валюту (по Code)\s
                4. Просмотр операций по валюте\s
                5. Удалить валюту (по Code)\s
                0. Вернуться""");
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
                updateCurrency();
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

    private void updateCurrency() {
        System.out.println(brightWhite + underline + "Меню редактирования валюты:" + reset);
        System.out.println("Доступные валюты:");
        showCurrencyList();
        System.out.println(brightYellow + "Введите код валюты:" + reset);
        String code = scanner.nextLine();
        CurrencyModel currency = currencyService.getCurrency(code);
        if (currency == null) {
            System.out.println(brightRed + "Валюты с таким кодом не существует" + reset);
        } else {
            System.out.println(brightYellow + "Введите новый код валюты" + reset);
            String newCode = scanner.nextLine();
            currency.setCodeName(newCode);
            currencyService.updateCurrency(currentUser, code, currency);
            transactionService.updateCurrencyInTransactions(code, newCode);
            bankAccountService.updateCurrencyInBankAccounts(code, newCode);
            System.out.println(brightGreen + "Валюта успешно изменена" + reset);
        }
        currencyAdminMenu();
    }

    private void showCurrencyTransactions() {
        System.out.println(brightWhite + underline + "Просмотр истории транзакция по валюте:" + reset);
        System.out.println("Доступные валюты:");
        showCurrencyList();
        System.out.println(brightYellow + "Введите код валюты" + reset);
        String code = scanner.nextLine();
        List<TransactionModel> transactions = transactionService.getAllTransactionsByCurrencyCode(code);
        if (transactions == null || transactions.isEmpty()) {
            System.out.println(brightRed + "Транзакций нет" + reset);
        } else {
            for (var i : transactions) {
                System.out.println(brightYellow + i.getTransactionId() + brightWhite+ ": " +
                        i.getDate() + " - " +
                        (i.getTransactionType() == TransactionType.DEPOSIT?
                                dimGreen+i.getTransactionType()+reset+ " - " + i.getCurrencyCode() + ": "+dimGreen + i.getFormattedAmount()+reset:
                                dimRed+i.getTransactionType()+reset+ " - " + i.getCurrencyCode() + ": "+dimRed + i.getFormattedAmount()+reset) );
            }
        }
        currencyAdminMenu();
    }

    // Методы для работы с валютами
    private void addCurrency() {
        System.out.println(brightWhite + underline + "Добавление валюты в систему:" + reset);
        System.out.println(brightYellow + "Введите код валюты" + reset);
        CurrencyModel newCurrency = new CurrencyModel("", scanner.nextLine());
        currencyService.addCurrency(currentUser, newCurrency);
        System.out.println("Валюта успешно создана");
        currencyAdminMenu();
    }

    private void userList () {
        System.out.println(brightWhite + underline + "Список пользователей системы:" + reset);
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
        showCurrencyList();
        currencyAdminMenu();
    }

    private void showCurrencyList()
    {
        System.out.println(brightWhite + underline + "Список валют системы:" + reset);
        var allCurrency = CurrencyService.getAllCurrencies();
        if (allCurrency == null || allCurrency.isEmpty()) {
            System.out.println(brightRed + "Валют нет" + reset);
        } else {
            System.out.println(brightWhite + "Список валют" + reset);
            for (var i : allCurrency) {
                System.out.println(i.getCodeName());
            }
        }
    }

    //endregion AdminFlow =====

    //region HelperMethods =====

    private boolean isUserLoggedIn () {
        return currentUser != null;
    }

    //endregion HelperMethods =====

    // Дополнительные методы для других операций
    public static void main(String[] args)  {

        // Инициализация и запуск UI
        ConsoleUI_V2 consoleUI = new ConsoleUI_V2();
        consoleUI.auth();


    }
}
