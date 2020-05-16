package Menus.Manages;

import Menus.BossMenu;
import Menus.LoginOrRegisterMenu;
import Menus.Menu;
import Menus.SalesmanMenu;
import Menus.shows.ShowProductsMenu;
import Menus.shows.ShowRequestsMenu;

import java.util.HashMap;

public class ManageProductsMenu extends Menu {
    public ManageProductsMenu(Menu fatherMenu, String menuName) {
        super(fatherMenu, menuName);
        this.logoutType = false;
        HashMap<Integer, Menu> subMenus = new HashMap<Integer, Menu>();
        if (fatherMenu instanceof BossMenu) {
            subMenus.put(1, new ShowProductsMenu(this, "Show Products Menu"));
            subMenus.put(2, getSearchProductMenu());
            subMenus.put(3, new ShowRequestsMenu(this, "Add Products Requests"));
            subMenus.put(4, new ShowRequestsMenu(this, "Delete Products Requests"));
            subMenus.put(5, new ShowRequestsMenu(this, "Edit Products Requests"));
            subMenus.put(6, new LoginOrRegisterMenu(this, "Login\\Register Menu"));
        } else if (fatherMenu instanceof SalesmanMenu) {
            subMenus.put(1, getAddProductMenu());
            subMenus.put(2, getSearchProductMenu());
            subMenus.put(3, new ShowProductsMenu(this, "Show My Products Menu"));
            subMenus.put(4, new LoginOrRegisterMenu(this, "Login\\Register Menu"));
        }
        this.setSubMenus(subMenus);
    }

    private Menu getSearchProductMenu() {
        return new Menu(this, "Search Products Menu") {

        };
    }

    private Menu getAddProductMenu() {
        return new Menu(this, "Add Product Menu") {
            private void checkBack(String input) {
                if (input.equals("back")) {
                    fatherMenu.execute();
                }
            }

            @Override
            public void execute() {
                System.out.println(this.getMenuName());
                System.out.println("if you input back we will go back");
                String productName, brand, description, price, remainder;
            }
        };
    }
}
