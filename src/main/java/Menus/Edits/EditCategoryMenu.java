package Menus.Edits;

import Menus.Menu;
import Menus.Views.ViewCategoryMenu;
import Model.Account.Salesman;

import java.util.HashMap;

public class EditCategoryMenu extends Menu {
    public EditCategoryMenu(Menu fatherMenu, String menuName) {
        super(fatherMenu, menuName);
        this.logoutType = false;
        HashMap<Integer, Menu> subMenus = new HashMap<Integer, Menu>();
        subMenus.put(1, getEditCategoryNameMenu());
        subMenus.put(2, getEditFatherCategoryMenu());
        subMenus.put(3, getAddAttributeMenu());
        subMenus.put(4, getDeleteAttributeMenu());
        subMenus.put(5, getEditAttributeMenu());
        this.setSubMenus(subMenus);
    }

    private Menu getEditCategoryNameMenu() {
        return new Menu(this, "Edit Category Name Menu") {
            @Override
            public void execute() {
                System.out.println(this.getMenuName());
                System.out.println("if you input back we will go back");
                System.out.println("insert the new category name:");
                String categoryName;
                categoryName = scanner.nextLine();
                if (categoryName.equals("back")) {
                    fatherMenu.execute();
                }
                server.clientToServer("edit category name+" + Menu.username + "+" +
                        ((ViewCategoryMenu) fatherMenu).getCategoryName() + "+" + categoryName);
                String serverAnswer = server.serverToClient();
                System.out.println(serverAnswer);
                if (serverAnswer.equals("edit successful")) {
                    ((ViewCategoryMenu) fatherMenu.getFatherMenu()).setCategoryName(categoryName);
                    fatherMenu.execute();
                } else {
                    this.execute();
                }
            }
        };
    }

