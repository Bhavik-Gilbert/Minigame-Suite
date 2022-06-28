package Pages.MiniGames;


import Pages.Page;
import Pages.Selection;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.util.Duration;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import java.io.File;
import java.util.*;

import Application.Song;
import Application.Volume;
import Tools.SoundPlayer;

public abstract class Games extends Page {
    protected GridPane grid;
    private Label timeLabel;

    protected Integer playerCount;
    private Timeline timer;

    private static int gameSoundIndex = 0;
    private static ArrayList<String> gameSoundPaths = new ArrayList<String>(
        Arrays.asList(
                "Resources" + File.separator + "Sounds" + File.separator + "VoloTheme.wav",
                "Resources" + File.separator + "Sounds" + File.separator + "GiritinaTheme.wav"
        ));

    public static String getSoundPath() {
        return gameSoundPaths.get(gameSoundIndex);
    }

    public static ArrayList<String> getSongs() {
        return gameSoundPaths;
    }
    
    public static void changeSongIndex(String filePath) {
        if (gameSoundPaths.contains(filePath)) {
            gameSoundIndex = gameSoundPaths.indexOf(filePath);
        }
    }

    public Games(Pane page, BorderPane root, Double height, Double width) {
        super(page, root, height, width);
    }

    protected GridPane drawGrid() {
        this.root.setId("gameBackground");
        this.playTheme(Games.getSoundPath());
        SoundPlayer.setMusicGame(this.getMusicMenu());
        
        grid = new GridPane();
        grid.setHgap(this.width/10);
        grid.setVgap(this.height/50);
        grid.setPadding(new Insets(this.height/50, this.width/50, this.height/50, this.width/50));

        HBox row = new HBox();
        row.setSpacing(this.width/10);
        row.setPadding(new Insets(this.height/50, this.width/50, this.height/50, this.width/50));

        Button quit = new Button("Quit");
        quit.setOnAction(e -> gameFinish());

        timeLabel = new Label("0:00");
        timeLabel.setId("timer");
        timer(timeLabel);

        row.getChildren().addAll(timeLabel, quit);
        grid.addRow(0, row);

        return grid;
    }

    protected VBox playerNumber(int players) {
        VBox vbox = new VBox();
        vbox.setAlignment(Pos.CENTER);

        Label question = new Label("How many players?");
        question.setId("information");

        FlowPane flow = new FlowPane();
        flow.setAlignment(Pos.CENTER);
        for(int i=2; i<=players; i++) {
            Label label = new Label(Integer.toString(i));
            label.setId("button");
            label.setOnMouseClicked((e)-> {
                this.playerCount = Integer.parseInt(label.getText());
                this.draw();
            });

            flow.getChildren().add(label);
        }
        
        vbox.getChildren().addAll(question, flow);

        return vbox;
    }

    private void timer(Label label) {
        this.timer = new Timeline();
        timer.setCycleCount(Timeline.INDEFINITE);
            if(timer!=null) {
                timer.stop();
            }
        KeyFrame frame = new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String time =  label.getText();
                String[] timeSplit = time.split(":");
                Integer minutes = Integer.parseInt(timeSplit[0]);
                Integer seconds = Integer.parseInt(timeSplit[1]);

                seconds+=1;

                if(seconds==60) {
                    minutes+=1;
                    seconds=0;
                }

                if(minutes==60) {
                   // max time reached
                }

                if (seconds < 10) {
                    label.setText(minutes + ":0" + seconds);
                } else {
                    label.setText(minutes + ":" + seconds);
                }
              }
        });

        timer.getKeyFrames().add(frame);
        timer.playFromStart();
    }

    protected void gameFinish() {
        Page game = new Selection(this.page, this.root, this.height, this.width);
        this.root.setId(null);
        transitionOut(this.page);
        SoundPlayer.stopMusic();
        SoundPlayer.setMusicHome(this.getMusicMenu());
        Volume.stop();
        Song.stop();
        timer.stop();
        game.draw();
    }

    protected void stopTimer() {
        timer.stop();   
    }

    protected String getFinalTime() {
        timer.stop();
        return timeLabel.getText();
    }
}
