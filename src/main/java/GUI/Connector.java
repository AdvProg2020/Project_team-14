package GUI;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
                command = addTheSecurity(command);
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
            while (!Thread.interrupted()) {
                try {
                    String response = connector.getDataInputStream().readUTF();
                    if (response.startsWith("this is a chat message")) {
                        connector.handleChatMessage(response);
                    } else if (response.startsWith("download file")) {
                        connector.handleDownloadFile(response);
                    } else {
                        connector.setResponse(response);
                    }
                    synchronized (MenuHandler.getLock()) {
                        MenuHandler.getLock().notifyAll();
                    }
                } catch (IOException e) {
                    Platform.runLater(() -> {
                        Alert alert = new Alert(Alert.AlertType.ERROR, "Connection to server lost :(", ButtonType.OK);
                        alert.showAndWait();
                    });
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    public void handleDownloadFile(String response) {
        String[] info = response.split("\\+");
        String fileName = info[1];
        String seller = info[2];
        String destinationHost = info[3];
        String destinationPort = info[4];
        String fileAddr = info[5];
        MenuHandler.sendFile(seller, fileName, destinationHost, destinationPort, fileAddr);
    }

    public void handleChatMessage(String response) {
        String[] info = response.split("\n");
        MenuHandler.addMessage(info[1], info[2], info[3]);
    }

    private void setResponse(String response) {
        this.response = response;
    }

    public String serverToClient() {
        if (response.equals("token has expired")) {
            logout();
        }
        return response;
    }

    public void closeSocket() throws IOException {
        mySocket.close();
    }

    public String addTheSecurity(String command) {
        String result = "this is a client" + "--1989--" + MenuHandler.getToken() + "--1989--" +
                command + "--1989--" + System.currentTimeMillis() + "--1989--" +
                ((MenuHandler.getUsername() == null) ? "no username" : MenuHandler.getUsername());
        return result;
    }

    public void logout() {
        Alert alert = new Alert(Alert.AlertType.ERROR, "you token has expired, login again!", ButtonType.OK);
        alert.showAndWait();
        MenuHandler.setToken("no token");
        MenuHandler.setUsername(null);
        MenuHandler.setUserType(null);
        MenuHandler.setIsUserLogin(false);
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/GUI/ProductScene/ProductScene.fxml"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        MenuHandler.getStage().setScene(new Scene(root));
    }

}
