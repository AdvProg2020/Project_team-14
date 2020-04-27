package Controller;

import Model.Account.Account;
import Model.Account.Role;
import Model.Account.Salesman;
import Model.Storage;

public class AccountManager {

    public void login(String username, String password) {
        if (Storage.isThereAccountWithUsername(username)) {
            Account account = Storage.getAccountWithUsername(username);
            assert account != null;
            if (account.getPassword().equals(password)) {
                if (account.getRole().equals(Role.SALESMAN)) {
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
        Account account = Storage.getAccountWithUsername(username);
        assert account != null;
        account.setOnline(false);
        Server.setAnswer("logout successful");
    }

    public void forgotPassword(String username) {
        if (Storage.isThereAccountWithUsername(username)) {
            Account account = Storage.getAccountWithUsername(username);
            Server.setAnswer("here is your password: " + account.getPassword());
        } else {
            Server.setAnswer("logout successful");
        }
    }

    public void viewAccountInformation(String username) {
        Account account = Storage.getAccountWithUsername(username);
        Server.setAnswer(account.toString());
    }

    public void editFirstName(String username, String name) {
        Server.setAnswer("edit successful");
        Account account = Storage.getAccountWithUsername(username);
        account.setFirstName(name);
    }

    public void editLastName(String username, String name) {
        Server.setAnswer("edit successful");
        Account account = Storage.getAccountWithUsername(username);
        account.setSecondName(name);
    }

    public void editUsername(String oldUsername, String newUsername) {
        if (Storage.isThereAccountWithUsername(newUsername)) {
            Server.setAnswer("username has already been taken");
        } else {
            Server.setAnswer("edit successful");
            Account account = Storage.getAccountWithUsername(oldUsername);
            account.setUsername(newUsername);
        }
    }

    public void editEmail(String username, String mail) {
        Account account = Storage.getAccountWithUsername(username);
        account.setEmail(mail);
        Server.setAnswer("edit successful");
    }

    public void editTelephone(String username, String telephone) {
        Account account = Storage.getAccountWithUsername(username);
        account.setTelephone(telephone);
        Server.setAnswer("edit successful");
    }
}
