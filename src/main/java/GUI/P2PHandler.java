package GUI;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Base64;

public class P2PHandler {
    private ServerSocket serverSocket;
    private String host;
    private int portNumber;
    private String savePath;
    private int i = 0;

    public void run() {
        try {
            serverSocket = new ServerSocket(0);
            this.portNumber = serverSocket.getLocalPort();
            this.host = serverSocket.getInetAddress().getHostName();
            listenForReceive();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void listenForReceive() {
        Thread listener = new Thread(new Runnable() {
            @Override
            public void run() {
                while (!Thread.interrupted()) {
                    try {
                        Socket socket = serverSocket.accept();
                        System.out.println("file is receiving");
                        DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
                        String response = dataInputStream.readUTF();
                        dataInputStream.close();

                        if (response.startsWith("Error")) {
                            Platform.runLater(() -> {
                                Alert alert = new Alert(Alert.AlertType.ERROR, "something is wrong with seller server :( try again later", ButtonType.OK);
                                alert.showAndWait();
                            });
                            continue;
                        }

                        JSONObject jObject = (JSONObject) JSONValue.parse(response);
                        String fileName = jObject.get("fileName").toString();
                        String encodedData = jObject.get("fileData").toString();

                        File file = new File(savePath);
                        FileOutputStream fileOutputStream = new FileOutputStream(file);
                        byte[] data = decodeData(encodedData);
                        fileOutputStream.write(data);
                        fileOutputStream.flush();
                        fileOutputStream.close();

                        System.out.println("file received");
                        Platform.runLater(() -> {
                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "your File downloaded successfully", ButtonType.OK);
                            alert.showAndWait();
                        });
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            private byte[] decodeData(String data) {
                return Base64.getDecoder().decode(data);
            }
        });

        listener.setDaemon(true);
        listener.start();
    }

    public void send(String fileName, String host, int portNumber) {
        (new Thread(new Runnable() {
            @Override
            public void run() {
                Socket socket = null;
                DataOutputStream dataOutputStream = null;
                try {
                    System.out.println("i'm sending file");
                    socket = new Socket(host, portNumber);
                    dataOutputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));

                    String path = "src/main/resources/FilesToSell/" + fileName;
                    File file = new File(path);
                    FileInputStream fileInputStream = new FileInputStream(file);
                    byte[] data = new byte[(int) file.length()];
                    fileInputStream.read(data);
                    fileInputStream.close();

                    String encodedData = encodeData(data);
                    JSONObject jObject = new JSONObject();
                    jObject.put("fileName", fileName);
                    jObject.put("fileData", encodedData);

                    dataOutputStream.writeUTF(jObject.toJSONString());
                    dataOutputStream.flush();
                    dataOutputStream.close();
                    socket.close();
                    System.out.println("file transition done");
                } catch (IOException e) {
                    //lets assume file exist in directory
                    try {
                        dataOutputStream.writeUTF("Error in sending file");
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }

            private String encodeData(byte[] data) {
                return Base64.getEncoder().encodeToString(data);
            }
        })).start();
    }

    public int getPortNumber() {
        return portNumber;
    }

    public String getHost() {
        return host;
    }

    public void setSavePath(String savePath) {
        this.savePath = savePath;
    }
}
