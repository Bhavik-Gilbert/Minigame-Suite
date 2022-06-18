package Pages.MiniGames;

import java.util.concurrent.ExecutorService;

import javax.crypto.spec.RC2ParameterSpec;

import Pages.Page;
import Pages.Selection;
import Tools.ImageReader;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.util.Duration;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public abstract class Games extends Page {
    protected GridPane grid;
    public Games(Pane page, Double height, Double width) {
        super(page, height, width);
    }

    protected GridPane drawGrid() {
        grid = new GridPane();
        grid.setHgap(this.width/10);
        grid.setVgap(this.height/50);
        grid.setPadding(new Insets(this.height/50, this.width/50, this.height/50, this.width/50));

        HBox row = new HBox();
        row.setSpacing(this.width/10);
        row.setPadding(new Insets(this.height/50, this.width/50, this.height/50, this.width/50));

        Button quit = new Button("Quit");
        quit.setOnAction(e -> {
            Page game = new Selection(this.page, this.height, this.width);
            transitionOut(page);
            game.draw();
        });

        Label time = new Label("0:00");
        time.setId("timer");
        timer(time);

        row.getChildren().addAll(time, quit);
        grid.addRow(0, row);

        return grid;
    }

    private void timer(Label label) {
        Timeline time = new Timeline();
        time.setCycleCount(Timeline.INDEFINITE);
            if(time!=null) {
                time.stop();
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

        time.getKeyFrames().add(frame);
        time.playFromStart();
    }
}
