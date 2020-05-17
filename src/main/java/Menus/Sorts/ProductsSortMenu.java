package Menus.Sorts;

import Menus.LoginOrRegisterMenu;
import Menus.Menu;

import java.util.HashMap;

public class ProductsSortMenu extends SortsMenu {
    public ProductsSortMenu(Menu fatherMenu, String menuName) {
        super(fatherMenu, menuName);
        HashMap<Integer, Menu> subMenus = new HashMap<>();
        subMenus.put(1, getResetSort(this));
        subMenus.put(2, getSubMenuSelectFactor(this, "ALPHABETICALLY"));
        subMenus.put(3, getSubMenuSelectFactor(this, "SEEN_COUNT"));
        subMenus.put(4, getSubMenuSelectFactor(this, "PRICE"));
        subMenus.put(5, getSubMenuSelectFactor(this, "HIGHEST_POINT"));
        subMenus.put(6, new LoginOrRegisterMenu(this, "Login\\Register Menu"));
        this.setSubMenus(subMenus);
    }
}
