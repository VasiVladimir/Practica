import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Account {

    private double balance;
    private List<Transaction> transactions;

    public Account() {
        balance = 0;
        transactions = new ArrayList<>();
    }

    // Внутренний класс для хранения информации об операциях
    public class Transaction {
        private Date date;
        private String type;
        private double amount;
        private double balanceAfter;

        public Transaction(String type, double amount, double balanceAfter) {
            this.date = new Date();
            this.type = type;
            this.amount = amount;
            this.balanceAfter = balanceAfter;
        }

        public String toString() {
            return date + " | " + type + " | Сумма: " + amount + " | Баланс после операции: " + balanceAfter;
        }
    }

    // Метод для внесения средств на счет
    public void deposit(double amount) {
        balance += amount;
        Transaction transaction = new Transaction("Поступление", amount, balance);
        transactions.add(transaction);
    }

    // Метод для снятия средств со счета
    public void withdraw(double amount) {
        if (balance >= amount) {
            balance -= amount;
            Transaction transaction = new Transaction("Снятие", amount, balance);
            transactions.add(transaction);
        } else {
            System.out.println("Недостаточно средств");
        }
    }

    // Метод для осуществления платежа
    public void payment(double amount) {
        if (balance >= amount) {
            balance -= amount;
            Transaction transaction = new Transaction("Платеж", amount, balance);
            transactions.add(transaction);
        } else {
            System.out.println("Недостаточно средств");
        }
    }

    // Метод для вывода всех операций
    public void printTransactions() {
        for (Transaction t : transactions) {
            System.out.println(t);
        }
    }

    // Получение текущего баланса
    public double getBalance() {
        return balance;
    }

    // Основной метод с возможностью выбора команд и ввода суммы
    public static void main(String[] args) {
        Account account = new Account();
        Scanner scanner = new Scanner(System.in);
        String command;
        double amount;

        System.out.println("Добро пожаловать в систему управления счетом.");
        System.out.println("Доступные команды: поступление, снятие, платеж, вывод, выход");

        while (true) {
            System.out.print("Введите команду: ");
            command = scanner.nextLine().trim().toLowerCase();

            if (command.equals("выход")) {
                break;
            } else if (command.equals("поступление")) {
                System.out.print("Введите сумму для внесения: ");
                if (scanner.hasNextDouble()) {
                    amount = scanner.nextDouble();
                    scanner.nextLine(); // Очистка буфера
                    account.deposit(amount);
                    System.out.println("Средства внесены на счет.");
                } else {
                    System.out.println("Некорректная сумма.");
                    scanner.nextLine(); // Очистка буфера
                }
            } else if (command.equals("снятие")) {
                System.out.print("Введите сумму для снятия: ");
                if (scanner.hasNextDouble()) {
                    amount = scanner.nextDouble();
                    scanner.nextLine(); // Очистка буфера
                    account.withdraw(amount);
                } else {
                    System.out.println("Некорректная сумма.");
                    scanner.nextLine(); // Очистка буфера
                }
            } else if (command.equals("платеж")) {
                System.out.print("Введите сумму платежа: ");
                if (scanner.hasNextDouble()) {
                    amount = scanner.nextDouble();
                    scanner.nextLine(); // Очистка буфера
                    account.payment(amount);
                } else {
                    System.out.println("Некорректная сумма.");
                    scanner.nextLine(); // Очистка буфера
                }
            } else if (command.equals("вывод")) {
                account.printTransactions();
                System.out.println("Текущий баланс: " + account.getBalance());
            } else {
                System.out.println("Неверная команда. Попробуйте снова.");
            }
        }

        System.out.println("Спасибо за использование системы.");
        scanner.close();
    }
}
