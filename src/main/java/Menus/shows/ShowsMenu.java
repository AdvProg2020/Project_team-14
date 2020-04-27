package Menus.shows;

import Menus.Filters.AccountsFilterMenu;
import Menus.Menu;

public class ShowsMenu extends Menu {
    AccountsFilterMenu filter;

    public ShowsMenu(Menu fatherMenu, String menuName) {
        super(fatherMenu, menuName);
        this.logoutType = false;
    }
}
