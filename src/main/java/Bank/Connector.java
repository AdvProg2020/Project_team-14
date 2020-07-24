package Bank;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Connector {
    private static final int PORT_NUMBER = 1989;
    private Controller controller;

    public Connector() {
        controller = new Controller();
    }

    public static void main(String[] args) throws IOException {
        (new Connector()).run();
    }

    public void run() throws IOException {
        ServerSocket serverSocket = new ServerSocket(PORT_NUMBER);
        System.out.println("BANK server started successfully...");
        while (true) {
            System.out.println("listening for clients ....");
            Socket clientSocket = serverSocket.accept();
            DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(clientSocket.getInputStream()));
            DataOutputStream dataOutputStream = new DataOutputStream(new BufferedOutputStream(clientSocket.getOutputStream()));

            (new ClientHandler(controller, clientSocket, dataInputStream, dataOutputStream)).start();
        }
    }

    static class ClientHandler extends Thread {
        final Controller controller;
        Socket socket;
        DataInputStream dataInputStream;
        DataOutputStream dataOutputStream;

        public ClientHandler(Controller controller, Socket socket, DataInputStream dataInputStream, DataOutputStream dataOutputStream) {
            this.controller = controller;
            this.socket = socket;
            this.dataInputStream = dataInputStream;
            this.dataOutputStream = dataOutputStream;
        }

        @Override
        public void run() {
            while (!Thread.interrupted()) {
                try {
                    String command = dataInputStream.readUTF();
                    System.out.println("FROM CLIENT: " + command);
                    String respond = "";
                    synchronized (controller) {
                        controller.takeAction(command);
                        respond = controller.getServerAnswer();
                        System.out.println("FROM SERVER: " + respond);
                    }
                    dataOutputStream.writeUTF(respond);
                    dataOutputStream.flush();
                } catch (IOException e) {
                    System.out.println("client closed its socket ):");
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    private void requestDecoder(){

    }

    private void messageEncoder(){

    }
}
