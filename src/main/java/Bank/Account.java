package Bank;

import java.io.Serializable;
import java.util.ArrayList;

public class Account implements Serializable {
    public static long sumOfCredits = 0;
    private static ArrayList<Account> allAccounts = new ArrayList<>();
    private static String bossUsername = "BOSS";
    private static String bossPassword = "manuel";
    public static Account bossAccount = new Account(bossUsername, bossPassword, "first name", "second name");
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
        return this == bossAccount ? this.balance : this.balance;
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

    public static ArrayList<Account> getAllAccounts() {
        return allAccounts;
    }

}
