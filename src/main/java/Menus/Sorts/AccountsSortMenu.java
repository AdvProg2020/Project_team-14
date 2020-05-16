package Menus.Sorts;

import Menus.LoginOrRegisterMenu;
import Menus.Menu;

import java.util.HashMap;

public class AccountsSortMenu extends SortsMenu {

    public AccountsSortMenu(Menu fatherMenu, String menuName) {
        super(fatherMenu, menuName);
        this.logoutType = false;
        HashMap<Integer, Menu> subMenus = new HashMap<Integer, Menu>();
        /*
        subMenus.put(1, getChangeCreditSortMenu());
        subMenus.put(2, getChangeSortUsernameAlphabetically());
         */
        subMenus.put(1, getResetSort(this));
        subMenus.put(2, getSubMenuSelectFactor(this, "CREDIT"));
        subMenus.put(3, getSubMenuSelectFactor(this, "ALPHABETICALLY"));
        subMenus.put(4, new LoginOrRegisterMenu(this, "Login\\Register Menu"));
        this.setSubMenus(subMenus);
    }
/*
    private Menu getChangeSortUsernameAlphabetically() {
        return new Menu(this, "Change Username Alphabetically Sort") {
            @Override
            public void execute() {
                System.out.println(menuName);
                System.out.println("if you input back we will go back");
                while (true) {
                    System.out.println("your current sort type is: " + ((SortsMenu) fatherMenu).getSortType());
                    System.out.println("please insert your new sort factor(ascending\\descending\\)or clear for" +
                            " clearing current sort");
                    String input = scanner.nextLine();
                    if (input.equalsIgnoreCase("back")) {
                        fatherMenu.execute();
                    } else {
                        if (input.equalsIgnoreCase("ascending") || input.equalsIgnoreCase
                                ("descending") || input.equalsIgnoreCase("none")) {
                            ((SortsMenu) fatherMenu).setSortType(input);
                            ((SortsMenu) fatherMenu).setSortFactor("USERNAME");
                        } else {
                            System.out.println("wrong input");
                        }
                    }
                }
            }
        };
    }

    private Menu getChangeCreditSortMenu() {
        return new Menu(this, "Change Credit Sort Menu") {
            @Override
            public void execute() {
                System.out.println("if you input back we will go back");
                while (true) {
                    System.out.println("your current sort type is: " + ((SortsMenu) fatherMenu).getSortType());
                    System.out.println("please insert your new sort factor(ascending\\descending\\)or clear for" +
                            " clearing current sort");
                    String input = scanner.nextLine();
                    if (input.equalsIgnoreCase("back")) {
                        fatherMenu.execute();
                    } else {
                        if (input.equalsIgnoreCase("ascending") || input.equalsIgnoreCase
                                ("descending") || input.equalsIgnoreCase("none")) {
                            ((SortsMenu) fatherMenu).setSortType(input);
                            ((SortsMenu) fatherMenu).setSortFactor("CREDIT");
                        } else {
                            System.out.println("wrong input");
                        }
                    }
                }
            }
        };
    }
*/
}