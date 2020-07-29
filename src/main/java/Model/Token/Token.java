package Model.Token;

import Model.Account.Account;
import Model.Storage;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;

public class Token {
    private static ArrayList<String> token = new ArrayList<>();
    private static final long ONLINE_TIME_DURATION = 50000;

    // the first one is username and the other is time sent

    private static HashMap<String, Long> onlineUsers = new HashMap<>();
    private static final SecureRandom secureRandom = new SecureRandom();
    private static final Base64.Encoder base64Encoder = Base64.getUrlEncoder();

    public static String generateNewToken(String username) {
        byte[] randomBytes = new byte[100];
        secureRandom.nextBytes(randomBytes);
        String s = System.currentTimeMillis() + "--caption neuer--" + username + "--caption neuer--" + base64Encoder.encodeToString(randomBytes);
        String token = base64Encoder.encodeToString(s.getBytes());
        token = new StringBuilder(token).reverse().toString();
        token = base64Encoder.encodeToString(token.getBytes());
        Token.token.add(token);
        return token;
    }

    public static String decode(String token) {
        token = new String(Base64.getDecoder().decode(token));
        token = new StringBuilder(token).reverse().toString();
        token = new String(Base64.getDecoder().decode(token));
        return token;
    }

    public static boolean isTokenValid(String token) {
        return Token.token.contains(token);
    }

    public static boolean hasTokenExpired(String token) {
        try {
            token = decode(token);
            long past = Long.parseLong(token.substring(0, 13));
            boolean flag = System.currentTimeMillis() - past > 1000000;
            if (flag) {
                Token.token.remove(token);
            }
            return flag;
        } catch (Exception e) {
            return false;
        }
    }

    public static void deleteToken(String username) {
        token.remove(username);
    }

    public static void addOnlineUsers(String username, Long time) {
        onlineUsers.remove(username);
        onlineUsers.put(username, time);
    }

    public static boolean isOnline(String username) {
        if (!Storage.getAccountWithUsername(username).isOnline()) {
            return false;
        }
        Long time = onlineUsers.getOrDefault(username, (long) -1);
        System.out.println(onlineUsers.getOrDefault("kos", (long) -1));
        return ((System.currentTimeMillis() - time) < ONLINE_TIME_DURATION && time != -1);
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

    public static ArrayList<String> getToken() {
        return token;
    }

    public static void setToken(ArrayList<String> token) {
        Token.token = token;
    }

    public static long getOnlineTimeDuration() {
        return ONLINE_TIME_DURATION;
    }

    public static void setOnlineUsers(HashMap<String, Long> onlineUsers) {
        Token.onlineUsers = onlineUsers;
    }

    public static SecureRandom getSecureRandom() {
        return secureRandom;
    }

    public static Base64.Encoder getBase64Encoder() {
        return base64Encoder;
    }

    public static String getUsernameFromToken(String token) {
        token = decode(token);
        return token.split("--caption neuer--")[1];
    }

}
