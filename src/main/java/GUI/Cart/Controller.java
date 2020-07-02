package GUI.Cart;

import GUI.Media.Audio;
import GUI.MenuHandler;
import Menus.Menu;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
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
    private int ans = 0;

    ObservableList<Cart> list = FXCollections.observableArrayList();

    public void initialize() throws IOException, ParseException {
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
            MenuHandler.getServer().clientToServer("what is product name+" + item.getValue1());
            String productName = MenuHandler.getServer().serverToClient();
            MenuHandler.getServer().clientToServer("is product on sale by+" + item.getValue0() + "+" + item.getValue1());
            String isOnSale = MenuHandler.getServer().serverToClient();
            MenuHandler.getServer().clientToServer("get product price by salesman+" + item.getValue0() + "+" + item.getValue1());
            String price = MenuHandler.getServer().serverToClient();
            Cart cart = new Cart(item.getValue0(), item.getValue1(), productName, Integer.parseInt(price),
                    getPrice(Integer.parseInt(price), item.getValue2(), isOnSale), item.getValue2(),
                    isOnSale.equalsIgnoreCase("none") ? "No" : "Yes", isOnSale);
            list.add(cart);
        }
        products.setItems(list);
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
        MenuHandler.getServer().clientToServer("can use offCode+" + MenuHandler.getUsername() + "+" + offCodeId.getText());
        Alert alert = new Alert(Alert.AlertType.INFORMATION, MenuHandler.getServer().serverToClient(), ButtonType.OK);
        if (!MenuHandler.getServer().serverToClient().equals("Use It")) {
            alert.setAlertType(Alert.AlertType.ERROR);
        }
        alert.showAndWait();
    }

    public void buy(ActionEvent mouseEvent) throws IOException, ParseException {
        Audio.playClick3();
        if (MenuHandler.isIsUserLogin()) {
            MenuHandler.getServer().clientToServer("get money+" + MenuHandler.getUsername());
            int money = Integer.parseInt(MenuHandler.getServer().serverToClient());
            System.out.println(money);
            if (money < ans) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Not Enough Money", ButtonType.OK);
                alert.showAndWait();
            } else {
                String products = "buy+" + MenuHandler.getUsername() + "\n";
                for (Cart cart : list) {
                    products += cart.getSalesman() + "+" + cart.getProductId() + "+" + cart.getCount() + "\n";
                }
                MenuHandler.getServer().clientToServer(products);
                if (MenuHandler.getServer().serverToClient().equals("Buy Successful")) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Buy Successful", ButtonType.OK);
                    alert.showAndWait();
                }
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "You Should Login First", ButtonType.OK);
            alert.showAndWait();
            MenuHandler.setLoginBackAddress("/GUI/Cart/Cart.fxml");
            MenuHandler.getStage().setScene(new Scene(FXMLLoader.load(getClass().getResource("/GUI/Login/Login.fxml"))));
        }
        MenuHandler.getStage().setScene(new Scene(FXMLLoader.load(getClass().getResource("/GUI/ProductScene/ProductScene.fxml"))));
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
            if (item.getValue0().equals(cart.getSalesman())) {
                if (item.getValue1().equals(cart.getProductId())) {
                    items.remove(item);
                    update();
                    return;
                }
            }
        }
    }
}
