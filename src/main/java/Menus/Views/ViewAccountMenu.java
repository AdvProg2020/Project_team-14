package Menus.Views;

import Menus.*;
import Menus.Edits.EditAccountMenu;

import java.util.HashMap;

public class ViewAccountMenu extends Menu {
    private int whereItHasBeenCalled;

    private void PersonalInfoSubMenus(HashMap subMenus) {
        subMenus.put(1, new EditAccountMenu(this, "Edit Personal Info Menu"));
        subMenus.put(2, new LoginOrRegisterMenu(this, "Login\\Register Menu"));
    }

    public ViewAccountMenu(Menu fatherMenu, String menuName) {
        super(fatherMenu, menuName);
        this.logoutType = false;
        HashMap<Integer, Menu> subMenus = new HashMap<Integer, Menu>();
        if (fatherMenu instanceof BossMenu) {
            whereItHasBeenCalled = 1;
            PersonalInfoSubMenus(subMenus);
        } else if (fatherMenu instanceof CustomerMenu) {
            whereItHasBeenCalled = 2;
            PersonalInfoSubMenus(subMenus);
        } else if (fatherMenu instanceof SalesmanMenu) {
            whereItHasBeenCalled = 3;
            PersonalInfoSubMenus(subMenus);
        }
        this.setSubMenus(subMenus);
    }

    @Override
    public int getWhereItHasBeenCalled() {
        return whereItHasBeenCalled;
    }

    private void getInfo() {
        if (fatherMenu instanceof BossMenu || fatherMenu instanceof CustomerMenu || fatherMenu instanceof SalesmanMenu) {
            server.clientToServer("view personal info " + username);
        }
        String serverAnswer;
        serverAnswer = server.serverToClient();
        System.out.println(serverAnswer);
    }

    @Override
    protected void show() {
        super.show();
        this.getInfo();
    }

    @Override
    public void execute() {
        super.execute();
    }
}
