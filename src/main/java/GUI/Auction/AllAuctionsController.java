package GUI.Auction;

import GUI.MenuHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class AllAuctionsController {
    public VBox vBox;
    public Button newButton;

    public void newAuction(ActionEvent actionEvent) throws IOException {
        MenuHandler.getStage().setScene(new Scene(FXMLLoader.load(getClass().getResource("/GUI/Auction/NewAction.fxml"))));
    }

    public void back(ActionEvent actionEvent) throws IOException {
        if (MenuHandler.getUserType().equalsIgnoreCase("Salesman")) {
            MenuHandler.getStage().setScene(new Scene(FXMLLoader.load(getClass().getResource("/GUI/SalesmanProfile/SalesmanProfileLayout.fxml"))));
        } else {
            MenuHandler.getStage().setScene(new Scene(FXMLLoader.load(getClass().getResource("/GUI/CustomerProfile/CustomerProfileLayout.fxml"))));
        }
    }

    public void initialize() throws IOException {
        if (MenuHandler.getUserType().equalsIgnoreCase("Customer")) {
            newButton.setDisable(true);
        }
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
                hBox.setStyle("-fx-padding: 10;" + "-fx-border-style: solid inside;"
                        + "-fx-border-width: 2;" + "-fx-border-insets: 5;"
                        + "-fx-border-radius: 5;" + "-fx-border-color: red;");
                hBox.setPrefWidth(419);
                hBox.setOnMouseEntered(mouseEvent -> {
                    Stage stage = (Stage) ((HBox) mouseEvent.getSource()).getScene().getWindow();
                    stage.getScene().setCursor(Cursor.HAND);
                });
                hBox.setOnMouseExited(mouseEvent -> {
                    Stage stage = (Stage) ((HBox) mouseEvent.getSource()).getScene().getWindow();
                    stage.getScene().setCursor(Cursor.DEFAULT);
                });
                vBox.getChildren().add(hBox);
                hBox.setOnMouseClicked(mouseEvent -> {
                    MenuHandler.setCurrentAuction(((Label) hBox.getChildren().get(0)).getText());
                    try {
                        MenuHandler.getStage().setScene(new Scene(FXMLLoader.load(getClass().getResource("/GUI/Auction/AuctionBet.fxml"))));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            }
        }
    }
}
