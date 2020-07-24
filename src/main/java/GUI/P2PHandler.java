package GUI;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class P2PHandler {
    private ServerSocket serverSocket;
    private int portNumber;
    private final int MAX_LENGTH = 1024;
    private int i = 0;

    public void run() {
        try {
            serverSocket = new ServerSocket(0);
            this.portNumber = serverSocket.getLocalPort();
            listenForReceive();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void listenForReceive() {
        (new Thread(() -> {
            while (true) {
                try {
                    Socket socket = serverSocket.accept();
                    DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
                    byte[] fileByte = new byte[MAX_LENGTH];
                    dataInputStream.read(fileByte, 0, MAX_LENGTH);
                    String path = "file:src/main/resources/DownloadedFiles/file" + i + ".txt";
                    File file = new File(path);
                    FileOutputStream fileOutputStream = new FileOutputStream(file);
                    fileOutputStream.write(fileByte, 0, fileByte.length);
                    fileOutputStream.flush();
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        })).start();
    }

    public void send(String fileName, String host, int portNumber) {
        String path = "file:src/main/resources/FilesToSell/" + fileName;
        File file = new File(path);
        (new Thread(() -> {
            try {
                FileInputStream fileInputStream = new FileInputStream(file);
                byte[] fileByte = new byte[MAX_LENGTH];
                fileInputStream.read(fileByte, 0, MAX_LENGTH);

                Socket socket = new Socket(host, portNumber);
                DataOutputStream dataOutputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
                dataOutputStream.write(fileByte, 0, fileByte.length);
                dataOutputStream.flush();
                dataOutputStream.close();
            } catch (IOException e) {
                //lets assume file exist in directory
            }
        })).start();
    }
}
