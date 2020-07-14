package Bank;

import java.util.ArrayList;

public class Account {
    private static ArrayList<Account> allAccounts = new ArrayList<>();
    private String firstName;
    private String secondName;
    private String username;
    private String password;
    private long balance = 0;

    public Account(String username, String password, String firstName, String secondName) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.username = username;
        this.password = password;
        allAccounts.add(this);
    }

    public long getBalance() {
        return balance;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setBalance(long balance) {
        this.balance = balance;
    }

    public static Account getAccountWithUsername(String username) {
        for (Account account : allAccounts) {
            if (account.username.equals(username)) {
                return account;
            }
        }
        return null;
    }

}
