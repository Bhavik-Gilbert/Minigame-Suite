import javafx.util.Duration;
import java.util.*;
import javafx.stage.Stage;
import javafx.animation.FadeTransition;
import javafx.scene.layout.*;

public abstract class Page {
    // The BorderPane for the page to be drawn onto.
    protected Pane page;

    protected double height;
    protected double width;

    /**
     * Constructor for the page.
     */
    public Page(Pane page, double height, double width) {
        this.page = page;
        this.height = height;
        this.width = width;
    }

    /**
     * Draws the page onto the BorderPane.
     */
    abstract public void draw();

    protected void clear() {
        this.page.getChildren().clear();
    }

    protected void transitionOut(Pane window) {
        int time = 300;

        FadeTransition transition = new FadeTransition(Duration.millis(time), window);
        transition.setFromValue(1.0);
        transition.setToValue(0.0);
        transition.play();

        /** 
        try {
            wait(time);
        } catch (InterruptedException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        */
    }

    /**
     * Plays transition in.
     */
    protected void transitionIn(Pane window) {
        int time = 300;
        FadeTransition transition = new FadeTransition(Duration.millis(300), window);
        transition.setFromValue(0.0);
        transition.setToValue(1.0);
        transition.play();

        /**
        try {
            wait(time);
        } catch (InterruptedException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        */
    }
}
