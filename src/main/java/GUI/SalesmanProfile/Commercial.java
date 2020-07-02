package GUI.SalesmanProfile;

import GUI.Media.Audio;
import GUI.MenuHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import javafx.util.Duration;

import javax.sound.sampled.AudioInputStream;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;

public class Commercial {
    public MediaView mediaVeiw;
    public TextArea name;

    public void Done() throws ParseException, IOException {
        Audio.playClick3();
        MenuHandler.getServer().clientToServer("commercial+" + MenuHandler.getUsername() + "+" + name.getText());
        String answer = MenuHandler.getServer().serverToClient();
        switch (answer) {
            case "it's already on commercial": {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.OK);
                alert.setContentText("it's already on commercial");
                alert.showAndWait();
                break;
            }
            case "not enough money": {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.OK);
                alert.setContentText("not enough money");
                alert.showAndWait();
                break;
            }
            case "added successfully": {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.OK);
                alert.setContentText("added successfully");
                alert.showAndWait();
                break;
            }
            case "not your product": {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.OK);
                alert.setContentText("not your product");
                alert.showAndWait();
                break;
            }
            case "invalid product": {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.OK);
                alert.setContentText("invalid product");
                alert.showAndWait();
                break;
            }
        }
    }

    public void Back(ActionEvent actionEvent) throws IOException {
        Audio.playClick5();
        Parent root = FXMLLoader.load(getClass().getResource("/GUI/SalesmanProfile/SalesmanProfileLayout.fxml"));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    public void initialize() {
        String path = "src/main/java/GUI/Login/resources/mp4.mp4";
        Media media = new Media(new File(path).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaVeiw.setMediaPlayer(mediaPlayer);
        mediaPlayer.setAutoPlay(true);
        mediaPlayer.setOnEndOfMedia(() -> mediaPlayer.seek(Duration.ZERO));
    }
}
