package Menus;

import Menus.shows.ShowAccountsMenu;

import java.util.HashMap;

public class ManageAccountsMenu extends Menu {
    public ManageAccountsMenu(Menu fatherMenu, String menuName) {
        super(fatherMenu, menuName);
        this.logoutType = false;
        HashMap<Integer, Menu> subMenus = new HashMap<Integer, Menu>();
        subMenus.put(1, new ShowAccountsMenu(this, "Show Accounts Menu"));
        subMenus.put(2, new LoginOrRegisterMenu(this, "Login\\Register Menu"));
    }
}
