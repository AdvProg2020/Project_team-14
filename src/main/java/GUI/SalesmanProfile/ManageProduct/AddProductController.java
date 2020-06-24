package GUI.SalesmanProfile.ManageProduct;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.MalformedURLException;

public class AddProductController {
    public ImageView img;
    public VBox pane;


    public void uploadImg(MouseEvent mouseEvent) {
        Stage stage = (Stage) pane.getScene().getWindow();

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("upload product picture");
        File chosenFile = fileChooser.showOpenDialog(stage);
        if (chosenFile == null) return;
        img.setImage(new Image(String.valueOf(chosenFile.toURI())));
    }
}
