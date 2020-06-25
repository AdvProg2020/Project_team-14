package GUI.Cart;

import GUI.Media.Audio;
import javafx.scene.input.MouseEvent;

public class Controller {
    public void back(MouseEvent mouseEvent) {
        Audio.playClick1();
    }

    public void useOffCode(MouseEvent mouseEvent) {
        Audio.playClick2();
    }

    public void buy(MouseEvent mouseEvent) {
        Audio.playClick3();
    }
}
