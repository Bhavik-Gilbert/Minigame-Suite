import javafx.scene.layout.Pane;

public class Checkers extends Games {
    public Checkers(Pane page, Double height, Double width) {
        super(page, height, width);
    }

    public void draw() {
        this.clear();
        transitionIn(page);
    }
}
