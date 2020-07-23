package GUI;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

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

    public void clientToServer(String command) {
        try {
            command = addTheSecurity(command);
            dataOutputStream.writeUTF(command);
            dataOutputStream.flush();

            this.response = dataInputStream.readUTF();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "your connection to server failed ): try again later", ButtonType.OK);
            alert.showAndWait();
            System.exit(1989);
        }
    }

    public String serverToClient() {
        if (response.equals("token has expired")) {
            logout();
        }
        return response;
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
