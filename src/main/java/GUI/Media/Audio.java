package GUI.Media;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

public class Audio {

    private static boolean muteness = false;
    private static String path = "src/main/java/GUI/Media/resources/1.mp3";
    private static Media media = new Media(new File(path).toURI().toString());
    private static MediaPlayer mediaPlayer = new MediaPlayer(media);

    public static void playBackGroundMusic() {
        if (muteness) {
            return;
        }
        mediaPlayer.setAutoPlay(true);
    }

    public static void playClick1() {
        if (muteness) {
            return;
        }
        String path = "src/main/java/GUI/Media/resources/click audio/1.mp3";
        Media media = new Media(new File(path).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setAutoPlay(true);
    }

    public static void playClick2() {
        if (muteness) {
            return;
        }
        String path = "src/main/java/GUI/Media/resources/click audio/2.mp3";
        Media media = new Media(new File(path).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setAutoPlay(true);
    }

    public static void playClick3() {
        if (muteness) {
            return;
        }
        String path = "src/main/java/GUI/Media/resources/click audio/3.mp3";
        Media media = new Media(new File(path).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setAutoPlay(true);
    }

    public static void playClick4() {
        if (muteness) {
            return;
        }
        String path = "src/main/java/GUI/Media/resources/click audio/4.mp3";
        Media media = new Media(new File(path).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setAutoPlay(true);

    }

    public static void playClick5() {
        if (muteness) {
            return;
        }
        String path = "src/main/java/GUI/Media/resources/click audio/5.mp3";
        Media media = new Media(new File(path).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setAutoPlay(true);
    }

    public static void playClick6() {
        if (muteness) {
            return;
        }
        String path = "src/main/java/GUI/Media/resources/click audio/6.mp3";
        Media media = new Media(new File(path).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setAutoPlay(true);
    }

    public static void playClick7() {
        if (muteness) {
            return;
        }
        String path = "src/main/java/GUI/Media/resources/click audio/7.mp3";
        Media media = new Media(new File(path).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setAutoPlay(true);
    }

    public static void mute() {
        mediaPlayer.setMute(true);
        muteness = true;
    }

    public static void unMute() {
        mediaPlayer.setMute(false);
        muteness = false;
    }

}
