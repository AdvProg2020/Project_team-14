package Menus.Filters;

import Menus.Menu;

import java.util.ArrayList;

public class FiltersMenu extends Menu {
    protected ArrayList<Object> filters;

    public FiltersMenu(Menu fatherMenu, String menuName) {
        super(fatherMenu, menuName);
        this.logoutType = false;
        filters = new ArrayList<Object>();
    }

    public void resetFilters() {
        for (int i = 1; i < filters.size(); i += 2) {
            filters.set(i, null);
        }
    }

    public String getFilters() {
        StringBuilder filter = new StringBuilder("");
        for (int i = 0; i < filters.size(); i += 2) {
            if (filters.get(i + 1) != null) {
                filter.append("+");
                filter.append(filters.get(i));
                filter.append("+");
                filter.append(filters.get(i + 1));
            }
        }
        if (filter.toString().equals("")) {
            return filter.toString();
        } else {
            return "+filters:" + filter;
        }
    }
}
