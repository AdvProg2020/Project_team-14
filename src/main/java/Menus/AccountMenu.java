package Menus;

import java.text.ParseException;
import java.util.HashMap;

public class AccountMenu extends Menu {
    private HashMap<String, Menu> subMenus;

    public AccountMenu(Menu fatherMenu, String menuName) throws ParseException {
        super(fatherMenu, menuName);
        this.logoutType = false;
        HashMap<String, Menu> subMenus = new HashMap<String, Menu>();
        subMenus.put("BOSS", new BossMenu(this, "Boss Menu"));
        subMenus.put("CUSTOMER", new CustomerMenu(this, "Customer Menu"));
        subMenus.put("SALESMAN", new SalesmanMenu(this, "Salesman Menu"));
        subMenus.put("login", new LoginOrRegisterMenu(this, "Login\\Register Menu"));
        this.subMenus = subMenus;
    }

    @Override
    public void execute() throws ParseException {
        if (Menu.backFromAccountMenu) {
            Menu.backFromAccountMenu = false;
            fatherMenu.execute();
        }
        if (isUserLogin) {
            this.subMenus.get(userType).execute();
        } else {
            this.subMenus.get("login").execute();
        }
    }
}
