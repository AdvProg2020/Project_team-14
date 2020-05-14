package Menus.Views;

import Menus.Edits.EditCategoryMenu;
import Menus.LoginOrRegisterMenu;
import Menus.Menu;
import Menus.shows.ShowCategoriesMenu;

import java.util.HashMap;

public class ViewCategoryMenu extends Menu {
    private String categoryName;

    private void categorySubMenus() {
        HashMap<Integer, Menu> subMenus = new HashMap<Integer, Menu>();
        subMenus.put(1, new EditCategoryMenu(this, "Edit Category Menu"));
        subMenus.put(2, new ShowCategoriesMenu(this, "Show Sub Categories Menu"));
        subMenus.put(3, getDeleteCategoryMenu());
        subMenus.put(4, new LoginOrRegisterMenu(this, "Login\\Register Menu"));
        this.setSubMenus(subMenus);
    }

    public ViewCategoryMenu(Menu fatherMenu, String menuName, String categoryName) {
        super(fatherMenu, menuName);
        this.categoryName = categoryName;
        this.logoutType = false;
        categorySubMenus();
    }

    private Menu getDeleteCategoryMenu() {
        return new Menu(this, "Delete Category Menu") {

        };
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    private void getCategoryInfo() {
        server.clientToServer("view category " + Menu.username + " " + categoryName);
        String serverAnswer = server.serverToClient();
        System.out.println(serverAnswer);
    }

    @Override
    protected void show() {
        super.show();
        getCategoryInfo();
    }
}
