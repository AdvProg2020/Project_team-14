package GUI.SalesmanProfile.ManageProduct;

import GUI.MenuHandler;
import Menus.Menu;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.ParseException;

public class ManageProductsController {

    public TilePane products;

    public void newProductMenu(ActionEvent actionEvent) throws IOException {
        Parent root = null;
        root = FXMLLoader.load(getClass().getResource("/GUI/SalesmanProfile/ManageProduct/NewProductLayout.fxml"));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    public void initialize() throws ParseException, IOException {
        update();
    }

    private void update() throws ParseException, IOException {
        MenuHandler.getServer().clientToServer("show products+" + MenuHandler.getUsername());
        String serverAnswer = MenuHandler.getServer().serverToClient();

        for (int i = 1; i <= products.getChildren().size(); i++) {
            Object object = products.getChildren().remove(0);
        }
        if (serverAnswer.equals("nothing found")) {
            return;
        }
        for (String s : serverAnswer.split("\n")) {
            if (s.startsWith("here")) continue;
            Object object = FXMLLoader.load(getClass().getResource
                    ("/GUI/SalesmanProfile/ManageProduct/ProductCard.fxml"));
            products.getChildren().add((Node) object);
            Label label = (Label) ((VBox) object).getChildren().get(1);
            label.setText(s.split("\\s")[4]);
            ((VBox) object).setOnMouseEntered(event -> {
                Stage stage = (Stage) ((VBox) event.getSource()).getScene().getWindow();
                stage.getScene().setCursor(Cursor.HAND);
            });
            ((VBox) object).setOnMouseExited(event -> {
                Stage stage = (Stage) ((VBox) event.getSource()).getScene().getWindow();
                stage.getScene().setCursor(Cursor.DEFAULT);
            });
            ((VBox) object).setOnMouseClicked(event -> {
                //open view product
            });
            String productId = s.split("\\s")[2];
            MenuHandler.getServer().clientToServer("get product picture path+" + productId);
            String path = MenuHandler.getServer().serverToClient();
            if (path == null) {
                path = "file:\\F:\\AP\\AP\\Project_team-23\\src\\main\\resources\\Pictures\\default.png";
            }
            if (path.equalsIgnoreCase("none")) {
                path = "file:\\F:\\AP\\AP\\Project_team-23\\src\\main\\resources\\Pictures\\default.png";
            }
            ImageView imageView = (ImageView) ((VBox) object).getChildren().get(0);
            System.out.println(s);
            System.out.println(path);
            imageView.setImage(new Image(path));
        }

        /*for (String s : serverAnswer.split("\n")) {
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
            requestList.getChildren().add(hBox);*/
    }
}

