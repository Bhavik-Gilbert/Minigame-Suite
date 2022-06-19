package Pages;

import java.io.File;
import java.util.*;
import java.util.concurrent.Flow;

import Application.Song;
import Application.Volume;
import Tools.ImageReader;
import Tools.SoundPlayer;
import Pages.MiniGames.Blackjack;
import Pages.MiniGames.Games;
import Pages.MiniGames.Checkers_R.Checkers;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

public class Selection extends Page {
    HashMap<String, Games> games;

    public Selection(Pane page, Double height, Double width) {
        super(page, height, width);
    }

    public void draw() {
            this.playTheme(Page.getSoundPath());
            SoundPlayer.setMusicHome(this.getMusicMenu());
            this.clear();
            transitionIn(page);

            generateGames();

            FlowPane flow = new FlowPane();
            showGames(flow);

            this.page.getChildren().add(flow);
    }

    private void generateGames() {
        this.games = new HashMap<String, Games>();

        this.games.put("Checkers", new Checkers(this.page, this.height, this.width));
        this.games.put("Blackjack", new Blackjack(this.page, this.height, this.width));
    }

    private void showGames(FlowPane flow) {
        //flow.setVgap(this.height/3);
        //flow.setHgap(this.width/4);
        flow.orientationProperty().setValue(Orientation.VERTICAL);
        
        this.games.forEach((k,v) -> {
            VBox game = new VBox();
            game.setAlignment(Pos.CENTER);
            game.setId("gameselection");

            Label logo = new Label();
            ImageView image = ImageReader.readImage("Resources" + File.separator + "Images" + File.separator + "Logos" + File.separator + k  + ".png", this.height/7, this.width/7);
            if(image != null) {
                logo.setGraphic(image);
            }

            Label label = new Label(k);
            label.setId("gameselectionlabel");

            game.getChildren().addAll(label, logo);

            game.setOnMouseClicked((e)->{
                transitionOut(page);
                SoundPlayer.stopMusic();
                Volume.stop();
                Song.stop();
                v.draw();
            });

            flow.getChildren().add(game);
        });
    }
}