package GUI.ProductView.Film;

import GUI.Media.Audio;
import GUI.MenuHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;

public class Controller {
    public javafx.scene.media.MediaView MediaView;
    public ImageView exit;


    public void exitButtonClicked(MouseEvent mouseEvent) throws IOException {
        Audio.unMute();
        Audio.playClick4();
        Parent root = FXMLLoader.load(getClass().getResource("/GUI/ProductView/ProductViewLayout.fxml"));
        Stage stage = (Stage) ((Button) mouseEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        if (MediaView.getMediaPlayer() != null) {
            MediaView.getMediaPlayer().stop();
        }
    }

    public void initialize() throws IOException, ParseException {
        Audio.mute();
        MenuHandler.getServer().clientToServer("get product video+" + MenuHandler.getProductID());
        MediaPlayer mediaPlayer;
        if (MenuHandler.getServer().serverToClient().startsWith("no video")) {
            mediaPlayer = new MediaPlayer(new Media(new File("src/main/java/GUI/ProductView/Film/videoplayback (1).mp4").toURI().toString()));
        } else {
            mediaPlayer = new MediaPlayer(new Media(MenuHandler.getServer().serverToClient()));
        }
        MediaView.setMediaPlayer(mediaPlayer);
        mediaPlayer.setAutoPlay(true);
    }

}
