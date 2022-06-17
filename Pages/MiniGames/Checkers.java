package Pages.MiniGames;

import Pages.Selection;
import javafx.scene.control.Button;
import javafx.scene.layout.*;

public class Checkers extends Games {
    public Checkers(Pane page, Double height, Double width) {
        super(page, height, width);
    }

    public void draw() {
        this.clear();
        transitionIn(page);

        drawGrid();
        page.getChildren().add(this.grid);
    }

    private void generateBoard() {
        
    }
}
