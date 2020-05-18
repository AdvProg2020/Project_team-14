package Menus.Manages;

import Menus.LoginOrRegisterMenu;
import Menus.Menu;

import java.util.HashMap;

public class ManageBalanceMenu extends Menu {
    public ManageBalanceMenu(Menu fatherMenu, String menuName) {
        super(fatherMenu, menuName);
        HashMap<Integer, Menu> subMenus = new HashMap<>();
        subMenus.put(1, getIncreaseBalance());
        subMenus.put(2, new LoginOrRegisterMenu(this, "Login\\Register Menu"));
        this.setSubMenus(subMenus);
    }

    private Menu getIncreaseBalance() {
        return new Menu(this, "Increase Balance Menu") {

            @Override
            public void execute() {
                System.out.println(menuName);

                //get info
                System.out.println("How much money do you want to add to your wallet?(insert BACK to cancel process)");
                String money = scanner.nextLine();
                checkBack(money);
                server.clientToServer("edit personal info+" + "money" + "+" + money + "+" + Menu.username);
                System.out.println(server.serverToClient());
                if (server.serverToClient().equals("edit successful")) {
                    fatherMenu.execute();
                } else {
                    this.execute();
                }
            }
        };
    }

    private void getInfo() {
        server.clientToServer("show balance" + "+" + Menu.username);
        System.out.println(server.serverToClient());
    }

    @Override
    protected void show() {
        super.show();
        getInfo();
    }
}
