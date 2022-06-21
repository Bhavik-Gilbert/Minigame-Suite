package Pages.MiniGames.Connect4_R;

import Pages.MiniGames.Games;
import javafx.scene.layout.*;

public class Connect4 extends Games {
    public Connect4(Pane page, BorderPane root, Double height, Double width) {
        super(page, root, height, width);
    }

    public void draw() {
        this.clear();
        transitionIn(page);

        drawGrid();
        page.getChildren().add(this.grid);
    }
}
