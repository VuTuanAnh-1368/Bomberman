package uet.oop.bomberman.userInterface;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import java.awt.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import static uet.oop.bomberman.userInterface.Main.*;
import static uet.oop.bomberman.userInterface.Main.userLoginScene;

public class HomePageController implements Initializable {

    @FXML
    private Button playButton;

    @FXML
    private Button instructionButton;

    @FXML
    private Button exitButton;

    public void setPlayButton() throws IOException {
        Stage stage = (Stage) playButton.getScene().getWindow();
        stage.setTitle("Enter your name");
        stage.setScene(userLoginScene);
    }


    public void setInstructionButton() {
        Stage stage = new Stage();
        StackPane stackPane = new StackPane();
        Image instructionImage = null;
        try {
            instructionImage = new Image(new FileInputStream("res/image/instruction.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        ImageView imageView = new ImageView(instructionImage);
        stackPane.getChildren().add(imageView);
        stage.setTitle("Bomberman Instruction");
        stage.setScene(new Scene(stackPane, 1280, 600));
        stage.show();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        instructionButton.setOnAction(actionEvent -> {
            setInstructionButton();
        });
        playButton.setOnAction(actionEvent -> {
            try {
                setPlayButton();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        exitButton.setOnAction(actionEvent -> {
            setExitButton();
        });
    }

    public void setExitButton() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("EXIT BOMBERMAN");
        alert.setHeaderText("Are you sure? Play one more time please!");
        ButtonType yes = new ButtonType("YES", ButtonBar.ButtonData.YES);
        ButtonType no = new ButtonType("NO", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(yes, no);
        Optional<ButtonType> answer = alert.showAndWait();
        if (answer.get() == yes) {
            Platform.exit();
            System.out.println("You have quit game.");
        } else {
            if (answer.get() == no) {
                alert.close();
            }
        }
    }
}
