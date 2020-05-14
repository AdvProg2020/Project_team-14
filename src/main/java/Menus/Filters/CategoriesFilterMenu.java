package Menus.Filters;

import Menus.LoginOrRegisterMenu;
import Menus.Menu;
import Menus.shows.ShowCategoriesMenu;

import java.util.ArrayList;
import java.util.HashMap;

public class CategoriesFilterMenu extends FiltersMenu {
    private void makeFilters() {
        filters.add("fatherCategory");
        filters.add(new String());
        filters.set(1, null);
    }

    public CategoriesFilterMenu(Menu fatherMenu, String menuName) {
        super(fatherMenu, menuName);
        filters = new ArrayList<Object>();
        HashMap<Integer, Menu> subMenus = new HashMap<>();
        makeFilters();
        if (((ShowCategoriesMenu) fatherMenu).isCanChangeSubCategoryFilter()) {
            subMenus.put(1, getChangeFatherCategoryFilterMenu());
            subMenus.put(2, new LoginOrRegisterMenu(this, "Login\\Register Menu"));
        } else {
            subMenus.put(1, new LoginOrRegisterMenu(this, "Login\\Register Menu"));
        }
        this.setSubMenus(subMenus);
    }

    public String getFatherCategoryFilter() {
        if (filters.get(1) == null) {
            return "none";
        } else {
            return (String) filters.get(1);
        }
    }

    public void setFatherCategoryFilter(String categoryName) {
        if (categoryName.equals("clear")) {
            filters.set(1, null);
        } else {
            if (this.filters.get(1) == null) {
                filters.set(1, categoryName);
            } else {
                filters.set(1, filters.get(1) + "," + categoryName);
            }
        }
    }

    private Menu getChangeFatherCategoryFilterMenu() {
        return new Menu(this, "Change Father Category Filter Menu") {
            private boolean checkInputValidation(String categoryName) {
                if (categoryName.equals("clear")) {
                    return true;
                }
                server.clientToServer("is category exists " + categoryName);
                if (server.serverToClient().equalsIgnoreCase("yes")) {
                    return true;
                } else {
                    return false;
                }
            }

            @Override
            public void execute() {
                System.out.println(this.getMenuName());
                System.out.println("if you input back we will go back");
                while (true) {
                    System.out.println("your current father Category(ies) is: " + getFatherCategoryFilter());
                    System.out.println("please insert your new added father category or clear for clearing current filter");
                    String input = scanner.nextLine();
                    if (input.equals("back")) {
                        fatherMenu.execute();
                    } else {
                        if (checkInputValidation(input)) {
                            setFatherCategoryFilter(input);
                        } else {
                            System.out.println("that's not a valid category name");
                        }
                    }
                }
            }
        };
    }
}
