package Model.Token;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.HashMap;

public class Token {
    private HashMap<String, String> token = new HashMap<>();

    private static final SecureRandom secureRandom = new SecureRandom();
    private static final Base64.Encoder base64Encoder = Base64.getUrlEncoder();

    public static String generateNewToken() {
        byte[] randomBytes = new byte[100];
        secureRandom.nextBytes(randomBytes);
        return System.currentTimeMillis() + base64Encoder.encodeToString(randomBytes);
    }

    public boolean isTokenValid(String token) {
        return this.token.containsValue(token);
    }

    public boolean hasTokenExpired(String token) {
        long past = Long.parseLong(token.substring(0, 13));
        return System.currentTimeMillis() - past > 1000000;
    }

    public void createNewToken(String username) {
        token.put(username, generateNewToken());
    }

    public void deleteToken(String username) {
        token.remove(username);
    }

}
