package Menus.Views;

//import Menus.Edits.EditSaleMenu;
import Menus.LoginOrRegisterMenu;
import Menus.Menu;

import java.text.ParseException;
import java.util.HashMap;

public class ViewSalesMenu extends Menu {
    private String saleID;

    public ViewSalesMenu(Menu fatherMenu, String menuName, String saleID) {
        super(fatherMenu, menuName);
        this.saleID = saleID;
        HashMap<Integer, Menu> subMenus = new HashMap<>();
        //subMenus.put(1, new EditSaleMenu(this, "Edit Sale Menu"));
        subMenus.put(2, new LoginOrRegisterMenu(this, "Login\\Register Menu"));
    }

    public void getInfo() throws ParseException {
        server.clientToServer("view sale" + "+" + Menu.username + "+" + saleID);
        System.out.println(server.serverToClient());
    }

    @Override
    protected void show() throws ParseException {
        super.show();
        getInfo();
    }
}
