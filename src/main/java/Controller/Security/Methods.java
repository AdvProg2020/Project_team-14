package Controller.Security;

import java.security.SecureRandom;
import java.util.Base64;

public class Methods {
    public static boolean checkStringLength(String command) {
        return command.length() >= 10000;
    }

    public static boolean mayContainScript(String command) {
        return command.contains("<") || command.contains(">") || command.contains("\\") || command.contains("/");
    }

    public String encode(String string) {
        String s1 = Base64.getEncoder().encodeToString(string.getBytes());
        String s2 = Base64.getEncoder().encodeToString(s1.getBytes());
        String s3 = Base64.getEncoder().encodeToString(s2.getBytes());
        String s4 = Base64.getEncoder().encodeToString(s3.getBytes());
        String result = Base64.getEncoder().encodeToString(s4.getBytes()).replace("a", ",,,,,");
        result = result.replace("b", "]()[[[");
        result = result.replace("l", "_________");
        return result;
    }

    public String decode(String string) {
        String s4 = new String(Base64.getDecoder().decode(string));
        String s3 = new String(Base64.getDecoder().decode(s4));
        String s2 = new String(Base64.getDecoder().decode(s3));
        String s1 = new String(Base64.getDecoder().decode(s2));
        String result = new String(Base64.getDecoder().decode(s1));
        result = result.replace(",,,,,", "a");
        result = result.replace("]()[[[", "b");
        return result;
    }


}
