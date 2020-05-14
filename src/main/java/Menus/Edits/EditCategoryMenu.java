package Menus.Edits;

import Menus.Menu;
import Menus.Views.ViewCategoryMenu;

import java.util.HashMap;

public class EditCategoryMenu extends Menu {
    public EditCategoryMenu(Menu fatherMenu, String menuName) {
        super(fatherMenu, menuName);
        this.logoutType = false;
        HashMap<Integer, Menu> subMenus = new HashMap<Integer, Menu>();
        subMenus.put(1, getEditCategoryNameMenu());
        subMenus.put(2, getEditFatherCategoryMenu());
        subMenus.put(3, getAddAttributeMenu());
        subMenus.put(4, getDeleteAttributeMenu());
        subMenus.put(5, getEditAttributeMenu());
        this.setSubMenus(subMenus);
    }

    private Menu getEditCategoryNameMenu() {
        return new Menu(this, "Edit Category Name Menu") {
            @Override
            public void execute() {
                System.out.println(this.getMenuName());
                System.out.println("if you input back we will go back");
                System.out.println("insert the new category name:");
                String categoryName;
                categoryName = scanner.nextLine();
                if (categoryName.equals("back")) {
                    fatherMenu.execute();
                }
                server.clientToServer("edit category name " + Menu.username + " " +
                        ((ViewCategoryMenu) fatherMenu).getCategoryName() + " " + categoryName);
                String serverAnswer = server.serverToClient();
                System.out.println(serverAnswer);
                if (serverAnswer.equals("edit successful")) {
                    ((ViewCategoryMenu) fatherMenu).setCategoryName(categoryName);
                    fatherMenu.execute();
                } else {
                    this.execute();
                }
            }
        };
    }

    private Menu getEditFatherCategoryMenu() {
        return new Menu(this, "Edit Father Category Menu") {

            @Override
            public void execute() {
                System.out.println(this.getMenuName());
                System.out.println("if you input back we will go back");
                System.out.println("insert the new father category name or none for making this " +
                        "category a root category:");
                String categoryName;
                categoryName = scanner.nextLine();
                if (categoryName.equals("back")) {
                    fatherMenu.execute();
                }
                server.clientToServer("edit father category " + Menu.username + " " +
                        ((ViewCategoryMenu) fatherMenu).getCategoryName() + " " + categoryName);
                String serverAnswer = server.serverToClient();
                System.out.println(serverAnswer);
                if (serverAnswer.equals("edit successful")) {
                    fatherMenu.execute();
                } else {
                    this.execute();
                }
            }
        };
    }

    private Menu getAddAttributeMenu() {
        return new Menu(this, "Add Attribute Menu") {

        };
    }

    private Menu getDeleteAttributeMenu() {
        return new Menu(this, "Delete Attribute Menu") {

        };
    }

    private Menu getEditAttributeMenu() {
        return new Menu(this, "Edit Attributes Menu") {

        };
    }
}
