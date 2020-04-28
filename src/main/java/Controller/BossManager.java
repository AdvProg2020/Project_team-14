package Controller;

import ModelTest.Account.Boss;
import ModelTest.Storage;

public class BossManager {

    public void register(String[] information) {
        if (Storage.isThereAccountWithUsername(information[3])) {
            Server.setAnswer("username has already been taken");
        }
        Server.setAnswer("register successful");
        Server.setHasBoss(true);
        new Boss(information[3], information[4], information[1], information[2], information[6], information[7], information[5]);

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