package uet.oop.bomberman.userInterface;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import static uet.oop.bomberman.sound.Sound.*;
import static uet.oop.bomberman.BombermanGame.point;
import static uet.oop.bomberman.userInterface.userLoginController.username;

public class Main extends Application {

    public static Scene homeScene = showScene("src/uet/oop/bomberman/userInterface/HomePage.fxml");
    public static Scene userLoginScene = showScene("sr" +
            "c/uet/oop/bomberman/userInterface/userLogin.fxml");

    public static void main(String[] args) {
        Platform.setImplicitExit(false);
        Application.launch(Main.class);
    }

    public static void addHistoryPlay(String name, int point) {
        String url = "HighScore.txt";
        FileReader fileReader = null;
        BufferedReader bufferedReader = null;
        FileWriter fileWriter = null;
        BufferedWriter bufferedWriter = null;
        List<String> contentLine = new ArrayList<>();
        try {
            fileReader = new FileReader(url);
            bufferedReader = new BufferedReader(fileReader);
            String getLine;
            while ((getLine = bufferedReader.readLine()) != null) {
                contentLine.add(getLine);
            }
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            fileWriter = new FileWriter(url);
            bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(contentLine.get(0));
            for (int i = 1; i < contentLine.size(); i++) {
                bufferedWriter.write("\n"+ contentLine.get(i));
            }
            bufferedWriter.write("\n" + name + "\t\t\t\t" + point);
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static Scene showScene(String url) {
        FXMLLoader fxmlLoader = null;
        try {
            fxmlLoader = new FXMLLoader(new File(url).toURI().toURL());
        } catch (Exception e) {
            e.printStackTrace();
        }
        Parent parent = null;
        try {
            parent = fxmlLoader.load();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(parent);
        return scene;
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setResizable(false);
        stage.setOnCloseRequest(e -> {
            Platform.exit();
            System.exit(0);
        });
        loadHomePage(stage);
    }

    public static void loadHomePage(Stage stage) throws IOException {
        playMusic(introMusic);
        stage.setResizable(false);
        stage.setTitle("BOMBERMAN");
        stage.setScene(homeScene);
        stage.show();
    }
}
