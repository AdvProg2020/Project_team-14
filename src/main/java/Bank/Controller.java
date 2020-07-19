package Bank;

import Bank.SQL.SQL;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Base64;

public class Controller {
    private static ArrayList<String> tokens = new ArrayList<>();
    private String clientRequest;
    private String serverAnswer;
    private static final SecureRandom secureRandom = new SecureRandom();
    private static final Base64.Encoder base64Encoder = Base64.getUrlEncoder();
    private static SQL sql = new SQL();
    private static boolean flag = true;

    public String getToken() {
        byte[] randomBytes = new byte[100];
        secureRandom.nextBytes(randomBytes);
        String token = System.currentTimeMillis() + base64Encoder.encodeToString(randomBytes);
        tokens.add(token);
        return token;
    }

    public boolean tokenIsWrong(String token) {
        return !tokens.contains(token);
    }

    public boolean tokenIsExpired(String token) {
        long past = Long.parseLong(token.substring(0, 13));
        return System.currentTimeMillis() - past > 1000000;
    }

    public void takeAction(String command) {

        if (flag) {
            flag = false;
            sql.startProgramme();
        } else {
            sql.updateProgramme();
        }

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
        } else if (command.startsWith("get all receipts by me+")) {
            getAllReceiptsByMe(command);
        } else if (command.startsWith("get all receipts involving me+")) {
            getAllReceiptsInvolvingMe(command);
        } else if (command.startsWith("get amount of transaction+")) {
            getTransactionAmount(command);
        } else {
            serverAnswer = "invalid input";
        }
    }

    private void getTransactionAmount(String command) {
        try {
            String token = command.split("\\+")[1];
            int ID = Integer.parseInt(command.split("\\+")[2]);
            Transaction transaction = Transaction.getTransactionBasedOnID(ID);

            if (tokenIsWrong(token)) {
                serverAnswer = "token isn't authentic";
                return;
            }

            if (tokenIsExpired(token)) {
                serverAnswer = "token has expired";
                return;
            }

            if (transaction == null) {
                serverAnswer = "wrong transaction id";
                return;
            }

            serverAnswer = Long.toString(transaction.getAmount());

        } catch (Exception e) {
            serverAnswer = "something went wrong";
        }
    }

    private void getAllReceiptsInvolvingMe(String command) {
        try {
            StringBuilder result = new StringBuilder("");
            String token = command.split("\\+")[1];
            if (tokenIsWrong(token)) {
                serverAnswer = "token isn't authentic";
                return;
            }
            if (tokenIsExpired(token)) {
                serverAnswer = "token has expired";
                return;
            }
            for (String string : Transaction.getAllTransactionsInvolvingUsername(command.split("\\+")[2])) {
                result.append(string).append("\n");
            }
            serverAnswer = result.toString();
        } catch (Exception e) {
            serverAnswer = "something went wrong";
        }
    }

    private void getAllReceiptsByMe(String command) {
        try {
            StringBuilder result = new StringBuilder("");
            String token = command.split("\\+")[1];

            if (tokenIsWrong(token)) {
                serverAnswer = "token isn't authentic";
                return;
            }

            if (tokenIsExpired(token)) {
                serverAnswer = "token has expired";
                return;
            }

            for (String string : Transaction.getAllTransactionsWithSource(command.split("\\+")[2])) {
                result.append(string).append("\n");
            }

            serverAnswer = result.toString();
        } catch (Exception e) {
            serverAnswer = "something went wrong";
        }
    }

    private void isTherePersonWithUsername(String command) {
        try {
            String username = command.split("\\+")[1];
            Account account = Account.getAccountWithUsername(username);
            if (account == null) {
                serverAnswer = "false";
                return;
            }
            serverAnswer = "true";
        } catch (Exception e) {
            serverAnswer = "something went wrong";
        }
    }

    private void createAccount(String command) {
        try {
            if (Account.getAccountWithUsername(command.split("\\+")[1]) != null) {
                serverAnswer = "you already have an account";
                return;
            }
            new Account(command.split("\\+")[1], command.split("\\+")[2], command.split("\\+")[3], command.split("\\+")[4]);
            serverAnswer = "created successfully";
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
                return;
            }
            serverAnswer = "fuck off, identification was wrong";
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
            if (tokenIsWrong(token)) {
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
                return;
            }
            serverAnswer = "one of usernames isn't valid";
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
            if (tokenIsWrong(token)) {
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
                return;
            }
            serverAnswer = "the username is invalid";
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
            if (tokenIsWrong(token)) {
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
                return;
            }
            serverAnswer = "the username is invalid";
        } catch (Exception e) {
            serverAnswer = "something went wrong";
        }
    }

    private void getBalance(String command) {
        try {
            String token = command.split("\\+")[1];
            System.out.println("this is the wrong one: " + token);
            System.out.println(tokens.get(0));
            String username = command.split("\\+")[2];
            Account account = Account.getAccountWithUsername(username);
            if (tokenIsWrong(token)) {
                serverAnswer = "token isn't authentic";
                return;
            }
            if (tokenIsExpired(token)) {
                serverAnswer = "token has expired";
                return;
            }
            if (account != null) {
                serverAnswer = Long.toString(account.getBalance());
                return;
            }
            serverAnswer = "the username is invalid";
        } catch (Exception e) {
            serverAnswer = "something went wrong";
        }
    }

    private void pay(String command) {
        try {
            String token = command.split("\\+")[1];
            int id = Integer.parseInt(command.split("\\+")[2]);
            String username = command.split("\\+")[3];
            if (tokenIsWrong(token)) {
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
                return;
            }
            if (!transaction.getFromUsername().equals(username)) {
                serverAnswer = "that's not your receipt to pay";
                return;
            }
            if (transaction.isDone()) {
                serverAnswer = "it's already done";
                return;
            }
            serverAnswer = transaction.Do();
        } catch (Exception e) {
            serverAnswer = "something went wrong";
        }
    }

    public String getServerAnswer() {
        return serverAnswer;
    }

}


// create account+username+password+firstName+secondName
// get token+username+password
// create transfer receipt+token+username1+username2+amount+description
// create withdraw receipt+token+username+amount+description
// create deposit receipt+token+username+amount+description
// get balance+token+username
// pay transaction with id+token+id+username
// get all receipts by me+token+username
// get all receipts involving me+token+username
