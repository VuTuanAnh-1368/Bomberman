package uet.oop.bomberman.sound;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

public class Sound {
    public final static Media bombExplosion = new Media(new File("Resources/gamesounds/bombexplosion.mp3").toURI().toString());
    public final static Media getItem = new Media(new File("Resources/gamesounds/getItemSound.wav").toURI().toString());
    public final static Media intro = new Media(new File("Resources/gamesounds/introMusic.mp3").toURI().toString());
    public final static Media bomberDi = new Media(new File("Resources/gamesounds/OiDoiOi.mp3").toURI().toString());
    public final static Media loseGame = new Media(new File("Resources/gamesounds/loseGame.wav").toURI().toString());
    public final static Media winGame = new Media(new File("Resources/gamesounds/victory.mp3").toURI().toString());
    public final static Media musicInGame = new Media(new File("Resources/gamesounds/gameSound.mp3").toURI().toString());
    public final static Media plantBomb = new Media(new File("Resources/gamesounds/plantBomb.mp3").toURI().toString());

    public final static MediaPlayer bombExplosionSound = new MediaPlayer(bombExplosion);
    public final static MediaPlayer getItemSound = new MediaPlayer(getItem);
    public final static MediaPlayer introMusic = new MediaPlayer(intro);
    public final static MediaPlayer bomberDieSound = new MediaPlayer(bomberDi);
    public final static MediaPlayer loseGameSound = new MediaPlayer(loseGame);
    public final static MediaPlayer winGameSound = new MediaPlayer(winGame);
    public final static MediaPlayer playMusicInGame = new MediaPlayer(musicInGame);
    public final static MediaPlayer plantBombSound = new MediaPlayer(plantBomb);

    public static void playMedia(MediaPlayer mp) {
        mp.play();
        mp.seek(mp.getStartTime());
        mp.setVolume(0.5);
    }

    public static void playMusic(MediaPlayer mp) {
        mp.play();
        mp.setCycleCount(-1);
        mp.seek(mp.getStartTime());
        mp.setVolume(0.4);
    }
}
