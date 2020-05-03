package Menus;

import Menus.Manages.ManageAccountsMenu;
import Menus.Views.ViewAccountMenu;

import java.util.HashMap;

public class BossMenu extends Menu {
    public BossMenu(Menu fatherMenu, String menuName) {
        super(fatherMenu, menuName);
        this.logoutType = false;
        HashMap<Integer, Menu> subMenus = new HashMap<Integer, Menu>();
        subMenus.put(1, new ViewAccountMenu(this, "View Personal Info Menu"));
        subMenus.put(2, new ManageAccountsMenu(this, "Manage Accounts Menu"));
        subMenus.put(3, new LoginOrRegisterMenu(this, "Login\\Register Menu"));
        this.setSubMenus(subMenus);
    }
}
