package Menus.shows;

import Menus.Filters.ProductsFilterMenu;
import Menus.LoginOrRegisterMenu;
import Menus.Menu;
import Menus.Sorts.ProductsSortMenu;

import java.util.HashMap;

public class ShowProductsMenu extends ShowsMenu {
    public ShowProductsMenu(Menu fatherMenu, String menuName) {
        super(fatherMenu, menuName);
        this.type = "products";
        HashMap<Integer, Menu> subMenus = new HashMap<Integer, Menu>();
        filter = new ProductsFilterMenu(this, "Product Filter Menu");
        sort = new ProductsSortMenu(this, "Product Sort Menu");
        subMenus.put(1, filter);
        subMenus.put(2, sort);
        subMenus.put(3, getSelectMenu());
        subMenus.put(4, new LoginOrRegisterMenu(this, "Login\\Register Menu"));
        this.setSubMenus(subMenus);
    }

    private Menu getSelectMenu() {
        return new Menu(this, "Select Menu") {

        };
    }
}
