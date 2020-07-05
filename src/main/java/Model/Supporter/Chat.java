package Model.Supporter;

import java.util.ArrayList;

public class Chat {
    private String supporterUsername;
    private String userUsername;
    private ArrayList<String> messages = new ArrayList<>();

    public Chat(String supporterUsername, String userUsername) {
        this.supporterUsername = supporterUsername;
        this.userUsername = userUsername;
    }

    public void addMessage(String sender, String message) {
        messages.add(sender + " : " + message);
    }

    public ArrayList<String> getMessages() {
        return messages;
    }

    public String getSupporterUsername() {
        return supporterUsername;
    }

    public String getUserUsername() {
        return userUsername;
    }

}
