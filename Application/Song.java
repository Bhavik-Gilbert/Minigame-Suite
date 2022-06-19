package Application;

import java.io.File;
import java.util.ArrayList;

import Pages.Page;
import Pages.MiniGames.Games;
import Tools.SoundPlayer;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

public class Song {
    private static Stage stage = new Stage();

    public static void start(double height, double width) throws Exception {
        FlowPane root = new FlowPane();
        root.alignmentProperty().setValue(Pos.CENTER);

        displaySongs(root);


        Scene scene = new Scene(root, width/3, height/4);
        scene.getStylesheets().add("resources/CSS/volume.css");

        stage.setTitle("Songlist");
        stage.setScene(scene);
        stage.show();
        stage.show();
    }

    private static void displaySongs(FlowPane root){
        ArrayList<String> songs = new ArrayList<String>();

        switch(SoundPlayer.getPageType()) {
            case MAIN:
                songs = Page.getSongs();
                break;
            case GAME:
                songs = Games.getSongs();
                break;
            default:
                songs = Page.getSongs();
        }

        if(songs == null) return;

        songs.forEach((filePath) -> {
            String song = filePath.substring(filePath.lastIndexOf(File.separator) + 1);
            Label songLabel = new Label(song);
            songLabel.setId("songbutton");
            songLabel.setOnMouseClicked((e) -> {
                SoundPlayer.stopMusic();
                SoundPlayer.playMusic(filePath);
                switch (SoundPlayer.getPageType()) {
                    case MAIN:
                        Page.changeSongIndex(filePath);
                        break;
                    case GAME:
                        Games.changeSongIndex(filePath);
                        break;
                }
            });
            root.getChildren().add(songLabel);
        });
    }

    public static void stop() {
        stage.hide();
    }
    
}
