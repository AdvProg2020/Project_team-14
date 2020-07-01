package GUI.ProductView.SimilarProduct;

import GUI.Media.Audio;
import GUI.MenuHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.ParseException;

public class SimilarProduct {
    public String productID;
    public Label brand;
    public Label price;
    public Label name;
    public ImageView imageView;

    public void initialize() throws ParseException, IOException {
        MenuHandler.getServer().clientToServer("similar product+" + MenuHandler.getProductID());
        productID = MenuHandler.getServer().serverToClient();
        MenuHandler.getServer().clientToServer("get product min price+" + productID);
        price.setText(MenuHandler.getServer().serverToClient());
        MenuHandler.getServer().clientToServer("what is product name+" + productID);
        name.setText(MenuHandler.getServer().serverToClient());
        MenuHandler.getServer().clientToServer("get product picture path+" + productID);
        String path = MenuHandler.getServer().serverToClient();
        if (path != null) {
            if (!path.equalsIgnoreCase("none")) {
                imageView.setImage(new Image(path));
            }
        }

    }

    public void visitClicked(ActionEvent actionEvent) throws IOException {
        Audio.playClick7();
        Parent root = FXMLLoader.load(getClass().getResource("/GUI/ProductView/ProductViewLayout.fxml"));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    public void backClicked(ActionEvent actionEvent) throws IOException {
        Audio.playClick4();
        Parent root = FXMLLoader.load(getClass().getResource("/GUI/ProductView/ProductViewLayout.fxml"));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
    }
}
