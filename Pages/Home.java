package Pages;
import Tools.Constants;
import Tools.SoundPlayer;

import javafx.scene.control.*;
import javafx.scene.layout.*;

public class Home extends Page {
    public Home(Pane page, Double height, Double width) {
        super(page, height, width);
    }

    public void draw() {
        this.playTheme(Constants.getHomeSoundPath(), true);
        this.clear();
        transitionIn(page);

        page.setId("home");

        Label start = new Label("Play"); 
        start.setId("mainbutton");
        start.setOnMouseClicked((e)->{
            Page game = new Selection(this.page, this.height, this.width);
            transitionOut(page);
            game.draw();
        });

        AnchorPane anchor = new AnchorPane();
        AnchorPane.setTopAnchor(start, height/5);
        AnchorPane.setLeftAnchor(start, width/15);
        anchor.getChildren().add(start);

        page.getChildren().add(anchor);
    }
}
