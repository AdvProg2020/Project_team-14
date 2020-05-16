package Menus.shows;

import Menus.Filters.FiltersMenu;
import Menus.Menu;
import Menus.Sorts.SortsMenu;

public class ShowsMenu extends Menu {
    protected FiltersMenu filter;
    protected SortsMenu sort;
    protected String serverAnswer;
    protected String type;

    public ShowsMenu(Menu fatherMenu, String menuName) {
        super(fatherMenu, menuName);
        this.logoutType = false;
    }

    public void resetSorts() {
        sort.resetSorts();
    }

    public void resetFilters() {
        filter.resetFilters();
    }

    public String getServerAnswer() {
        return serverAnswer;
    }

    public void getInfo(String type) {
        server.clientToServer("show " + type + "+" + username + "+" + filter.getFilters() + "+" + sort.getSort());
        this.serverAnswer = server.serverToClient();
        System.out.println(serverAnswer);
    }

    @Override
    protected void show() {
        super.show();
        getInfo(type);
    }
}
