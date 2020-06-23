package GUI.ManageRequests;

import GUI.MenuHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.ParseException;

public class ManageRequestsController {


    public FlowPane filterList;
    public ComboBox<String> filter;
    public ComboBox<String> sortFactor;
    public VBox requestList;
    private String requestUsername;

    ObservableList<String> list = FXCollections.observableArrayList("CHANGE_SALE", "DELETE_SALE", "ADD_NEW_SALE",
            "DELETE_PRODUCT", "ADD_NEW_PRODUCT", "CHANGE_PRODUCT", "REGISTER_SALESMAN", "COMMENT_CONFIRMATION", "ADD_TO_PRODUCT");


    //521 width

    public void initialize() throws IOException, ParseException {
        MenuHandler.getServer().clientToServer("request username show+" + MenuHandler.getUsername());
        requestUsername = MenuHandler.getServer().serverToClient();
        filter.setItems(list);
        for (String s : requestUsername.split("\\+")) {
            filter.getItems().add("Username:" + s);
        }
        MenuHandler.getServer().clientToServer("show requests+" + MenuHandler.getUsername());
        String serverAnswer = MenuHandler.getServer().serverToClient();
        for (String s : serverAnswer.split("\n")) {
            if (s.startsWith("here")) continue;
            HBox hBox = new HBox();
            hBox.setPrefWidth(490);
            Label label = new Label();
            label.setText("Request Type: " + s.split("\\s")[2] + " Username: " + s.split("\\s")[7]);
            label.setFont(Font.font(15));
            hBox.getChildren().add(label);
            MenuHandler.getServer().clientToServer("is request state checking+" + s.split("\\s")[5]);
            String respond = MenuHandler.getServer().serverToClient();
            if (respond.equalsIgnoreCase("yes")) {
                hBox.setStyle("-fx-padding: 10;" + "-fx-border-style: solid inside;"
                        + "-fx-border-width: 2;" + "-fx-border-insets: 5;"
                        + "-fx-border-radius: 5;" + "-fx-border-color: blue;");
            } else {
                hBox.setStyle("-fx-padding: 10;" + "-fx-border-style: solid inside;"
                        + "-fx-border-width: 2;" + "-fx-border-insets: 5;"
                        + "-fx-border-radius: 5;" + "-fx-border-color: red;");
            }
            hBox.setOnMouseClicked(mouseEvent -> {
                MenuHandler.setRequestID(s.split("\\s")[5]);
                Stage stage = (Stage) ((HBox) mouseEvent.getSource()).getScene().getWindow();
                Parent root = null;
                try {
                    root = FXMLLoader.load(getClass().getResource("/GUI/ManageRequests/ViewRequest.fxml"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                stage.setScene(new Scene(root));
            });
            hBox.setOnMouseEntered(mouseEvent -> {
                Stage stage = (Stage) ((HBox) mouseEvent.getSource()).getScene().getWindow();
                stage.getScene().setCursor(Cursor.HAND);
            });
            hBox.setOnMouseExited(mouseEvent -> {
                Stage stage = (Stage) ((HBox) mouseEvent.getSource()).getScene().getWindow();
                stage.getScene().setCursor(Cursor.DEFAULT);
            });
            requestList.getChildren().add(hBox);
        }
    }

    public void chooseFilter(ActionEvent actionEvent) {

    }

    public void chooseSort(ActionEvent actionEvent) {

    }
}
