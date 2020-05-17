package Menus.Manages;

import Menus.BossMenu;
import Menus.LoginOrRegisterMenu;
import Menus.Menu;
import Menus.SalesmanMenu;
import Menus.shows.ShowProductsMenu;
import Menus.shows.ShowRequestsMenu;

import java.text.ParseException;
import java.util.HashMap;

public class ManageProductsMenu extends Menu {
    public ManageProductsMenu(Menu fatherMenu, String menuName) {
        super(fatherMenu, menuName);
        this.logoutType = false;
        HashMap<Integer, Menu> subMenus = new HashMap<Integer, Menu>();
        if (fatherMenu instanceof BossMenu) {
            subMenus.put(1, new ShowProductsMenu(this, "Show Products Menu"));
            subMenus.put(2, getSearchProductMenu());
            subMenus.put(3, new ShowRequestsMenu(this, "Add Products Requests"));
            subMenus.put(4, new ShowRequestsMenu(this, "Delete Products Requests"));
            subMenus.put(5, new ShowRequestsMenu(this, "Edit Products Requests"));
            subMenus.put(6, new LoginOrRegisterMenu(this, "Login\\Register Menu"));
        } else if (fatherMenu instanceof SalesmanMenu) {
            subMenus.put(1, getAddProductMenu());
            subMenus.put(2, getSearchProductMenu());
            subMenus.put(3, new ShowProductsMenu(this, "Show Products Menu"));
            subMenus.put(4, new ShowProductsMenu(this, "Show My Products Menu"));
            subMenus.put(5, new LoginOrRegisterMenu(this, "Login\\Register Menu"));
        }
        this.setSubMenus(subMenus);
    }

    private Menu getSearchProductMenu() {
        return new Menu(this, "Search Products Menu") {

        };
    }

    private Menu getAddProductMenu() {
        return new Menu(this, "Add Product Menu") {

            @Override
            public void execute() throws ParseException {
                System.out.println(this.getMenuName());
                System.out.println("if you input back we will go back");
                String productName, brand, description, price, remainder;
                System.out.println("please enter product name:");
                productName = scanner.nextLine();
                checkBack(productName);
                System.out.println("please enter brand of the product:");
                brand = scanner.nextLine();
                checkBack(brand);
                System.out.println("please enter the description of the product:");
                description = scanner.nextLine();
                checkBack(description);
                System.out.println("please enter the price of product:");
                price = scanner.nextLine();
                checkBack(price);
                System.out.println("please enter the number of remainder:");
                remainder = scanner.nextLine();
                checkBack(remainder);
                server.clientToServer("create product+" + Menu.username + "+" + productName + "+"
                        + brand + "+" + description + "+" + price + "+" + remainder);
                String serverAnswer;
                serverAnswer = server.serverToClient();
                System.out.println(serverAnswer);
                if (serverAnswer.equals("product created")) {
                    fatherMenu.execute();
                } else {
                    this.execute();
                }
            }
        };
    }
}
