package Pages;
import javafx.beans.binding.DoubleExpression;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class Home extends Page {
    public Home(Pane page, Double height, Double width) {
        super(page, height, width);
    }

    public void draw() {
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
