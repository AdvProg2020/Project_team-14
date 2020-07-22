package Controller;

import Model.Supporter.Supporter;

import java.net.Socket;
import java.util.ArrayList;

public class SupporterManager {

    public static ArrayList<Supporter> getAllSupporters() {
        return Supporter.getAllSupporters();
    }

    public static void makeNewSupporter(String username, String password) {
        new Supporter(username, password);
    }

    public static void deleteSupporter(String username) {
        Supporter.getAllSupporters().remove(Supporter.getSupporterWithUsername(username));
    }

    public void joinChat(String username, Socket socket) {

    }

    public void sendMessage(String sender, String receiver, String content) {

    }
}
