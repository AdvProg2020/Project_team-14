package Menus;

import Menus.Manages.ManageProductsMenu;
import Menus.Views.ViewAccountMenu;

import java.text.ParseException;
import java.util.HashMap;

public class SalesmanMenu extends Menu {
    public SalesmanMenu(Menu fatherMenu, String menuName) throws ParseException {
        super(fatherMenu, menuName);
        this.logoutType = false;
        HashMap<Integer, Menu> subMenus = new HashMap<Integer, Menu>();
        subMenus.put(1, new ViewAccountMenu(this, "View Personal Info Menu"));
        subMenus.put(2, new ManageProductsMenu(this, "Manage Products Menu"));
        subMenus.put(3, new LoginOrRegisterMenu(this, "Login\\Register Menu"));
        this.setSubMenus(subMenus);
    }
}
