package GUI.Supporter;

import GUI.Connector;
import GUI.Media.Audio;
import GUI.MenuHandler;
import Model.Supporter.Chat;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Popup;

import java.io.IOException;
import java.util.ArrayList;

public class ChatController {
    public HBox onlineSupporterBox;
    public TextField messageBox;
    public FlowPane chatPane;
    public ScrollPane scrollPane;
    private String selectedSupporter = "";
    private Listener listener;
    private boolean firstTime = true;

    @FXML
    public void initialize() throws IOException {
        scrollPane.vvalueProperty().bind(chatPane.heightProperty());
        updateOnlineMans();
        this.listener = new Listener(this);
        listener.setDaemon(true);
        listener.start();
    }

    public void updateOnlineMans() throws IOException {
        if (MenuHandler.getUserType() == null) {
            MenuHandler.setUserType("");
        }
        if (MenuHandler.getUserType().equalsIgnoreCase("supporter")) {
            ArrayList<String> users = Chat.getSupporterChats(MenuHandler.getUsername());
            if (users.size() == 0) {
                showMessageOnChatBox("no one is on your chat list :(", "serverMessage");
            } else {
                String[] names = new String[users.size()];
                names = users.toArray(names);
                showSupportersAvatar(names);
            }
        } else {
            MenuHandler.getConnector().clientToServer("get all online supporters");
            String response = MenuHandler.getConnector().serverToClient();
            if (response.equals("no supporter is online")) {
                showMessageOnChatBox("none of our supporters are online now :(\nHey dont worry they check very soon ;)\ntry again later.",
                        "serverMessage");
            } else {
                showMessageOnChatBox("chose one of our supporter and we solve your problem immediately", "serverMessage");
                String supporterNames = response.substring("online supporters name:".length());
                showSupportersAvatar(supporterNames.split("\\n"));
            }
        }
        messageBox.setDisable(true);
        if (!selectedSupporter.equals("")) {
            updateStyles(selectedSupporter);
            updateChatBoxForSupporter(selectedSupporter);
            messageBox.setDisable(false);
        }
    }

    static class Listener extends Thread {
        ChatController chatController;

        public Listener(ChatController chatController) {
            this.chatController = chatController;
        }

        @Override
        public void run() {
            while (!Thread.interrupted()) {
                synchronized (MenuHandler.getNewMessageLock()) {
                    try {
                        MenuHandler.getNewMessageLock().wait();
                        Chat chat = Chat.getChatWith(chatController.selectedSupporter, MenuHandler.getMyChats());
                        if (chat != null) {
                            if (chat.hasUnreadMessage()) {
                                String sender = chat.getSender().get(chat.getSender().size() - 1);
                                String message = chat.getMessage().get(chat.getMessage().size() - 1);
                                Platform.runLater(() -> {
                                    try {
                                        chatController.showMessageOnChatBox(message, sender);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                });
                                chat.setMessagesRead();
                            }
                        }

                        Platform.runLater(() -> {
                            try {
                                chatController.updateOnlineMans();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        });

                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        }
    }

    private void showSupportersAvatar(String[] supporterNames) {
        onlineSupporterBox.getChildren().clear();
        for (String name : supporterNames) {
            Image img = new Image("file:src/main/java/GUI/Supporter/resources/customer-support.png");
            ImageView imageView = new ImageView(img);
            imageView.setFitWidth(75);
            imageView.setFitHeight(65);
            if (name.equalsIgnoreCase(MenuHandler.getUsername())) continue;
            imageView.setId(name);
            StackPane stackPane = new StackPane();
            stackPane.setId(name);
            stackPane.getChildren().add(imageView);
            stackPane.getStyleClass().add("persons-icons");
            onlineSupporterBox.getChildren().add(stackPane);

            stackPane.setOnMouseClicked(this::selectSupporter);
        }
    }

    public void send(MouseEvent mouseEvent) throws IOException {
        String message = messageBox.getText();
        if (!message.trim().equals("")) {
            Audio.playClick2();
            String toServer = "send message to supporter" + "\n" + MenuHandler.getUsername() + "\n" + selectedSupporter + "\n" + message;
            MenuHandler.getConnector().clientToServer(toServer);
        }
    }

    public void selectSupporter(MouseEvent mouseEvent) {
        Audio.playClick2();
        StackPane stackPane = (StackPane) mouseEvent.getSource();
        if (!selectedSupporter.equals(stackPane.getId())) {
            selectedSupporter = stackPane.getId();
            messageBox.setDisable(false);
            System.out.println("chosen person: " + selectedSupporter);
            updateStyles(stackPane.getId());
            updateChatBoxForSupporter(selectedSupporter);
        }
    }

    private void updateStyles(String name) {
        for (Node child : onlineSupporterBox.getChildren()) {
            if (child.getId().equalsIgnoreCase(name)) {
                System.out.println("change style");
                child.getStyleClass().clear();
                child.getStyleClass().add("selected-icon");
            } else {
                child.getStyleClass().clear();
                child.getStyleClass().add("persons-icons");
            }
        }
    }

    private void updateChatBoxForSupporter(String selectedSupporter) {
        try {
            chatPane.getChildren().clear();
            Chat chat = Chat.getChatWith(selectedSupporter, MenuHandler.getMyChats());
            if (chat != null) {
                ArrayList<String> senders = chat.getSender();
                ArrayList<String> messages = chat.getMessage();
                for (int i = 0; i < senders.size(); i++) {
                    String sender = senders.get(i);
                    String message = messages.get(i);

                    showMessageOnChatBox(message, sender);
                }
                chat.setMessagesRead();
            } else {
                chatPane.getChildren().clear();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //type policy: type == "from me" for my messages, type == "from other" for others messages
    public void showMessageOnChatBox(String message, String sender) throws IOException {
        String path = "/GUI/Supporter/";
        Pos pos = null;
        if (sender.equals(MenuHandler.getUsername())) {
            path = "clientMessage.fxml";
            pos = Pos.CENTER_RIGHT;
        } else {
            path = "otherMessage.fxml";
            pos = Pos.CENTER_LEFT;
        }
        Parent root = FXMLLoader.load(getClass().getResource(path));
        Label label = (Label) ((AnchorPane) root).getChildren().get(1);
        label.setWrapText(true);
        label.setText(message);

        HBox hbox = new HBox();
        hbox.setPrefHeight(Region.USE_COMPUTED_SIZE);
        hbox.setPrefWidth(390);
        hbox.getChildren().add(root);
        hbox.setAlignment(pos);

        chatPane.getChildren().add(hbox);
        messageBox.setText("");
    }

    public void closeChat(MouseEvent mouseEvent) throws IOException {
        Popup chatPopup = (Popup) ((ImageView) mouseEvent.getSource()).getScene().getWindow();
        chatPopup.hide();
        listener.interrupt();
        MenuHandler.showSupporterPopup();
    }
}
