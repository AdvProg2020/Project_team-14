package Model.Supporter;

import java.util.ArrayList;

public class Supporter {
    private String username;
    private String password;
    private static ArrayList<Supporter> allSupporters = new ArrayList<>();

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

    public static Supporter getSupporterWithUsername(String username) {
        for (Supporter supporter : allSupporters) {
            if (supporter.getUsername().equals(username)) {
                return supporter;
            }
        }
        return null;
    }

}
