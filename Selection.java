import java.util.*;
import java.util.concurrent.Flow;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.*;

public class Selection extends Page {
    HashMap<String, Games> games;

    public Selection(Pane page, Double height, Double width) {
        super(page, height, width);
    }

    public void draw() {
            this.clear();
            transitionIn(page);

            generateGames();

            FlowPane flow = new FlowPane();
            showGames(new FlowPane());

            this.page.getChildren().add(flow);
    }

    private void generateGames() {
        this.games = new HashMap<String, Games>();
    }

    private void showGames(FlowPane flow) {
        flow.setVgap(this.height/3);
        flow.setHgap(this.width/4);

        this.games.forEach((k,v) -> {
            Label label = new Label(k);
            label.setId("mainbutton");
            label.setOnMouseClicked((e)->{
                Page game = v;
                transitionOut(page);
                game.draw();
            });

            flow.getChildren().add(label);
        });
    }
}