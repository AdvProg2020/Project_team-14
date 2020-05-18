package Menus;

import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;

public class LoginOrRegisterMenu extends Menu {
    public LoginOrRegisterMenu(Menu fatherMenu, String menuName) {
        super(fatherMenu, menuName);
        this.logoutType = true;
        HashMap<Integer, Menu> subMenus = new HashMap<Integer, Menu>();
        subMenus.put(1, getRegisterMenu());
        subMenus.put(2, getLoginMenu());
        subMenus.put(3, getForgotPasswordMenu());
        this.setSubMenus(subMenus);
    }

    @Override
    public String getMenuName() {
        if (Menu.isUserLogin) {
            return "Logout";
        } else {
            return "register\\Login";
        }
    }

    private void Logout() throws ParseException, IOException {
        server.clientToServer("logout+" + Menu.username);
        String answer = server.serverToClient();
        System.out.println(answer);
        if (answer.equals("logout successful")) {
            Menu.setIsUserLogin(false);
        }
        fatherMenu.execute();
    }

    @Override
    public void execute() throws ParseException, IOException {
        if (Menu.isUserLogin) {
            Logout();
            return;
        }
        super.execute();
    }

    private Menu getRegisterMenu() {
        return new Menu(this, "Register Menu") {

            /*private void checkBack(String input) {
                if (input.equals("back")) {
                    fatherMenu.execute();
                }
            }*/

            @Override
            public void execute() throws ParseException, IOException {
                System.out.println("register Menu:");
                System.out.println("if you input back we will go back");
                String message;
                String firstName, lastName;
                String username, password, role;
                String Email, telephone;
                String company;
                System.out.println("please enter your first and last name in separate lines:");
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
                message = "register+" + firstName + "+" + lastName + "+" + username + "+" + password + "+" + role + "+"
                        + Email + "+" + telephone;
                if (role.equalsIgnoreCase("salesman")) {
                    System.out.println("please enter your company:");
                    company = scanner.nextLine();
                    checkBack(company);
                    message += "+" + company;
                }
                server.clientToServer(message);
                String serverAnswer;
                serverAnswer = server.serverToClient();
                System.out.println(serverAnswer);
                if (serverAnswer.equals("register successful")) {
                    fatherMenu.execute();
                } else {
                    this.execute();
                }
            }
        };
    }

    private Menu getLoginMenu() {
        return new Menu(this, "Login Menu") {
            @Override
            public void execute() throws ParseException, IOException {
                System.out.println("login menu:");
                System.out.println("if you input back we will go back");
                String username, password;
                String serverAnswer;
                System.out.println("Please Enter Your Username:");
                username = scanner.nextLine();
                if (username.equals("back")) {
                    fatherMenu.execute();
                }
                System.out.println("Please Enter Your Password");
                password = scanner.nextLine();
                if (password.equals("back")) {
                    fatherMenu.execute();
                }
                server.clientToServer("login+" + username + "+" + password);
                serverAnswer = server.serverToClient();
                System.out.println(serverAnswer);
                if (serverAnswer.startsWith("login successful ")) {
                    Menu.setIsUserLogin(true);
                    if (serverAnswer.contains("BOSS")) {
                        Menu.setUserType("BOSS");
                    } else if (serverAnswer.contains("CUSTOMER")) {
                        Menu.setUserType("CUSTOMER");
                    } else {
                        Menu.setUserType("SALESMAN");
                    }
                    Menu.username = serverAnswer.split("\\s")[4];
                    fatherMenu.fatherMenu.execute();
                } else {
                    this.execute();
                }
            }
        };
    }

    private Menu getForgotPasswordMenu() {
        return new Menu(this, "Forgot Password Menu") {
            @Override
            public void execute() throws ParseException, IOException {
                System.out.println("forgot password menu:");
                System.out.println("if you input back we will go back");
                System.out.println("please input your username:");
                String username;
                username = scanner.nextLine();
                if (username.equals("back")) {
                    fatherMenu.execute();
                }
                server.clientToServer("forgot password+" + username);
                String answer = server.serverToClient();
                System.out.println(answer);
                if (answer.contains("here is your password: ")) {
                    fatherMenu.execute();
                } else {
                    this.execute();
                }
            }
        };
    }
}
