package Menus.Views;

import Menus.AccountMenu;
import Menus.BossMenu;
import Menus.Edits.EditAccountMenu;
import Menus.LoginOrRegisterMenu;
import Menus.Menu;

import java.util.HashMap;

public class ViewAccountMenu extends Menu {
    private int whereItHasBeenCalled;

    private void BossPersonalInfoSubMenus(HashMap subMenus) {
        this.whereItHasBeenCalled = 1;
        subMenus.put(1, new EditAccountMenu(this, "Edit Personal Info Menu"));
        subMenus.put(2, new LoginOrRegisterMenu(this, "Login\\Register Menu"));
    }

    public ViewAccountMenu(Menu fatherMenu, String menuName) {
        super(fatherMenu, menuName);
        this.logoutType = false;
        HashMap<Integer, Menu> subMenus = new HashMap<Integer, Menu>();
        if (fatherMenu instanceof BossMenu) {
            BossPersonalInfoSubMenus(subMenus);
        }
        this.setSubMenus(subMenus);
    }

    @Override
    public int getWhereItHasBeenCalled() {
        return whereItHasBeenCalled;
    }

    private void getInfo() {
        if (fatherMenu instanceof BossMenu) {
            server.clientToServer("view personal info BOSS " + username);
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
