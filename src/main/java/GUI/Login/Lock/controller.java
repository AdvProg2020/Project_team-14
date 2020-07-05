package GUI.Login.Lock;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Random;

public class controller {

    public Label timer;
    public Button back;
    public int time;
    public ImageView image1;
    public ImageView image2;
    public ImageView image3;
    public ImageView image6;
    public ImageView image7;
    public ImageView image8;
    public ImageView image9;
    public MediaView mediaView;
    public ImageView image5;
    public ImageView image4;
    private int r;

    public void initialize() throws InterruptedException, FileNotFoundException {
        String path = "src/main/java/GUI/Login/resources/mp4.mp4";
        Media media = new Media(new File(path).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaView.setMediaPlayer(mediaPlayer);
        mediaPlayer.setAutoPlay(true);
        mediaPlayer.setOnEndOfMedia(() -> mediaPlayer.seek(Duration.ZERO));
        image1.setImage(new Image(new FileInputStream("src/main/java/GUI/Register/resources/download (1).jpg")));
        image2.setImage(new Image(new FileInputStream("src/main/java/GUI/Register/resources/download (1).jpg")));
        image3.setImage(new Image(new FileInputStream("src/main/java/GUI/Register/resources/download (1).jpg")));
        image4.setImage(new Image(new FileInputStream("src/main/java/GUI/Register/resources/download (1).jpg")));
        image5.setImage(new Image(new FileInputStream("src/main/java/GUI/Register/resources/download (1).jpg")));
        image6.setImage(new Image(new FileInputStream("src/main/java/GUI/Register/resources/download (1).jpg")));
        image7.setImage(new Image(new FileInputStream("src/main/java/GUI/Register/resources/download (1).jpg")));
        image8.setImage(new Image(new FileInputStream("src/main/java/GUI/Register/resources/download (1).jpg")));
        image9.setImage(new Image(new FileInputStream("src/main/java/GUI/Register/resources/download (1).jpg")));
        Random random = new Random();
        r = random.nextInt(9) + 1;
        if (r == 1) {
            image1.setImage(new Image(new FileInputStream("src/main/java/GUI/Register/resources/download (2).jpg")));
        } else if (r == 2) {
            image2.setImage(new Image(new FileInputStream("src/main/java/GUI/Register/resources/download (2).jpg")));
        } else if (r == 3) {
            image3.setImage(new Image(new FileInputStream("src/main/java/GUI/Register/resources/download (2).jpg")));
        } else if (r == 4) {
            image4.setImage(new Image(new FileInputStream("src/main/java/GUI/Register/resources/download (2).jpg")));
        } else if (r == 5) {
            image5.setImage(new Image(new FileInputStream("src/main/java/GUI/Register/resources/download (2).jpg")));
        } else if (r == 6) {
            image6.setImage(new Image(new FileInputStream("src/main/java/GUI/Register/resources/download (2).jpg")));
        } else if (r == 7) {
            image7.setImage(new Image(new FileInputStream("src/main/java/GUI/Register/resources/download (2).jpg")));
        } else if (r == 8) {
            image8.setImage(new Image(new FileInputStream("src/main/java/GUI/Register/resources/download (2).jpg")));
        } else if (r == 9) {
            image9.setImage(new Image(new FileInputStream("src/main/java/GUI/Register/resources/download (2).jpg")));
        }
    }


    public void Back(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/GUI/Login/Login.fxml"));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    public void clicked1(MouseEvent mouseEvent) throws FileNotFoundException, InterruptedException {
        if (r == 1) {
            disableAll();
        } else {
            initialize();
        }
    }

    public void clicked2(MouseEvent mouseEvent) throws FileNotFoundException, InterruptedException {
        if (r == 2) {
            disableAll();
        } else {
            initialize();
        }
    }

    public void clicked4(MouseEvent mouseEvent) throws FileNotFoundException, InterruptedException {
        if (r == 4) {
            disableAll();
        } else {
            initialize();
        }
    }

    public void clicked5(MouseEvent mouseEvent) throws FileNotFoundException, InterruptedException {
        if (r == 5) {
            disableAll();
        } else {
            initialize();
        }
    }

    public void clicked3(MouseEvent mouseEvent) throws FileNotFoundException, InterruptedException {
        if (r == 3) {
            disableAll();
        } else {
            initialize();
        }
    }

    public void clicked6(MouseEvent mouseEvent) throws FileNotFoundException, InterruptedException {
        if (r == 6) {
            disableAll();
        } else {
            initialize();
        }
    }

    public void clicked7(MouseEvent mouseEvent) throws FileNotFoundException, InterruptedException {
        if (r == 7) {
            disableAll();
        } else {
            initialize();
        }
    }

    public void clicked8(MouseEvent mouseEvent) throws FileNotFoundException, InterruptedException {
        if (r == 8) {
            disableAll();
        } else {
            initialize();
        }
    }

    public void clicked9(MouseEvent mouseEvent) throws FileNotFoundException, InterruptedException {
        if (r == 9) {
            disableAll();
        } else {
            initialize();
        }
    }

    private void disableAll() {
        back.setDisable(false);
        image1.setDisable(true);
        image2.setDisable(true);
        image3.setDisable(true);
        image4.setDisable(true);
        image5.setDisable(true);
        image6.setDisable(true);
        image7.setDisable(true);
        image8.setDisable(true);
        image9.setDisable(true);
    }

}
