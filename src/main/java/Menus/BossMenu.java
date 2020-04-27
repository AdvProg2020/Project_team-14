package Menus;

import Menus.Views.ViewAccountMenu;

import java.util.HashMap;

public class BossMenu extends Menu {
    public BossMenu(Menu fatherMenu, String menuName) {
        super(fatherMenu, menuName);
        this.logoutType = false;
        HashMap<Integer, Menu> subMenus = new HashMap<Integer, Menu>();
        subMenus.put(1, new ViewAccountMenu(this, "Show Personal Info Menu"));
        subMenus.put(2, new LoginOrRegisterMenu(this, "Login\\Register Menu"));
        this.setSubMenus(subMenus);
    }
}
