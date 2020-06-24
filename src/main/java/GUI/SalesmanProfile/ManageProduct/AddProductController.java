package GUI.SalesmanProfile.ManageProduct;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class AddProductController {
    public ImageView img;
    public VBox pane;
    public TextField productName;
    public TextField brand;
    public TextArea description;
    public TextField price;
    public Spinner count;
    private String path;

    public void initialize() {
        SpinnerValueFactory<Integer> count = new SpinnerValueFactory.IntegerSpinnerValueFactory
                (0, 100000, 0);
        this.count.setValueFactory(count);
    }

    public void uploadImg(MouseEvent mouseEvent) {
        Stage stage = (Stage) pane.getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Upload Product Picture");
        File chosenFile = fileChooser.showOpenDialog(stage);
        if (chosenFile == null) return;
        img.setImage(new Image(String.valueOf(chosenFile.toURI())));
        path = String.valueOf(chosenFile.toURI());
    }

    public void newProduct(ActionEvent actionEvent) {

    }

    public void back(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/GUI/SalesmanProfile/SalesmanProfileLayout.fxml"));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
    }
}
