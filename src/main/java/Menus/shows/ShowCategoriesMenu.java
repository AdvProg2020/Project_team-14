package Menus.shows;

import Menus.Filters.CategoriesFilterMenu;
import Menus.Filters.RequestsFilterMenu;
import Menus.LoginOrRegisterMenu;
import Menus.Menu;
import Menus.Views.ViewAccountMenu;
import Menus.Views.ViewCategoryMenu;

import java.util.HashMap;

public class ShowCategoriesMenu extends ShowsMenu {
    private String serverAnswer;
    private boolean canChangeSubCategoryFilter = true;

    public ShowCategoriesMenu(Menu fatherMenu, String menuName) {
        super(fatherMenu, menuName);
        HashMap<Integer, Menu> subMenus = new HashMap<Integer, Menu>();
        if (fatherMenu instanceof ViewCategoryMenu) {
            canChangeSubCategoryFilter = false;
        }
        filter = new CategoriesFilterMenu(this, "Category Filter Menu");
        if (fatherMenu instanceof ViewAccountMenu) {
            ((RequestsFilterMenu) filter).setUsernameFilter(((ViewAccountMenu) fatherMenu).getUsername());
            subMenus.put(1, getSelectMenu());
            subMenus.put(2, new LoginOrRegisterMenu(this, "Login\\Register Menu"));
        } else {
            subMenus.put(1, filter);
            subMenus.put(2, getSelectMenu());
            subMenus.put(3, new LoginOrRegisterMenu(this, "Login\\Register Menu"));
        }
        this.setSubMenus(subMenus);
    }

    private Menu getSelectMenu() {
        return new Menu(this, "Select Menu") {

        };
    }

    public boolean isCanChangeSubCategoryFilter() {
        return canChangeSubCategoryFilter;
    }

    public String getServerAnswer() {
        return this.serverAnswer;
    }

    private void getInfo() {
        server.clientToServer("show categories " + Menu.username);
        this.serverAnswer = server.serverToClient();
        System.out.println(serverAnswer);
    }

    @Override
    protected void show() {
        super.show();
        getInfo();
    }
}
