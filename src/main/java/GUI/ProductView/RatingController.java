package GUI.ProductView;

import GUI.Media.Audio;
import GUI.MenuHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Line;
import javafx.stage.Popup;

import javax.sound.sampled.AudioInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

public class RatingController {
    public ImageView star1;
    public ImageView star2;
    public ImageView star3;
    public ImageView star4;
    public ImageView star5;
    public HBox starList;
    public Line line5;
    public Line line4;
    public Line line3;
    public Line line2;
    public Line line1;
    public Label total;
    public Label average;
    private boolean hasRate;

    @FXML
    public void initialize() throws IOException, ParseException {
        updateLines();
    }

    private void updateLines() throws ParseException, IOException {

        MenuHandler.getServer().clientToServer("what is point product+" + MenuHandler.getProductID());
        String rate = MenuHandler.getServer().serverToClient();


        ArrayList<Integer> points = new ArrayList<>();
        points.add(Integer.valueOf(rate.split("\\+")[0]));
        points.add(Integer.valueOf(rate.split("\\+")[1]));
        points.add(Integer.valueOf(rate.split("\\+")[2]));
        points.add(Integer.valueOf(rate.split("\\+")[3]));
        points.add(Integer.valueOf(rate.split("\\+")[4]));
        int sum = Integer.valueOf(rate.split("\\+")[0]) + Integer.valueOf(rate.split("\\+")[1]) +
                Integer.valueOf(rate.split("\\+")[2]) + Integer.valueOf(rate.split("\\+")[3])
                + Integer.valueOf(rate.split("\\+")[4]);
        if (sum == 0) return;

        total.setText(String.valueOf(sum));
        double av = Integer.valueOf(rate.split("\\+")[0]) * 1 + Integer.valueOf(rate.split("\\+")[1]) * 2 +
                Integer.valueOf(rate.split("\\+")[2]) * 3 + Integer.valueOf(rate.split("\\+")[3]) * 4 +
                Integer.valueOf(rate.split("\\+")[4]) * 5;
        av = av / sum;
        int Av = (int) (av * 10);
        average.setText(String.valueOf(Av).charAt(0) + "." + String.valueOf(Av).charAt(1));

        line1.setEndX((points.get(0) * 1.0 / sum) * 100 - 100);
        line2.setEndX((points.get(1) * 1.0 / sum) * 100 - 100);
        line3.setEndX((points.get(2) * 1.0 / sum) * 100 - 100);
        line4.setEndX((points.get(3) * 1.0 / sum) * 100 - 100);
        line5.setEndX((points.get(4) * 1.0 / sum) * 100 - 100);
    }

    public void changeStars(MouseEvent mouseEvent) {
        if (hasRate) return;
        String starId = ((ImageView) mouseEvent.getSource()).getId();
        int number = Integer.parseInt(starId.substring(4));
        Image filledStar = new Image("file:src/main/java/GUI/ProductView/resources/filledStar.png");
        for (int i = 0; i < number; i++) {
            ((ImageView) starList.getChildren().get(i)).setImage(filledStar);
        }
    }

    public void backToNormal(MouseEvent mouseEvent) {
        if (hasRate) return;

        Image whiteStar = new Image("file:src/main/java/GUI/ProductView/resources/whiteStar.png");
        for (int i = 0; i < 5; i++) {
            ((ImageView) starList.getChildren().get(i)).setImage(whiteStar);
        }
    }

    public void setRate(MouseEvent mouseEvent) throws ParseException, IOException {
        Audio.playClick2();
        if (hasRate) return;
        hasRate = true;
        String starId = ((ImageView) mouseEvent.getSource()).getId();
        int number = Integer.parseInt(starId.substring(4));

        MenuHandler.getServer().clientToServer("point product+" + MenuHandler.getUsername() + "+" + MenuHandler.getProductID() + "+" + number);
        updateLines();
    }

    public void exit(ActionEvent actionEvent) throws IOException {
        Audio.playClick6();
        Popup popup = (Popup) ((Button) actionEvent.getSource()).getScene().getWindow();
        popup.hide();
        Parent root = FXMLLoader.load(getClass().getResource("/GUI/ProductView/ProductViewLayout.fxml"));
        MenuHandler.getStage().setScene(new Scene(root));
    }
}
