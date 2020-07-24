package GUI.Auction;

import GUI.MenuHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class AllAuctionsController {
    public VBox vBox;
    public Button newButton;

    public void newAuction(ActionEvent actionEvent) throws IOException {
        MenuHandler.getStage().setScene(new Scene(FXMLLoader.load(getClass().getResource("/GUI/Auction/Auction.fxml"))));
    }

    public void back(ActionEvent actionEvent) throws IOException {
        if (MenuHandler.getUserType().equalsIgnoreCase("Salesman")) {
            MenuHandler.getStage().setScene(new Scene(FXMLLoader.load(getClass().getResource("/GUI/SalesmanProfile/SalesmanProfileLayout.fxml"))));
        } else {
            MenuHandler.getStage().setScene(new Scene(FXMLLoader.load(getClass().getResource("/GUI/CustomerProfile/CustomerProfileLayout.fxml"))));
        }
    }

    public void initialize() throws IOException {
        MenuHandler.getConnector().clientToServer("show auctions");
        String serverAnswer = MenuHandler.getConnector().serverToClient();
        if (serverAnswer.equals("nothing found")) {
            return;
        } else {
            for (String s : serverAnswer.split("\n")) {
                HBox hBox = new HBox();
                Label auctionId = new Label(s.split("\\+")[0]);
                Label productName = new Label(s.split("\\+")[1]);
                hBox.getChildren().add(auctionId);
                hBox.getChildren().add(productName);
                vBox.getChildren().add(hBox);
                hBox.setOnMouseClicked(mouseEvent -> {
                    MenuHandler.setCurrentAuction(((Label) hBox.getChildren().get(0)).getText());
                    try {
                        MenuHandler.getStage().setScene(new Scene(FXMLLoader.load(getClass().getResource("/GUI/Auction/AuctionBet"))));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            }
        }
    }
}
