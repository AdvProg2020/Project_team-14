package Menus.shows;

import Menus.Filters.AccountsFilterMenu;
import Menus.LoginOrRegisterMenu;
import Menus.Menu;

import java.util.HashMap;

public class ShowAccountsMenu extends ShowsMenu {

    public ShowAccountsMenu(Menu fatherMenu, String menuName) {
        super(fatherMenu, menuName);
        HashMap<Integer, Menu> subMenus = new HashMap<Integer, Menu>();
        filter = new AccountsFilterMenu(this, "Account Filter Menu");
        subMenus.put(1, filter);
        subMenus.put(2, new LoginOrRegisterMenu(this, "Login\\Register Menu"));
        this.setSubMenus(subMenus);
    }

    private void getInfo() {
        server.clientToServer("show accounts " + username + filter.getFilters());
        String serverAnswer = server.serverToClient();
        System.out.println(serverAnswer);
    }

    @Override
    protected void show() {
        super.show();
        getInfo();
    }
}
