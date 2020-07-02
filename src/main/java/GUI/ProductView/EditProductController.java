package GUI.ProductView;

import GUI.Media.Audio;
import GUI.MenuHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Popup;

import java.io.IOException;
import java.text.ParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EditProductController {
    public TextField name;
    public TextArea description;
    public TextField price;
    public TextField count;

    public void back(ActionEvent actionEvent) throws IOException {
        Audio.playClick6();
        Popup popup = (Popup) ((Button) actionEvent.getSource()).getScene().getWindow();
        popup.hide();
        Parent root = FXMLLoader.load(getClass().getResource("/GUI/ProductView/ProductViewLayout.fxml"));
        MenuHandler.getStage().setScene(new Scene(root));
    }

    public void editProduct(ActionEvent actionEvent) throws ParseException, IOException {
        Audio.playClick1();
        if (checkProductNameFormat(name.getText())) {
            MenuHandler.getServer().clientToServer("edit product+name+" + MenuHandler.getUsername() + "+"
                    + MenuHandler.getProductID() + "+" + name.getText());
        } else {

        } if (checkDescriptionFormat(description.getText())) {
            MenuHandler.getServer().clientToServer("edit product+description+" + MenuHandler.getUsername() + "+"
                    + MenuHandler.getProductID() + "+" + description.getText());
        } else {

        }
        if (price.getText().matches("\\d+") && price.getText().length() <= 8) {
            MenuHandler.getServer().clientToServer("edit product price+" + MenuHandler.getUsername() + "+" +
                    MenuHandler.getProductID() + "+" + price.getText());
        } else {

        }
        if (count.getText().matches("\\d+") && count.getText().length() <= 5) {
            MenuHandler.getServer().clientToServer("add product remainder+" + MenuHandler.getUsername() + "+"
                    + MenuHandler.getProductID() + "+" + count.getText());
        } else {

        }
        Popup popup = (Popup) ((Button) actionEvent.getSource()).getScene().getWindow();
        popup.hide();
        Parent root = FXMLLoader.load(getClass().getResource("/GUI/ProductView/ProductViewLayout.fxml"));
        MenuHandler.getStage().setScene(new Scene(root));
    }

    private boolean checkProductNameFormat(String input) {
        return getMatcher("([\\d\\w_\\-,\\s'])+", input).matches();
    }

    private boolean checkDescriptionFormat(String description) {
        return getMatcher("[\\w\\s\\.]+", description).matches();
    }

    private Matcher getMatcher(String regex, String command) {
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(command);
    }
}
