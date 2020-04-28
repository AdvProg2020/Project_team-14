package Controller;

import ModelTest.Account.Salesman;
import ModelTest.Storage;

public class SalesmanManager {
    public void register(String[] information) {
        if (Storage.isThereAccountWithUsername(information[3])) {
            Server.setAnswer("username has already been taken");
        }
        Server.setAnswer("register successful");
        new Salesman(information[3], information[4], information[1], information[2],
                information[6], information[7], information[5], information[8], 0);
    }
    /*
    private SalesManager salesManager;
    private ProductManager productManager;

    public SalesmanManager() {
        salesManager = new SalesManager();
        productManager = new ProductManager();
    }

     */
}
