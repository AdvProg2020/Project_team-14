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

    public void run() throws IOException {
        mySocket = new Socket(host, port);
        dataInputStream = new DataInputStream(new BufferedInputStream(mySocket.getInputStream()));
        dataOutputStream = new DataOutputStream(new BufferedOutputStream(mySocket.getOutputStream()));
    }

    public void clientToServer(String command) throws IOException {
        try {
            command = addTheSecurity(command);
            dataOutputStream.writeUTF(command);
            dataOutputStream.flush();

            this.response = dataInputStream.readUTF();
        } catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR, "your connection to server failed ): try again later", ButtonType.OK);
            alert.showAndWait();
            System.exit(1989);
        }
    }

    public String serverToClient() {
        return response;
    }

    public String addTheSecurity(String command) {
        String result = "this is a client" + "--1989--" + MenuHandler.getToken() + "--1989--" + command + "--1989--" + System.currentTimeMillis();
        return result;
    }
}
