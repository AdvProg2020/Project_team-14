package Controller;

import Bank.Controller;
import Bank.Controller.*;
import Model.RandomString;

import java.io.*;
import java.net.Socket;
import java.security.SecureRandom;
import java.util.Base64;

public class BankConnector {
    private static final int BANK_PORT = 1989;
    private static final SecureRandom secureRandom = new SecureRandom();
    private static final Base64.Encoder base64Encoder = Base64.getUrlEncoder();

    public static String sendToBank(String command) {
        String result = "";
        try {
            Socket socket = new Socket("localhost", BANK_PORT);
            DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            DataOutputStream dataOutputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));

            dataOutputStream.writeUTF(command);
            dataOutputStream.flush();

            result = dataInputStream.readUTF();
            socket.close();
        } catch (IOException e) {
            System.out.println("something went wrong with bank server");
            result = "something went wrong";
        }
        return result;
        /*Controller controller = new Controller();
        controller.takeAction(command);
        System.out.println("message: " + command);
        System.out.println("result: " + controller.getServerAnswer());
        return controller.getServerAnswer();*/
    }

    private static String requestEncoder(String command) {
        command = RandomString.getRandomString(1989) + command;
        command = base64Encoder.encodeToString(command.getBytes());
        command = new StringBuilder(command).reverse().toString();
        return command;
    }

    private static String requestDecoder(String command) {
        command = new StringBuilder(command).reverse().toString();
        command = new String(Base64.getDecoder().decode(command));
        command = command.substring(1989);
        return command;
    }

    private static void messageDecoder(String command) {

    }

}
