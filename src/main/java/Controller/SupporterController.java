package Controller;

import Model.Supporter.Chat;
import Model.Supporter.Supporter;

import java.util.ArrayList;
import java.util.HashMap;

public class SupporterController {

    public static ArrayList<Supporter> getAllSupporters() {
        return Supporter.getAllSupporters();
    }

    public static HashMap<String, Chat> getSupporterChats(String supporterUsername) {
        return Supporter.getSupporterWithUsername(supporterUsername).getChats();
    }

}
