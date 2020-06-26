package GUI.Cart;

import GUI.Media.Audio;
import GUI.MenuHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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
            System.out.println(cart);

        }
        products.setItems(list);
    }

    private int getPrice(int price, Integer count, String isOnSale) {
        if (isOnSale.equalsIgnoreCase("none")) {
            return price * count;
        } else {
            return -1;
        }
    }


    public void back(ActionEvent mouseEvent) {
        Audio.playClick1();
    }

    public void useOffCode(ActionEvent mouseEvent) {
        Audio.playClick2();
    }

    public void buy(ActionEvent mouseEvent) throws IOException {
        Audio.playClick3();
        if (MenuHandler.isIsUserLogin()) {

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
