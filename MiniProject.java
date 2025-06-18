import java.util.*;

class User {
    String username;
    String password;
    double balance;
    List<String> transactions;

    User(String username, String password) {
        this.username = username;
        this.password = password;
        this.balance = 0.0;
        this.transactions = new ArrayList<>();
        transactions.add("Account created with balance ₹0.0");
    }

    void deposit(double amount) {
        balance += amount;
        transactions.add("Deposited ₹" + amount + ", current balance: ₹" + balance);
    }

    boolean withdraw(double amount) {
        if (amount <= balance) {
            balance -= amount;
            transactions.add("Withdrew ₹" + amount + ", current balance: ₹" + balance);
            return true;
        } else {
            transactions.add("Failed withdrawal attempt of ₹" + amount + " (Insufficient funds)");
            return false;
        }
    }

    void receiveTransfer(String fromUser, double amount) {
        balance += amount;
        transactions.add("Received ₹" + amount + " from " + fromUser + ", current balance: ₹" + balance);
    }

    boolean sendTransfer(User toUser, double amount) {
        if (amount <= balance) {
            balance -= amount;
            transactions.add("Sent ₹" + amount + " to " + toUser.username + ", current balance: ₹" + balance);
            toUser.receiveTransfer(this.username, amount);
            return true;
        } else {
            transactions.add("Failed transfer of ₹" + amount + " to " + toUser.username + " (Insufficient funds)");
            return false;
        }
    }

    void printTransactions() {
        System.out.println("Transaction History for " + username + ":");
        for (String t : transactions) {
            System.out.println("- " + t);
        }
    }
}

public class MiniProject {
    static Scanner sc = new Scanner(System.in);
    static HashMap<String, User> users = new HashMap<>();

    public static void main(String[] args) {
        System.out.println("=== Welcome to CLI Bank System ===");
        while (true) {
            System.out.println("\n1. Login\n2. Sign up\n3. Exit");
            System.out.print("Enter choice: ");
            int choice = sc.nextInt();
            sc.nextLine(); // Consume newline

            switch (choice) {
                case 1 -> login();
                case 2 -> signup();
                case 3 -> {
                    System.out.println("Thank you for using CLI Bank.");
                    return;
                }
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    static void signup() {
        System.out.print("Choose a username: ");
        String username = sc.nextLine();
        if (users.containsKey(username)) {
            System.out.println("Username already exists. Try login.");
            return;
        }
        System.out.print("Choose a password: ");
        String password = sc.nextLine();
        users.put(username, new User(username, password));
        System.out.println("Signup successful! You can now login.");
    }

    static void login() {
        System.out.print("Enter username: ");
        String username = sc.nextLine();
        if (!users.containsKey(username)) {
            System.out.println("User doesn't exist. Please signup first.");
            return;
        }
        System.out.print("Enter password: ");
        String password = sc.nextLine();
        User user = users.get(username);
        if (!user.password.equals(password)) {
            System.out.println("Incorrect password!");
            return;
        }
        System.out.println("Login successful! Welcome " + username);
        userDashboard(user);
    }

    static void userDashboard(User currentUser) {
        while (true) {
            System.out.println("\n-- Menu --");
            System.out.println("1. Check balance");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. View transactions");
            System.out.println("5. Transfer money");
            System.out.println("6. Logout");
            System.out.print("Choose an option: ");
            int choice = sc.nextInt();
            sc.nextLine(); // Consume newline

            switch (choice) {
                case 1 -> System.out.println("Current balance: ₹" + currentUser.balance);
                case 2 -> {
                    System.out.print("Enter deposit amount: ₹");
                    double amount = sc.nextDouble();
                    sc.nextLine();
                    if (amount <= 0) {
                        System.out.println("Amount must be positive.");
                    } else {
                        currentUser.deposit(amount);
                        System.out.println("Deposit successful.");
                    }
                }
                case 3 -> {
                    System.out.print("Enter withdraw amount: ₹");
                    double amount = sc.nextDouble();
                    sc.nextLine();
                    if (amount <= 0) {
                        System.out.println("Amount must be positive.");
                    } else if (!currentUser.withdraw(amount)) {
                        System.out.println("Insufficient balance!");
                    } else {
                        System.out.println("Withdrawal successful.");
                    }
                }
                case 4 -> currentUser.printTransactions();
                case 5 -> {
                    System.out.print("Enter recipient username: ");
                    String toUsername = sc.nextLine();
                    if (!users.containsKey(toUsername)) {
                        System.out.println("Recipient not found.");
                        break;
                    }
                    if (toUsername.equals(currentUser.username)) {
                        System.out.println("You can't send money to yourself.");
                        break;
                    }
                    System.out.print("Enter amount to transfer: ₹");
                    double amount = sc.nextDouble();
                    sc.nextLine();
                    if (amount <= 0) {
                        System.out.println("Amount must be positive.");
                    } else if (currentUser.sendTransfer(users.get(toUsername), amount)) {
                        System.out.println("Transfer successful.");
                    } else {
                        System.out.println("Transfer failed due to insufficient funds.");
                    }
                }
                case 6 -> {
                    System.out.println("Logged out.");
                    return;
                }
                default -> System.out.println("Invalid choice!");
            }
        }
    }
}
