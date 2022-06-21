package Pages.MiniGames.Chess_R;

import Pages.MiniGames.Games;

import javafx.scene.layout.*;

public class Chess extends Games{
    public Chess(Pane page, BorderPane root, Double height, Double width) {
        super(page, root, height, width);
    }

    public void draw() {
        this.clear();
        transitionIn(page);

        drawGrid();
        page.getChildren().add(this.grid);
    }
}
