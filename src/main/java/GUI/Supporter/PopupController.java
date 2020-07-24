package GUI.Supporter;

import GUI.MenuHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import javafx.stage.Stage;

import java.io.IOException;

public class PopupController {

    public void showChat(MouseEvent mouseEvent) throws IOException {
        Stage stage = MenuHandler.getStage();
        VBox chat = FXMLLoader.load(getClass().getResource("/GUI/Supporter/SupporterChatLayout.fxml"));
        MenuHandler.hideSupporterPopup();

        Popup popup = new Popup();
        popup.getContent().add(chat);
        double x = stage.getX() + stage.getWidth() - chat.getPrefWidth() - 15;
        double y = stage.getY() + stage.getHeight() - chat.getPrefHeight() - 15;
        popup.setAnchorX(x);
        popup.setAnchorY(y);
        popup.show(stage);
    }
}
