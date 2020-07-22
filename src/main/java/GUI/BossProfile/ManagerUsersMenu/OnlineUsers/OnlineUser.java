package GUI.BossProfile.ManagerUsersMenu.OnlineUsers;

import GUI.Media.Audio;
import GUI.MenuHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.io.IOException;
import java.text.ParseException;

public class OnlineUser {
    public VBox box;

    public void Back(MouseEvent mouseEvent) {
        Audio.playClick5();
        ((ImageView) mouseEvent.getSource()).getScene().getWindow().hide();
    }

    public void initialize() throws ParseException, IOException {
        MenuHandler.getConnector().clientToServer("get online users+");
        String result = MenuHandler.getConnector().serverToClient();
        if (result.equals("")) {
            Label label = new Label("no user in online");
            label.setFont(new Font("Arial", 30));
            box.getChildren().add(label);
            return;
        }
        for (String string : result.split("\n")) {
            Label label = new Label(string);
            label.setFont(new Font("Arial", 30));
            box.getChildren().add(label);
        }
    }

    public void refresh(MouseEvent mouseEvent) throws IOException, ParseException {
        box.getChildren().clear();
        initialize();
    }

}
