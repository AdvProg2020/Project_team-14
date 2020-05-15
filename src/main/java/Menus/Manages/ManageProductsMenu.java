package Menus.Manages;

import Menus.BossMenu;
import Menus.LoginOrRegisterMenu;
import Menus.Menu;
import Menus.SalesmanMenu;
import Menus.shows.ShowProductsMenu;
import Menus.shows.ShowRequestsMenu;

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
            subMenus.put(3, new ShowProductsMenu(this, "Show My Products Menu"));
            subMenus.put(4, new LoginOrRegisterMenu(this, "Login\\Register Menu"));
        }
        this.setSubMenus(subMenus);
    }

    private Menu getSearchProductMenu() {
        return new Menu(this, "Search Products Menu") {

        };
    }

    private Menu getAddProductMenu() {
        return new Menu(this, "Add Product Menu") {
            private void checkBack(String input) {
                if (input.equals("back")) {
                    fatherMenu.execute();
                }
            }

            @Override
            public void execute() {
                System.out.println(this.getMenuName());
                System.out.println("if you input back we will go back");
                String productName, brand, description, price, remainder;
                /*System.out.println("please enter your first and last name in separate lines:");
                firstName = scanner.nextLine();
                checkBack(firstName);
                lastName = scanner.nextLine();
                checkBack(lastName);
                System.out.println("please enter your username and password in separate lines:");
                username = scanner.nextLine();
                checkBack(username);
                password = scanner.nextLine();
                checkBack(password);
                System.out.println("please enter your role");
                role = scanner.nextLine();
                checkBack(role);
                System.out.println("please enter your Email and telephone in separate lines:");
                Email = scanner.nextLine();
                checkBack(Email);
                telephone = scanner.nextLine();
                checkBack(telephone);
                message = "register " + firstName + " " + lastName + " " + username + " " + password + " " + role + " "
                        + Email + " " + telephone + " ";
                if (role.equals("salesman")) {
                    System.out.println("please enter your company:");
                    company = scanner.nextLine();
                    checkBack(company);
                    message += company;
                }
                server.clientToServer(message);
                String serverAnswer;
                serverAnswer = server.serverToClient();
                System.out.println(serverAnswer);
                if (serverAnswer.equals("register successful")) {
                    fatherMenu.execute();
                } else {
                    this.execute();
                }*/
            }
        };
    }
}
