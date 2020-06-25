package GUI.BossProfile.ManageRequests;

import GUI.MenuHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.ParseException;

public class ViewRequestController {
    public TextField requestTypeField;
    public TextArea requestContent;
    public Button declineButton;
    public Button acceptButton;
    public Button viewUsernameButton;
    public Button viewOtherButton;
    private String requestID;

    public void initialize() throws ParseException, IOException {
        requestContent.setDisable(false);
        requestContent.setEditable(false);
        MenuHandler.getServer().clientToServer("view request+" + MenuHandler.getUsername() + "+" + MenuHandler.getRequestID());
        String serverAnswer = MenuHandler.getServer().serverToClient();
        MenuHandler.getServer().clientToServer("is request state checking+" + MenuHandler.getRequestID());
        if (!MenuHandler.getServer().serverToClient().equalsIgnoreCase("yes")) {
            declineButton.setVisible(false);
            acceptButton.setText("Delete");
        }
        System.out.println(serverAnswer);
        requestTypeField.setText(serverAnswer.split("\\s")[1]);
        requestID = MenuHandler.getRequestID();
        if (serverAnswer.split("\\s")[1].equals("REGISTER_SALESMAN")) {
            String ans = serverAnswer.split("\n")[1] + "\n";
            ans += serverAnswer.split("\n")[2] + "\n";
            ans += serverAnswer.split("\n")[3].split("\\s")[0] + " " + serverAnswer.split("\n")[3].split("\\s")[1] + " " +
                    serverAnswer.split("\n")[3].split("\\s")[2] + "\n";
            ans += serverAnswer.split("\n")[3].split("\\s")[3] + " " + serverAnswer.split("\n")[3].split("\\s")[4] + " " +
                    serverAnswer.split("\n")[3].split("\\s")[5] + "\n";
            ans += serverAnswer.split("\n")[3].split("\\s")[6] + " " + serverAnswer.split("\n")[3].split("\\s")[7] + "\n";

            ans += serverAnswer.split("\n")[3].split("\\s")[8] + " " + serverAnswer.split("\n")[3].split("\\s")[9] + "\n";
            ans += "Request ID: " + requestID;
            requestContent.setText(ans);
        } else if (serverAnswer.split("\\s")[1].equals("ADD_NEW_PRODUCT")) {
            viewOtherButton.setText("View Product");
            viewOtherButton.setVisible(true);
            String ans = serverAnswer.split("\n")[1] + "\n";
            ans += serverAnswer.split("\n")[2] + "\n";
            ans += serverAnswer.split("\n")[3].split("\\s")[0] + " " + serverAnswer.split("\n")[3].
                    split("\\s")[1] + " " + serverAnswer.split("\n")[3].split("\\s")[2] + "\n";
            ans += serverAnswer.split("\n")[3].split("\\s")[3] + " " + serverAnswer.split("\n")[3].split("\\s")[4] + " " +
                    serverAnswer.split("\n")[3].split("\\s")[5] + "\n";
            ans += serverAnswer.split("\n")[3].split("\\s")[6] + " " + serverAnswer.split("\n")[3].split("\\s")[7] + "\n";
            ans += serverAnswer.split("\n")[3].split("\\s")[8] + " " + serverAnswer.split("\n")[3].split("\\s")[9] + "\n";
            ans += serverAnswer.split("\n")[4] + "\n";
            ans += serverAnswer.split("\n")[5].split("\\s")[0] + " " + serverAnswer.split("\n")[5].split("\\s")[1] + " " +
                    serverAnswer.split("\n")[5].split("\\s")[2] + "\n";
            ans += "Product " + serverAnswer.split("\n")[5].split("\\s")[3] + " " + serverAnswer.split("\n")[5].split("\\s")[4] + "\n";
            ans += "Product " + serverAnswer.split("\n")[5].split("\\s")[5] + " " + serverAnswer.split("\n")[5].split("\\s")[6] + "\n";
            ans += serverAnswer.split("\n")[5].split("\\s")[7] + " " + serverAnswer.split("\n")[5].split("\\s")[8] + " " +
                    serverAnswer.split("\n")[5].split("\\s")[9] + "\n";
            ans += "Request ID: " + requestID;
            requestContent.setText(ans);
        } else if (serverAnswer.split("\\s")[1].equals("ADD_TO_PRODUCT")) {
            viewOtherButton.setText("View Product");
            viewOtherButton.setVisible(true);
            String ans = serverAnswer.split("\n")[1] + "\n";
            ans += serverAnswer.split("\n")[2] + "\n";
            ans += serverAnswer.split("\n")[3].split("\\s")[0] + " " + serverAnswer.split("\n")[3].
                    split("\\s")[1] + " " + serverAnswer.split("\n")[3].split("\\s")[2] + "\n";
            ans += serverAnswer.split("\n")[3].split("\\s")[3] + " " + serverAnswer.split("\n")[3].split("\\s")[4] + " " +
                    serverAnswer.split("\n")[3].split("\\s")[5] + "\n";
            ans += serverAnswer.split("\n")[3].split("\\s")[6] + " " + serverAnswer.split("\n")[3].split("\\s")[7] + "\n";
            ans += serverAnswer.split("\n")[3].split("\\s")[8] + " " + serverAnswer.split("\n")[3].split("\\s")[9] + "\n";
            ans += serverAnswer.split("\n")[4] + "\n";
            ans += serverAnswer.split("\n")[5].split("\\s")[0] + " " + serverAnswer.split("\n")[5].split("\\s")[1] + " " +
                    serverAnswer.split("\n")[5].split("\\s")[2] + "\n";
            ans += "Product " + serverAnswer.split("\n")[5].split("\\s")[3] + " " + serverAnswer.split("\n")[5].split("\\s")[4] + "\n";
            ans += "Offered " + serverAnswer.split("\n")[5].split("\\s")[6] + " " + serverAnswer.split("\n")[5].split("\\s")[7] + "\n";
            ans += serverAnswer.split("\n")[5].split("\\s")[8] + " " + serverAnswer.split("\n")[5].split("\\s")[9] + " " +
                    serverAnswer.split("\n")[5].split("\\s")[10] + "\n";
            ans += "Request ID: " + requestID;
            requestContent.setText(ans);
        } else if (serverAnswer.split("\\s")[1].equals("CHANGE_PRODUCT")) {
            viewOtherButton.setText("View Product");
            viewOtherButton.setVisible(true);
            String ans = serverAnswer.split("\n")[1] + "\n";
            ans += serverAnswer.split("\n")[2] + "\n";
            ans += serverAnswer.split("\n")[3].split("\\s")[0] + " " + serverAnswer.split("\n")[3].
                    split("\\s")[1] + " " + serverAnswer.split("\n")[3].split("\\s")[2] + "\n";
            ans += serverAnswer.split("\n")[3].split("\\s")[3] + " " + serverAnswer.split("\n")[3].split("\\s")[4] + " " +
                    serverAnswer.split("\n")[3].split("\\s")[5] + "\n";
            ans += serverAnswer.split("\n")[3].split("\\s")[6] + " " + serverAnswer.split("\n")[3].split("\\s")[7] + "\n";
            ans += serverAnswer.split("\n")[3].split("\\s")[8] + " " + serverAnswer.split("\n")[3].split("\\s")[9] + "\n";
            ans += serverAnswer.split("\n")[4].split("\\s")[0] + " " + serverAnswer.split("\n")[4].split("\\s")[1] + " " +
                    serverAnswer.split("\n")[4].split("\\s")[2] + "\n";
            ans += serverAnswer.split("\n")[5].split("\\s")[0] + " " + serverAnswer.split("\n")[5].split("\\s")[1] + " " +
                    serverAnswer.split("\n")[5].split("\\s")[2] + "\n";
            ans += serverAnswer.split("\n")[6].split("\\s")[0] + " " + serverAnswer.split("\n")[6].split("\\s")[1] + " " +
                    serverAnswer.split("\n")[6].split("\\s")[2] + "\n";
            ans += serverAnswer.split("\n")[7].split("\\s")[0] + " " + serverAnswer.split("\n")[7].split("\\s")[1] + " " +
                    serverAnswer.split("\n")[7].split("\\s")[2] + " " + serverAnswer.split("\n")[8].split("\\s")[3] + "\n";
            ans += "Request ID: " + requestID;
            requestContent.setText(ans);
        }
    }

