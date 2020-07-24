package GUI.Auction;

import GUI.MenuHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;

import java.io.IOException;

public class NewAuctionController {
    public ComboBox allProducts;
    public DatePicker endDate;
    public DatePicker startDate;

    public void initialize() throws IOException {
        MenuHandler.getConnector().clientToServer("getting all products for me+" + MenuHandler.getUsername());
        String serverAnswer = MenuHandler.getConnector().serverToClient();
        for (String s : serverAnswer.split("\n")) {
            allProducts.getItems().add(s);
        }
    }

    public void back(ActionEvent actionEvent) throws IOException {
        MenuHandler.getStage().setScene(new Scene(FXMLLoader.load(getClass().getResource("/GUI/Auction/AllAuctions.fxml"))));
    }

    public void done(ActionEvent actionEvent) throws IOException {
        MenuHandler.getConnector().clientToServer("create new auction+" + MenuHandler.getUsername() + "+" + allProducts.getSelectionModel().getSelectedItem() + "+" + startDate.toString() + "+" + endDate.toString());
        String serverAnswer = MenuHandler.getConnector().serverToClient();
        if (serverAnswer.equals("successful")) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "New Auction Created Successfully", ButtonType.OK);
            alert.showAndWait();
        }
    }
}
