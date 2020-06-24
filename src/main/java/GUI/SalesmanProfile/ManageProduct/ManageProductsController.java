package GUI.SalesmanProfile.ManageProduct;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class ManageProductsController {

    public void newProductMenu(ActionEvent actionEvent) throws IOException {
        Parent root = null;
        root = FXMLLoader.load(getClass().getResource("/GUI/SalesmanProfile/ManageProduct/NewProductLayout.fxml"));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    public void initialize() {

    }

    private void update() {
        
    }
}
