package Bank;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

public class Transaction implements Serializable {
    private static ArrayList<Transaction> allTransaction = new ArrayList<>();
    private String fromUsername;
    private String toUsername;
    private TransactionType transactionType;
    private long amount;
    private boolean isDone = false;
    private String description;
    private int ID;


    public Transaction(TransactionType transactionType, String fromUsername, long amount, String description) {
        this.transactionType = transactionType;
        this.fromUsername = fromUsername;
        this.amount = amount;
        this.description = description;
        allTransaction.add(this);
        Random random = new Random();
        ID = transactionType.equals(TransactionType.DEPOSIT) ? 100000 + random.nextInt(100000) : 200000 + random.nextInt(100000);
    }

    public Transaction(String fromUsername, String toUsername, long amount, String description) {
        this.transactionType = TransactionType.TRANSFER;
        this.fromUsername = fromUsername;
        this.toUsername = toUsername;
        this.amount = amount;
        this.description = description;
        allTransaction.add(this);
        Random random = new Random();
        ID = random.nextInt(100000) + 300000;
    }

    public String getFromUsername() {
        return fromUsername;
    }

    public String toString() {
        if (transactionType.equals(TransactionType.WITHDRAW)) {
            return "withdraw from " + this.fromUsername + " with amount " + this.amount + ",description: " + description + ", ID: " + ID;
        } else if (transactionType.equals(TransactionType.TRANSFER)) {
            return "transfer from " + this.fromUsername + " to " + this.toUsername + " with amount " + this.amount + ",description: " + description + ", ID: " + ID;
        } else if (transactionType.equals(TransactionType.DEPOSIT)) {
            return "deposit from " + this.fromUsername + " with amount " + amount + ",description: " + description + ", ID: " + ID;
        }
        return null;
    }

    public static ArrayList<String> getAllTransactionsWithSource(String sourceUsername) {
        ArrayList<String> arrayList = new ArrayList<>();
        for (Transaction transaction : allTransaction) {
            if (transaction.isDone)
                continue;
            if (transaction.fromUsername.equals(sourceUsername)) {
                arrayList.add(transaction.toString());
            }
        }
        return arrayList;
    }

    public static ArrayList<String> getAllTransactionsInvolvingUsername(String username) {
        ArrayList<String> arrayList = new ArrayList<>();
        for (Transaction transaction : allTransaction) {
            if (transaction.isDone)
                continue;
            if (transaction.fromUsername.equals(username) || transaction.toUsername.equals(username)) {
                arrayList.add(transaction.toString());
            }
        }
        return arrayList;
    }

    public String withdraw(String username, long amount, String role) {
        Account account = Account.getAccountWithUsername(username);
        if (account == null) {
            return "invalid username";
        }
        if (account.getBalance() > amount) {
            account.setBalance(account.getBalance() - amount);
            Account.bossAccount.setBalance(Account.bossAccount.getBalance() + amount);
            this.isDone = true;
            Account.sumOfCredits += amount;
            return "successful";
        }
        return "not enough credit";
    }

    public String deposit(String username, long amount, String role) {
        Account account = Account.getAccountWithUsername(username);
        if (account == null) {
            return "invalid username";
        }
        if (role.equalsIgnoreCase("customer")) {
            account.setBalance(account.getBalance() + amount);
            this.isDone = true;
            return "successful";
        } else if (role.equalsIgnoreCase("salesman")) {
            account.setBalance(account.getBalance() + amount);
            Account.bossAccount.setBalance(Account.bossAccount.getBalance() - amount);
            this.isDone = true;
            return "successful";
        }
        return null;
    }

    public String transfer(String fromUsername, String toUsername, long amount) {
        Account firstAccount = Account.getAccountWithUsername(fromUsername);
        Account secondAccount = Account.getAccountWithUsername(toUsername);
        if (firstAccount == null || secondAccount == null) {
            return "one of usernames is invalid";
        }
        if (firstAccount.getBalance() >= amount) {
            firstAccount.setBalance(firstAccount.getBalance() - amount);
            secondAccount.setBalance(secondAccount.getBalance() + amount);
            this.isDone = true;
            return "successful";
        }
        return "not enough credit";
    }

    public boolean isDone() {
        return isDone;
    }

    public String Do(String role) {
        if (transactionType.equals(TransactionType.TRANSFER)) {
            return transfer(fromUsername, toUsername, amount);
        } else if (transactionType.equals(TransactionType.WITHDRAW)) {
            return withdraw(fromUsername, amount, role);
        } else if (transactionType.equals(TransactionType.DEPOSIT)) {
            return deposit(fromUsername, amount, role);
        }
        return "unsuccessful";
    }

    public static Transaction getTransactionBasedOnID(int id) {
        for (Transaction transaction : allTransaction) {
            if (transaction.ID == id) {
                return transaction;
            }
        }
        return null;
    }

    public long getAmount() {
        return amount;
    }

    public static ArrayList<Transaction> getAllTransaction() {
        return allTransaction;
    }

}
