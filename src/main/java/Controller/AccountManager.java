package Controller;

import Model.Account.Account;
import Model.Account.Customer;
import Model.Account.Salesman;
import Model.Storage;

public class AccountManager {

    public void login(String username, String password) {
        if (Storage.isThereAccountWithUsername(username)) {
            Account account = Storage.getUserWithUsername(username);
            if (account.getPassword().equals(password)) {
                if (account.getRole().equals("salesman")) {
                    Salesman salesman = (Salesman) account;
                    if (!salesman.isConfirmed()) {
                        Server.setAnswer("you're not a confirmed salesman");
                    }
                }
                Server.setAnswer("login successful as " + account.getRole() + " " + username);
                account.setOnline(true);
            } else {
                Server.setAnswer("incorrect password");
            }
        } else {
            Server.setAnswer("incorrect username");
        }
    }

    public void logout(String username) {
        Account account = Storage.getUserWithUsername(username);
        account.setOnline(false);
        Server.setAnswer("logout successful");
    }
}
