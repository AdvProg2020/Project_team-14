package Menus.Views;

import Menus.LoginOrRegisterMenu;
import Menus.Menu;
import Menus.shows.ShowAccountsMenu;
import Menus.shows.ShowRequestsMenu;

public class ViewProductMenu extends Menu {
    private String productID;

    public ViewProductMenu(Menu fatherMenu, String menuName, String productID) {
        super(fatherMenu, menuName);
        this.productID = productID;
        this.logoutType = false;
        if (Menu.isUserLogin == false) {

        } else {
            server.clientToServer("what is account role+" + Menu.username);
            if (server.serverToClient().equalsIgnoreCase("boss")) {
                subMenus.put(1, new ShowAccountsMenu(this, "Show Accounts That Sells This"));
                subMenus.put(2, new ShowRequestsMenu(this, "Show Requests About This Product"));
                server.clientToServer("what is product category+" + this.productID);
                if (!server.serverToClient().equalsIgnoreCase("it doesn't have a category")) {
                    subMenus.put(3, new ViewCategoryMenu(this, "View Product Category Menu",
                            server.serverToClient()));
                    subMenus.put(4, getDeleteAccountFromSellingThisProduct());
                    subMenus.put(5, new LoginOrRegisterMenu(this, "Login\\Register Menu"));
                } else {
                    subMenus.put(3, new LoginOrRegisterMenu(this, "Login\\Register Menu"));
                }
            } else if (server.serverToClient().equalsIgnoreCase("salesman")) {

            } else {

            }
        }
    }

    private Menu getDeleteAccountFromSellingThisProduct() {
        return new Menu(this, "Delete Account From Selling Product") {

        };
    }
}
