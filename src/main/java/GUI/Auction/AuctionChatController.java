package GUI.Auction;

import GUI.MenuHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class AuctionChatController {
    public ImageView sendMessage;
    public TextField message;
    public ImageView backIcon;
    public VBox VBOX;

    public void sendMessage(MouseEvent mouseEvent) throws IOException {
        if (message.getText().equals("")) {
            return;
        }
        MenuHandler.getConnector().clientToServer("send an auction chat+" + MenuHandler.getCurrentAuction() + "+" + MenuHandler.getUsername() + "+" + message.getText());
        String serverAnswer = MenuHandler.getConnector().serverToClient();
        if (serverAnswer.equals("message sent")) {
            Label label = new Label(MenuHandler.getUsername() + ":" + message.getText());
            VBOX.getChildren().add(label);
            label.setText("");
        }
    }

    public void back(MouseEvent mouseEvent) throws IOException {
        MenuHandler.getStage().setScene(FXMLLoader.load(getClass().getResource("/GUI/Auction/AuctionBet.fxml")));
    }

    public void initialize() throws IOException {
        MenuHandler.getConnector().clientToServer("get an auction chat+" + MenuHandler.getCurrentAuction());
        String serverAnswer = MenuHandler.getConnector().serverToClient();
        for (String s : serverAnswer.split("\n")) {
            Label label = new Label(s);
            VBOX.getChildren().add(label);
        }
    }
}
