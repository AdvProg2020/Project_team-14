package Menus.Edits;

import Menus.Menu;

import java.util.HashMap;

public class EditAccountMenu extends Menu {
    private void BossEditPersonalInfoSubMenus(HashMap subMenus) {
        subMenus.put(1, getEditNameMenu("firstName"));
        subMenus.put(2, getEditNameMenu("lastName"));
        subMenus.put(3, getEditNameMenu("username"));
    }

    public EditAccountMenu(Menu fatherMenu, String menuName) {
        super(fatherMenu, menuName);
        this.logoutType = false;
        HashMap<Integer, Menu> subMenus = new HashMap<Integer, Menu>();
        if (fatherMenu.getWhereItHasBeenCalled() == 1) {
            BossEditPersonalInfoSubMenus(subMenus);
        }
        this.setSubMenus(subMenus);
    }

    private String getMenuNameByCodeName(String menuName) {
        if (menuName.equals("firstName")) {
            return "First Name";
        } else if (menuName.equals("lastName")) {
            return "Last Name";
        } else {
            return "Username";
        }
    }

    private Menu getEditNameMenu(String editableName) {
        String changeType = getMenuNameByCodeName(editableName);
        return new Menu(this, "Edit " + changeType + " Menu") {
            @Override
            public void execute() {
                System.out.println(this.getMenuName());
                System.out.println("if you input back we will go back");
                System.out.println("insert the new " + changeType + ":");
                String name;
                name = scanner.nextLine();
                if (name.equals("back")) {
                    fatherMenu.execute();
                }
                server.clientToServer("edit personal info " + editableName + " " + name + " " + username);
                String serverAnswer = server.serverToClient();
                System.out.println(serverAnswer);
                if (serverAnswer.equals("edit successful")) {
                    if (changeType.equals("Username")) {
                        Menu.username = name;
                    }
                    fatherMenu.execute();
                } else {
                    this.execute();
                }
            }
        };
    }
}
