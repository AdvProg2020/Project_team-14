package Controller;

import Model.Account.Account;
import Model.Account.Customer;
import Model.Account.Role;
import Model.Account.Salesman;
import Model.Storage;
import Exception.*;

public class AccountManager {

    public void login(String username, String password) throws SalesmanNotConfirmedYetException, IncorrectPasswordException, InvalidUserNameException {
        if (Storage.isThereAccountWithUsername(username)) {
            Account account = Storage.getAccountWithUsername(username);
            assert account != null;
            if (account.getPassword().equals(password)) {
                if (account.getRole().equals(Role.SALESMAN)) {
                    Salesman salesman = (Salesman) account;
                    if (!salesman.isConfirmed()) {
                        throw new SalesmanNotConfirmedYetException("you're not confirmed yet");
                    }
                }
                Server.setAnswer("login successful as " + account.getRole() + " " + username);
                account.setOnline(true);
            } else {
                throw new IncorrectPasswordException("your password is incorrect");
            }
        } else {
            throw new InvalidUserNameException("the username doesn't exists");
        }
    }

    public void logout(String username) {
        Account account = Storage.getAccountWithUsername(username);
        assert account != null;
        account.setOnline(false);
        Server.setAnswer("logout successful");
    }

    public void forgotPassword(String username) throws InvalidUserNameException {
        if (Storage.isThereAccountWithUsername(username)) {
            Account account = Storage.getAccountWithUsername(username);
            assert account != null;
            Server.setAnswer("here is your password: " + account.getPassword());
        } else {
            throw new InvalidUserNameException("the username doesn't exists");
        }
    }

    public void viewAccountInformation(String username) {
        Account account = Storage.getAccountWithUsername(username);
        assert account != null;
        Server.setAnswer(account.toString());
    }

    public void editFirstName(String username, String name) {
        Server.setAnswer("edit successful");
        Account account = Storage.getAccountWithUsername(username);
        assert account != null;
        account.setFirstName(name);
    }

    public void editLastName(String username, String name) {
        Server.setAnswer("edit successful");
        Account account = Storage.getAccountWithUsername(username);
        assert account != null;
        account.setSecondName(name);
    }

    public void editUsername(String oldUsername, String newUsername) throws UsernameAlreadyExistException {
        if (Storage.isThereAccountWithUsername(newUsername)) {
            throw new UsernameAlreadyExistException("username already exists, choose another one!");
        } else {
            Server.setAnswer("edit successful");
            Account account = Storage.getAccountWithUsername(oldUsername);
            assert account != null;
            account.setUsername(newUsername);
        }
    }

    public void editEmail(String username, String mail) {
        Account account = Storage.getAccountWithUsername(username);
        assert account != null;
        account.setEmail(mail);
        Server.setAnswer("edit successful");
    }

    public void editTelephone(String username, String telephone) {
        Account account = Storage.getAccountWithUsername(username);
        assert account != null;
        account.setTelephone(telephone);
        Server.setAnswer("edit successful");
    }

    public void editPassword(String username, String oldPassword, String newPassword) throws IncorrectPasswordException {
        Account account = Storage.getAccountWithUsername(username);
        assert account != null;
        if (account.getPassword().equals(oldPassword)) {
            account.setPassword(newPassword);
            Server.setAnswer("edit successful");
        } else {
            throw new IncorrectPasswordException("your previous password isn't correct");
        }
    }

    public void editMoney(String username, String money) throws MoreMoneyThanLimitsException {
        if (money.length() <= 8) {
            Account account = Storage.getAccountWithUsername(username);
            if (account instanceof Customer) {
                ((Customer) account).setCredit(((Customer) account).getCredit() + Integer.parseInt(money));
            } else if (account instanceof Salesman) {
                ((Salesman) account).setCredit(((Salesman) account).getCredit() + Integer.parseInt(money));
            }
            Server.setAnswer("edit successful");
        } else {
            throw new MoreMoneyThanLimitsException("that's too much money, less money perhaps");
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
