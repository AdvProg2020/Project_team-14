package GUI.ProductView;

import GUI.Media.Audio;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Line;

import javax.sound.sampled.AudioInputStream;
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
    private boolean hasRate;

    @FXML
    public void initialize() {
        updateLines();
    }

    private void updateLines() {
        /*
         *   get point data from server and make black lines
         */

        ArrayList<Integer> points = new ArrayList<>();
        points.add(2);
        points.add(1);
        points.add(4);
        points.add(5);
        points.add(8);
        int sum = 20;
        if (sum == 0) return;

        line1.setEndX((points.get(0)*1.0 / sum) * 100 - 100);
        line2.setEndX((points.get(1)*1.0 / sum) * 100 - 100);
        line3.setEndX((points.get(2)*1.0 / sum) * 100 - 100);
        line4.setEndX((points.get(3)*1.0 / sum) * 100 - 100);
        line5.setEndX((points.get(4)*1.0 / sum) * 100 - 100);
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

    public void setRate(MouseEvent mouseEvent) {
        Audio.playClick2();
        if (hasRate) return;
        if (hasRate) return;
        hasRate = true;

        //... send point to server
    }
}
