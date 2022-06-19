package Pages.MiniGames.Blackjack_R;

import Pages.MiniGames.Games;
import javafx.scene.layout.Pane;

public class Blackjack extends Games {
    public Blackjack(Pane page, Double height, Double width) {
        super(page, height, width);
    }

    public void draw() {
        this.clear();
        transitionIn(page);

        drawGrid();
        page.getChildren().add(this.grid);
    }
}