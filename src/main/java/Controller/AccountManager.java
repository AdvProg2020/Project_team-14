package Controller;

import Model.Account.*;
import Model.Cart.Cart;
import Model.Confirmation;
import Model.Log.BuyLog;
import Model.Log.SellLog;
import Model.Off.OffCode;
import Model.Off.Sale;
import Model.Product.Comment;
import Model.Product.Point;
import Model.Product.Product;
import Model.Request.Request;
import Model.Storage;

import java.net.ServerSocket;

public class AccountManager {

    public void login(String username, String password) {
        if (Storage.isThereAccountWithUsername(username)) {
            Account account = Storage.getAccountWithUsername(username);
            assert account != null;
            if (account.getPassword().equals(password)) {
                if (account.getRole().equals(Role.SALESMAN)) {
                    Salesman salesman = (Salesman) account;
                    if (!salesman.isConfirmed()) {
                        Server.setAnswer("You're Not A Confirmed Salesman Yet");
                        if (salesman.getConfirmationState().equals(Confirmation.DENIED)) {
                            Server.setAnswer("Your Request Of Registering Has Been Denied So Your Account Will Be " +
                                    "Deleted From Now On");
                            Storage.getAllAccounts().remove(account);
                        }
                        return;
                    }
                }
                Server.setAnswer("login successful as " + account.getRole() + " " + username);
                account.setOnline(true);
            } else {
                Server.setAnswer("Your Password Is Incorrect");
            }
        } else {
            Server.setAnswer("The Username Doesn't Exists");
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
            assert account != null;
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
        SellLog.updateAllSellLogsWithNewUsername(oldUsername, newUsername);
        OffCode.updateAllOffCodesWithNewUsername(oldUsername, newUsername);
        for (Request request : Storage.getAllRequests()) {
            if (request.getAccountUsername().equals(oldUsername)) {
                request.setAccountUsername(newUsername);
            }
        }
        for (Sale sale : Storage.allSales) {
            if (sale.getSalesmanID().equals(oldUsername)) {
                sale.setSalesmanID(newUsername);
            }
        }
        for (Comment comment : Storage.allComments) {
            if (comment.getSenderUsername().equals(oldUsername)) {
                comment.setSenderUsername(newUsername);
            }
        }
        for (Point point : Storage.allPoints) {
            if (point.getUsername().equals(oldUsername)) {
                point.setUsername(oldUsername);
            }
        }
        for (Cart cart : Storage.allCarts) {
            if (cart.getUsername().equals(oldUsername)) {
                cart.setUsername(newUsername);
            }
        }
        for (Product product : Storage.getAllProducts()) {
            product.updateUserNames(oldUsername, newUsername);
        }
        for (BuyLog buyLog : Storage.allBuyLogs) {
            buyLog.updateUserNames(oldUsername, newUsername);
        }
        for (SellLog sellLog : Storage.allSellLogs) {
            if (sellLog.getSalesmanID().equals(oldUsername)) sellLog.setSalesmanID(newUsername);
            if (sellLog.getCustomerID().equals(oldUsername)) sellLog.setCustomerID(newUsername);
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
        if(bossUsername.equals(username)){
            Server.setAnswer("you're not allowed to delete yourself :)");
            return;
        }
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