    public void declineRequest(ActionEvent actionEvent) throws ParseException, IOException {
        MenuHandler.getServer().clientToServer("decline request+" + MenuHandler.getUsername() + "+" + requestID);
        if (MenuHandler.getServer().serverToClient().equalsIgnoreCase("declined successfully")) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Request Declined Successfully", ButtonType.OK);
            alert.showAndWait();
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("/GUI/BossProfile/ManageRequests/ViewRequest.fxml"));
            stage.setScene(new Scene(root));
        }
    }

    public void acceptRequest(ActionEvent actionEvent) throws ParseException, IOException {
        if (acceptButton.getText().equalsIgnoreCase("accept")) {
            MenuHandler.getServer().clientToServer("accept request+" + MenuHandler.getUsername() + "+" + requestID);
            if (MenuHandler.getServer().serverToClient().equalsIgnoreCase("accepted successfully")) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Request Accepted Successfully", ButtonType.OK);
                alert.showAndWait();
                Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
                Parent root = FXMLLoader.load(getClass().getResource("/GUI/BossProfile/ManageRequests/ViewRequest.fxml"));
                stage.setScene(new Scene(root));
            }
        } else {
            MenuHandler.getServer().clientToServer("delete request+" + MenuHandler.getUsername() + "+" + requestID);
            if (MenuHandler.getServer().serverToClient().equalsIgnoreCase("deleted successfully")) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Request Deleted Successfully", ButtonType.OK);
                alert.showAndWait();
                Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
                Parent root = FXMLLoader.load(getClass().getResource("/GUI/ProfileLayout/ProfileLayout.fxml"));
                stage.setScene(new Scene(root));
            }
        }
    }

    public void viewUsername(ActionEvent actionEvent) {

    }

    public void viewProduct(ActionEvent actionEvent) {

    }

    public void back(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/GUI/ProfileLayout/ProfileLayout.fxml"));
        stage.setScene(new Scene(root));
    }
}
