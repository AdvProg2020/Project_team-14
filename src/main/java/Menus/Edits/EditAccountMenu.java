package Menus.Edits;

import Menus.Menu;

import java.util.HashMap;

public class EditAccountMenu extends Menu {
    private void EditPersonalInfoSubMenus(HashMap subMenus) {
        subMenus.put(1, getEditInfoMenu("firstName"));
        subMenus.put(2, getEditInfoMenu("lastName"));
        subMenus.put(3, getEditInfoMenu("username"));
        subMenus.put(4, getEditInfoMenu("Email"));
        subMenus.put(5, getEditInfoMenu("telephone"));
        subMenus.put(6, getEditPasswordMenu());
    }

    public EditAccountMenu(Menu fatherMenu, String menuName) {
        super(fatherMenu, menuName);
        this.logoutType = false;
        HashMap<Integer, Menu> subMenus = new HashMap<Integer, Menu>();
        if (fatherMenu.getWhereItHasBeenCalled() == 1) {
            EditPersonalInfoSubMenus(subMenus);
        } else if (fatherMenu.getWhereItHasBeenCalled() == 2) {
            EditPersonalInfoSubMenus(subMenus);
            subMenus.put(7, getEditInfoMenu("money"));
        } else if (fatherMenu.getWhereItHasBeenCalled() == 3) {
            EditPersonalInfoSubMenus(subMenus);
        }
        this.setSubMenus(subMenus);
    }

    private String getMenuNameByCodeName(String menuName) {
        if (menuName.equals("firstName")) {
            return "First Name";
        } else if (menuName.equals("lastName")) {
            return "Last Name";
        } else if (menuName.equals("username")) {
            return "Username";
        } else if (menuName.equals("Email")) {
            return "Email";
        } else if (menuName.equals("telephone")) {
            return "Telephone";
        } else {
            return "Money";
        }
    }

    private Menu getEditInfoMenu(String editableName) {
        String changeType = getMenuNameByCodeName(editableName);
        return new Menu(this, "Edit " + changeType + " Menu:") {
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

    private Menu getEditPasswordMenu() {
        return new Menu(this, "Edit Password Menu:") {
            @Override
            public void execute() {
                System.out.println(this.getMenuName());
                System.out.println("if you input back we will go back");
                String pass1, pass2, pass3;
                System.out.println("insert your old password:");
                pass1 = scanner.nextLine();
                if (pass1.equals("back")) {
                    fatherMenu.execute();
                }
                System.out.println("insert your new password:");
                pass2 = scanner.nextLine();
                if (pass2.equals("back")) {
                    fatherMenu.execute();
                }
                System.out.println("insert your new password again:");
                pass3 = scanner.nextLine();
                if (pass3.equals("back")) {
                    fatherMenu.execute();
                }
                server.clientToServer("edit personal info password " + pass1 + " " + pass2 + " " + pass3
                        + " " + username);
                String clientAnswer = server.serverToClient();
                System.out.println(clientAnswer);
                if (clientAnswer.equals("edit successful")) {
                    fatherMenu.execute();
                } else {
                    this.execute();
                }
            }
        };
    }
}
