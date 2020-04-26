package Model.Account;

import static Model.Storage.*;

public abstract class Account {
    private boolean isOnline;
    private String username;
    private String password;
    private String firstName;
    private String secondName;
    private String Email;
    private String telephone;
    private Role role;

    public Account(String username, String password, String firstName, String secondName, String Email, String telephone, String role) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.secondName = secondName;
        this.Email = Email;
        this.telephone = telephone;
        allAccounts.add(this);
        if (role.equalsIgnoreCase("boss")) {
            this.role = Role.BOSS;
        } else if (role.equalsIgnoreCase("customer")) {
            this.role = Role.CUSTOMER;
        } else if (role.equalsIgnoreCase("salesman")) {
            this.role = Role.SALESMAN;
        }
    }

    public boolean isOnline() {
        return this.isOnline;
    }

    public void setOnline(boolean online) {
        this.isOnline = online;
    }

    public String getPassword() {
        return this.password;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getSecondName() {
        return this.secondName;
    }

    public String getUsername() {
        return this.username;
    }

    public String getEmail() {
        return this.Email;
    }

    public String getRole() {
        return this.role.name();
    }

    public String getTelephone() {
        return this.telephone;
    }

    public String toString() {
        String result = "";
        result = result + "Username: " + this.getUsername() + "\n";
        result = result + "Name: " + this.getFirstName() + " " + this.getSecondName() + "\n";
        result = result + "Email: " + this.getEmail() + "\n";
        result = result + "Telephone: " + this.getTelephone() + "\n";
        result = result + "Role: " + this.getRole() + "\n";
        return result;
    }


}
