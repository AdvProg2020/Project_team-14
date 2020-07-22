package Controller.Security;

import Controller.Server;
import Model.Token.Token;

import java.security.SecureRandom;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Base64;

public class Security {
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
        return Base64.getEncoder().encodeToString(s4.getBytes());
    }

    public String decode(String string) {
        String s4 = new String(Base64.getDecoder().decode(string));
        String s3 = new String(Base64.getDecoder().decode(s4));
        String s2 = new String(Base64.getDecoder().decode(s3));
        String s1 = new String(Base64.getDecoder().decode(s2));
        return new String(Base64.getDecoder().decode(s1));
    }

    public static void securityCheck(String command) throws ParseException {

        //running basic security checks

        if (checkStringLength(command) || mayContainScript(command)) {
            return;
        }

        // if it doesn't start with the specified string

        if (!command.startsWith("this is a client")) {
            return;
        }

        String token, message;
        long time_sent;

        try {
            token = command.split("--1989--")[1];
            message = command.split("--1989--")[2];
            time_sent = Long.parseLong(command.split("--1989--")[3]);
        } catch (Exception e) {
            System.out.println("we're under attackkkkkkk");
            return;
        }

        //the time is old

        if (System.currentTimeMillis() - time_sent > 100) {
            return;
        }

        //asking for not important information

        if (token.equals("no token")) {
            Server.server.takeAction(message);
            return;
        }

        //asking for important information

        if (Token.isTokenValid(token)) {
            if (!Token.hasTokenExpired(token)) {
                Server.server.takeAction(message);
            }
        }
    }

}
