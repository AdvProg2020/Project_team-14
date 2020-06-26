package GUI.Register;

import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;

import javafx.util.Duration;
import java.util.ArrayList;

public class AvatarController {
    final Duration DURATION = Duration.millis(500);
    public Circle source;
    public Circle destination1;
    public Circle destination2;
    public Circle destination3;
    public Circle destination4;
    public Circle destination5;
    public ImageView image1;
    public ImageView image2;
    public ImageView image3;
    public ImageView image4;
    public ImageView image5;
    ArrayList<Circle> destinations = new ArrayList<>();
    ArrayList<ImageView> images = new ArrayList<>();
    ArrayList<Double> toX;
    ArrayList<Double> toY;
    boolean isOtherVisible = false;




    @FXML
    public void initialize() {
        destinations.add(destination1);
        destinations.add(destination2);
        destinations.add(destination3);
        destinations.add(destination4);
        destinations.add(destination5);

        images.add(image1);
        images.add(image2);
        images.add(image3);
        images.add(image4);
        images.add(image5);
        for (int i = 0; i < 5; i++) {
            if (i != 2) {
                images.get(i).setDisable(true);
                images.get(i).setOpacity(0);
            } else {
                images.get(i).setScaleX(1.5);
                images.get(i).setScaleY(1.5);
            }
        }

        toX = getTo("X");
        toY = getTo("Y");

        choseItem(2);
    }

    private ArrayList<Double> getTo(String type) {
        ArrayList<Double> ans = new ArrayList<>();
        for (Circle destination : destinations) {
            double toX = destination.getCenterX() - source.getCenterX();
            double toY = destination.getCenterY() - source.getCenterY();
            if (type.equals("X")) ans.add(toX);
            else ans.add(toY);
        }
        return ans;
    }

    public void changePolicy(MouseEvent mouseEvent) {
        int i = Integer.parseInt(((ImageView) mouseEvent.getSource()).getId().substring(5)) - 1;

        if (isOtherVisible) choseItem(i);
        else showChoices(i);
    }

    private void choseItem(int i) {
        isOtherVisible = false;
        moveToCenter(i);
        hideOthers(i);
    }

    private void showChoices(int i) {
        isOtherVisible = true;
        for (ImageView image : images) {
            image.setDisable(false);
        }
        moveToRound(i);
        showOthers(i);
    }

    private void moveToCenter(int i) {
        TranslateTransition tt = new TranslateTransition(DURATION, images.get(i));
        tt.setToX(-toX.get(i));
        tt.setToY(-toY.get(i));

        ScaleTransition st = new ScaleTransition(DURATION, images.get(i));
        st.setToX(2.2);
        st.setToY(2.2);

        ParallelTransition pt = new ParallelTransition(tt, st);
        pt.play();
    }

    private void hideOthers(int i) {
        ParallelTransition pt = new ParallelTransition();
        for (int j = 0; j < 5; j++) {
            if (j != i){
                FadeTransition ft = new FadeTransition(DURATION, images.get(j));
                ft.setFromValue(1);
                ft.setToValue(0);

                pt.getChildren().add(ft);
            }
        }

        pt.play();
        pt.setOnFinished(e -> {
            for (int j = 0; j < 5; j++) {
                if (j != i) images.get(j).setDisable(true);
            }
        });

    }

    private void moveToRound(int i) {
        TranslateTransition tt = new TranslateTransition(DURATION, images.get(i));
        tt.setFromX(- toX.get(i));
        tt.setFromY(- toY.get(i));
        tt.setToX(0);
        tt.setToY(0);

        ScaleTransition st = new ScaleTransition(DURATION, images.get(i));
        st.setToX(1);
        st.setToY(1);

        ParallelTransition pt = new ParallelTransition(tt, st);
        pt.play();
    }

    private void showOthers(int i) {
        ParallelTransition pt = new ParallelTransition();
        for (int j = 0; j < 5; j++) {
            if (j != i) {
                FadeTransition ft = new FadeTransition(DURATION, images.get(j));
                ft.setFromValue(0);
                ft.setToValue(1);

                pt.getChildren().add(ft);
            }
        }

        pt.play();
    }

}
