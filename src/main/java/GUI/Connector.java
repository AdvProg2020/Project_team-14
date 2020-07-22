package GUI;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.io.*;
import java.net.Socket;

public class Connector {
    private Socket mySocket;
    private String host;
    private int port;
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;
    private String response;

    public Connector(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public DataInputStream getDataInputStream() {
        return dataInputStream;
    }

    public void run() throws IOException {
        mySocket = new Socket(host, port);
        dataInputStream = new DataInputStream(new BufferedInputStream(mySocket.getInputStream()));
        dataOutputStream = new DataOutputStream(new BufferedOutputStream(mySocket.getOutputStream()));

        Thread listener = new ListenerForServer(this);
        listener.setDaemon(true);
        listener.start();
    }

    public void clientToServer(String command) throws IOException {
        try {
            synchronized (MenuHandler.getLock()) {
                dataOutputStream.writeUTF(command);
                dataOutputStream.flush();
                MenuHandler.getLock().wait();
            }
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Connection to Server failed :(", ButtonType.OK);
            alert.showAndWait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    static class ListenerForServer extends Thread {
        Connector connector;

        public ListenerForServer(Connector connector) {
            this.connector = connector;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    String response = connector.getDataInputStream().readUTF();
                    if (response.startsWith("this is a chat message")) {
                        connector.handelChatMessage(response);
                    } else {
                        connector.setResponse(response);
                    }
                    synchronized (MenuHandler.getLock()) {
                        MenuHandler.getLock().notifyAll();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void handelChatMessage(String response) {
        String[] info = response.split("\n");
        MenuHandler.addMessage(info[1], info[2], info[3]);
    }

    private void setResponse(String response) {
        this.response = response;
    }

    public String serverToClient() {
        return response;
    }

    public void closeSocket() throws IOException {
        mySocket.close();
    }
}
