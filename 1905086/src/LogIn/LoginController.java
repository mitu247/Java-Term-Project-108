package LogIn;

import Search.PlaySearches;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import util.LoginDTO;

import java.io.IOException;


public class LoginController {

    private PlaySearches playSearches;
    private String inputClubName;

    private Main main;

    @FXML
    private TextField userText;

    @FXML
    private PasswordField passwordText;

    @FXML
    private Button resetButton;

    @FXML
    private Button loginButton;
    @FXML
    private ImageView image;

    private boolean correctInput = false;

    @FXML
    void loginAction(ActionEvent event) throws IOException {

        String clubName = userText.getText();
        String password = passwordText.getText();

        if (clubName.length() != 0 && password.length() != 0) {
            LoginDTO lg = new LoginDTO();
            lg.setUserName(clubName);
            lg.setPassword(password);
            main.getNetworkUtil().write(lg);
        }
    }

    @FXML
    void resetAction(ActionEvent event) {
        userText.setText("");
        passwordText.setText("");
    }
    @FXML
    void exit(ActionEvent event) {
        main.getStage().close();
    }

    void setMain(Main main) {
        this.main = main;
    }

    public void loadimage() {
        Image img = new Image(Main.class.getResourceAsStream("logIn.jpg"));
        image.setImage(img);
    }

    public void setPlaySearches(PlaySearches playSearches) {
        this.playSearches = playSearches;
    }
}
