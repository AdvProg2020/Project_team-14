package Menus.Sorts;

import Menus.LoginOrRegisterMenu;
import Menus.Menu;

import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;

public class SortsMenu extends Menu {
    protected String sortFactor = "none";
    protected String sortType = "none";

    public SortsMenu(Menu fatherMenu, String menuName) {
        super(fatherMenu, menuName);
        this.logoutType = false;
    }

    public String getSortFactor() {
        return sortFactor;
    }

    public void setSortFactor(String sortFactor) {
        this.sortFactor = sortFactor;
    }

    public String getSortType() {
        return sortType;
    }

    public void setSortType(String sortType) {
        this.sortType = sortType;
    }

    public String getSort() {
        if (this.getSortType().equals("none") || this.getSortFactor().equals("none")) {
            return "";
        } else {
            return "+sort:+" + this.getSortFactor() + "+" + this.getSortType();
        }
    }

    public void resetSorts() {
        this.setSortFactor("none");
        this.setSortType("none");
    }

    protected String showCurrentTypeOfFactor(String sortFactor) {
        if (this.sortFactor.equalsIgnoreCase(sortFactor)) return this.sortType;
        return "none";
    }

    protected Menu getSubMenuSelectFactor(Menu fatherMenu, String wantedSortFactor) {
        return new SortsMenu(fatherMenu, "Select " + wantedSortFactor + " as a Sort Factor") {
            private String sortFactor = wantedSortFactor;

            @Override
            public void execute() throws ParseException, IOException {
                System.out.println(menuName);
                System.out.println("your current sort type is: " + ((SortsMenu) fatherMenu).showCurrentTypeOfFactor(sortFactor));
                ((SortsMenu) fatherMenu).getTypeFromUser(fatherMenu, sortFactor);
                System.out.println(((SortsMenu) fatherMenu).getSortType() + " " + ((SortsMenu) fatherMenu).getSortFactor());
                fatherMenu.execute();
            }
        };
    }

    protected void getTypeFromUser(Menu fatherMenu, String sortFactor) {
        System.out.println("please insert your new Sort Type (ASCENDING\\DESCENDING) or NONE to cancel sorting or BACK to get back");
        //get info from user
        while (true) {
            String sortType = scanner.nextLine();
            if (sortType.equalsIgnoreCase("ascending") || sortType.equalsIgnoreCase("descending") ||
                    sortType.equalsIgnoreCase("none")) {
                ((SortsMenu) fatherMenu).setSortType(sortType.toLowerCase());
                ((SortsMenu) fatherMenu).setSortFactor(sortFactor.toLowerCase());
                break;
            } else if (sortType.equalsIgnoreCase("back")) {
                break;
            } else {
                System.err.println("wrong input");
            }
        }
    }

    protected Menu getResetSort(Menu fatherMenu) {
        return new Menu(fatherMenu, "Reset Sort Factor") {

            @Override
            public void execute() throws ParseException, IOException {
                System.out.println("Are you sure ? (Y/N)");
                while (true) {
                    String input = scanner.nextLine();
                    if (input.equalsIgnoreCase("y")) {
                        ((SortsMenu) fatherMenu).resetSorts();
                        break;
                    } else if (input.equalsIgnoreCase("n")) {
                        break;
                    } else {
                        System.err.println("invalid input!! (Y : yes, N: no");
                    }
                }
                fatherMenu.execute();
            }
        };
    }

    @Override
    protected void show() throws ParseException, IOException {
        super.show();
        if (this.getSortType().equals("none") || this.getSortFactor().equals("none")) {
            System.out.println("you don't have a sort factor right now");
        } else {
            System.out.println("your current sort factor is " + this.getSortFactor() + " and it is in " +
                    this.getSortType() + " order");
        }
    }
}
