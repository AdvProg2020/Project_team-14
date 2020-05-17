package Menus.Views;

import Menus.Edits.EditProductMenu;
import Menus.LoginOrRegisterMenu;
import Menus.Menu;
import Menus.shows.ShowAccountsMenu;
import Menus.shows.ShowCategoriesMenu;
import Menus.shows.ShowRequestsMenu;

import java.text.ParseException;
import java.util.HashMap;

public class ViewProductMenu extends Menu {
    private String productID;

    private void productSubMenus() throws ParseException {
        HashMap<Integer, Menu> subMenus = new HashMap<Integer, Menu>();
        if (!Menu.isUserLogin) {
            //we will add something here like add to cart menu
        } else {
            server.clientToServer("what is account role+" + Menu.username);
            if (server.serverToClient().equalsIgnoreCase("boss")) {
                subMenus.put(1, new ShowAccountsMenu(this, "Show Accounts That Sells This"));
                subMenus.put(2, new ShowRequestsMenu(this, "Show Requests About This Product"));
                server.clientToServer("what is product category+" + this.productID);
                if (!server.serverToClient().equalsIgnoreCase("it doesn't have a category")) {
                    subMenus.put(3, new ViewCategoryMenu(this, "View Product Category Menu",
                            server.serverToClient()));
                    subMenus.put(4, getDeleteAccountFromSellingThisProductMenu());
                    subMenus.put(5, getDeleteProductFromCategoryMenu());
                    subMenus.put(6, new LoginOrRegisterMenu(this, "Login\\Register Menu"));
                } else {
                    subMenus.put(3, new ShowCategoriesMenu(this, "Show Categories Menu"));
                    subMenus.put(4, getDeleteAccountFromSellingThisProductMenu());
                    subMenus.put(5, getAddProductToCategoryMenu());
                    subMenus.put(6, new LoginOrRegisterMenu(this, "Login\\Register Menu"));
                }
            } else if (server.serverToClient().equalsIgnoreCase("salesman")) {
                server.clientToServer("can account add product+" + Menu.username + "+" + this.productID);
                if (server.serverToClient().equalsIgnoreCase("yes")) {
                    subMenus.put(1, getAddProductMenu());
                    subMenus.put(2, new LoginOrRegisterMenu(this, "Login\\Register Menu"));
                } else {
                    subMenus.put(1, getDeleteProductMenu());
                    subMenus.put(2, new EditProductMenu(this, "Edit Product Menu"));
                    subMenus.put(3, new LoginOrRegisterMenu(this, "Login\\Register Menu"));
                }
            } else {
                // we will ad comment and add to cart here later
            }
        }
        setSubMenus(subMenus);
    }

    public ViewProductMenu(Menu fatherMenu, String menuName, String productID) throws ParseException {
        super(fatherMenu, menuName);
        this.productID = productID;
        this.logoutType = false;
        productSubMenus();
    }

    private Menu getDeleteProductMenu() {
        return new Menu(this, "Delete Product Menu") {

        };
    }

    private Menu getAddProductMenu() {
        return new Menu(this, "Add Product Menu") {

        };

    }

    private Menu getDeleteProductFromCategoryMenu() {
        return new Menu(this, "Delete Product From Category Menu") {

        };
    }

    private Menu getAddProductToCategoryMenu() {
        return new Menu(this, "Add Product To Category Menu") {

        };
    }

    private Menu getDeleteAccountFromSellingThisProductMenu() {
        return new Menu(this, "Delete Account From Selling Product Menu") {

        };
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    private void getProductInfo() throws ParseException {
        server.clientToServer("view product+" + Menu.username + "+" + productID);
        String serverAnswer = server.serverToClient();
        System.out.println(serverAnswer);
    }

    @Override
    protected void show() throws ParseException {
        super.show();
        getProductInfo();
    }
}
