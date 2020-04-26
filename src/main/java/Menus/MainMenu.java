package Menus;

import java.util.HashMap;

public class MainMenu extends Menu {
    public MainMenu(Menu fatherMenu, String menuName) {
        super(fatherMenu, menuName);
        this.logoutType = true;
        HashMap<Integer, Menu> subMenus = new HashMap<Integer, Menu>();
        subMenus.put(1, new AccountMenu(this, "Account Menu"));
        subMenus.put(2, new SalesMenu(this, "Sales Menu"));
        subMenus.put(3, new ProductsMenu(this, "Products Menu"));
        subMenus.put(4, new LoginOrRegisterMenu(this, "Login\\Register Menu"));
        this.setSubMenus(subMenus);
    }
}
