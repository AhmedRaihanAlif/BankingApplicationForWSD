import java.util.ArrayList;
import java.util.Scanner;
enum AccountType {
    SAVINGS,
    CURRENT,
    SALARY
}

class BankAccount {
    private String name;
    private int number;
    private String creationDate;
    private double balance;
    private double minimumBalance;
    private AccountType accountType;

    public BankAccount(String name, int number, String creationDate, double balance, double minimumBalance, AccountType accountType) {
        this.name = name;
        this.number = number;
        this.creationDate = creationDate;
        this.balance = balance;
        this.minimumBalance = minimumBalance;
        this.accountType = accountType;
    }
    public void displayAccount() {
        System.out.println("Name: " + name);
        System.out.println("Account Number: " + number);
        System.out.println("Creation Date: " + creationDate);
        System.out.println("Account Type: " + accountType);
        System.out.println("Balance: $" + balance);
        System.out.println("Minimum Balance: $" + minimumBalance);
        System.out.println();
    }

    public void deposit(double amount) {
        balance += amount;
        System.out.println("Deposit successful. Updated balance: $" + balance);
    }


    public void withdraw(double amount) {
        if (accountType == AccountType.SAVINGS && (balance - amount) < 500) {
            System.out.println("Withdrawal failed. Minimum balance requirement for SAVINGS account not met.");
        } else if (accountType == AccountType.CURRENT && (balance - amount) < 1000) {
            System.out.println("Withdrawal failed. Minimum balance requirement for CURRENT account not met.");
        } else if (accountType == AccountType.SALARY && (balance - amount) < 1500) {
            System.out.println("Withdrawal failed. Minimum balance requirement for SALARY account not met.");
        } else if (balance - amount >= minimumBalance) {
            balance -= amount;
            System.out.println("Withdrawal successful. Updated balance: $" + balance);
        } else {
            System.out.println("Insufficient funds. Withdrawal failed. Minimum balance requirement not met.");
        }
    }


    public double getMinimumBalance() {
        return minimumBalance;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public int getNumber() {
        return number;
    }

    public double getBalance() {
        return 0;
    }
}

public class BankingApplication {
    private static ArrayList<BankAccount> accounts = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int choice;
        do {
            displayMenu();
            System.out.print("Enter choice from 1-8: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    createAccount();
                    break;
                case 2:
                    displayAllAccounts();
                    break;
                case 3:
                    updateAccount();
                    break;
                case 4:
                    deleteAccount();
                    break;
                case 5:
                    depositAmount();
                    break;
                case 6:
                    withdrawAmount();
                    break;
                case 7:
                    searchAccount();
                    break;
                case 8:
                    System.out.println("Exiting the application.");
                    break;
                default:
                    System.out.println("Invalid choice.");
            }

        } while (choice != 8);

        scanner.close();
    }

    private static void displayMenu() {
        System.out.println("1. Create a new account");
        System.out.println("2. Display all accounts");
        System.out.println("3. Update an account");
        System.out.println("4. Delete an account");
        System.out.println("5. Deposit an amount into your account");
        System.out.println("6. Withdraw an amount from your account");
        System.out.println("7. Search for account");
        System.out.println("8. Exit");
    }

    private static void createAccount() {
        System.out.print("Enter account holder's name: ");
        String name = scanner.next();
        System.out.print("Enter account number: ");
        int number = scanner.nextInt();
        System.out.print("Enter creation date: ");
        String creationDate = scanner.next();
        System.out.print("Enter initial balance: $");
        double balance = scanner.nextDouble();

        AccountType accountType;
        System.out.print("Enter account type (SAVINGS, CURRENT, SALARY): ");
        accountType = AccountType.valueOf(scanner.next().toUpperCase());

        double minimumBalance;
        if (accountType == AccountType.SAVINGS) {
            minimumBalance = 500;
        } else if (accountType == AccountType.CURRENT) {
            minimumBalance = 1000;
        } else if (accountType == AccountType.SALARY) {
            minimumBalance = 1500;
        } else {
            System.out.print("Enter minimum balance requirement: $");
            minimumBalance = scanner.nextDouble();
        }

        if (balance < minimumBalance) {
            System.out.println("Error: Initial balance is less than the required minimum balance.");
            return;
        }

        BankAccount newAccount = new BankAccount(name, number, creationDate, balance, minimumBalance, accountType);
        accounts.add(newAccount);

        System.out.println("Account created successfully.");
    }

    private static void displayAllAccounts() {
        if (accounts.isEmpty()) {
            System.out.println("No accounts found.");
        } else {
            System.out.println("All Accounts:");
            for (BankAccount account : accounts) {
                account.displayAccount();
            }
        }
    }

    private static void updateAccount() {
        System.out.print("Enter account number to update: ");
        int accountNumber = scanner.nextInt();

        for (BankAccount account : accounts) {
            if (accountNumber == account.getNumber()) {
                System.out.print("Enter new balance: $");
                double newBalance = scanner.nextDouble();
                account.deposit(newBalance - account.getBalance());
                System.out.println("Account updated successfully.");
                return;
            }
        }

        System.out.println("Account not found.");
    }

    private static void deleteAccount() {
        System.out.print("Enter account number to delete: ");
        int accountNumber = scanner.nextInt();

        for (BankAccount account : accounts) {
            if (accountNumber == account.getNumber()) {
                accounts.remove(account);
                System.out.println("Account deleted successfully.");
                return;
            }
        }

        System.out.println("Account not found. Deletion failed.");
    }

    private static void depositAmount() {
        System.out.print("Enter account number to deposit into: ");
        int accountNumber = scanner.nextInt();

        for (BankAccount account : accounts) {
            if (accountNumber == account.getNumber()) {
                System.out.print("Enter deposit amount: ");
                double amount = scanner.nextDouble();
                account.deposit(amount);
                return;
            }
        }

        System.out.println("Account not found. Deposit failed.");
    }

    private static void withdrawAmount() {
        System.out.print("Enter account number to withdraw from: ");
        int accountNumber = scanner.nextInt();

        for (BankAccount account : accounts) {
            if (accountNumber == account.getNumber()) {
                System.out.print("Enter withdrawal amount: $");
                double amount = scanner.nextDouble();
                account.withdraw(amount);
                return;
            }
        }

        System.out.println("Account not found. Withdrawal failed.");
    }


    private static void searchAccount() {
        System.out.print("Enter account number to search for: ");
        int accountNumber = scanner.nextInt();

        for (BankAccount account : accounts) {
            if (accountNumber == account.getNumber()) {
                System.out.println("Account found:");
                account.displayAccount();
                return;
            }
        }

        System.out.println("Account not found.");
    }
}
