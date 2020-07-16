package Bank;

import jdk.nashorn.internal.codegen.LocalStateRestorationInfo;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Base64;

public class Controller {
    private ArrayList<String> tokens = new ArrayList<>();
    private String clientRequest;
    private String serverAnswer;
    private static final SecureRandom secureRandom = new SecureRandom();
    private static final Base64.Encoder base64Encoder = Base64.getUrlEncoder();

    public String getToken() {
        byte[] randomBytes = new byte[100];
        secureRandom.nextBytes(randomBytes);
        String token = System.currentTimeMillis() + base64Encoder.encodeToString(randomBytes);
        tokens.add(token);
        return token;
    }

    public boolean tokenIsAuthentic(String token) {
        return tokens.contains(token);
    }

    public boolean tokenIsExpired(String token) {
        long past = Long.parseLong(token.substring(0, 13));
        return System.currentTimeMillis() - past > 1000000;
    }

    public void takeAction(String command) {
        if (command.startsWith("create account+")) {
            createAccount(command);
        } else if (command.startsWith("get token+")) {
            getToken(command);
        } else if (command.startsWith("create transfer receipt+")) {
            createTransferReceipt(command);
        } else if (command.startsWith("create withdraw receipt+")) {
            createWithdrawReceipt(command);
        } else if (command.startsWith("create deposit receipt+")) {
            createDepositReceipt(command);
        } else if (command.startsWith("get balance+")) {
            getBalance(command);
        } else if (command.startsWith("pay transaction with id+")) {
            pay(command);
        } else if (command.startsWith("is there person with username+")) {
            isTherePersonWithUsername(command);
        }
    }

    private void isTherePersonWithUsername(String command) {
        String username = command.split("\\+")[1];
        Account account = Account.getAccountWithUsername(username);
        if (account == null) {
            serverAnswer = "false";
        } else {
            serverAnswer = "true";
        }
    }

    private void createAccount(String command) {
        try {
            if (Account.getAccountWithUsername(command.split("\\+")[1]) != null) {
                serverAnswer = "you already have an account";
            } else {
                new Account(command.split("\\+")[1], command.split("\\+")[2], command.split("\\+")[3], command.split("\\+")[4]);
                serverAnswer = "created successfully";
            }
        } catch (Exception e) {
            serverAnswer = "something went wrong";
        }
    }

    private void getToken(String command) {
        try {
            String username = command.split("\\+")[1];
            String password = command.split("\\+")[2];
            Account account = Account.getAccountWithUsername(username);
            if (account != null && account.getPassword().equals(password)) {
                serverAnswer = getToken();
            } else {
                serverAnswer = "fuck off, identification was wrong";
            }
        } catch (Exception e) {
            serverAnswer = "something went wrong";
        }
    }

    private void createTransferReceipt(String command) {
        try {
            String token = command.split("\\+")[1];
            String username1 = command.split("\\+")[2];
            String username2 = command.split("\\+")[3];
            long amount = Long.parseLong(command.split("\\+")[4]);
            String description = command.split("\\+")[5];
            if (!tokenIsAuthentic(token)) {
                serverAnswer = "token isn't authentic";
                return;
            }
            if (tokenIsExpired(token)) {
                serverAnswer = "token has expired";
                return;
            }
            if (Account.getAccountWithUsername(username1) != null && Account.getAccountWithUsername(username2) != null) {
                new Transaction(username1, username2, amount, description);
                serverAnswer = "successful";
            } else {
                serverAnswer = "one of usernames isn't valid";
            }
        } catch (Exception e) {
            serverAnswer = "something went wrong";
        }
    }

    private void createWithdrawReceipt(String command) {
        try {
            String token = command.split("\\+")[1];
            String username = command.split("\\+")[2];
            long amount = Long.parseLong(command.split("\\+")[3]);
            String description = command.split("\\+")[4];
            if (!tokenIsAuthentic(token)) {
                serverAnswer = "token isn't authentic";
                return;
            }
            if (tokenIsExpired(token)) {
                serverAnswer = "token has expired";
                return;
            }
            if (Account.getAccountWithUsername(username) != null) {
                new Transaction(TransactionType.WITHDRAW, username, amount, description);
                serverAnswer = "successful";
            } else {
                serverAnswer = "the username is invalid";
            }
        } catch (Exception e) {
            serverAnswer = "something went wrong";
        }
    }

    private void createDepositReceipt(String command) {
        try {
            String token = command.split("\\+")[1];
            String username = command.split("\\+")[2];
            long amount = Long.parseLong(command.split("\\+")[3]);
            String description = command.split("\\+")[4];
            if (!tokenIsAuthentic(token)) {
                serverAnswer = "token isn't authentic";
                return;
            }
            if (tokenIsExpired(token)) {
                serverAnswer = "token has expired";
                return;
            }
            if (Account.getAccountWithUsername(username) != null) {
                new Transaction(TransactionType.DEPOSIT, username, amount, description);
                serverAnswer = "successful";
            } else {
                serverAnswer = "the username is invalid";
            }
        } catch (Exception e) {
            serverAnswer = "something went wrong";
        }
    }

    private void getBalance(String command) {
        try {
            String token = command.split("\\+")[1];
            String username = command.split("\\+")[2];
            Account account = Account.getAccountWithUsername(username);
            if (!tokenIsAuthentic(token)) {
                serverAnswer = "token isn't authentic";
                return;
            }
            if (tokenIsExpired(token)) {
                serverAnswer = "token has expired";
                return;
            }
            if (account != null) {
                serverAnswer = Long.toString(account.getBalance());
            } else {
                serverAnswer = "the username is invalid";
            }
        } catch (Exception e) {
            serverAnswer = "something went wrong";
        }
    }

    private void pay(String command) {
        String token = command.split("\\+")[1];
        int id = Integer.parseInt(command.split("\\+")[2]);
        if (!tokenIsAuthentic(token)) {
            serverAnswer = "token isn't authentic";
            return;
        }
        if (tokenIsExpired(token)) {
            serverAnswer = "token has expired";
            return;
        }
        Transaction transaction = Transaction.getTransactionBasedOnID(id);
        if (transaction == null) {
            serverAnswer = "transaction ID is not authentic";
        } else {
            if (transaction.isDone()) {
                serverAnswer = "it's already done";
            } else {
                serverAnswer = transaction.Do();
            }
        }
    }

}

// create account+username+password+firstName+secondName
// get token+username+password
// create transfer receipt+token+username1+username2+amount+description
// create withdraw receipt+token+username+amount+description
// create deposit receipt+token+username+amount+description
// get balance+token+username