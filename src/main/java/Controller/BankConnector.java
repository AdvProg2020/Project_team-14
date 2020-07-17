package Controller;

import Bank.Controller;
import Bank.Controller.*;

public class BankConnector {
    private static final int bankPort = 1989;

    public static String sendToBank(String command) {
        Controller controller = new Controller();
        controller.takeAction(command);
        System.out.println("result: "+ controller.getServerAnswer());
        return controller.getServerAnswer();
    }

}
