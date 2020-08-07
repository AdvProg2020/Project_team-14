package GUI.SalesmanProfile.ManageProduct;

import GUI.Media.Audio;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Popup;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.ParseException;

public class ManageProductsController {

    public TilePane products;
    public ComboBox<String> sortFactor;
    public ComboBox<String> filter;
    public FlowPane filterList;
    public Button addButton;
    ObservableList<String> list = FXCollections.observableArrayList("Confirmation:ACCEPTED", "Confirmation:DENIED",
            "Confirmation:CHECKING", "Available", "Not Available");

    public void newProductMenu(ActionEvent actionEvent) throws IOException {
        Audio.playClick5();
        Popup popup = new Popup();
        popup.getContent().add(FXMLLoader.load(getClass().getResource("/GUI/SalesmanProfile/ManageProduct/NewProductLayout.fxml")));
        popup.show(((Button) actionEvent.getSource()).getScene().getWindow());
        popup.setOnHiding(e -> {
            try {
                update();
            } catch (ParseException ex) {
                ex.printStackTrace();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }

    public void initialize() throws ParseException, IOException {
        if (MenuHandler.getUserType().equalsIgnoreCase("Boss")) {
            addButton.setDisable(true);
        }
        MenuHandler.getConnector().clientToServer("show the salesman+" + MenuHandler.getUsername());
        String salesman = MenuHandler.getConnector().serverToClient();
        filter.setItems(list);
        for (String s : salesman.split("\n")) {
            if (s.equalsIgnoreCase("here are")) {
                continue;
            }
            filter.getItems().add("Salesman:" + s);
        }
        MenuHandler.getConnector().clientToServer("show categories+" + MenuHandler.getUsername());
        String categories = MenuHandler.getConnector().serverToClient();
        for (String s : categories.split("\n")) {
            if (s.startsWith("Category")) {
                filter.getItems().add("Category:" + s.split("\\s")[2]);
            }
        }
        update();
    }

    private void update() throws ParseException, IOException {
        StringBuilder categories = new StringBuilder();
        StringBuilder salesman = new StringBuilder();
        StringBuilder available = new StringBuilder();
        StringBuilder confirmationState = new StringBuilder();
        for (Object object : filterList.getChildren()) {
            if (object instanceof HBox) {
                Object object1 = ((HBox) object).getChildren().get(0);
                if (object1 instanceof Label) {
                    if (((Label) object1).getText().startsWith("Confirmation:")) {
                        if (confirmationState.toString().equals("")) {
                            confirmationState = new StringBuilder(((Label) object1).getText().substring(13));
                        } else {
                            confirmationState.append(",").append(((Label) object1).getText().substring(13));
                        }
                    } else if (((Label) object1).getText().startsWith("Category:")) {
                        if (categories.toString().equals("")) {
                            categories = new StringBuilder(((Label) object1).getText().substring(9));
                        } else {
                            categories.append(",").append(((Label) object1).getText().substring(9));
                        }
                    } else if (((Label) object1).getText().startsWith("Salesman:")) {
                        if (salesman.toString().equals("")) {
                            salesman = new StringBuilder(((Label) object1).getText().substring(9));
                        } else {
                            salesman.append(",").append(((Label) object1).getText().substring(9));
                        }
                    } else {
                        if (available.toString().equals("")) {
                            available = new StringBuilder(((Label) object1).getText());
                        } else {
                            available.append(",").append(((Label) object1).getText());
                        }
                    }
                }
            }
        }
        String command = "show products+" + MenuHandler.getUsername();
        if (available.toString().equals("") && salesman.toString().equals("") && categories.toString().equals("") && confirmationState.toString().equals("")) {

        } else {
            command += "+filters:";
            if (!available.toString().equals("")) {
                command += "+remainder+" + available;
            }
            if (!salesman.toString().equals("")) {
                command += "+salesmanIDs+" + salesman;
            }
            if (!categories.toString().equals("")) {
                command += "+categories+" + categories;
            }
            if (!confirmationState.toString().equals("")) {
                command += "+Confirmation+" + confirmationState;
            }
        }

        MenuHandler.getConnector().clientToServer(command);
        String serverAnswer = MenuHandler.getConnector().serverToClient();
        for (int i = 1; i <= products.getChildren().size(); ) {
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
            Label label = (Label) ((AnchorPane) object).getChildren().get(1);
            label.setText(s.split("\\s")[4]);
            ((AnchorPane) object).setOnMouseClicked(event -> {
                if (MenuHandler.getUserType().equalsIgnoreCase("Salesman")) {
                    MenuHandler.setBackProduct("/GUI/SalesmanProfile/SalesmanProfileLayout.fxml");
                } else if (MenuHandler.getUserType().equalsIgnoreCase("Boss")) {
                    MenuHandler.setBackProduct("/GUI/ProfileLayout/ProfileLayout.fxml");
                }
                MenuHandler.setProductID(s.split("\\s")[2]);
                Parent root = null;
                try {
                    root = FXMLLoader.load(getClass().getResource("/GUI/ProductView/ProductViewLayout.fxml"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //stage.setScene(new Scene(root));
                Popup popup = new Popup();
                popup.getContent().add(root);
                popup.show(MenuHandler.getStage());
            });
            String productId = s.split("\\s")[2];
            MenuHandler.getConnector().clientToServer("get product picture path+" + productId);
            String path = MenuHandler.getConnector().serverToClient();
            if (path == null) {
                path = "file:src/main/java/GUI/SalesmanProfile/ManageProduct/resources/billboard.png";
            }
            if (path.equalsIgnoreCase("none")) {
                path = "file:src/main/java/GUI/SalesmanProfile/ManageProduct/resources/billboard.png";
            }
            ImageView imageView = (ImageView) ((AnchorPane) object).getChildren().get(0);
            Image image = new Image(path);
            imageView.setImage(image);
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

