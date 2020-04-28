package Menus.shows;

import Menus.Filters.FiltersMenu;
import Menus.Menu;

public class ShowsMenu extends Menu {
    protected FiltersMenu filter;

    public ShowsMenu(Menu fatherMenu, String menuName) {
        super(fatherMenu, menuName);
        this.logoutType = false;
    }
}
