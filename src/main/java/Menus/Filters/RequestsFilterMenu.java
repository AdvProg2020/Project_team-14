package Menus.Filters;

import Menus.Menu;

import java.util.ArrayList;

public class RequestsFilterMenu extends FiltersMenu {
    public RequestsFilterMenu(Menu fatherMenu, String menuName) {
        super(fatherMenu, menuName);
        filters = new ArrayList<Object>();
    }
}
