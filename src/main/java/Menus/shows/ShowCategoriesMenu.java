package Menus.shows;

import Menus.Filters.CategoriesFilterMenu;
import Menus.Filters.RequestsFilterMenu;
import Menus.LoginOrRegisterMenu;
import Menus.Menu;
import Menus.Sorts.CategoriesSortMenu;
import Menus.Sorts.SortsMenu;
import Menus.Views.ViewAccountMenu;
import Menus.Views.ViewCategoryMenu;
import Menus.Views.ViewRequestMenu;

import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;

public class ShowCategoriesMenu extends ShowsMenu {
    //private String serverAnswer;
    private boolean canChangeSubCategoryFilter = true;

    public ShowCategoriesMenu(Menu fatherMenu, String menuName) {
        super(fatherMenu, menuName);
        this.type = "categories";
        HashMap<Integer, Menu> subMenus = new HashMap<>();
        if (fatherMenu instanceof ViewCategoryMenu) {
            canChangeSubCategoryFilter = false;
        }
        filter = new CategoriesFilterMenu(this, "Category Filter Menu");
        sort = new CategoriesSortMenu(this, "Category Sort Menu");
        if (fatherMenu instanceof ViewCategoryMenu) {
            ((CategoriesFilterMenu) filter).setFatherCategoryFilter(((ViewCategoryMenu) fatherMenu).getCategoryName());
            subMenus.put(1, getSelectMenu());
            subMenus.put(2, sort);
            subMenus.put(3, new LoginOrRegisterMenu(this, "Login\\Register Menu"));
        } else {
            subMenus.put(1, filter);
            subMenus.put(2, sort);
            subMenus.put(3, getSelectMenu());
            subMenus.put(4, new LoginOrRegisterMenu(this, "Login\\Register Menu"));
        }
        this.setSubMenus(subMenus);
    }

    private Menu getSelectMenu() {
        return new Menu(this, "Select Menu") {
            private boolean hasBeenCalled = true;

            private boolean isThereCategoryWithNameInList(String list, String categoryName) {
                int wordCount = Menu.wordCount(list);
                String[] lists = list.split("\\s");
                for (int i = 0; i < wordCount; i++) {
                    if (lists[i].equals(categoryName) && lists[i - 1].equals("Name:") && lists[i - 2].equals("Category")) {
                        return true;
                    }
                }
                return false;
            }

            @Override
            public void execute() throws ParseException, IOException {
                if (hasBeenCalled) {
                    hasBeenCalled = false;
                } else {
                    hasBeenCalled = true;
                    fatherMenu.execute();
                }
                System.out.println(menuName);
                System.out.println("if you input back we will go back");
                System.out.println("select one of categories above by inserting category name");
                String categoryName = scanner.nextLine();
                if (username.equalsIgnoreCase("back")) {
                    fatherMenu.execute();
                }
                String categories = ((ShowCategoriesMenu) fatherMenu).getServerAnswer();
                if (isThereCategoryWithNameInList(categories, categoryName)) {
                    new ViewCategoryMenu(this, "View Category Menu", categoryName).execute();
                } else {
                    System.out.println("this category is not on the list");
                    this.execute();
                }
            }
        };
    }

    public boolean isCanChangeSubCategoryFilter() {
        return canChangeSubCategoryFilter;
    }

    /*public String getServerAnswer() {
        return this.serverAnswer;
    }*/

    /*private void getInfo() {
        server.clientToServer("show categories " + Menu.username + filter.getFilters());
        this.serverAnswer = server.serverToClient();
        System.out.println(serverAnswer);
    }*/

    /*@Override
    protected void show() {
        super.show();
        getInfo();
    }*/
}
