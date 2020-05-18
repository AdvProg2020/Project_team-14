package Menus;

import Menus.Manages.ManageBalanceMenu;
import Menus.Manages.ManageProductsMenu;
import Menus.Manages.ManageSalesMenu;
import Menus.Views.ViewAccountMenu;

import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;

public class SalesmanMenu extends Menu {
    public SalesmanMenu(Menu fatherMenu, String menuName) throws ParseException, IOException {
        super(fatherMenu, menuName);
        this.logoutType = false;
        HashMap<Integer, Menu> subMenus = new HashMap<Integer, Menu>();
        subMenus.put(1, new ViewAccountMenu(this, "View Personal Info Menu"));
        subMenus.put(2, new ManageProductsMenu(this, "Manage Products Menu"));
        subMenus.put(3, new ManageSalesMenu(this, "Manage Sales Menu"));
        subMenus.put(4, new ManageBalanceMenu(this, "ManageBalanceMenu"));
        subMenus.put(5, new LoginOrRegisterMenu(this, "Login\\Register Menu"));
        this.setSubMenus(subMenus);
    }
}
