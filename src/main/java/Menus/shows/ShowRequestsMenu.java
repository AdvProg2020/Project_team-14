package Menus.shows;

import Menus.Filters.RequestsFilterMenu;
import Menus.LoginOrRegisterMenu;
import Menus.Menu;

import java.util.HashMap;

public class ShowRequestsMenu extends ShowsMenu {
    private String serverAnswer;

    public ShowRequestsMenu(Menu fatherMenu, String menuName) {
        super(fatherMenu, menuName);
        HashMap<Integer, Menu> subMenus = new HashMap<Integer, Menu>();
        filter = new RequestsFilterMenu(this, "Request Filter Menu");
        subMenus.put(1, filter);
        subMenus.put(2, new LoginOrRegisterMenu(this, "Login\\Register Menu"));
        this.setSubMenus(subMenus);
    }

    public String getServerAnswer() {
        return this.serverAnswer;
    }

    private void getInfo() {
        server.clientToServer("show requests " + username + filter.getFilters());
        this.serverAnswer = server.serverToClient();
        System.out.println(serverAnswer);
    }

    @Override
    protected void show() {
        super.show();
        getInfo();
    }
}
