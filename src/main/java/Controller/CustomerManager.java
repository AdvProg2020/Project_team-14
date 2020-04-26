package Controller;

import Model.Account.Boss;
import Model.Account.Customer;
import Model.Storage;

public class CustomerManager {
    public void register(String[] information) {
        if (Storage.isThereAccountWithUsername(information[3])) {
            Server.setAnswer("username has already been taken");
        }
        Server.setAnswer("register successful");
        new Customer(information[3], information[4], information[1], information[2],
                information[6], information[7], information[5], 0);
    }
    /*
    private OrderManager orderManager;
    private PurchaseManager purchaseManager;

    public CustomerManager() {
        orderManager = new OrderManager();
        purchaseManager = new PurchaseManager();
    }

     */
}
