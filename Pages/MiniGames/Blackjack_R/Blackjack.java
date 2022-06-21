package Pages.MiniGames.Blackjack_R;

import java.util.*;

import Pages.MiniGames.Games;
import javafx.geometry.Pos;
import javafx.scene.layout.*;

public class Blackjack extends Games {
    private StackPane table;

    private ArrayList<Player> players;
    private int turn;

    public enum CARDS {
        ACE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, JACK, QUEEN, KING
    }
    
    private static final Map<CARDS, Integer> values = Map.ofEntries(
            Map.entry(CARDS.ACE, 1),
            Map.entry(CARDS.TWO, 2),
            Map.entry(CARDS.THREE, 3),
            Map.entry(CARDS.FOUR, 4),
            Map.entry(CARDS.FIVE, 5),
            Map.entry(CARDS.SIX, 6),
            Map.entry(CARDS.SEVEN, 7),
            Map.entry(CARDS.EIGHT, 8),
            Map.entry(CARDS.NINE, 9),
            Map.entry(CARDS.TEN, 10),
            Map.entry(CARDS.JACK, 11),
            Map.entry(CARDS.QUEEN, 11),
            Map.entry(CARDS.KING, 11)
    );

    public Blackjack(Pane page, BorderPane root, Double height, Double width) {
        super(page, root, height, width);
    }

    public void draw() {
        this.clear();
        transitionIn(page);

        if (this.playerCount == null) {
            page.getChildren().add(playerNumber(4));
            return;
        }

        this.grid = drawGrid();
        this.table = generateTable();

        this.grid.addRow(1, this.table, displayInformation());

        drawGrid();
        page.getChildren().add(this.grid);
    }

    private StackPane generateTable() {
        StackPane table = new StackPane();
        table.setAlignment(Pos.CENTER);

        buildTable();
        displayTable(this.players);

        return table;
    }

    private void buildTable() {
        this.players = getPlayers();
        this.turn = 0;
    }

    private ArrayList<Player> getPlayers() {
        ArrayList<Player> players = new ArrayList<>();
        for (int i = 1; i <= this.playerCount; i++) {
            ArrayList<CARDS> hand =  new ArrayList<CARDS>();
            hand.add(getCard());
            hand.add(getCard());

            int score = 0;
            for(CARDS card: hand) {
                score += getValue(card);
            }

            Player player = new Player(i, hand, score);
            players.add(player);
        }
        return players;
    }

    private GridPane displayTable(ArrayList<Player> players) {
        GridPane grid = new GridPane();

        return grid;
    }

    private VBox displayInformation() {
        VBox information = new VBox();
        information.setAlignment(Pos.CENTER);

        //hit or stand
        return information;
    }
    
    private int getValue(CARDS card) {
        return values.get(card);
    }

    private CARDS getCard() {
        while (true) {
            int card = (int) (Math.random() * 13);
            switch (card) {
                case 0:
                    return CARDS.ACE;
                case 1:
                    return CARDS.TWO;
                case 2:
                    return CARDS.THREE;
                case 3:
                    return CARDS.FOUR;
                case 4:
                    return CARDS.FIVE;
                case 5:
                    return CARDS.SIX;
                case 6:
                    return CARDS.SEVEN;
                case 7:
                    return CARDS.EIGHT;
                case 8:
                    return CARDS.NINE;
                case 9:
                    return CARDS.TEN;
                case 10:
                    return CARDS.JACK;
                case 11:
                    return CARDS.QUEEN;
                case 12:
                    return CARDS.KING;
            }
        }
    }
}
