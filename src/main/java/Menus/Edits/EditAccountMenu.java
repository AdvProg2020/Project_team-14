package Menus.Edits;

import Menus.Menu;

import java.util.HashMap;

public class EditAccountMenu extends Menu {
    private void BossEditPersonalInfoSubMenus(HashMap subMenus) {
        subMenus.put(1, getEditFirstNameMenu());
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

    private Menu getEditFirstNameMenu() {
        return new Menu(this, "Edit First Name Menu") {
            @Override
            public void execute() {
                System.out.println("Edit First Name Menu");
                System.out.println("if you input back we will go back");
                System.out.println("insert the new name:");
                String name;
                name = scanner.nextLine();
                if (name.equals("back")) {
                    fatherMenu.execute();
                }
                server.clientToServer("edit personal info firstName " + name + " " + username);
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
}
