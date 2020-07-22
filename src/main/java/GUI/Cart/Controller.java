package GUI.Cart;

import GUI.Media.Audio;
import GUI.MenuHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.javatuples.Triplet;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

public class Controller {
    public TableColumn<Cart, String> salesman;
    public TableColumn<Cart, String> product;
    public TableColumn<Cart, Integer> price;
    public TableColumn<Cart, Integer> number;
    public TableColumn<Cart, String> isOnSale;
    public TableColumn<Cart, Integer> priceAll;
    public TableView<Cart> products;
    public Label usedOffCode;
    public Label finalPrice;
    public TextField offCodeId;
    public int ans;
    public static Controller controller;

    ObservableList<Cart> list = FXCollections.observableArrayList();

    public void initialize() throws IOException, ParseException {
        ans = 0;
        controller = this;
        update();
    }

    private void update() throws IOException, ParseException {
        salesman.setCellValueFactory(new PropertyValueFactory<>("salesman"));
        product.setCellValueFactory(new PropertyValueFactory<>("productName"));
        price.setCellValueFactory(new PropertyValueFactory<>("price"));
        number.setCellValueFactory(new PropertyValueFactory<>("count"));
        isOnSale.setCellValueFactory(new PropertyValueFactory<>("isOnSale"));
        priceAll.setCellValueFactory(new PropertyValueFactory<>("finalPrice"));
        ArrayList<Triplet<String, String, Integer>> items = MenuHandler.getCart();
        for (Triplet<String, String, Integer> item : items) {
            MenuHandler.getConnector().clientToServer("what is product name+" + item.getValue1());
            String productName = MenuHandler.getConnector().serverToClient();
            MenuHandler.getConnector().clientToServer("is product on sale by+" + item.getValue0() + "+" + item.getValue1());
            String isOnSale = MenuHandler.getConnector().serverToClient();
            MenuHandler.getConnector().clientToServer("get product price by salesman+" + item.getValue0() + "+" + item.getValue1());
            String price = MenuHandler.getConnector().serverToClient();
            Cart cart = new Cart(item.getValue0(), item.getValue1(), productName, Integer.parseInt(price),
                    getPrice(Integer.parseInt(price), item.getValue2(), isOnSale), item.getValue2(),
                    isOnSale.equalsIgnoreCase("none") ? "No" : "Yes", isOnSale);
            list.add(cart);
        }
        products.setItems(list);
        finalPrice.setText(String.valueOf(ans));
    }

    private int getPrice(int price, Integer count, String isOnSale) {
        if (isOnSale.equalsIgnoreCase("none")) {
            ans += price * count;
            return price * count;
        } else {
            return -1;
        }
    }


    public void back(ActionEvent mouseEvent) throws IOException {
        Audio.playClick1();
        MenuHandler.getStage().setScene(new Scene(FXMLLoader.load(getClass().getResource("/GUI/ProductScene/ProductScene.fxml"))));
    }

    public void useOffCode(ActionEvent mouseEvent) throws ParseException, IOException {
        Audio.playClick2();
        MenuHandler.getConnector().clientToServer("can use offCode+" + MenuHandler.getUsername() + "+" + offCodeId.getText());
        Alert alert = new Alert(Alert.AlertType.INFORMATION, MenuHandler.getConnector().serverToClient(), ButtonType.OK);
        if (!MenuHandler.getConnector().serverToClient().equals("Use It")) {
            alert.setAlertType(Alert.AlertType.ERROR);
        } else {
            usedOffCode.setText(offCodeId.getText());
        }
        alert.showAndWait();
    }

    public void buy(ActionEvent actionEvent) throws IOException, ParseException {
        Audio.playClick3();
        if (MenuHandler.isIsUserLogin()) {

            //going to pay page

            Parent root = FXMLLoader.load(getClass().getResource("/GUI/Cart/Pay.fxml"));
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));

            /*MenuHandler.getServer().clientToServer("get money+" + MenuHandler.getUsername());
            int money = Integer.parseInt(MenuHandler.getServer().serverToClient());

            if (money < ans) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Not Enough Money", ButtonType.OK);
                alert.showAndWait();
            } else {
                StringBuilder products = new StringBuilder("buy+" + MenuHandler.getUsername() + "\n");
                for (Cart cart : list) {
                    products.append(cart.getSalesman()).append("+").append(cart.getProductId()).append("+").append(cart.getCount()).append("\n");
                }
                MenuHandler.getServer().clientToServer(products.toString());
                if (MenuHandler.getServer().serverToClient().equals("Buy Successful")) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Buy Successful", ButtonType.OK);
                    alert.showAndWait();
                }
            }  */
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "You Should Login First", ButtonType.OK);
            alert.showAndWait();
            MenuHandler.setLoginBackAddress("/GUI/Cart/Cart.fxml");
            MenuHandler.getStage().setScene(new Scene(FXMLLoader.load(getClass().getResource("/GUI/Login/Login.fxml"))));
        }
    }

    public void deleteFromCart(ActionEvent actionEvent) throws IOException, ParseException {
        if (products.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "You Must Choose From Products First", ButtonType.OK);
            alert.showAndWait();
            return;
        }
        Cart cart = products.getSelectionModel().getSelectedItem();
        ArrayList<Triplet<String, String, Integer>> items = MenuHandler.getCart();
        for (Triplet<String, String, Integer> item : items) {
            if (item.getValue0().equals(cart.getSalesman()) && item.getValue1().equals(cart.getProductId())) {
                items.remove(item);
                update();
                return;
            }
        }
    }

}
