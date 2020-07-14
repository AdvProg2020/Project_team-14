package Bank;

import java.util.ArrayList;

public class Transaction {
    private static ArrayList<Transaction> allTransaction = new ArrayList<>();
    private String fromUsername;
    private String toUsername;
    private TransactionType transactionType;
    private long amount;
    private String description;

    public Transaction(TransactionType transactionType, String fromUsername, long amount, String description) {
        this.transactionType = transactionType;
        this.fromUsername = fromUsername;
        this.amount = amount;
        this.description = description;
        allTransaction.add(this);
    }

    public Transaction(String fromUsername, String toUsername, long amount, String description) {
        this.transactionType = TransactionType.TRANSFER;
        this.fromUsername = fromUsername;
        this.toUsername = toUsername;
        this.amount = amount;
        this.description = description;
        allTransaction.add(this);
    }

    public String toString() {
        if (transactionType.equals(TransactionType.WITHDRAW)) {
            return "withdraw from " + this.fromUsername + " with amount " + this.amount;
        } else if (transactionType.equals(TransactionType.TRANSFER)) {
            return "transfer from " + this.fromUsername + " to " + this.toUsername + " with amount " + this.amount;
        } else if (transactionType.equals(TransactionType.DEPOSIT)) {
            return "deposit from " + this.fromUsername + " with amount " + amount;
        }
        return null;
    }

    public ArrayList<String> getAllTransactionsWithSource(String sourceUsername) {
        ArrayList<String> arrayList = new ArrayList<>();
        for (Transaction transaction : allTransaction) {
            if (transaction.fromUsername.equals(sourceUsername)) {
                arrayList.add(transaction.toString());
            }
        }
        return arrayList;
    }

    public ArrayList<String> getAllTransactionsInvolvingUsername(String username) {
        ArrayList<String> arrayList = new ArrayList<>();
        for (Transaction transaction : allTransaction) {
            if (transaction.fromUsername.equals(username) || transaction.toUsername.equals(username)) {
                arrayList.add(transaction.toString());
            }
        }
        return arrayList;
    }

    public boolean withdraw(String username, long amount) {
        Account account = Account.getAccountWithUsername(username);
        if (account == null) {
            return false;
        } else {
            if (account.getBalance() > amount) {
                account.setBalance(account.getBalance() - amount);
                return true;
            } else {
                return false;
            }
        }
    }

    public boolean deposit(String username, long amount) {
        Account account = Account.getAccountWithUsername(username);
        if (account == null) {
            return false;
        } else {
            account.setBalance(account.getBalance() + amount);
            return true;
        }
    }

    public boolean transfer(String fromUsername, String toUsername, long amount) {
        Account firstAccount = Account.getAccountWithUsername(fromUsername);
        Account secondAccount = Account.getAccountWithUsername(toUsername);
        if (firstAccount == null || secondAccount == null) {
            return false;
        } else {
            if (firstAccount.getBalance() >= amount) {
                firstAccount.setBalance(firstAccount.getBalance() - amount);
                secondAccount.setBalance(secondAccount.getBalance() + amount);
                return true;
            } else {
                return false;
            }
        }
    }
    
}
