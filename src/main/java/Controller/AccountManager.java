package Controller;

import Model.Account.*;
import Model.Confirmation;
import Model.Request.Request;
import Model.Storage;
import Exception.*;

import javax.print.DocFlavor;

public class AccountManager {

    public void login(String username, String password) {
        if (Storage.isThereAccountWithUsername(username)) {
            Account account = Storage.getAccountWithUsername(username);
            assert account != null;
            if (account.getPassword().equals(password)) {
                if (account.getRole().equals(Role.SALESMAN)) {
                    Salesman salesman = (Salesman) account;
                    if (!salesman.isConfirmed()) {
                        Server.setAnswer("you're not a confirmed salesman yet");
                        if (salesman.getConfirmationState().equals(Confirmation.DENIED)) {
                            Server.setAnswer("you're request of registering has been denied so you're account will be " +
                                    "deleted from now on");
                        }
                        return;
                    }
                }
                Server.setAnswer("login successful as " + account.getRole() + " " + username);
                account.setOnline(true);
            } else {
                Server.setAnswer("your password is incorrect");
            }
        } else {
            Server.setAnswer("the username doesn't exists");
        }
    }

    public void logout(String username) {
        Account account = Storage.getAccountWithUsername(username);
        account.setOnline(false);
        Server.setAnswer("logout successful");
    }

    public void forgotPassword(String username) {
        if (Storage.isThereAccountWithUsername(username)) {
            Account account = Storage.getAccountWithUsername(username);
            Server.setAnswer("here is your password: " + account.getPassword());
        } else {
            Server.setAnswer("the username doesn't exists");
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

    public void editUsername(String oldUsername, String newUsername) {
        if (Storage.isThereAccountWithUsername(newUsername)) {
            Server.setAnswer("username already exists, choose another one!");
        } else {
            Server.setAnswer("edit successful");
            Account account = Storage.getAccountWithUsername(oldUsername);
            assert account != null;
            account.setUsername(newUsername);
            updateUsernameForModel(oldUsername, newUsername);
        }
    }

    private void updateUsernameForModel(String oldUsername, String newUsername) {
        for (Request request : Storage.getAllRequests()) {
            if (request.getAccountUsername().equals(oldUsername)) {
                request.setAccountUsername(newUsername);
            }
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

    public void editPassword(String username, String oldPassword, String newPassword) {
        Account account = Storage.getAccountWithUsername(username);
        assert account != null;
        if (account.getPassword().equals(oldPassword)) {
            account.setPassword(newPassword);
            Server.setAnswer("edit successful");
        } else {
            Server.setAnswer("your previous password isn't correct");
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
            Server.setAnswer("that's too much money, less money perhaps");
        }
    }

    public void editCompany(String username, String company) {
        Account account = Storage.getAccountWithUsername(username);
        if (account instanceof Salesman) {
            ((Salesman) account).setCompany(company);
            Server.setAnswer("edit successful");
        }
    }

    public void deleteAccount(String bossUsername, String username) {
        Account account = Storage.getAccountWithUsername(username);
        if (account instanceof Boss) {
            BossManager.changeFathers(bossUsername, username);
        }
        Storage.getAllAccounts().remove(account);
        Server.setAnswer("deleted successfully");
        updateDeleteUsernameForModel(username);
    }

    private void updateDeleteUsernameForModel(String username) {
        for (Request request : Storage.getAllRequests()) {
            if (request.getAccountUsername().equals(username)) {
                request.setAccountUsername("deleted account");
            }
        }
    }
}
