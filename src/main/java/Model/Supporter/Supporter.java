package Model.Supporter;

import java.util.ArrayList;
import java.util.HashMap;

public class Supporter {
    private String username;
    private String password;
    private HashMap<String, Chat> chats = new HashMap<>();
    private static ArrayList<Supporter> allSupporters = new ArrayList<>();

    public Supporter(String username, String password) {
        this.username = username;
        this.password = password;
        allSupporters.add(this);
    }

    private void newChat(String username) {
        chats.putIfAbsent(username, new Chat(this.username, username));
    }

    public void sendMessage(String username, String sender, String message) {
        newChat(username);
        chats.get(username).addMessage(sender, message);
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public HashMap<String, Chat> getChats() {
        return chats;
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
