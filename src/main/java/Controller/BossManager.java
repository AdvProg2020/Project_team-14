package Controller;

import Model.Account.Account;
import Model.Account.Boss;
import Model.Account.Role;
import Model.Storage;

import java.util.ArrayList;

public class BossManager {

    public void register(String[] information) {
        if (Storage.isThereAccountWithUsername(information[3])) {
            Server.setAnswer("username has already been taken");
        }
        Server.setAnswer("register successful");
        Server.setHasBoss(true);
        new Boss(information[3], information[4], information[1], information[2], information[6], information[7], information[5]);

    }

    public void showAccounts(String username) {
        ArrayList<Account> accounts = Storage.getAllAccounts();
        StringBuilder answer = new StringBuilder("");
        if (accounts.size() == 0) {
            Server.setAnswer("nothing found");
        } else {
            for (Account account : accounts) {
                if (!account.getUsername().equals(username))
                    answer.append(account.toStringForBoss() + "\n");
            }
            answer.append("here are what we found");
            String ans = answer.toString();
            Server.setAnswer(ans);
        }
    }
    /*
    private CategoryManager categoryManager;
    private OffCodeManager offCodeManager;
    private RequestManager requestManager;

    public BossManager() {
        categoryManager = new CategoryManager();
        offCodeManager = new OffCodeManager();
        requestManager = new RequestManager();
    }

     */
}