package Menus.Manages;

import Menus.LoginOrRegisterMenu;
import Menus.Menu;
import Menus.Views.ViewCategoryMenu;
import Menus.shows.ShowCategoriesMenu;

import java.util.HashMap;

public class ManageCategoriesMenu extends Menu {
    public ManageCategoriesMenu(Menu fatherMenu, String menuName) {
        super(fatherMenu, menuName);
        this.logoutType = false;
        HashMap<Integer, Menu> subMenus = new HashMap<Integer, Menu>();
        subMenus.put(1, new ShowCategoriesMenu(this, "Show Categories Menu"));
        subMenus.put(2, getSearchCategoryMenu());
        subMenus.put(3, getCreateNewCategoryMenu());
        subMenus.put(4, new LoginOrRegisterMenu(this, "Login\\Register Menu"));
        this.setSubMenus(subMenus);
    }

    private Menu getSearchCategoryMenu() {
        return new Menu(this, "Search Request Menu") {
            boolean hasBeenCalled = true;

            @Override
            public void execute() {
                if (hasBeenCalled) {
                    hasBeenCalled = false;
                } else {
                    hasBeenCalled = true;
                    fatherMenu.execute();
                }
                System.out.println(menuName);
                System.out.println("if you input back we will go back");
                System.out.println("please input the category name");
                String categoryName = scanner.nextLine();
                server.clientToServer("search category " + Menu.username + " " + categoryName);
                String serverAnswer = server.serverToClient();
                System.out.println(serverAnswer);
                if (serverAnswer.equals("search completed")) {
                    ViewCategoryMenu menu = new ViewCategoryMenu(this, "View Category Menu",
                            categoryName);
                    menu.execute();
                } else {
                    this.execute();
                }
            }
        };
    }

    private Menu getCreateNewCategoryMenu() {
        return new Menu(this, "Create New Category Menu") {
            private void checkBack(String input) {
                if (input.equals("back")) {
                    fatherMenu.execute();
                }
            }

            private int countWordsBySemicolon(String str) {
                int counter = 0;
                for (int i = 0; i < str.length(); i++) {
                    if (str.charAt(i) == ',') {
                        counter++;
                    }
                }
                return counter + 1;
            }

            private String addAttribute(String attributes, String input) {
                if (attributes.equals("")) {
                    attributes = input;
                    return attributes;
                }
                int attributeCount = countWordsBySemicolon(attributes);
                String[] attribute = attributes.split(",");
                for (int i = 0; i < attributeCount; i++) {
                    if (attribute[i].equals(input)) {
                        System.out.println("this attribute has been added before");
                        return attributes;
                    }
                }
                attributes = attributes + "," + input;
                return attributes;
            }

            private String deleteAttribute(String attributes, String input) {
                if (attributes.equals("")) {
                    System.out.println("there's nothing to delete");
                    return attributes;
                }
                if (attributes.contains(input + ",")) {
                    int position = attributes.indexOf(input + ",");
                    return attributes.substring(0, position) + attributes.substring(position + input.length() + 1);
                } else {
                    System.out.println("it hasn't the attribute you want to delete");
                    return attributes;
                }
            }

            @Override
            public void execute() {
                System.out.println(this.getMenuName());
                System.out.println("if you input back we will go back");
                String message = new String();
                String categoryName, fatherCategoryName = "";
                String categoryAttributes = "";
                System.out.println("please enter the category name:");
                categoryName = scanner.nextLine();
                checkBack(categoryName);
                System.out.println("if you want this category to have a father category insert the father category " +
                        "name or insert enter");
                fatherCategoryName = scanner.nextLine();
                checkBack(fatherCategoryName);
                if (fatherCategoryName.equals("")) {
                    fatherCategoryName = "rootCategory";
                }
                while (true) {
                    System.out.println("this category current attributes are: " + categoryAttributes);
                    System.out.println("insert the attributes you want to add to this category or insert delete " +
                            "[attribute] to delete it and insert done for finishing adding attributes");
                    String input = scanner.nextLine();
                    if (input.equalsIgnoreCase("done")) {
                        break;
                    }
                    if (!input.startsWith("delete ")) {
                        categoryAttributes = addAttribute(categoryAttributes, input);
                    } else {
                        categoryAttributes = deleteAttribute(categoryAttributes, input.split("\\s")[1]);
                    }
                }
                if (categoryAttributes.equals("")) {
                    categoryAttributes = "none";
                }
                server.clientToServer("create category " + categoryName + " " + fatherCategoryName +
                        " " + categoryAttributes);
                String serverAnswer;
                serverAnswer = server.serverToClient();
                System.out.println(serverAnswer);
                if (serverAnswer.equals("category created")) {
                    fatherMenu.execute();
                } else {
                    this.execute();
                }
            }
        };
    }
}
