package Controller.Security;

import Controller.Server;
import Model.Account.Account;
import Model.Storage;
import Model.Token.Token;

import java.net.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;

public class Security {

    public static boolean checkStringLength(String command) {
        try {
            command.charAt(10000);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static HashMap<String, Integer> IP_Counter = new HashMap<>();

    private static ArrayList<String> blackListOfIPs = new ArrayList<>();

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

    public static void securityCheck(String command, Socket socket) throws ParseException {

        if (blackListOfIPs.contains(getIP(socket))) {
            return;
        }

        //running basic security checks

        if (checkStringLength(command) || mayContainScript(command)) {
            blackListOfIPs.add(getIP(socket));
            return;
        }

        // if it doesn't start with the specified string

        if (!command.startsWith("this is a client")) {
            blackListOfIPs.add(getIP(socket));
            return;
        }

        String token, message, username;
        long time_sent;

        try {
            token = command.split("--1989--")[1];
            message = command.split("--1989--")[2];
            time_sent = Long.parseLong(command.split("--1989--")[3]);
            username = command.split("--1989--")[4];
        } catch (Exception e) {
            System.out.println("we're under attackkkkkkk");
            blackListOfIPs.add(getIP(socket));
            return;
        }

        //the time is old

        if (System.currentTimeMillis() - time_sent > 100) {
            blackListOfIPs.add(getIP(socket));
            return;
        }

        //asking for not important information

        if (token.equals("no token")) {
            if (username.equals("no username")) {
                Server.server.takeAction(message);
                return;
            } else {
                blackListOfIPs.add(getIP(socket));
                return;
            }
        }

        //asking for important information


        if (Token.isTokenValid(token)) {

            try {
                String decodedToken = Token.decode(token);
                long time = Long.parseLong(decodedToken.split("--caption neuer--")[0]);
                Account account = Storage.getAccountWithUsername(decodedToken.split("--caption neuer--")[1]);
                if (!Token.getUsernameFromToken(token).equals(account.getUsername())) {
                    throw new Exception("piss off");
                }
            } catch (Exception e) {
                System.out.println("we're under attack by trying wring tokens");
                blackListOfIPs.add(getIP(socket));
                return;
            }

            //checking that it's still authentic

            if (!Token.hasTokenExpired(token)) {
                Server.server.takeAction(message);
                Token.addOnlineUsers(Token.getUsernameFromToken(token), System.currentTimeMillis());
            } else {
                Server.server.takeAction("token has expired");
                Token.addOnlineUsers(Token.getUsernameFromToken(token), (long) -1);
            }

        }
    }

    public static String getIP(Socket socket) {
        SocketAddress socketAddress = socket.getRemoteSocketAddress();
        if (socketAddress instanceof InetSocketAddress) {
            InetAddress inetAddress = ((InetSocketAddress) socketAddress).getAddress();
            if (inetAddress instanceof Inet4Address)
                return inetAddress.getHostAddress();
            else if (inetAddress instanceof Inet6Address)
                return inetAddress.getHostAddress();
            else
                return "not an ip";
        } else {
            return null;
        }
    }

    public static boolean isInBlackList(Socket socket) {
        return blackListOfIPs.contains(getIP(socket));
    }

}
