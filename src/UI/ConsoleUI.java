package UI;

import java.util.Scanner;

public class ConsoleUI {
    private double balance;

    public ConsoleUI (double initialBalance) {
        this.balance = initialBalance;
    }

    public void displayMenu() {
        System.out.println("Bank Machine User Interface");
        System.out.println("1. Check Balance");
        System.out.println("2. Deposit");
        System.out.println("3. Withdraw");
        System.out.println("4. Exit");
    }

    public void checkBalance() {
        System.out.println("Your balance is: $" + balance);
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("Deposit successful. Your new balance is: $" + balance);
        } else {
            System.out.println("Invalid deposit amount. Please enter a positive value.");
        }
    }

    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            System.out.println("Withdrawal successful. Your new balance is: $" + balance);
        } else {
            System.out.println("Invalid withdrawal amount or insufficient funds.");
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ConsoleUI bankMachine = new ConsoleUI(1000.0); 

        int choice;
        do {
            bankMachine.displayMenu();
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    bankMachine.checkBalance();
                    break;
                case 2:
                    System.out.print("Enter deposit amount: $");
                    double depositAmount = scanner.nextDouble();
                    bankMachine.deposit(depositAmount);
                    break;
                case 3:
                    System.out.print("Enter withdrawal amount: $");
                    double withdrawalAmount = scanner.nextDouble();
                    bankMachine.withdraw(withdrawalAmount);
                    break;
                case 4:
                    System.out.println("Exiting Bank Machine. Thank you!");
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }
        } while (choice != 4);

        scanner.close();
    }
}

