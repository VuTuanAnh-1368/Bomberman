package uet.oop.bomberman.userInterface;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import uet.oop.bomberman.BombermanGame;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static uet.oop.bomberman.userInterface.Main.*;
import static uet.oop.bomberman.sound.Sound.*;

public class userLoginController extends HomePageController implements Initializable {

    @FXML
    private Button startButton;

    @FXML
    private Button backButton;

    @FXML
    private TextField userName;

    public static BombermanGame bombermanGame = new BombermanGame();

    public static String username;

    public void setStartButton() {
        introMusic.stop();
        playMusic(playMusicInGame);
        username = userName.getText();
        Stage stage = (Stage) startButton.getScene().getWindow();
        Scene scene = bombermanGame.startGame(stage);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        startButton.setOnAction(actionEvent -> {
            setStartButton();
        });
        backButton.setOnAction(actionEvent -> {
            Stage stage = (Stage) backButton.getScene().getWindow();
            stage.setTitle("HOME PAGE");
            stage.setScene(homeScene);
        });
    }

    public static String getUsername() {
        return username;
    }
}
