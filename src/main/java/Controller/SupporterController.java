package Controller;

import Model.Supporter.Supporter;

import java.util.ArrayList;

public class SupporterController {

    public static ArrayList<Supporter> getAllSupporters() {
        return Supporter.getAllSupporters();
    }

    public static void makeNewSupporter(String username, String password) {
        new Supporter(username, password);
    }

    public static void deleteSupporter(String username) {
        Supporter.getAllSupporters().remove(Supporter.getSupporterWithUsername(username));
    }

}
