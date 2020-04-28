package Controller;

import ModelTest.Account.Account;
import ModelTest.Account.Customer;
import ModelTest.Account.Role;
import ModelTest.Account.Salesman;
import ModelTest.Storage;

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
            Server.setAnswer("incorrect username");
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

    public void editPassword(String username, String oldPassword, String newPassword) {
        Account account = Storage.getAccountWithUsername(username);
        if (account.getPassword().equals(oldPassword)) {
            account.setPassword(newPassword);
            Server.setAnswer("edit successful");
        } else {
            Server.setAnswer("wrong old password");
        }
    }

    public void editMoney(String username, String money) {
        if (money.length() <= 8) {
            Account account = Storage.getAccountWithUsername(username);
            if (account instanceof Customer) {
                ((Customer) account).setCredit(((Customer) account).getCredit() + Integer.parseInt(money));
            } else if (account instanceof Salesman) {
                ((Salesman) account).setCredit(((Salesman) account).getCredit() + Integer.parseInt(money));
            }
            Server.setAnswer("edit successful");
        } else {
            Server.setAnswer("money more than limits");
        }
    }

    public void editCompany(String username, String company) {
        Account account = Storage.getAccountWithUsername(username);
        if (account instanceof Salesman) {
            ((Salesman) account).setCompany(company);
            Server.setAnswer("edit successful");
        }
    }
}