    private Menu getEditFatherCategoryMenu() {
        return new Menu(this, "Edit Father Category Menu") {

            @Override
            public void execute() {
                System.out.println(this.getMenuName());
                System.out.println("if you input back we will go back");
                System.out.println("insert the new father category name or none for making this " +
                        "category a root category:");
                String categoryName;
                categoryName = scanner.nextLine();
                if (categoryName.equals("back")) {
                    fatherMenu.execute();
                }
                server.clientToServer("edit father category+" + Menu.username + "+" +
                        ((ViewCategoryMenu) fatherMenu.getFatherMenu()).getCategoryName() + "+" + categoryName);
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

    public int countWordsBySemicolon(String str) {
        int counter = 0;
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == ',') {
                counter++;
            }
        }
        return counter + 1;
    }

    private Menu getAddAttributeMenu() {
        return new Menu(this, "Add Attribute Menu") {

            private String addAttribute(String attributes, String input) {
                if (input.equals("")) {
                    System.out.println("you didn't add anything");
                    return attributes;
                }
                if (attributes.equals("")) {
                    attributes = input;
                    return attributes;
                }
                int attributeCount = ((EditCategoryMenu) fatherMenu).countWordsBySemicolon(attributes);
                String[] attribute = attributes.split(",");
                for (int i = 0; i < attributeCount; i++) {
                    if (attribute[i].equals(input)) {
                        System.out.println("this attribute has been added before");
                        return attributes;
                    }
                }
                attributes = attributes + "," + input;
                return attributes;
            }

            @Override
            public void execute() {
                server.clientToServer("what is category attribute+" + ((ViewCategoryMenu) fatherMenu).getCategoryName());
                String currentAttribute = server.serverToClient();
                System.out.println(this.getMenuName());
                System.out.println("if you input back we will go back");
                while (true) {
                    System.out.println("this category current attributes are: " + currentAttribute);
                    System.out.println("insert the attributes you want to add to this category" +
                            " and insert done for finishing adding attributes");
                    String input = scanner.nextLine();
                    if (input.equalsIgnoreCase("done")) {
                        break;
                    }
                    currentAttribute = addAttribute(currentAttribute, input);
                }
                server.clientToServer("add category attribute+" + Menu.username + "+" +
                        ((ViewCategoryMenu) fatherMenu.getFatherMenu()).getCategoryName() + "+" + currentAttribute);
                System.out.println(server.serverToClient());
                fatherMenu.execute();
            }
        };
    }

    private Menu getDeleteAttributeMenu() {
        return new Menu(this, "Delete Attribute Menu") {

            private String deleteAttribute(String attributes, String input) {
                if (attributes.equals("")) {
                    System.out.println("there's nothing to delete");
                    return attributes;
                }
                if (!attributes.contains(",")) {
                    if (input.equals(attributes)) {
                        return "";
                    } else {
                        System.out.println("it hasn't the attribute you want to delete");
                        return attributes;
                    }
                }
                if (attributes.contains(input + ",")) {
                    int position = attributes.indexOf(input + ",");
                    return attributes.substring(0, position) + attributes.substring(position + input.length() + 1);
                } else if (attributes.contains("," + input)) {
                    int position = attributes.indexOf(("," + input));
                    return attributes.substring(0, position);
                } else {
                    System.out.println("it hasn't the attribute you want to delete");
                    return attributes;
                }
            }


            @Override
            public void execute() {
                server.clientToServer("what is category attribute+" + ((ViewCategoryMenu) fatherMenu).getCategoryName());
                String currentAttribute = server.serverToClient();
                System.out.println(this.getMenuName());
                System.out.println("if you input back we will go back");
                while (true) {
                    System.out.println("this category current attributes are: " + currentAttribute);
                    System.out.println("insert the attributes you want to delete from this category" +
                            " and insert done for finishing deleting attributes");
                    String input = scanner.nextLine();
                    if (input.equalsIgnoreCase("done")) {
                        break;
                    }
                    currentAttribute = deleteAttribute(currentAttribute, input);
                }
                server.clientToServer("delete category attribute+" + Menu.username + "+" +
                        ((ViewCategoryMenu) fatherMenu.getFatherMenu()).getCategoryName() + "+" + currentAttribute);
                System.out.println(server.serverToClient());
                fatherMenu.execute();
            }
        };
    }

    private Menu getEditAttributeMenu() {
        return new Menu(this, "Edit Attribute Menu") {

            private String editAttribute(String attributes, String input, String newInput) {
                if (attributes.equals("")) {
                    System.out.println("there's nothing to edit");
                    return attributes;
                }
                if (!attributes.contains(",")) {
                    if (attributes.equals(input)) {
                        return newInput;
                    } else {
                        System.out.println("it hasn't the attribute you want to edit");
                        return attributes;
                    }
                }
                if (attributes.contains(input + ",")) {
                    int position = attributes.indexOf(input + ",");
                    return attributes.substring(0, position) + newInput + "," + attributes.substring(position + input.length() + 1);
                } else if (attributes.contains("," + input)) {
                    int position = attributes.indexOf(("," + input));
                    return attributes.substring(0, position) + "," + newInput;
                } else {
                    System.out.println("it hasn't the attribute you want to edit");
                    return attributes;
                }
            }


            @Override
            public void execute() {
                server.clientToServer("what is category attribute+" + ((ViewCategoryMenu) fatherMenu).getCategoryName());
                String currentAttribute = server.serverToClient();
                System.out.println(this.getMenuName());
                System.out.println("if you input back we will go back");
                while (true) {
                    System.out.println("this category current attributes are: " + currentAttribute);
                    System.out.println("insert the attributes you want to change from this category" +
                            " or insert done for finishing editing attributes");
                    String input = scanner.nextLine();
                    if (input.equalsIgnoreCase("done")) {
                        break;
                    }
                    System.out.println("insert the new attribute you want to change that attribute with");
                    String newInput = scanner.nextLine();
                    currentAttribute = editAttribute(currentAttribute, input, newInput);
                }
                server.clientToServer("edit category attribute+" + Menu.username + "+" +
                        ((ViewCategoryMenu) fatherMenu.getFatherMenu()).getCategoryName() + "+" + currentAttribute);
                System.out.println(server.serverToClient());
                fatherMenu.execute();
            }
        };
    }
}
