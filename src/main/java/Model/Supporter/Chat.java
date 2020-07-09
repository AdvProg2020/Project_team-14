package Model.Supporter;

import java.util.ArrayList;

public class Chat {
    private String supporterUsername;
    private String userUsername;
    private ArrayList<String> sender = new ArrayList<>();
    private ArrayList<String> message = new ArrayList<>();
    private static ArrayList<Chat> allChats = new ArrayList<>();

    public Chat(String supporterUsername, String userUsername) {
        this.supporterUsername = supporterUsername;
        this.userUsername = userUsername;
        allChats.add(this);
    }

    public void addMessage(String sender, String message) {
        this.sender.add(sender);
        this.message.add(message);
    }

    public String getMessagesStringFormatted() {
        return sender.toString() + " - " + message.toString();
    }

    public String getSupporterUsername() {
        return supporterUsername;
    }

    public String getUserUsername() {
        return userUsername;
    }

    public ArrayList<Chat> getChatsOfSupporterWithUsername(String supporterUsername) {
        ArrayList<Chat> buffer = new ArrayList<>();
        for (Chat chat : allChats) {
            if (chat.supporterUsername.equals(supporterUsername)) {
                buffer.add(chat);
            }
        }
        return buffer;
    }

    public ArrayList<Chat> getChatsOfAccountWithUsername(String userUsername) {
        ArrayList<Chat> buffer = new ArrayList<>();
        for (Chat chat : allChats) {
            if (chat.userUsername.equals(userUsername)) {
                buffer.add(chat);
            }
        }
        return buffer;
    }

}
