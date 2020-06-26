package GUI.ProductView;

import GUI.Media.Audio;
import GUI.MenuHandler;
import Menus.Menu;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.ParseException;

public class Compare {
    public Label name1;
    public Label name2;
    public Label minPrice2;
    public Label minPrice1;
    public TextField text;

    public void search(ActionEvent actionEvent) throws ParseException, IOException {
        Audio.playClick5();
        MenuHandler.getServer().clientToServer("is there product name+" + text.getText());
        if (MenuHandler.getServer().serverToClient().startsWith("tr")) {
            name2.setText(text.getText());
            MenuHandler.getServer().clientToServer("product min price with name+" + text.getText());
            minPrice2.setText(MenuHandler.getServer().serverToClient()+"$");
            MenuHandler.getServer().clientToServer("get product min price+" + MenuHandler.getProductID());
            minPrice1.setText(MenuHandler.getServer().serverToClient() + "$");
            MenuHandler.getServer().clientToServer("what is product name+" + MenuHandler.getProductID());
            name1.setText(MenuHandler.getServer().serverToClient());
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "", ButtonType.OK);
            alert.setContentText("no product found");
            alert.showAndWait();
        }
    }

    public void back(ActionEvent actionEvent) throws IOException {
        Audio.playClick4();
        Parent root = FXMLLoader.load(getClass().getResource("/GUI/ProductView/ProductViewLayout.fxml"));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));

    }
}
