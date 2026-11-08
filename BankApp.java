import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Bank Management Console App (Basic Version)
 * Features: Deposit, Withdraw, Check Balance, Mini Statement, Exit
 * Concepts used: Scanner, loops, conditional statements, methods, OOP
 */
public class BankApp {

    // ---------- Account class (the "locker" that holds the money) ----------
    static class Account {
        private final String holderName;
        private final long accountNumber;
        private double balance;
        private final List<String> transactions = new ArrayList<>();

        Account(String holderName, long accountNumber, double openingBalance) {
            this.holderName = holderName;
            this.accountNumber = accountNumber;
            this.balance = openingBalance;
            transactions.add(String.format("Account opened with Rs. %.2f", openingBalance));
        }

        void deposit(double amount) {
            if (amount <= 0) {
                System.out.println("Invalid amount! Deposit must be greater than 0.");
                return;
            }
            balance += amount;
            transactions.add(String.format("Deposited  Rs. %.2f | Balance: Rs. %.2f", amount, balance));
            System.out.printf("Rs. %.2f deposited successfully.%n", amount);
        }

        void withdraw(double amount) {
            if (amount <= 0) {
                System.out.println("Invalid amount! Withdrawal must be greater than 0.");
            } else if (amount > balance) {
                System.out.println("Insufficient balance! Transaction declined.");
            } else {
                balance -= amount;
                transactions.add(String.format("Withdrawn  Rs. %.2f | Balance: Rs. %.2f", amount, balance));
                System.out.printf("Rs. %.2f withdrawn successfully.%n", amount);
            }
        }

        void checkBalance() {
            System.out.printf("Current Balance: Rs. %.2f%n", balance);
        }

        void printStatement() {
            System.out.println("\n----- Mini Statement -----");
            System.out.println("Holder : " + holderName);
            System.out.println("A/C No : " + accountNumber);
            for (int i = 0; i < transactions.size(); i++) {
                System.out.println((i + 1) + ". " + transactions.get(i));
            }
            System.out.println("--------------------------");
        }

        String getHolderName() {
            return holderName;
        }
    }

    // ---------- Safe input helpers (don't crash on wrong input) ----------
    static double readAmount(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = sc.nextLine().trim();
            try {
                return Double.parseDouble(input);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    static int readChoice(Scanner sc) {
        System.out.print("Enter your choice (1-5): ");
        String input = sc.nextLine().trim();
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            return -1; // treated as invalid choice by the menu
        }
    }

    static void showMenu() {
        System.out.println("\n===== BANK MENU =====");
        System.out.println("1. Deposit");
        System.out.println("2. Withdraw");
        System.out.println("3. Check Balance");
        System.out.println("4. Mini Statement");
        System.out.println("5. Exit");
        System.out.println("=====================");
    }

    // ---------- Main ----------
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("=================================");
        System.out.println("   WELCOME TO CONSOLE BANK APP   ");
        System.out.println("=================================");

        System.out.print("Enter account holder name: ");
        String name = sc.nextLine().trim();
        if (name.isEmpty()) {
            name = "Customer";
        }

        double opening = readAmount(sc, "Enter opening balance: Rs. ");
        while (opening < 0) {
            System.out.println("Opening balance cannot be negative.");
            opening = readAmount(sc, "Enter opening balance: Rs. ");
        }

        long accNo = 1000000000L + (long) (Math.random() * 9000000000L);
        Account account = new Account(name, accNo, opening);
        System.out.println("\nAccount created for " + account.getHolderName()
                + " | A/C No: " + accNo);

        boolean running = true;
        while (running) {
            showMenu();
            int choice = readChoice(sc);

            switch (choice) {
                case 1:
                    account.deposit(readAmount(sc, "Enter amount to deposit: Rs. "));
                    break;
                case 2:
                    account.withdraw(readAmount(sc, "Enter amount to withdraw: Rs. "));
                    break;
                case 3:
                    account.checkBalance();
                    break;
                case 4:
                    account.printStatement();
                    break;
                case 5:
                    System.out.println("Thank you for banking with us, " + name + "!");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice! Please select between 1 and 5.");
            }
        }

        sc.close();
    }
}