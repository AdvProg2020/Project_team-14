package Menus.shows;

import Menus.Filters.FiltersMenu;
import Menus.Menu;
import Menus.Sorts.SortsMenu;

public class ShowsMenu extends Menu {
    protected FiltersMenu filter;
    protected SortsMenu sort;

    public ShowsMenu(Menu fatherMenu, String menuName) {
        super(fatherMenu, menuName);
        this.logoutType = false;
    }
}
