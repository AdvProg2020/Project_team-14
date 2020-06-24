package GUI.BossProfile.ManageRequests;

import GUI.MenuHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
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
            "DELETE_PRODUCT", "ADD_NEW_PRODUCT", "CHANGE_PRODUCT", "REGISTER_SALESMAN", "COMMENT_CONFIRMATION",
            "ADD_TO_PRODUCT", "State:ACCEPTED", "State:DENIED", "State:CHECKING");


    public void initialize() throws IOException, ParseException {
        MenuHandler.getServer().clientToServer("request username show+" + MenuHandler.getUsername());
        requestUsername = MenuHandler.getServer().serverToClient();
        filter.setItems(list);
        for (String s : requestUsername.split("\\+")) {
            filter.getItems().add("Username:" + s);
        }
        updateList();
    }

    private void updateList() throws ParseException, IOException {
        String username = new String("");
        String type = new String("");
        String state = new String("");
        for (Object object : filterList.getChildren()) {
            if (object instanceof HBox) {
                Object object1 = ((HBox) object).getChildren().get(0);
                if (object1 instanceof HBox) {
                    Object object2 = (((HBox) object1).getChildren().get(0));
                    if (object2 instanceof Label) {
                        String s = ((Label) object2).getText();
                        if (s.startsWith("Username:")) {
                            if (username.equals("")) {
                                username = s.substring(9);
                            } else {
                                username += "," + s.substring(9);
                            }
                        } else if (s.startsWith("State:")) {
                            if (state.equals("")) {
                                state = s.substring(6);
                            } else {
                                state += "," + s.substring(6);
                            }
                        } else {
                            if (type.equals("")) {
                                type = s;
                            } else {
                                type += "," + s;
                            }
                        }
                    }
                }
            }
        }
        String command = "show requests+" + MenuHandler.getUsername();
        if (state.equals("") && username.equals("") && type.equals("")) {

        } else {
            command += "+filters:";
            if (!state.equals("")) {
                command += "+state";
                command += "+" + state;
            }
            if (!username.equals("")) {
                command += "+username";
                command += "+" + username;
            }
            if (!type.equals("")) {
                command += "+requestType";
                command += "+" + type;
            }
        }
        for (int i = 1; i <= requestList.getChildren().size(); i++) {
            Object object = requestList.getChildren().remove(0);
        }
        MenuHandler.getServer().clientToServer(command);
        String serverAnswer = MenuHandler.getServer().serverToClient();
        if (serverAnswer.equals("nothing found")) {
            return;
        }
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
                    root = FXMLLoader.load(getClass().getResource("/GUI/BossProfile/ManageRequests/ViewRequest.fxml"));
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

    public void chooseFilter(ActionEvent actionEvent) throws IOException, ParseException {
        String s = filter.getValue();
        try {
            checkExistenceOfFilter(s);
        } catch (Exception e) {
            return;
        }
        /*for (Object object : filterList.getChildren()) {
            if (object instanceof HBox) {
                Object object1 = ((HBox) object).getChildren().get(0);
                if (object1 instanceof HBox) {
                    Object object2 = ((HBox) object1).getChildren().get(0);
                    if (object2 instanceof Label) {
                        if (((Label) object2).getText().equals(s)) return;
                    }
                }
            }
        }*/
//        HBox hBox = new HBox();
//        hBox.getChildren().add(FXMLLoader.load(getClass().getResource("/GUI/MainTheme/ChosenItemLayout.fxml")));
        Parent item = FXMLLoader.load(getClass().getResource("/GUI/MainTheme/chosenItemLayout.fxml"));
        Label label = (Label) ((HBox) item).getChildren().get(0);
        label.setText(s);
        Button button = (Button) ((HBox) item).getChildren().get(1);
        button.setOnAction(actionEvent2 -> {
            filterList.getChildren().remove(item);
            try {
                updateList();
            } catch (ParseException | IOException e) {
                e.printStackTrace();
            }
        });
        filterList.getChildren().add(item);
        updateList();
    }

    private void checkExistenceOfFilter(String filterFactor) throws Exception {
        for (Node child : filterList.getChildren()) {
            Label prevFactor = (Label) ((HBox) child).getChildren().get(0);
            if (prevFactor.getText().equals(filterFactor)) throw new Exception("filter exist");
        }
    }

    public void chooseSort(ActionEvent actionEvent) {

    }
}
