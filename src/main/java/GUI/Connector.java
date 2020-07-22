package GUI;

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
        dataOutputStream.writeUTF(command);
        dataOutputStream.flush();

        this.response = dataInputStream.readUTF();
    }

    public String serverToClient() {
        return response;
    }
}
