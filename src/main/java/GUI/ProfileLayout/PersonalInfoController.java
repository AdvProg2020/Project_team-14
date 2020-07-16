package GUI.ProfileLayout;

import GUI.Media.Audio;
import GUI.MenuHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Popup;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.ParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PersonalInfoController {

    private String theRole, theTelephone, theEmail, thePassword, theBalance, theCompanyInfo, theLastName,
            theUsername, theFirstName;
    public TextField role;
    public TextField telephone;
    public TextField Email;
    public TextField password;
    public TextField username;
    public TextField balance;
    public TextField companyInfo;
    public TextField lastName;
    public TextField firstName;
    public Label companyInfoLabel;
    public Label balanceLabel;

    public void initialize() throws ParseException, IOException {
        MenuHandler.getServer().clientToServer("view personal info+" + MenuHandler.getUsername());
        String serverAnswer = MenuHandler.getServer().serverToClient();
        String[] information = serverAnswer.split("\\s");
        username.setText(information[1]);
        password.setText(information[3]);
        firstName.setText(information[5]);
        lastName.setText(information[6]);
        Email.setText(information[8]);
        telephone.setText(information[10]);
        role.setText(information[12]);
        theRole = role.getText();
        theTelephone = telephone.getText();
        theBalance = balance.getText();
        theCompanyInfo = companyInfo.getText();
        theEmail = Email.getText();
        theFirstName = firstName.getText();
        theLastName = lastName.getText();
        thePassword = password.getText();
        theUsername = username.getText();
        if (information[12].equalsIgnoreCase("boss")) {
            balance.setVisible(false);
            companyInfoLabel.setVisible(false);
            balanceLabel.setVisible(false);
            companyInfo.setVisible(false);
        } else if (information[12].equalsIgnoreCase("salesman")) {
            companyInfo.setText(information[14]);
            balance.setText(information[16]);
        } else {
            balance.setText(information[14]);
            companyInfoLabel.setVisible(false);
            companyInfo.setVisible(false);
        }
    }

    public void chargeWallet(ActionEvent actionEvent) throws IOException {
        Audio.playClick3();
        Popup popup = new Popup();
        popup.getContent().add(FXMLLoader.load(getClass().getResource("/GUI/ProfileLayout/ChargeWallet.fxml")));
        popup.show(((Button) actionEvent.getSource()).getScene().getWindow());
        popup.setOnHiding(e -> {
            try {
                initialize();
            } catch (ParseException | IOException ex) {
                ex.printStackTrace();
            }
        });
    }

    public void returnable(ActionEvent actionEvent) throws IOException {
        Parent root;
        root = FXMLLoader.load(getClass().getResource("/GUI/ProfileLayout/ProfileLayout.fxml"));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    public void editInfo(ActionEvent actionEvent) throws ParseException, IOException {
        Audio.playClick5();
        String message = "";
        boolean edit = false;
        if (!username.getText().equals(theUsername)) {
            edit = true;
            if (!checkUsernameFormat(username.getText())) {
                message += "Username Format Is Invalid\n";
            } else {
                MenuHandler.getServer().clientToServer("edit personal info+username+" + username.getText() + "+" + MenuHandler.getUsername());
                String serveAnswer = MenuHandler.getServer().serverToClient();
                if (serveAnswer.equals("username already exists, choose another one!")) {
                    message += "username already exists, choose another one!\n";
                } else {
                    MenuHandler.setUsername(username.getText());
                    message += "Username Has Been Edited Successfully\n";
                }
            }
        }
        if (!firstName.getText().equals(theFirstName)) {
            edit = true;
            if (checkNameFormat(firstName.getText())) {
                message += "First Name Format Is Invalid\n";
            } else {
                MenuHandler.getServer().clientToServer("edit personal info+firstName+" + firstName.getText() + "+" + MenuHandler.getUsername());
                String serveAnswer = MenuHandler.getServer().serverToClient();
                message += "First Name Has Been Edited Successfully\n";
            }
        }
        if (!lastName.getText().equals(theLastName)) {
            edit = true;
            if (checkNameFormat(lastName.getText())) {
                message += "Last Name Format Is Invalid\n";
            } else {
                MenuHandler.getServer().clientToServer("edit personal info+lastName+" + lastName.getText() + "+" + MenuHandler.getUsername());
                String serveAnswer = MenuHandler.getServer().serverToClient();
                message += "Last Name Has Been Edited Successfully\n";
            }
        }
        if (!Email.getText().equals(theEmail)) {
            edit = true;
            if (!checkEmailFormat(Email.getText())) {
                message += "Email Format Is Invalid\n";
            } else {
                MenuHandler.getServer().clientToServer("edit personal info+Email+" + Email.getText() + "+" + MenuHandler.getUsername());
                String serveAnswer = MenuHandler.getServer().serverToClient();
                message += "Email Has Been Edited Successfully\n";
            }
        }
        if (!telephone.getText().equals(theTelephone)) {
            edit = true;
            if (!checkTelephoneFormat(telephone.getText())) {
                message += "Telephone Format Is Invalid\n";
            } else {
                MenuHandler.getServer().clientToServer("edit personal info+telephone+" + telephone.getText() + "+" + MenuHandler.getUsername());
                String serveAnswer = MenuHandler.getServer().serverToClient();
                message += "Telephone Has Been Edited Successfully\n";
            }
        }
        if (!companyInfo.getText().equals(theCompanyInfo)) {
            edit = true;
            MenuHandler.getServer().clientToServer("edit personal info+company+" + companyInfo.getText() + "+" + MenuHandler.getUsername());
            String serveAnswer = MenuHandler.getServer().serverToClient();
            message += "Company Has Been Edited Successfully\n";
        }
        if (!edit) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "You Changed Nothing", ButtonType.OK);
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, message, ButtonType.OK);
            alert.showAndWait();
//            returnable(actionEvent);
            initialize();
        }
    }

    public void changePassword(ActionEvent actionEvent) throws IOException {
        Audio.playClick7();
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("ChangePassLayout.fxml"));
        Popup popup = new Popup();
        popup.getContent().add(root);
        popup.setOnHiding(event -> {
            try {
                initialize();
            } catch (ParseException | IOException e) {
                e.printStackTrace();
            }
        });
        popup.show(stage);
    }

    private Matcher getMatcher(String regex, String command) {
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(command);
    }

    private boolean checkMoneyFormat(String money) {
        if (money.equals("")) return false;
        return getMatcher("(\\d)+", money).matches();
    }

    private boolean checkNameFormat(String name) {
        if (name.equals("")) return true;
        return !getMatcher("[a-zA-Z]+", name).matches();
    }

    private boolean checkUsernameFormat(String input) {
        if (input.equals("")) return false;
        return getMatcher("(\\w+)", input).matches();
    }

    private boolean checkEmailFormat(String mail) {
        if (mail.equals("")) return false;
        return getMatcher("((\\w||\\.)+)@(\\w+)\\.(com||ir||io||edu)", mail).matches();
    }

    private boolean checkTelephoneFormat(String number) {
        if (number.equals("")) return false;
        return getMatcher("0(\\d+)", number).matches() && number.length() == 11;
    }

    public void uploadImg(ActionEvent actionEvent) throws ParseException, IOException {
        Audio.playClick5();
        /*Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("upload your image");
        File chosenFile = fileChooser.showOpenDialog(stage);
        if (chosenFile == null) return;
        String path = String.valueOf(chosenFile.toURI());
        MenuHandler.getServer().clientToServer("set person image+" + MenuHandler.getUsername() + "+" + path);
        ProfileLayoutController profileLayoutController = new ProfileLayoutController();
       // profileLayoutController.profileImage.setImage(new Image(String.valueOf(chosenFile.toURI())));
        System.out.println("set person image+" + MenuHandler.getUsername() + "+" + path);
        System.err.println(path);
       // profileLayoutController.setProfileImage();

         */
    }

    public void deleteImg(ActionEvent actionEvent) throws ParseException, IOException {
        Audio.playClick5();
    }

}
