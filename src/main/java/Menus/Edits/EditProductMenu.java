package Menus.Edits;

import Menus.LoginOrRegisterMenu;
import Menus.Menu;
import Menus.Views.ViewProductMenu;

import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;

public class EditProductMenu extends Menu {
    public EditProductMenu(Menu fatherMenu, String menuName) {
        super(fatherMenu, menuName);
        this.logoutType = false;
        HashMap<Integer, Menu> subMenus = new HashMap<Integer, Menu>();
        subMenus.put(1, getEditPriceMenu());
        subMenus.put(2, getAddRemainderMenu());
        subMenus.put(3, getDecreaseRemainderMenu());
        subMenus.put(4, getEditNameMenu());
        subMenus.put(5, getEditDescriptionMenu());
        subMenus.put(6, getEditBrandMenu());
        subMenus.put(7, new LoginOrRegisterMenu(this, "Login\\Register Menu"));
    }

    private Menu getEditPriceMenu() {
        return new Menu(this, "Edit Price Menu") {
            @Override
            public void execute() throws ParseException, IOException {
                System.out.println(this.getMenuName());
                System.out.println("if you input back we will go back");
                System.out.println("insert the new price: ");
                String price;
                price = scanner.nextLine();
                if (price.equals("back")) {
                    fatherMenu.execute();
                }
                if (!price.matches("\\d+")) {
                    System.out.println("you should insert number or back");
                    this.execute();
                }
                server.clientToServer("edit product price+" + Menu.username + "+" +
                        ((ViewProductMenu) fatherMenu.getFatherMenu()).getProductID() + "+" + price);
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

    private Menu getAddRemainderMenu() {
        return new Menu(this, "Add Remainder Menu") {
            @Override
            public void execute() throws ParseException, IOException {
                System.out.println(this.getMenuName());
                System.out.println("if you input back we will go back");
                System.out.println("insert the remainder you want to add:");
                String count;
                count = scanner.nextLine();
                if (count.equals("back")) {
                    fatherMenu.execute();
                }
                if (!count.matches("\\d+")) {
                    System.out.println("you should insert number or back");
                    this.execute();
                }
                server.clientToServer("add product remainder+" + Menu.username + "+" +
                        ((ViewProductMenu) fatherMenu.getFatherMenu()).getProductID() + "+" + count);
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

    private Menu getDecreaseRemainderMenu() {
        return new Menu(this, "Decrease Remainder Menu") {
            @Override
            public void execute() throws ParseException, IOException {
                System.out.println(this.getMenuName());
                System.out.println("if you input back we will go back");
                System.out.println("insert the remainder you want to decrease:");
                String count;
                count = scanner.nextLine();
                if (count.equals("back")) {
                    fatherMenu.execute();
                }
                if (!count.matches("\\d+")) {
                    System.out.println("you should insert number or back");
                    this.execute();
                }
                server.clientToServer("decrease product remainder+" + Menu.username + "+" +
                        ((ViewProductMenu) fatherMenu.getFatherMenu()).getProductID() + "+" + count);
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

    private Menu getEditNameMenu() {
        return new Menu(this, "Edit Name Menu") {
            @Override
            public void execute() throws ParseException, IOException {
                System.out.println(this.getMenuName());
                System.out.println("if you input back we will go back");
                System.out.println("insert the new name:");
                String name;
                name = scanner.nextLine();
                if (name.equals("back")) {
                    fatherMenu.execute();
                }
                server.clientToServer("edit product+name+" + Menu.username + "+" +
                        ((ViewProductMenu) fatherMenu.getFatherMenu()).getProductID() + "+" + name);
                String serverAnswer = server.serverToClient();
                System.out.println(serverAnswer);
                if (serverAnswer.equals("request submitted")) {
                    fatherMenu.execute();
                } else {
                    this.execute();
                }
            }
        };
    }

    private Menu getEditDescriptionMenu() {
        return new Menu(this, "Edit Description Menu") {
            @Override
            public void execute() throws ParseException, IOException {
                System.out.println(this.getMenuName());
                System.out.println("if you input back we will go back");
                System.out.println("insert the new price: ");
                String description;
                description = scanner.nextLine();
                if (description.equals("back")) {
                    fatherMenu.execute();
                }
                server.clientToServer("edit product+description+" + Menu.username + "+" +
                        ((ViewProductMenu) fatherMenu.getFatherMenu()).getProductID() + "+" + description);
                String serverAnswer = server.serverToClient();
                System.out.println(serverAnswer);
                if (serverAnswer.equals("request submitted")) {
                    fatherMenu.execute();
                } else {
                    this.execute();
                }
            }
        };
    }

    private Menu getEditBrandMenu() {
        return new Menu(this, "Edit Brand Menu") {
            @Override
            public void execute() throws ParseException, IOException {
                System.out.println(this.getMenuName());
                System.out.println("if you input back we will go back");
                System.out.println("insert the new brand:");
                String brand;
                brand = scanner.nextLine();
                if (brand.equals("back")) {
                    fatherMenu.execute();
                }
                server.clientToServer("edit product+brand+" + Menu.username + "+" +
                        ((ViewProductMenu) fatherMenu.getFatherMenu()).getProductID() + "+" + brand);
                String serverAnswer = server.serverToClient();
                System.out.println(serverAnswer);
                if (serverAnswer.equals("request submitted")) {
                    fatherMenu.execute();
                } else {
                    this.execute();
                }
            }
        };
    }
}
