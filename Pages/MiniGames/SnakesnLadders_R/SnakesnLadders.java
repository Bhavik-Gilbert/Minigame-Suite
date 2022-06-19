package Pages.MiniGames.SnakesnLadders_R;

import Pages.MiniGames.Games;

import javafx.scene.layout.*;

public class SnakesnLadders extends Games {
    public SnakesnLadders(Pane page, Double height, Double width) {
        super(page, height, width);
    }

    public void draw() {
        this.clear();
        transitionIn(page);

        drawGrid();
        page.getChildren().add(this.grid);
    }
}