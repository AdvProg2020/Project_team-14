package Model.Account;

import Model.RandomString;
import Model.Storage;

import java.io.Serializable;

import static Model.Storage.*;

public abstract class Account implements Serializable {
    private boolean isOnline;
    private String username;
    private String password;
    private String firstName;
    private String secondName;
    private String Email;
    private String telephone;
    private Role role;
    private String imgPath;

    public void setRole(Role role) {
        this.role = role;
    }

    private String avatar;
    private String ip = null;

    public Account(String username, String password, String firstName, String secondName, String Email, String telephone, String role) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.secondName = secondName;
        this.Email = Email;
        this.telephone = telephone;
        Storage.getAllAccounts().add(this);
        if (role.equalsIgnoreCase("boss")) {
            this.role = Role.BOSS;
        } else if (role.equalsIgnoreCase("customer")) {
            this.role = Role.CUSTOMER;
        } else if (role.equalsIgnoreCase("salesman")) {
            this.role = Role.SALESMAN;
        }
        isOnline = false;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getImgPath() {
        return imgPath;
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

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return this.secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return this.Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public Role getRole() {
        return this.role;
    }

    public String getTelephone() {
        return this.telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public abstract int getCredit();

    public String toStringForBoss() {
        return "Username: " + this.getUsername() + " Role: " + this.getRole() + " Name: " + this.getFirstName() + " " + this.getSecondName();
    }

    public String toString() {
        String result = "";
        result = result + "Username: " + this.getUsername() + "\n";
        result = result + "Password: " + this.getPassword() + "\n";
        result = result + "Name: " + this.getFirstName() + " " + this.getSecondName() + "\n";
        result = result + "Email: " + this.getEmail() + "\n";
        result = result + "Telephone: " + this.getTelephone() + "\n";
        result = result + "Role: " + this.getRole() + "\n";
        return result;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
