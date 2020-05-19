package Menus.Sorts;

import Menus.LoginOrRegisterMenu;
import Menus.Menu;

import java.util.HashMap;

public class CategoriesSortMenu extends SortsMenu {
    public CategoriesSortMenu(Menu fatherMenu, String menuName) {
        super(fatherMenu, menuName);
        HashMap<Integer, Menu> sunMenus = new HashMap<>();
        sunMenus.put(1, getResetSort(this));
        sunMenus.put(2, getSubMenuSelectFactor(this, "ALPHABETICALLY"));
        sunMenus.put(3, getSubMenuSelectFactor(this, "SEEN_COUNT"));
        sunMenus.put(4, new LoginOrRegisterMenu(this,"Login\\Register Menu"));
        this.setSubMenus(sunMenus);
    }
}
