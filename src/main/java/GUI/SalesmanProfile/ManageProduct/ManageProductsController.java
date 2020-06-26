package GUI.SalesmanProfile.ManageProduct;

import GUI.Media.Audio;
import GUI.MenuHandler;
import Menus.Menu;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.ParseException;

public class ManageProductsController {

    public TilePane products;
    public ComboBox<String> sortFactor;
    public ComboBox<String> filter;
    public FlowPane filterList;
    ObservableList<String> list = FXCollections.observableArrayList("Confirmation:ACCEPTED", "Confirmation:DENIED",
            "Confirmation:CHECKING", "Available", "Not Available");

    public void newProductMenu(ActionEvent actionEvent) throws IOException {
        Audio.playClick5();
        MenuHandler.getPane().getChildren().remove(MenuHandler.getPane().getChildren().get(0));
        MenuHandler.getPane().getChildren().add(FXMLLoader.load(getClass().getResource("/GUI/SalesmanProfile/ManageProduct/NewProductLayout.fxml")));
    }

    public void initialize() throws ParseException, IOException {
        MenuHandler.getServer().clientToServer("show the salesman+" + MenuHandler.getUsername());
        String salesman = MenuHandler.getServer().serverToClient();
        filter.setItems(list);
        for (String s : salesman.split("\n")) {
            if (s.equalsIgnoreCase("here are")) {
                continue;
            }
            filter.getItems().add("Salesman:" + s);
        }
        System.out.println(salesman);
        MenuHandler.getServer().clientToServer("show categories+" + MenuHandler.getUsername());
        String categories = MenuHandler.getServer().serverToClient();
        System.out.println(categories);
        update();
    }

    private void update() throws ParseException, IOException {
        MenuHandler.getServer().clientToServer("show products+" + MenuHandler.getUsername());
        String serverAnswer = MenuHandler.getServer().serverToClient();

        for (int i = 1; i <= products.getChildren().size(); ) {
            Object object = products.getChildren().remove(0);
        }
        System.out.println(products.getChildren().size());
        if (serverAnswer.equals("nothing found")) {
            return;
        }
        for (String s : serverAnswer.split("\n")) {
            if (s.startsWith("here")) continue;
            Object object = FXMLLoader.load(getClass().getResource
                    ("/GUI/SalesmanProfile/ManageProduct/ProductCard.fxml"));
            products.getChildren().add((Node) object);
            Label label = (Label) ((AnchorPane) object).getChildren().get(1);
            label.setText(s.split("\\s")[4]);
            ((AnchorPane) object).setOnMouseEntered(event -> {
                Stage stage = (Stage) ((AnchorPane) event.getSource()).getScene().getWindow();
                stage.getScene().setCursor(Cursor.HAND);
            });
            ((AnchorPane) object).setOnMouseExited(event -> {
                Stage stage = (Stage) ((AnchorPane) event.getSource()).getScene().getWindow();
                stage.getScene().setCursor(Cursor.DEFAULT);
            });
            ((AnchorPane) object).setOnMouseClicked(event -> {
                MenuHandler.setBackProduct("/GUI/SalesmanProfile/SalesmanProfileLayout.fxml");
                MenuHandler.setProductID(s.split("\\s")[2]);
                Stage stage = (Stage) ((AnchorPane) event.getSource()).getScene().getWindow();
                Parent root = null;
                try {
                    root = FXMLLoader.load(getClass().getResource("/GUI/ProductView/ProductViewLayout.fxml"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                stage.setScene(new Scene(root));
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
            ImageView imageView = (ImageView) ((AnchorPane) object).getChildren().get(0);
            Image image = new Image(path);
            double x = image.getWidth();
            double y = image.getHeight();
            //image.
            //double ratio=
            //160 140
            imageView.setImage(image);
            ((AnchorPane) object).setStyle("-fx-padding: 0;" + "-fx-border-style: solid inside;"
                    + "-fx-border-width: 1;" + "-fx-border-insets: 2;"
                    + "-fx-border-radius: 2;" + "-fx-border-color: black;");
        }
    }

    public void chooseFilter(ActionEvent actionEvent) throws IOException, ParseException {
        String s = filter.getValue();
        try {
            checkExistenceOfFilter(s);
        } catch (Exception e) {
            return;
        }
        for (Object object : filterList.getChildren()) {
            if (object instanceof HBox) {
                Object object1 = ((HBox) object).getChildren().get(0);
                if (object1 instanceof Label) {
                    if (((Label) object1).getText().equals(s)) return;
                }
            }
        }
        Parent item = FXMLLoader.load(getClass().getResource("/GUI/MainTheme/chosenItemLayout.fxml"));
        Label label = (Label) ((HBox) item).getChildren().get(0);
        label.setText(s);
        Button button = (Button) ((HBox) item).getChildren().get(1);
        button.setOnAction(actionEvent2 -> {
            filterList.getChildren().remove(item);
            try {
                update();
            } catch (ParseException | IOException e) {
                e.printStackTrace();
            }
        });
        filterList.getChildren().add(item);
        update();
    }

    private void checkExistenceOfFilter(String filterFactor) throws Exception {
        for (Node child : filterList.getChildren()) {
            Label prevFactor = (Label) ((HBox) child).getChildren().get(0);
            if (prevFactor.getText().equals(filterFactor)) throw new Exception("filter exist");
        }
    }
}

