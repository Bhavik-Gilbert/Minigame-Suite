package Pages.MiniGames;

import Pages.Selection;
import Pages.MiniGames.Games;
import Pages.MiniGames.Checkers_R.Pieces;
import Pages.MiniGames.Checkers_R.Pieces.COLOUR;
import Tools.ImageReader;

import java.util.ArrayList;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

public class Checkers extends Games {
    private ArrayList<ArrayList<Pieces>> pieces;
    private StackPane board;

    public Checkers(Pane page, Double height, Double width) {
        super(page, height, width);
    }

    public void draw() {
        this.clear();
        transitionIn(page);

        this.grid = drawGrid();
        this.board = generateBoard();

        this.grid.addRow(1, this.board);

        page.getChildren().addAll(this.grid);
    }

    private StackPane generateBoard() {
        this.pieces = new ArrayList<ArrayList<Pieces>>();

        board = new StackPane();

        String separator = System.getProperty("file.separator");
        ImageView image = null;
        Label boardImage = new Label();
        while(image==null) {
            image = ImageReader.readImage("Resources" + separator + "Games" + separator + "Checkers" + separator  + "board.png", this.width/2.5, this.width/2.5);
        }
        boardImage.setGraphic(image);
        
        GridPane boardPieces = new GridPane();

        for(int i=0; i<8; i++) {
            ArrayList<Pieces> row = new ArrayList<Pieces>();

            for(int j=0; j<8; j++) {
                // add piece to array
                if(i<=2) {
                    row.add(createPiece(Pieces.COLOUR.WHITE, i, j));
                    continue;
                }

                if(i>=5) {
                    row.add(createPiece(Pieces.COLOUR.BLACK, i, j));
                    continue;
                }

                row.add(null);
            }

            this.pieces.add(row);
        }

        board.getChildren().addAll(boardImage, boardPieces);

        return board;
    }

    private Pieces createPiece(COLOUR colour, int column, int row) {
        if ((column % 2 == 0 && row % 2 == 0) || (column % 2 == 1 && row % 2 == 1)) {
            return new Pieces(colour);
        }

        return null;
    }
}
