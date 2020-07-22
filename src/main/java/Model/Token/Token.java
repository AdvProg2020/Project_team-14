package Model.Token;

import Model.Account.Account;
import Model.Storage;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;

public class Token {
    private ArrayList<String> token = new ArrayList<>();
    private ArrayList<String> usedTimes = new ArrayList<>();
    private static final int ONLINE_TIME_DURATION = 1000000;

    // the first one is username and the other is time sent

    private static HashMap<String, Long> onlineUsers = new HashMap<>();


    private static final SecureRandom secureRandom = new SecureRandom();
    private static final Base64.Encoder base64Encoder = Base64.getUrlEncoder();

    public static String generateNewToken() {
        byte[] randomBytes = new byte[100];
        secureRandom.nextBytes(randomBytes);
        return System.currentTimeMillis() + base64Encoder.encodeToString(randomBytes);
    }

    public boolean isTokenValid(String token) {
        return this.token.contains(token);
    }

    public boolean hasTokenExpired(String token) {
        long past = Long.parseLong(token.substring(0, 13));
        return System.currentTimeMillis() - past > 1000000;
    }

    public void createNewToken(String username) {
        token.add(generateNewToken());
    }

    public void deleteToken(String username) {
        token.remove(username);
    }

    public void addOnlineUsers(String username, Long time) {
        onlineUsers.put(username, time);
    }

    public static boolean isOnline(String username) {
        if (onlineUsers.containsKey(username)) {
            Long time = onlineUsers.get(username);
            return (System.currentTimeMillis() - time) < ONLINE_TIME_DURATION;
        }
        return false;
    }

    public static ArrayList<String> getOnlineUsers() {
        ArrayList<String> arrayList = new ArrayList<>();
        for (Account account : Storage.getAllAccounts()) {
            if (isOnline(account.getUsername())) {
                arrayList.add(account.getUsername());
            }
        }
        return arrayList;
    }

}
