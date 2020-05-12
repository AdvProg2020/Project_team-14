package Menus.Sorts;

import Menus.LoginOrRegisterMenu;
import Menus.Menu;

import java.util.HashMap;

public class SortsMenu extends Menu {
    protected String sortFactor = "none";
    protected String sortType = "none";

    public SortsMenu(Menu fatherMenu, String menuName) {
        super(fatherMenu, menuName);
        this.logoutType = false;
    }

    public String getSortFactor() {
        return sortFactor;
    }

    public void setSortFactor(String sortFactor) {
        this.sortFactor = sortFactor;
    }

    public String getSortType() {
        return sortType;
    }

    public void setSortType(String sortType) {
        this.sortType = sortType;
    }

    public String getSort() {
        if (this.getSortType() == "none" || this.getSortFactor() == "none") {
            return "";
        } else {
            return "sort: " + this.getSortFactor() + " " + this.getSortType();
        }
    }

    public void resetSorts() {
        this.setSortFactor("none");
        this.setSortType("none");
    }
}
