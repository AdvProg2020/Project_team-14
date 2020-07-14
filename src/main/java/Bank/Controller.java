package Bank;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Base64;

public class Controller {
    private ArrayList<String> tokens = new ArrayList<>();
    private String clientRequest;
    private String ServerAnswer;
    private static final SecureRandom secureRandom = new SecureRandom();
    private static final Base64.Encoder base64Encoder = Base64.getUrlEncoder();

    public String fromClientToBank() {
        return null;
    }

    public void createAccount(String firstName, String secondName, String username, String password) {
        new Account(firstName, secondName, username, password);
    }

    public String getToken() {
        byte[] randomBytes = new byte[100];
        secureRandom.nextBytes(randomBytes);
        String token = System.currentTimeMillis() + base64Encoder.encodeToString(randomBytes);
        tokens.add(token);
        return token;
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
