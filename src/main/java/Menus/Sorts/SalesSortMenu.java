package Menus.Sorts;

import Menus.LoginOrRegisterMenu;
import Menus.Menu;

import java.util.HashMap;

public class SalesSortMenu extends SortsMenu {
    public SalesSortMenu(Menu fatherMenu, String menuName) {
        super(fatherMenu, menuName);
        HashMap<Integer, Menu> subMenus = new HashMap<>();
        subMenus.put(1, getResetSort(this));
        subMenus.put(2, getSubMenuSelectFactor(this, "START_TIME"));
        subMenus.put(3, getSubMenuSelectFactor(this, "END_TIME"));
        subMenus.put(4, getSubMenuSelectFactor(this, "PERCENTAGE"));
        subMenus.put(5, new LoginOrRegisterMenu(this, "Login\\Register Menu"));
        this.setSubMenus(subMenus);
    }
}
