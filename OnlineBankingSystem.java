import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

class User {
    String username;
    String password;
    double balance;
    List<String> transactions;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.balance = 0;
        this.transactions = new ArrayList<>();
    }
}

public class OnlineBankingSystem {
    private static Map<String, User> users = new HashMap<>();

    public static void createUser(String username, String password) {
        if (!users.containsKey(username)) {
            users.put(username, new User(username, password));
            System.out.println("Account created successfully.");
        } else {
            System.out.println("Username already exists.");
        }
    }

    public static boolean login(String username, String password) {
        if (users.containsKey(username)) {
            return users.get(username).password.equals(password);
        }
        return false;
    }

    public static void deposit(String username, double amount) {
        if (users.containsKey(username)) {
            User user = users.get(username);
            user.balance += amount;
            user.transactions.add("Deposited: $" + amount);
            System.out.println("$" + amount + " deposited successfully.");
        }
    }

    public static void withdraw(String username, double amount) {
        if (users.containsKey(username)) {
            User user = users.get(username);
            if (amount <= user.balance) {
                user.balance -= amount;
                user.transactions.add("Withdrawn: $" + amount);
                System.out.println("$" + amount + " withdrawn successfully.");
            } else {
                System.out.println("Insufficient balance.");
            }
        }
    }

    public static void transfer(String sender, String recipient, double amount) {
        if (users.containsKey(sender) && users.containsKey(recipient)) {
            User senderUser = users.get(sender);
            User recipientUser = users.get(recipient);
            if (amount <= senderUser.balance) {
                senderUser.balance -= amount;
                recipientUser.balance += amount;
                senderUser.transactions.add("Transferred: $" + amount + " to " + recipient);
                recipientUser.transactions.add("Received: $" + amount + " from " + sender);
                System.out.println("$" + amount + " transferred successfully to " + recipient + ".");
            } else {
                System.out.println("Insufficient balance.");
            }
        }
    }

    public static void viewTransactionHistory(String username) {
        if (users.containsKey(username)) {
            User user = users.get(username);
            System.out.println("Transaction history for " + username + ":");
            for (String transaction : user.transactions) {
                System.out.println(transaction);
            }
        } else {
            System.out.println("User not found.");
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\nOptions:");
            System.out.println("1. Create Account");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Select an option: ");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    System.out.print("Enter your username: ");
                    String username = scanner.next();
                    System.out.print("Enter your password: ");
                    String password = scanner.next();
                    createUser(username, password);
                    break;
                case 2:
                    System.out.print("Enter your username: ");
                    username = scanner.next();
                    System.out.print("Enter your password: ");
                    password = scanner.next();
                    if (login(username, password)) {
                        while (true) {
                            System.out.println("\nAccount Options:");
                            System.out.println("1. Deposit");
                            System.out.println("2. Withdraw");
                            System.out.println("3. Transfer Funds");
                            System.out.println("4. View Transaction History");
                            System.out.println("5. Logout");
                            System.out.print("Select an option: ");
                            int subChoice = scanner.nextInt();
                            switch (subChoice) {
                                case 1:
                                    System.out.print("Enter the amount to deposit: ");
                                    double depositAmount = scanner.nextDouble();
                                    deposit(username, depositAmount);
                                    break;
                                case 2:
                                    System.out.print("Enter the amount to withdraw: ");
                                    double withdrawAmount = scanner.nextDouble();
                                    withdraw(username, withdrawAmount);
                                    break;
                                case 3:
                                    System.out.print("Enter recipient's username: ");
                                    String recipient = scanner.next();
                                    System.out.print("Enter the amount to transfer: ");
                                    double transferAmount = scanner.nextDouble();
                                    transfer(username, recipient, transferAmount);
                                    break;
                                case 4:
                                    viewTransactionHistory(username);
                                    break;
                                case 5:
                                    break;
                                default:
                                    System.out.println("Invalid option.");
                            }
                            if (subChoice == 5) {
                                break;
                            }
                        }
                    } else {
                        System.out.println("Invalid username or password.");
                    }
                    break;
                case 3:
                    System.exit(0);
                default:
                    System.out.println("Invalid option.");
            }
        }
    }
}

