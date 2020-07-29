package Model.Supporter;

import Model.Storage;

import java.util.ArrayList;
import java.util.HashMap;

public class Supporter {
    private String username;
    private String password;
    private static ArrayList<Supporter> allSupporters = new ArrayList<>();
    private static ArrayList<String> onlineSupporters = new ArrayList<>();

    public Supporter(String username, String password) {
        this.username = username;
        this.password = password;
        allSupporters.add(this);
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public static ArrayList<Supporter> getAllSupporters() {
        return allSupporters;
    }

    public static void makeSupporterOnline(String username) {
        onlineSupporters.add(username);
    }

    public static void makeSupporterOffline(String username) {
        onlineSupporters.remove(username);
    }

    public static ArrayList<String> getOnlineSupporters() {
        return onlineSupporters;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public static void setAllSupporters(ArrayList<Supporter> allSupporters) {
        Supporter.allSupporters = allSupporters;
    }

    public static void setOnlineSupporters(ArrayList<String> onlineSupporters) {
        Supporter.onlineSupporters = onlineSupporters;
    }

    public static Supporter getSupporterWithUsername(String username) {
        for (Supporter supporter : allSupporters) {
            if (supporter.getUsername().equals(username)) {
                return supporter;
            }
        }
        return null;
    }

}
