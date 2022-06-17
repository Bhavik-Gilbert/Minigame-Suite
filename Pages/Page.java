package Pages;
import javafx.util.Duration;
import javafx.animation.AnimationTimer;
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
        AnimationTimer tm = new TimerMethod();
        tm.start();

        FadeTransition transition = new FadeTransition(Duration.millis(300), window);
        transition.setFromValue(1.0);
        transition.setToValue(0.0);
        transition.play();
    }

    /**
     * Plays transition in.
     */
    protected void transitionIn(Pane window) {
        AnimationTimer tm = new TimerMethod();
        tm.start();
        
        FadeTransition transition = new FadeTransition(Duration.millis(300), window);
        transition.setFromValue(0.0);
        transition.setToValue(1.0);
        transition.play();

        
    }
}

class TimerMethod extends AnimationTimer {
    private double op = 1;
    // define the handle method
    @Override
    public void handle(long now) {
        // call the method
        handlee();
    }

    // method handlee
    private void handlee() {
        op -= 0.01;
        if (op <= 0) {
            stop();
        }
    }
}
